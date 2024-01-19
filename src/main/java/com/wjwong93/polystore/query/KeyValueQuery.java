package com.wjwong93.polystore.query;

import com.wjwong93.polystore.dbExecutor.DBExecutor;
import com.wjwong93.polystore.dbExecutor.KeyValueDBExecutor;

import java.sql.Connection;
import java.util.List;

public abstract class KeyValueQuery extends Query {

    public KeyValueQuery(QueryType queryType, String query) {
        super(queryType, query);
    }

    public KeyValueQuery(QueryType queryType, String query, String tableId) {
        super(queryType, query, tableId);
    }

    abstract public List<String[]> getKeyValues();
    @Override
    public void executeAndStore(Connection conn) {
        execute();
    }
}
