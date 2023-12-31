public class GraphReadQuery extends GraphQuery {
    public GraphReadQuery(String query) {
        super(query);
    }

    @Override
    void execute() {
        try (GraphDBExecutor graphDBExecutor = new GraphDBExecutor("neo4j://localhost:7687", "neo4j", "password")) {
            graphDBExecutor.executeReadQuery(query);
        }
    }

    public static void main(String[] args) {
        String testQuery = "MATCH (n:TestNode) RETURN n;";
        GraphReadQuery graphReadQuery = new GraphReadQuery(testQuery);
        graphReadQuery.execute();
    }
}
