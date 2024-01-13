import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SQLParser {
    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if (args.length > 0) inputFile = args[0];
        InputStream is = System.in;
        if (inputFile != null) is = new FileInputStream(inputFile);

        List<Query> queryList = parse(is, inputFile);
        for (Query query : queryList) {
            System.out.println(query.toString() + "\n");
        }

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
        cypherExtractor.setTokenStream(tokens);
        walker.walk(cypherExtractor, tree);
        return cypherExtractor.getResult();
    }

    public static List<Query> parse(InputStream inputStream, String inputFile) {
        try {
            CharStream input = CharStreams.fromStream(inputStream);
            PostgreSQLLexer lexer = new PostgreSQLLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            PostgreSQLParser parser = new PostgreSQLParser(tokens);
            parser.setErrorHandler(new ParserErrorStrategy());
            ParseTree tree = parser.root();

            ParseTreeWalker walker = new ParseTreeWalker();
            SQLParserListener extractor = new SQLParserListener();
            extractor.setSourceString(inputFile);
            extractor.setTokenStream(tokens);
            walker.walk(extractor, tree);

            return extractor.getQueryList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
