package com.wjwong93.polystore.query;

public class OuterReadQuery extends Query {
    public OuterReadQuery(String query, String tableId) {
        super(QueryType.READ, query, tableId);
    }
}
