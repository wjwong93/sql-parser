package com.wjwong93.polystore.query;

import com.wjwong93.polystore.dbExecutor.DBExecutor;
import com.wjwong93.polystore.dbExecutor.KeyValueDBExecutor;

import java.util.List;

public class KeyValueCreateQuery extends KeyValueUpdateQuery {
    public KeyValueCreateQuery(List<String[]> keyvalues, String tableId) {
        super(keyvalues, tableId);
    }
}
