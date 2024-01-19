package com.wjwong93.polystore.query;

public class GraphUpdateQuery extends GraphQuery {
    public GraphUpdateQuery(String query, String tableId) {
        super(QueryType.UPDATE, query, tableId);
    }
}
