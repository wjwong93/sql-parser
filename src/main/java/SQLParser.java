import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SQLParser {
    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if (args.length > 0) inputFile = args[0];
        InputStream is = System.in;
        if (inputFile != null) is = new FileInputStream(inputFile);

        System.out.println(extractCypherQuery(is, inputFile));

    }

    public static String extractCypherQuery(InputStream inputStream, String inputFile) throws Exception {
        CharStream input = CharStreams.fromStream(inputStream);
        PostgreSQLLexer lexer = new PostgreSQLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PostgreSQLParser parser = new PostgreSQLParser(tokens);
        ParseTree tree = parser.root();

        ParseTreeWalker walker = new ParseTreeWalker();
        SQLParserListener cypherExtractor = new SQLParserListener();
        cypherExtractor.setSourceString(inputFile);
        walker.walk(cypherExtractor, tree);
        return cypherExtractor.getResult();
    }
}
