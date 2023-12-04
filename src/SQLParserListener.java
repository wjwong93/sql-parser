import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SQLParserListener extends PostgreSQLParserBaseListener{
    private String sourceString;
    private List<String> graphTableColumns;

    @Override
    public void enterGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        int startIndex = ctx.MATCH().getSymbol().getStartIndex();
        int stopIndex = ctx.graph_pattern().getStop().getStopIndex();

        System.out.println(sourceString.substring(startIndex, stopIndex+1).replaceAll("\\t+", " "));
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
