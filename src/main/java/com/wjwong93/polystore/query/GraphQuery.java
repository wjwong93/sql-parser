package com.wjwong93.polystore.query;

public abstract class GraphQuery extends Query {
    public GraphQuery(QueryType queryType, String query, String tableId) {
        super(queryType, query, tableId);
    }
}

