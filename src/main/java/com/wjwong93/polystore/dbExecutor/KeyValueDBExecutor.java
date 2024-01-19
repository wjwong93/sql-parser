package com.wjwong93.polystore.dbExecutor;

import com.wjwong93.polystore.query.KeyValueQuery;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public interface KeyValueDBExecutor extends DBExecutor {
    public void executeCreateQuery(KeyValueQuery query);
    public void executeReadQuery(KeyValueQuery query, Connection connection);
    public void executeUpdateQuery(KeyValueQuery query);
    public void executeDeleteQuery(KeyValueQuery query);
}
