package com.wjwong93.polystore.query;

import java.sql.Connection;

public abstract class Query {
    String query;

    public Query(String query) {
        this.query = query;
    }

    abstract public void execute();

    abstract public void executeAndStore(Connection conn);

    @Override
    public String toString() {
        return query;
    }
}
