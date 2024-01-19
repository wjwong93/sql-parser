package com.wjwong93.polystore.dbExecutor;

import com.wjwong93.polystore.query.GraphQuery;

import java.sql.Connection;

public interface GraphDBExecutor extends DBExecutor {
    void executeCreateQuery(GraphQuery query);
    void executeReadQuery(GraphQuery query, Connection connection);
    void executeUpdateQuery(GraphQuery query);
    void executeDeleteQuery(GraphQuery query);
}
