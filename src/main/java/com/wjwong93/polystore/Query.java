package com.wjwong93.polystore;

import java.sql.Connection;

public abstract class Query {
    String query;

    public Query(String query) {
        this.query = query;
    }

    abstract void execute();

    abstract void executeAndStore(Connection conn);

    @Override
    public String toString() {
        return query;
    }
}
