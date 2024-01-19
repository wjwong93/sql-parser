package com.wjwong93.polystore.query;

import com.wjwong93.polystore.GraphDBExecutor;

public class GraphUpdateQuery extends GraphQuery {
    public GraphUpdateQuery(String query, String tableId) {
        super(QueryType.UPDATE, query, tableId);
    }

    @Override
    public void execute() {
        try (GraphDBExecutor executor = new GraphDBExecutor("neo4j://localhost:7687", "neo4j", "password")) {
            executor.executeWriteQuery(this.toString());
        }
    }
}
