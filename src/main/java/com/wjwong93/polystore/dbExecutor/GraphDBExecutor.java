package com.wjwong93.polystore.dbExecutor;

import com.wjwong93.polystore.query.GraphQuery;

import java.sql.Connection;

public interface GraphDBExecutor extends DBExecutor {
    public void executeCreateQuery(GraphQuery query);
    public void executeReadQuery(GraphQuery query, Connection connection);
    public void executeUpdateQuery(GraphQuery query);
    public void executeDeleteQuery(GraphQuery query);
}
