import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.io.FileInputStream;
import java.util.List;
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

    public Stream<Record> executeQuery(String query) {
        var result = session.executeRead(tx -> {
            var res = tx.run(query);
            List<Record> recordList = res.list();
            System.out.println("Number of results: " + recordList.size());
            for (var key : recordList.get(0).fields()) {
                System.out.println(key.key() + " " + key.value().toString());
            }
            return res.stream();
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
            String graphQuery = SQLParser.extractCypherQuery(inputStream, inputFile);
            Stream<Record> resultStream = executor.executeQuery(graphQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
