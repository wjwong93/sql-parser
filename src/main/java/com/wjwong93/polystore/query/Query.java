package com.wjwong93.polystore.query;

public abstract class Query implements QueryInterface {
    final private String tableId;
    final private String query;
    final private QueryType type;

    public Query(QueryType queryType, String query, String tableId) {
        this.type = queryType;
        this.query = query;
        this.tableId = tableId;
    }

    public String getTableId() {
        return tableId;
    }

    public QueryType getType() { return type; }

    public String getQuery() {
        return query;
    }
}
