package com.wjwong93.polystore.query;

import java.util.List;

public class KeyValueCreateQuery extends KeyValueUpdateQuery {
    public KeyValueCreateQuery(List<String[]> keyvalues, String tableId) {
        super(keyvalues, tableId);
    }
}
