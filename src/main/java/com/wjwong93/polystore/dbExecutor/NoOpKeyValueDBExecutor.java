package com.wjwong93.polystore.dbExecutor;

import com.wjwong93.polystore.query.KeyValueQuery;

import java.sql.Connection;

public class NoOpKeyValueDBExecutor implements KeyValueDBExecutor {
    @Override
    public void executeCreateQuery(KeyValueQuery query) {

    }

    @Override
    public void executeReadQuery(KeyValueQuery query, Connection connection) {

    }

    @Override
    public void executeUpdateQuery(KeyValueQuery query) {

    }

    @Override
    public void executeDeleteQuery(KeyValueQuery query) {

    }
}
