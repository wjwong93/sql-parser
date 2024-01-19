package com.wjwong93.polystore.query;

import java.sql.Connection;

public abstract class GraphQuery extends Query {
    public GraphQuery(QueryType queryType, String query) {
        super(queryType, query);
    }

    @Override
    public void executeAndStore(Connection conn) {
        execute();
    }
}

