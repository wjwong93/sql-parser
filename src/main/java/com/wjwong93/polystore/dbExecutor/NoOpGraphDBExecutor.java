package com.wjwong93.polystore.dbExecutor;

import com.wjwong93.polystore.query.GraphQuery;

import java.sql.Connection;

public class NoOpGraphDBExecutor implements GraphDBExecutor {
    @Override
    public void executeCreateQuery(GraphQuery query) {

    }

    @Override
    public void executeReadQuery(GraphQuery query, Connection connection) {

    }

    @Override
    public void executeUpdateQuery(GraphQuery query) {

    }

    @Override
    public void executeDeleteQuery(GraphQuery query) {

    }
}
