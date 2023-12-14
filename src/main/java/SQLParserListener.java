import org.antlr.v4.runtime.ParserRuleContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SQLParserListener extends PostgreSQLParserBaseListener{
    private String sourceString;
    final private StringBuilder result = new StringBuilder();
    private List<String> graphTableColumns;


    @Override
    public void enterWhere_clause(PostgreSQLParser.Where_clauseContext ctx) {}
    @Override
    public void enterGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        result.append(ctx.MATCH().getText()).append(" ");
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
    public void enterVertex_pattern(PostgreSQLParser.Vertex_patternContext ctx) {
        result.append(ctx.OPEN_PAREN().getText());
    }
    @Override
    public void exitVertex_pattern(PostgreSQLParser.Vertex_patternContext ctx) {
        result.append(ctx.CLOSE_PAREN().getText());
    }
    @Override
    public void enterEdge_pattern(PostgreSQLParser.Edge_patternContext ctx) {
        if (ctx.full_edge_pattern() != null)
            result.append(ctx.full_edge_pattern().getStart().getText());
        else if (ctx.abbreviated_edge_pattern() != null)
            result.append(ctx.abbreviated_edge_pattern().getText());
    }
    @Override
    public void exitEdge_pattern(PostgreSQLParser.Edge_patternContext ctx) {
        if (ctx.full_edge_pattern() != null)
            result.append(ctx.full_edge_pattern().getStop().getText());
    }

    @Override
    public void enterElement_pattern_filler(PostgreSQLParser.Element_pattern_fillerContext ctx) {
        if (ctx.identifier(0) != null) {
            result.append(ctx.identifier(0).Identifier().getText());
            if (ctx.IS() != null) {
                result.append(":");
                result.append(ctx.identifier(1).Identifier().getText());
            }
        }
    }

    @Override
    public void enterQuantified_path_primary(PostgreSQLParser.Quantified_path_primaryContext ctx) {
        result.append(ctx.full_edge_pattern().getStart().getText());
    }
    @Override
    public void exitQuantified_path_primary(PostgreSQLParser.Quantified_path_primaryContext ctx) {
        result.append(ctx.full_edge_pattern().getStop().getText());
    }
    @Override
    public void enterGraph_pattern_quantifier(PostgreSQLParser.Graph_pattern_quantifierContext ctx) {
        if (ctx.STAR() != null) {
            result.append("*0..");
        } else if (ctx.PLUS() != null) {
            result.append("*");
        } else if (ctx.COMMA() != null) {
            // General Quantifier
            result
                .append("*")
                .append(ctx.Integral(0).getText())
                .append("..")
                .append(ctx.Integral(1).getText());

        } else {
            // Fixed Quantifier
            result.append("*").append(ctx.Integral(0).getText());
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
        columnDefinition.append(ctx.identifier(0).Identifier().getText());

        if (ctx.DOT() != null) {
            columnDefinition.append(".");
            columnDefinition.append(ctx.identifier(1).Identifier().getText());
        }

        if (ctx.AS() != null) {
            columnDefinition.append(" ").append(ctx.AS().getText()).append(" ");
            columnDefinition.append(ctx.identifier(2).Identifier().getText());
        }

        graphTableColumns.add(columnDefinition.toString());
    }

    @Override
    public void exitGraph_table_columns_clause(PostgreSQLParser.Graph_table_columns_clauseContext ctx) {
        result.append(String.join(", ", graphTableColumns)).append("\n");
    }

    public void setSourceString(String filepath) throws Exception {
        sourceString = Files.readString(Path.of(filepath));
    }

    public String getResult() {
        return result.toString().strip();
    }
}
