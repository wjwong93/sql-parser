import org.antlr.v4.runtime.ParserRuleContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SQLParserListener extends PostgreSQLParserBaseListener{
    private String sourceString;
    final private StringBuilder result = new StringBuilder();
    private List<String> graphTableColumns;
    private List<String> pathPatterns;

    @Override
    public void enterGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        result.append("USE ").append(ctx.graph_reference().getText()).append("\n");
        result.append(ctx.MATCH().getText()).append(" ");
    }

    @Override
    public void exitGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        result.append(";\n");
    }

    @Override
    public void exitGraph_pattern(PostgreSQLParser.Graph_patternContext ctx) {
        result.append("\n");

        int startIndex = ctx.where_clause().getStart().getStartIndex();
        int stopIndex = ctx.getStop().getStopIndex();

        result.append(sourceString.substring(startIndex, stopIndex+1).replaceAll("\\s+", " "));
        result.append("\n");
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
        graphTableColumns = new ArrayList<>();
        result.append("RETURN ");
    }

    @Override
    public void enterGraph_table_column_definition(PostgreSQLParser.Graph_table_column_definitionContext ctx) {
        StringBuilder columnDefinition = new StringBuilder();
        int identifier_i = 0;

        columnDefinition.append(ctx.identifier(identifier_i).getText().replace("\"", ""));
        identifier_i++;

        if (ctx.DOT() != null) {
            columnDefinition.append(".");
            columnDefinition.append(ctx.identifier(identifier_i).getText().replace("\"", ""));
            identifier_i++;
        }

        if (ctx.AS() != null) {
            columnDefinition.append(" ").append(ctx.AS().getText()).append(" ");
            columnDefinition.append(ctx.identifier(identifier_i).getText().replace("\"", ""));
        }

        graphTableColumns.add(columnDefinition.toString());
    }

    @Override
    public void exitGraph_table_columns_clause(PostgreSQLParser.Graph_table_columns_clauseContext ctx) {
        result.append(String.join(", ", graphTableColumns));
    }

    public void setSourceString(String filepath) throws Exception {
        sourceString = Files.readString(Path.of(filepath));
    }

    public String getResult() {
        return result.toString().strip();
    }
}
