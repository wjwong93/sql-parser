package com.wjwong93.polystore.query;

import com.wjwong93.polystore.dbExecutor.DBExecutor;

import java.sql.Connection;

public abstract class Query implements QueryInterface {
    final private String tableId;
    final private String query;
    final private QueryType type;

    public Query(QueryType queryType, String query) {
        this.tableId = null;
        this.type = queryType;
        this.query = query;
    }

    public Query(QueryType queryType, String query, String tableId) {
        this.type = queryType;
        this.query = query;
        this.tableId = tableId;
    }

    abstract public void execute();

    abstract public void executeAndStore(Connection conn);

    public String getTableId() {
        return tableId;
    }
    public QueryType getType() { return type; }

    @Override
    public String toString() {
        return query;
    }
}
