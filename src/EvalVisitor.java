import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EvalVisitor extends PostgreSQLParserBaseVisitor<Integer> {
    private String sourceString;
    @Override
    public Integer visitGraph_table(PostgreSQLParser.Graph_tableContext ctx) {
        int startIndex = ctx.MATCH().getSymbol().getStartIndex();
        int stopIndex = ctx.graph_pattern().getStop().getStopIndex();
        System.out.println("Start index: " + startIndex);
        System.out.println("Stop index: " + stopIndex);

        System.out.println(sourceString.substring(startIndex, stopIndex+1).replaceAll("\\t+", " "));

        return visitChildren(ctx);
    }

    @Override
    public Integer visitPath_pattern(PostgreSQLParser.Path_patternContext ctx) {
        return visitChildren(ctx);
    }

    public void setSourceFile(String filename) throws Exception {
        sourceString = Files.readString(Paths.get(filename));
    }
}
