package com.wjwong93.polystore;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphDBExecutor implements AutoCloseable {
    private final Driver driver;
    private final Session session;

    public GraphDBExecutor(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
        session = driver.session();
    }

    @Override
    public void close() throws RuntimeException {
        driver.close();
    }

    public List<Record> executeQuery(String query) {
        var result = session.executeRead(tx -> {
            var res = tx.run(query);
            List<Record> recordList = res.list();

            return recordList;
        });
        return result;
    }
    public void executeReadQuery(String query) {
        session.executeRead(tx -> {
            var res = tx.run(query);
            System.out.println("Number of results: " + res.list().size());
            return null;
        });
    }
    public void executeWriteQuery(String query) {
        session.executeWriteWithoutResult(tx -> {
            tx.run(query);
        });
    }

    public static void main(String[] args) {
        String inputFile = "src/test/resources/input/yasuda3.sql";
        try (
            var executor = new GraphDBExecutor("neo4j://localhost:7687", "neo4j", "password");
            FileInputStream inputStream = new FileInputStream(inputFile);
        ) {
            List<Query> queryList = SQLParser.parse(inputStream);
            List<Record> recordList = executor.executeQuery(queryList.get(0).toString());
            printRecordList(recordList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void printRecordList(List<Record> recordList) {
        System.out.println(String.join(", ", recordList.get(0).keys()));
        for (var record : recordList) {
            System.out.println(
                    record.values().stream()
                            .map(Value::asString)
                            .collect(Collectors.joining(", "))
            );
        }
    }
}
