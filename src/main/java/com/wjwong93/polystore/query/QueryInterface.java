package com.wjwong93.polystore.query;

import java.sql.Connection;

public interface QueryInterface {
    public void execute();

    public void executeAndStore(Connection conn);
}
