import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class SQLParser {
    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if (args.length > 0) inputFile = args[0];
        InputStream is = System.in;
        if (inputFile != null) is = new FileInputStream(inputFile);
        CharStream input = CharStreams.fromStream(is);
        PostgreSQLLexer lexer = new PostgreSQLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PostgreSQLParser parser = new PostgreSQLParser(tokens);
        ParseTree tree = parser.root();

// Visitor method
//        EvalVisitor eval = new EvalVisitor();
//        eval.setSourceFile(inputFile);
//        eval.visit(tree);

//    Listener method
        ParseTreeWalker walker = new ParseTreeWalker();
        SQLParserListener cypherExtractor = new SQLParserListener();
        cypherExtractor.setSourceString(inputFile);
        walker.walk(cypherExtractor, tree);

    }
}
