package com.wjwong93.polystore.dbExecutor;

import com.wjwong93.polystore.query.KeyValueQuery;

import java.sql.Connection;

public interface KeyValueDBExecutor extends DBExecutor {
    void executeCreateQuery(KeyValueQuery query);
    void executeReadQuery(KeyValueQuery query, Connection connection);
    void executeUpdateQuery(KeyValueQuery query);
    void executeDeleteQuery(KeyValueQuery query);
}
