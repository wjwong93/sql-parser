package com.wjwong93.polystore;

import com.wjwong93.polystore.dbExecutor.Executor;
import com.wjwong93.polystore.factory.QueryFactory;
import com.wjwong93.polystore.parser.SQLParser;
import com.wjwong93.polystore.query.Query;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please specify input query file.");
            return;
        }

        String inputFile = args[0];
        try (
                FileInputStream inputStream = new FileInputStream(inputFile);
                Executor executor = new Executor()
        ) {
            List<Query> queryPlan = SQLParser.parse(inputStream, new QueryFactory());

            executor.setKeyValueDBExecutor(new LevelDBExecutor("./leveldb"));

            executor.setGraphDBExecutor(new Neo4jExecutor("neo4j://localhost:7687", "neo4j", "password"));

//            executor.setKeyValueDBExecutor(new RiakExecutor("localhost", 8087, "keyvalue"));

//            executor.setGraphDBExecutor(new MemgraphExecutor("bolt://localhost:7687", "", ""));

//            ArcadeDBExecutor arcadeDB = new ArcadeDBExecutor("localhost", 2480, "root", "password");
//            arcadeDB.setKeyValueDatabase("kv");
//            arcadeDB.setGraphDatabase("graph");
//            executor.setKeyValueDBExecutor(arcadeDB);
//            executor.setGraphDBExecutor(arcadeDB);

            executor.executeQueryPlan(queryPlan);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
