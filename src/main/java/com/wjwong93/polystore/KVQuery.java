package com.wjwong93.polystore;

import java.sql.Connection;

public abstract class KVQuery extends Query {
    public KVQuery(String query) {
        super(query);
    }

    @Override
    void executeAndStore(Connection conn) {
        execute();
    }
}
