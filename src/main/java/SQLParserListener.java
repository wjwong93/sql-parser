import org.antlr.v4.runtime.ParserRuleContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SQLParserListener extends PostgreSQLParserBaseListener{
    private String sourceString;
    final private StringBuilder result = new StringBuilder();

    private List<String> pathPatterns;
    private List<String> repeatedTokens;

    @Override
    public void enterGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        result.append("USE ").append(ctx.graph_reference().getText()).append("\n");
    }

    @Override
    public void exitGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        result.append(";\n");
    }

    @Override
    public void exitGraph_pattern(PostgreSQLParser.Graph_patternContext ctx) {
        result.append("\n");

        if (ctx.where_clause().WHERE() != null) {
            int startIndex = ctx.where_clause().getStart().getStartIndex();
            int stopIndex = ctx.getStop().getStopIndex();

            result.append(sourceString.substring(startIndex, stopIndex + 1).replaceAll("\\s+", " "));
            result.append("\n");
        }
    }
    @Override
    public void enterPath_pattern_list(PostgreSQLParser.Path_pattern_listContext ctx) {
        pathPatterns = new ArrayList<>();
    }
    @Override
    public void exitPath_pattern_list(PostgreSQLParser.Path_pattern_listContext ctx) {
        result.append(String.join(", ", pathPatterns));
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
        result.append("RETURN ");
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
        result.append(String.join(", ", repeatedTokens)).append("\n");
        repeatedTokens = null;
    }

    @Override
    public void enterUpdategraphstmt(PostgreSQLParser.UpdategraphstmtContext ctx) {
        result.append("USE ").append(ctx.graph_reference().getText()).append("\n");
    }

    @Override
    public void exitUpdategraphstmt(PostgreSQLParser.UpdategraphstmtContext ctx) {
        result.append(";\n");
    }

    @Override
    public void enterGraph_match_clause(PostgreSQLParser.Graph_match_clauseContext ctx) {
        result.append(ctx.MATCH().getText()).append(" ");
    }

    @Override
    public void enterGraph_create_clause(PostgreSQLParser.Graph_create_clauseContext ctx) {
        if (ctx.MERGE() != null) result.append(ctx.MERGE().getText()).append(" ");
        else if (ctx.CREATE() != null) result.append(ctx.CREATE().getText()).append(" ");
    }

    @Override
    public void enterGraph_set_clause(PostgreSQLParser.Graph_set_clauseContext ctx) {
        result.append(ctx.SET().getText()).append(" ");
        repeatedTokens = new ArrayList<>();
    }

    @Override
    public void exitGraph_set_clause(PostgreSQLParser.Graph_set_clauseContext ctx) {
        result.append(String.join(", ", repeatedTokens)).append("\n");
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

    public void setSourceString(String filepath) throws Exception {
        sourceString = Files.readString(Path.of(filepath));
    }

    public String getResult() {
        return result.toString().strip();
    }
}
