package com.wjwong93.polystore.query;

import java.util.List;

public abstract class KeyValueQuery extends Query {
    public KeyValueQuery(QueryType queryType, String query, String tableId) {
        super(queryType, query, tableId);
    }

    abstract public List<String[]> getKeyValues();
}
