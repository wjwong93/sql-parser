package com.wjwong93.polystore.query;

import com.wjwong93.polystore.GraphDBExecutor;

public class GraphWriteQuery extends GraphQuery {
    public GraphWriteQuery(String query) {
        super(query);
    }

    @Override
    public void execute() {
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
