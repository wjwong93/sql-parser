package com.wjwong93.polystore;

import java.sql.Connection;

public abstract class GraphQuery extends Query {
    public GraphQuery(String query) {
        super(query);
    }

    @Override
    void executeAndStore(Connection conn) {
        execute();
    }
}

