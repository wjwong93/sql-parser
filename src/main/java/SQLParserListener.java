import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class SQLParserListener extends PostgreSQLParserBaseListener{
    private String sourceString;
    private TokenStream tokenStream;
    private List<String> pathPatterns;
    private List<String> repeatedTokens;
    private StringBuilder queryStringBuilder;
    final private List<Query> queryList = new ArrayList<>();

    // used to assign tableId to read queries
    private int readTableCount = 0;

    // Store token text
    final private Map<Integer, String> tokenText = new HashMap<>();
    private Map<Interval, String> replaceTableIntervals;

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
            queryList.add(new RelationalReadQuery(query.toString(), "t"+readTableCount++));
        }
        replaceTableIntervals = null;
    }
    @Override
    public void enterGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        queryStringBuilder = new StringBuilder();
        queryStringBuilder.append("USE ").append(ctx.graph_reference().getText()).append("\n");
    }

    @Override
    public void exitGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        String tableId = "t" + readTableCount++;
        queryStringBuilder.append(";");
        queryList.add(new GraphReadQuery(queryStringBuilder.toString(), tableId));
        queryStringBuilder = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), tableId);
    }

    @Override
    public void exitGraph_pattern(PostgreSQLParser.Graph_patternContext ctx) {
        queryStringBuilder.append("\n");

        if (ctx.where_clause().WHERE() != null) {
            int startIndex = ctx.where_clause().getStart().getStartIndex();
            int stopIndex = ctx.getStop().getStopIndex();

            queryStringBuilder.append(sourceString.substring(startIndex, stopIndex + 1).replaceAll("\\s+", " "));
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
        pathPatterns.add("");
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
    public void enterElement_pattern_filler(PostgreSQLParser.Element_pattern_fillerContext ctx) {
        int n = pathPatterns.size();
        int identifier_i = 0;
        StringBuilder res = new StringBuilder();

        if (ctx.identifier().size() == 2) {
            res.append(ctx.identifier(identifier_i).getText().replace("\"", ""));
            identifier_i++;
        }

        if (ctx.IS() != null) {
            res.append(":");
        }

        if (!ctx.identifier().isEmpty()) {
            res.append(ctx.identifier(identifier_i).getText().replace("\"", ""));
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
    public void enterGraph_table_columns_clause(PostgreSQLParser.Graph_table_columns_clauseContext ctx) {
        repeatedTokens = new ArrayList<>();
        queryStringBuilder.append("RETURN ");
    }

    @Override
    public void enterGraph_table_column_definition(PostgreSQLParser.Graph_table_column_definitionContext ctx) {
        StringBuilder columnDefinition = new StringBuilder();
        int identifier_i = 0;

        columnDefinition.append(ctx.identifier(identifier_i++).getText().replace("\"", ""));

        if (ctx.DOT() != null) {
            columnDefinition
                .append(".")
                .append(ctx.identifier(identifier_i++).getText().replace("\"", ""));
        }

        if (ctx.AS() != null) {
            columnDefinition
                .append(" ").append(ctx.AS().getText()).append(" ")
                .append(ctx.identifier(identifier_i).getText().replace("\"", ""));
        }

        repeatedTokens.add(columnDefinition.toString());
    }

    @Override
    public void exitGraph_table_columns_clause(PostgreSQLParser.Graph_table_columns_clauseContext ctx) {
        queryStringBuilder.append(String.join(", ", repeatedTokens)).append("\n");
        repeatedTokens = null;
    }

    @Override
    public void enterUpdategraphstmt(PostgreSQLParser.UpdategraphstmtContext ctx) {
        queryStringBuilder = new StringBuilder();
        queryStringBuilder.append("USE ").append(ctx.graph_reference().getText()).append("\n");
    }

    @Override
    public void exitUpdategraphstmt(PostgreSQLParser.UpdategraphstmtContext ctx) {
        queryStringBuilder.append(";");
        queryList.add(new GraphWriteQuery(queryStringBuilder.toString()));
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
        res.append(ctx.identifier(identifier_i++).getText().replace("\"", ""));
        if (ctx.DOT() != null) {
            res
                .append(ctx.DOT().getText())
                .append(ctx.identifier(identifier_i++).getText().replace("\"", ""))
                .append(" ").append(ctx.EQUAL().getText()).append(" ")
                .append(ctx.identifier(identifier_i).getText());
        } else if (ctx.IS() != null) {
            res
                .append(":")
                .append(ctx.identifier(identifier_i).getText().replace("\"", ""));
        }
        repeatedTokens.add(res.toString());
    }

    @Override
    public void enterGraph_delete_clause(PostgreSQLParser.Graph_delete_clauseContext ctx) {
        if (ctx.DETACH() != null) queryStringBuilder.append(ctx.DETACH().getText()).append(" ");
        queryStringBuilder.append(ctx.DELETE_P().getText()).append(" ");
        queryStringBuilder.append(
            ctx.identifier().stream().map(identifierContext -> identifierContext.getText()).collect(Collectors.joining(", "))
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
        res.append(ctx.identifier(0).getText().replace("\"", ""));
        if (ctx.DOT() != null) res.append(".");
        else if (ctx.IS() != null) res.append(":");
        res.append(ctx.identifier(1).getText().replace("\"", ""));
        repeatedTokens.add(res.toString());
    }

    @Override
    public void enterKvs_table(PostgreSQLParser.Kvs_tableContext ctx) {
        if (!ctx.identifier().isEmpty()) {
            repeatedTokens = new ArrayList<>();
            for (var identifier : ctx.identifier()) {
                repeatedTokens.add(identifier.getText().replaceAll("\"", ""));
            }
        }
    }

    @Override
    public void exitKvs_table(PostgreSQLParser.Kvs_tableContext ctx) {
        String tableId = "t" + readTableCount++;
        queryList.add(new KVGetQuery(repeatedTokens, tableId));
        repeatedTokens = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), tableId);
    }

    @Override
    public void enterInsert_kvs(PostgreSQLParser.Insert_kvsContext ctx) {
        repeatedTokens = new ArrayList<>();
    }

    @Override
    public void exitInsert_kvs(PostgreSQLParser.Insert_kvsContext ctx) {
        List<String[]> res = new ArrayList<>();
        for (int i=0; i<repeatedTokens.size(); i+=2) {
            res.add(new String[] {repeatedTokens.get(i), repeatedTokens.get(i+1)});
        }
        queryList.add(new KVPutQuery(res));
        repeatedTokens = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), null);
    }

    @Override
    public void enterKv_pair(PostgreSQLParser.Kv_pairContext ctx) {
        repeatedTokens.add(ctx.identifier(0).getText());
        repeatedTokens.add(ctx.identifier(1).getText());
    }

    @Override
    public void exitUpdatekvsstmt(PostgreSQLParser.UpdatekvsstmtContext ctx) {
        List<String[]> res = new ArrayList<>();
        String newVal = ctx.identifier(0).getText();
        for (var identifier : ctx.identifier().subList(1, ctx.identifier().size())) {
            res.add(new String[] {identifier.getText(), newVal});
        }
        queryList.add(new KVPutQuery(res));
        replaceTableIntervals.put(ctx.getSourceInterval(), null);
    }
    @Override
    public void enterDeletekvsstmt(PostgreSQLParser.DeletekvsstmtContext ctx) {
        if (!ctx.identifier().isEmpty()) {
            repeatedTokens = new ArrayList<>();
            for (var identifier : ctx.identifier()) {
                repeatedTokens.add(identifier.getText().replaceAll("\"", ""));
            }
        }
    }
    @Override
    public void exitDeletekvsstmt(PostgreSQLParser.DeletekvsstmtContext ctx) {
        queryList.add(new KVDeleteQuery(repeatedTokens));
        repeatedTokens = null;
        replaceTableIntervals.put(ctx.getSourceInterval(), null);
    }
    public void setSourceString(String filepath) throws Exception {
        sourceString = Files.readString(Path.of(filepath));
    }

    public void setTokenStream(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }

    public String getResult() {
        if (!queryList.isEmpty())
            return queryList.get(0).toString().strip();
        else
            return "";
    }

    public List<Query> getQueryList() {
        return queryList;
    }
}
