import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SQLParserListener extends PostgreSQLParserBaseListener{
    private String sourceString;
    private List<String> graphTableColumns;

    @Override
    public void enterGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        System.out.print("MATCH ");
    }

    @Override
    public void exitGraph_pattern(PostgreSQLParser.Graph_patternContext ctx) {
        System.out.println();

        int startIndex = ctx.where_clause().getStart().getStartIndex();
        int stopIndex = ctx.getStop().getStopIndex();

        System.out.println(sourceString.substring(startIndex, stopIndex+1).replaceAll("\\t+", " "));
    }

    @Override
    public void enterVertex_pattern(PostgreSQLParser.Vertex_patternContext ctx) {
        System.out.print("(");
    }
    @Override
    public void exitVertex_pattern(PostgreSQLParser.Vertex_patternContext ctx) {
        System.out.print(")");
    }
    @Override
    public void enterFull_edge_pattern(PostgreSQLParser.Full_edge_patternContext ctx) {
        System.out.print(ctx.getStart().getText());
    }
    @Override
    public void exitFull_edge_pattern(PostgreSQLParser.Full_edge_patternContext ctx) {
        System.out.print(ctx.getStop().getText());
    }

    @Override
    public void enterElement_pattern_filler(PostgreSQLParser.Element_pattern_fillerContext ctx) {
        System.out.print(ctx.identifier(0).Identifier().getText());
        if (ctx.IS() != null) {
            System.out.print(":");
            System.out.print(ctx.identifier(1).Identifier().getText());
        }
    }

    @Override
    public void enterGraph_table_columns_clause(PostgreSQLParser.Graph_table_columns_clauseContext ctx) {
        graphTableColumns = new ArrayList<>();
        System.out.print("RETURN ");
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
        System.out.println(String.join(", ", graphTableColumns));
    }

    public void setSourceString(String filepath) throws Exception {
        sourceString = Files.readString(Path.of(filepath));
    }
}
