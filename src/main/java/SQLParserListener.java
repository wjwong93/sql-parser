import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SQLParserListener extends PostgreSQLParserBaseListener{
    private String sourceString;
    private StringBuilder result = new StringBuilder();
    private List<String> graphTableColumns;

    @Override
    public void enterGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        result.append("MATCH ");
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
        result.append("(");
    }
    @Override
    public void exitVertex_pattern(PostgreSQLParser.Vertex_patternContext ctx) {
        result.append(")");
    }
    @Override
    public void enterFull_edge_pattern(PostgreSQLParser.Full_edge_patternContext ctx) {
        result.append(ctx.getStart().getText());
    }
    @Override
    public void exitFull_edge_pattern(PostgreSQLParser.Full_edge_patternContext ctx) {
        result.append(ctx.getStop().getText());
    }

    @Override
    public void enterElement_pattern_filler(PostgreSQLParser.Element_pattern_fillerContext ctx) {
        result.append(ctx.identifier(0).Identifier().getText());
        if (ctx.IS() != null) {
            result.append(":");
            result.append(ctx.identifier(1).Identifier().getText());
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
        columnDefinition.append(".");
        columnDefinition.append(ctx.identifier(1).Identifier().getText()).append(" ");

        if (ctx.AS() != null) {
            columnDefinition.append(ctx.AS().getText()).append(" ");
            columnDefinition.append(ctx.identifier(2).Identifier().getText());
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
        return result.toString();
    }
}
