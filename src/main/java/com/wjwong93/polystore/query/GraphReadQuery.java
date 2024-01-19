package com.wjwong93.polystore.query;

public class GraphReadQuery extends GraphQuery {
    public GraphReadQuery(String query, String tableId) {
        super(QueryType.READ, query, tableId);
    }
}
