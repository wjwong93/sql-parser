package com.wjwong93.polystore.parser;

import com.wjwong93.polystore.factory.QueryFactory;
import com.wjwong93.polystore.query.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.stream.Collectors;

public class SQLParserListener extends PostgreSQLParserBaseListener {
    private final TokenStream tokenStream;
    private final QueryFactory queryFactory;
    private List<String> pathPatterns;
    private List<String> edgeVariables;
    private List<String> repeatedTokens;
    private StringBuilder queryStringBuilder;
    final private List<Query> queryList = new ArrayList<>();

    // Store token text
    final private Map<Integer, String> tokenText = new HashMap<>();
    private Map<Interval, String> replaceTableIntervals;

    private static class TableIdIssuer {
        private int tableCount = 0;
        private TableIdIssuer() {}

        String issueTableId() {
            return "t" + tableCount++;
        }
    }

    private final TableIdIssuer tableIdIssuer;

    public SQLParserListener(TokenStream tokenStream, QueryFactory queryFactory) {
        this.tokenStream = tokenStream;
        this.queryFactory = queryFactory;
        this.tableIdIssuer = new TableIdIssuer();
    }

    @Override
    public void visitTerminal(TerminalNode node) {
//        if (node.getSymbol().getType() == Token.EOF) {
//            for (var entry : tokenText.entrySet()) {
//                System.out.println("[" + entry.getKey() + "] " + entry.getValue());
//            }
//        } else {
//            Token token = node.getSymbol();
//            tokenText.put(token.getTokenIndex(), token.getText());
//        }
    }

    @Override
    public void enterStmt(PostgreSQLParser.StmtContext ctx) {
        replaceTableIntervals = new LinkedHashMap<>();
    }

    @Override
    public void exitStmt(PostgreSQLParser.StmtContext ctx) {
        StringBuilder query = new StringBuilder();

        Iterator<Map.Entry<Interval, String>> skipIntervalsIterator = replaceTableIntervals.entrySet().iterator();
        Map.Entry<Interval, String> currSkipInterval = null;
        if (skipIntervalsIterator.hasNext()) currSkipInterval = skipIntervalsIterator.next();

        int i = ctx.getSourceInterval().a;
        while (i <= ctx.getSourceInterval().b) {
            if (currSkipInterval != null && i == currSkipInterval.getKey().a) {
                i = currSkipInterval.getKey().b;
                String tableId = currSkipInterval.getValue();
                if (tableId != null) query.append(tableId);
                if (skipIntervalsIterator.hasNext()) currSkipInterval = skipIntervalsIterator.next();
            } else {
                query.append(tokenStream.get(i).getText());
            }
            i++;
        }
        if (!query.isEmpty()) {
            query.append("\n;");
            queryList.add(new OuterReadQuery(query.toString(), tableIdIssuer.issueTableId()));
        }
        replaceTableIntervals = null;
    }

    @Override
    public void enterGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        edgeVariables = new ArrayList<>();
        queryStringBuilder = new StringBuilder();
        if (!ctx.graph_reference().getText().isEmpty())
            queryStringBuilder.append("USE ").append(ctx.graph_reference().getText()).append("\n");
    }

    @Override
    public void exitGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        String tableId = tableIdIssuer.issueTableId();
        queryStringBuilder.append(";");
        queryList.add(queryFactory.createGraphQuery(QueryType.READ, tableId, queryStringBuilder.toString()));
        queryStringBuilder = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), tableId);
        edgeVariables = null;
    }

    @Override
    public void exitGraph_pattern(PostgreSQLParser.Graph_patternContext ctx) {
        queryStringBuilder.append("\n");

        if (ctx.where_clause().WHERE() != null) {
            int startIndex = ctx.where_clause().getSourceInterval().a;
            int stopIndex = ctx.where_clause().getSourceInterval().b;

            for (int i=startIndex; i<=stopIndex; i++) {
                queryStringBuilder.append(tokenStream.get(i).getText());
            }

            queryStringBuilder.append("\n");
        }
    }

    @Override
    public void enterPath_pattern_list(PostgreSQLParser.Path_pattern_listContext ctx) {
        pathPatterns = new ArrayList<>();
    }

    @Override
    public void exitPath_pattern_list(PostgreSQLParser.Path_pattern_listContext ctx) {
        queryStringBuilder.append(String.join(", ", pathPatterns));
    }

    @Override
    public void enterPath_pattern(PostgreSQLParser.Path_patternContext ctx) {
        String pathVariable = "";
        if (ctx.graph_element_identifier() != null) {
            pathVariable += ctx.graph_element_identifier().getText() + " " + ctx.EQUAL().getText() + " ";
        }
        if (ctx.any_shortest_path_search() != null) {
            pathVariable += "shortestPath(";
        }
        pathPatterns.add(pathVariable);
    }

    @Override
    public void exitPath_pattern(PostgreSQLParser.Path_patternContext ctx) {
        if (ctx.any_shortest_path_search() != null) {
            int n = pathPatterns.size();
            pathPatterns.set(n-1, pathPatterns.get(n-1) + ")");
        }
    }

    @Override
    public void enterVertex_pattern(PostgreSQLParser.Vertex_patternContext ctx) {
        int n = pathPatterns.size();
        pathPatterns.set(n-1, pathPatterns.get(n-1) + ctx.OPEN_PAREN().getText());
    }

    @Override
    public void exitVertex_pattern(PostgreSQLParser.Vertex_patternContext ctx) {
        int n = pathPatterns.size();
        pathPatterns.set(n-1, pathPatterns.get(n-1) + ctx.CLOSE_PAREN().getText());
    }

    @Override
    public void enterEdge_pattern(PostgreSQLParser.Edge_patternContext ctx) {
        int n = pathPatterns.size();
        if (ctx.full_edge_pattern() != null)
            pathPatterns.set(n-1, pathPatterns.get(n-1) + ctx.full_edge_pattern().getStart().getText());
        else if (ctx.abbreviated_edge_pattern() != null)
            pathPatterns.set(n-1, pathPatterns.get(n-1) + ctx.abbreviated_edge_pattern().getText());

    }

    @Override
    public void exitEdge_pattern(PostgreSQLParser.Edge_patternContext ctx) {
        int n = pathPatterns.size();
        if (ctx.full_edge_pattern() != null)
            pathPatterns.set(n-1, pathPatterns.get(n-1) + ctx.full_edge_pattern().getStop().getText());

    }

    @Override
    public void enterFull_edge_pattern(PostgreSQLParser.Full_edge_patternContext ctx) {
        PostgreSQLParser.Element_pattern_fillerContext elementPatternFillerContext = ctx.element_pattern_filler();
        if (elementPatternFillerContext.IS() != null
                && elementPatternFillerContext.graph_element_identifier().size() == 2
        ) {
            edgeVariables.add(elementPatternFillerContext.graph_element_identifier(0).getText());
        } else if (elementPatternFillerContext.IS() == null
                && elementPatternFillerContext.graph_element_identifier().size() == 1
        ) {
            edgeVariables.add(elementPatternFillerContext.graph_element_identifier(0).getText());
        }
    }

    @Override
    public void enterElement_pattern_filler(PostgreSQLParser.Element_pattern_fillerContext ctx) {
        int n = pathPatterns.size();
        int identifier_i = 0;
        StringBuilder res = new StringBuilder();

        if (ctx.graph_element_identifier().size() == 2) {
            res.append(ctx.graph_element_identifier(identifier_i).getText().replace("\"", ""));
            identifier_i++;
        }

        if (ctx.IS() != null) {
            res.append(":");
        }

        if (!ctx.graph_element_identifier().isEmpty()) {
            res.append(ctx.graph_element_identifier(identifier_i).getText().replace("\"", ""));
            pathPatterns.set(n-1, pathPatterns.get(n-1) + res);
        }
    }

    @Override
    public void enterQuantified_path_primary(PostgreSQLParser.Quantified_path_primaryContext ctx) {
        int n = pathPatterns.size();
        pathPatterns.set(n-1, pathPatterns.get(n-1) + ctx.full_edge_pattern().getStart().getText());
    }

    @Override
    public void exitQuantified_path_primary(PostgreSQLParser.Quantified_path_primaryContext ctx) {
        int n = pathPatterns.size();
        pathPatterns.set(n-1, pathPatterns.get(n-1) + ctx.full_edge_pattern().getStop().getText());
    }

    @Override
    public void enterGraph_pattern_quantifier(PostgreSQLParser.Graph_pattern_quantifierContext ctx) {
        int n = pathPatterns.size();
        if (ctx.STAR() != null) {
            pathPatterns.set(n-1, pathPatterns.get(n-1) + "*0..");
        } else if (ctx.PLUS() != null) {
            pathPatterns.set(n-1, pathPatterns.get(n-1) + "*");
        } else if (ctx.COMMA() != null) {
            // General Quantifier
            pathPatterns.set(n-1, pathPatterns.get(n-1) + "*" + ctx.Integral(0).getText() + ".." + ctx.Integral(1).getText());
        } else {
            // Fixed Quantifier
            pathPatterns.set(n-1, pathPatterns.get(n-1) + "*" + ctx.Integral(0).getText());
        }
    }

    @Override
    public void enterOne_row_per_step(PostgreSQLParser.One_row_per_stepContext ctx) {
        String vertex1 = ctx.graph_element_identifier(0).getText();
        String edge = ctx.graph_element_identifier(1).getText();
        String vertex2 = ctx.graph_element_identifier(2).getText();

        String unwindClause = String.format("UNWIND %s AS %s\n", edgeVariables.isEmpty() ? "[]" : String.join(" + ", edgeVariables), edge);
        String withClause = String.format("WITH *, startNode(%s) AS %s, endNode(%s) AS %s\n", edge, vertex1, edge ,vertex2);

        queryStringBuilder.append(unwindClause).append(withClause);
    }

    @Override
    public void enterGraph_table_columns_clause(PostgreSQLParser.Graph_table_columns_clauseContext ctx) {
        repeatedTokens = new ArrayList<>();
        queryStringBuilder.append("RETURN ");
    }

    @Override
    public void enterGraph_table_column_definition(PostgreSQLParser.Graph_table_column_definitionContext ctx) {
        repeatedTokens.add("");
    }

    @Override
    public void exitGraph_table_columns_clause(PostgreSQLParser.Graph_table_columns_clauseContext ctx) {
        queryStringBuilder.append(String.join(", ", repeatedTokens)).append("\n");
        repeatedTokens = null;
    }

    @Override
    public void exitGraph_table_column_definition(PostgreSQLParser.Graph_table_column_definitionContext ctx) {
        StringBuilder columnDefinition = new StringBuilder();
        int identifier_i = 0;

        if (ctx.graphical_value_expression_primary() == null) {
            columnDefinition.append(ctx.graph_element_identifier(identifier_i++).getText().replace("\"", ""));
        }

        if (ctx.DOT() != null) {
            columnDefinition
                .append(".")
                .append(ctx.graph_element_identifier(identifier_i++).getText().replace("\"", ""));
        }

        if (ctx.AS() != null) {
            columnDefinition
                .append(" ").append(ctx.AS().getText()).append(" ")
                .append(ctx.graph_element_identifier(identifier_i).getText().replace("\"", ""));
        }

        int n = repeatedTokens.size();
        repeatedTokens.set(n-1, repeatedTokens.get(n-1) + columnDefinition);
    }

    @Override
    public void enterElement_id_function(PostgreSQLParser.Element_id_functionContext ctx) {
        int n = repeatedTokens.size();
        repeatedTokens.set(n-1, "elementId(" + ctx.graph_element_identifier().getText() + ")");
    }

    @Override
    public void enterGraphical_path_length_function(PostgreSQLParser.Graphical_path_length_functionContext ctx) {
        int n = repeatedTokens.size();
        String functionName = "";
        if (ctx.PATH_LENGTH() != null) functionName = "length";
        else if (ctx.SIZE() != null) functionName = "size";
        repeatedTokens.set(n-1, functionName + "(" + ctx.graph_element_identifier().getText() + ")");
    }

    @Override
    public void enterUpdategraphstmt(PostgreSQLParser.UpdategraphstmtContext ctx) {
        edgeVariables = new ArrayList<>();
        queryStringBuilder = new StringBuilder();
        if (!ctx.graph_reference().getText().isEmpty())
            queryStringBuilder.append("USE ").append(ctx.graph_reference().getText()).append("\n");
    }

    @Override
    public void exitUpdategraphstmt(PostgreSQLParser.UpdategraphstmtContext ctx) {
        String tableId = tableIdIssuer.issueTableId();
        queryStringBuilder.append(";");
        queryList.add(queryFactory.createGraphQuery(QueryType.UPDATE, tableId, queryStringBuilder.toString()));
        queryStringBuilder = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), null);
    }

    @Override
    public void enterGraph_match_clause(PostgreSQLParser.Graph_match_clauseContext ctx) {
        queryStringBuilder.append(ctx.MATCH().getText()).append(" ");
    }

    @Override
    public void enterGraph_create_clause(PostgreSQLParser.Graph_create_clauseContext ctx) {
        if (ctx.MERGE() != null) queryStringBuilder.append(ctx.MERGE().getText()).append(" ");
        else if (ctx.CREATE() != null) queryStringBuilder.append(ctx.CREATE().getText()).append(" ");
    }

    @Override
    public void enterGraph_set_clause(PostgreSQLParser.Graph_set_clauseContext ctx) {
        queryStringBuilder.append(ctx.SET().getText()).append(" ");
        repeatedTokens = new ArrayList<>();
    }

    @Override
    public void exitGraph_set_clause(PostgreSQLParser.Graph_set_clauseContext ctx) {
        queryStringBuilder.append(String.join(", ", repeatedTokens)).append("\n");
        repeatedTokens = null;
    }

    @Override
    public void enterGraph_set_primary(PostgreSQLParser.Graph_set_primaryContext ctx) {
        int identifier_i = 0;
        StringBuilder res = new StringBuilder();
        res.append(ctx.graph_element_identifier(identifier_i++).getText().replace("\"", ""));
        if (ctx.DOT() != null) {
            res
                .append(ctx.DOT().getText())
                .append(ctx.graph_element_identifier(identifier_i++).getText().replace("\"", ""))
                .append(" ").append(ctx.EQUAL().getText()).append(" ")
                .append(ctx.graph_element_identifier(identifier_i).getText());
        } else if (ctx.IS() != null) {
            res
                .append(":")
                .append(ctx.graph_element_identifier(identifier_i).getText().replace("\"", ""));
        }
        repeatedTokens.add(res.toString());
    }

    @Override
    public void enterGraph_delete_clause(PostgreSQLParser.Graph_delete_clauseContext ctx) {
        if (ctx.DETACH() != null) queryStringBuilder.append(ctx.DETACH().getText()).append(" ");
        queryStringBuilder.append(ctx.DELETE_P().getText()).append(" ");
        queryStringBuilder.append(
            ctx.graph_element_identifier().stream().map(identifierContext -> identifierContext.getText()).collect(Collectors.joining(", "))
        ).append("\n");
    }

    @Override
    public void enterGraph_remove_clause(PostgreSQLParser.Graph_remove_clauseContext ctx) {
        queryStringBuilder.append(ctx.REMOVE().getText()).append(" ");
        repeatedTokens = new ArrayList<>();
    }

    @Override
    public void exitGraph_remove_clause(PostgreSQLParser.Graph_remove_clauseContext ctx) {
        queryStringBuilder.append(String.join(", ", repeatedTokens)).append("\n");
        repeatedTokens = null;
    }

    @Override
    public void enterProperty_or_node_label(PostgreSQLParser.Property_or_node_labelContext ctx) {
        StringBuilder res = new StringBuilder();
        res.append(ctx.graph_element_identifier(0).getText().replace("\"", ""));
        if (ctx.DOT() != null) res.append(".");
        else if (ctx.IS() != null) res.append(":");
        res.append(ctx.graph_element_identifier(1).getText().replace("\"", ""));
        repeatedTokens.add(res.toString());
    }

    @Override
    public void enterKvs_table(PostgreSQLParser.Kvs_tableContext ctx) {
        if (ctx.kvs_where_key_clause() != null) {
            repeatedTokens = new ArrayList<>();
        }
    }

    @Override
    public void exitKvs_table(PostgreSQLParser.Kvs_tableContext ctx) {
        String tableId = tableIdIssuer.issueTableId();
        queryList.add(queryFactory.createKeyValueQuery(
                QueryType.READ, tableId,
                repeatedTokens == null ? null : repeatedTokens.stream().map(token -> new String[] {token}).collect(Collectors.toList())
        ));
        repeatedTokens = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), tableId);
    }

    @Override
    public void enterKvs_where_key_clause(PostgreSQLParser.Kvs_where_key_clauseContext ctx) {
        for (var identifier : ctx.identifier()) {
            repeatedTokens.add(identifier.getText().replace("\"", ""));
        }
    }

    @Override
    public void enterInsert_kvs(PostgreSQLParser.Insert_kvsContext ctx) {
        repeatedTokens = new ArrayList<>();
    }

    @Override
    public void exitInsert_kvs(PostgreSQLParser.Insert_kvsContext ctx) {
        List<String[]> res = new ArrayList<>();
        String tableId = tableIdIssuer.issueTableId();
        for (int i=0; i<repeatedTokens.size(); i+=2) {
            res.add(new String[] {repeatedTokens.get(i), repeatedTokens.get(i+1)});
        }
        queryList.add(queryFactory.createKeyValueQuery(QueryType.CREATE, tableId, res));
        repeatedTokens = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), null);
    }

    @Override
    public void enterKv_pair(PostgreSQLParser.Kv_pairContext ctx) {
        repeatedTokens.add(ctx.identifier(0).getText().replace("\"", ""));
        repeatedTokens.add(ctx.identifier(1).getText().replace("\"", ""));
    }

    @Override
    public void enterUpdatekvsstmt(PostgreSQLParser.UpdatekvsstmtContext ctx) {
        repeatedTokens = new ArrayList<>();
    }
    @Override
    public void exitUpdatekvsstmt(PostgreSQLParser.UpdatekvsstmtContext ctx) {
        String newValue = ctx.identifier().getText().replace("\"", "");
        List<String[]> res = repeatedTokens.stream().map(key -> new String[] {key, newValue}).toList();
        String tableId = tableIdIssuer.issueTableId();

        queryList.add(queryFactory.createKeyValueQuery(QueryType.UPDATE, tableId, res));
        repeatedTokens = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), null);
    }

    @Override
    public void enterDeletekvsstmt(PostgreSQLParser.DeletekvsstmtContext ctx) {
        if (ctx.kvs_where_key_clause() != null) {
            repeatedTokens = new ArrayList<>();
        }
    }
    @Override
    public void exitDeletekvsstmt(PostgreSQLParser.DeletekvsstmtContext ctx) {
        String tableId = tableIdIssuer.issueTableId();
        queryList.add(queryFactory.createKeyValueQuery(
                QueryType.DELETE, tableId,
                repeatedTokens == null ? null : repeatedTokens.stream().map(token -> new String[] {token}).collect(Collectors.toList())
            ));
        repeatedTokens = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), null);
    }

    public List<Query> getQueryList() {
        return queryList;
    }
}
