package com.wjwong93.polystore;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SQLParser {
    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if (args.length > 0) inputFile = args[0];
        InputStream is = System.in;
        if (inputFile != null) is = new FileInputStream(inputFile);

        List<Query> queryList = parse(is);
        for (Query query : queryList) {
            System.out.println(query.toString() + "\n");
        }

    }

    public static List<Query> parse(InputStream inputStream) {
        try {
            CharStream input = CharStreams.fromStream(inputStream);
            PostgreSQLLexer lexer = new PostgreSQLLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            PostgreSQLParser parser = new PostgreSQLParser(tokens);
            parser.setErrorHandler(new ParserErrorStrategy());
            ParseTree tree = parser.root();

            ParseTreeWalker walker = new ParseTreeWalker();
            SQLParserListener extractor = new SQLParserListener(tokens);
            walker.walk(extractor, tree);

            return extractor.getQueryList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
