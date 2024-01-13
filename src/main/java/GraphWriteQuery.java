import java.sql.Connection;

public class GraphWriteQuery extends GraphQuery {
    public GraphWriteQuery(String query) {
        super(query);
    }

    @Override
    void execute() {
        try (GraphDBExecutor executor = new GraphDBExecutor("neo4j://localhost:7687", "neo4j", "password")) {
            executor.executeWriteQuery(query);
        }
    }

    public static void main(String[] args) {
        String testQuery = "CREATE (n) SET n:TestNode;";
        GraphWriteQuery graphWriteQuery = new GraphWriteQuery(testQuery);
        graphWriteQuery.execute();
    }
}
