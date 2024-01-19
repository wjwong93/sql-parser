package com.wjwong93.polystore.query;

import java.util.List;
import java.util.stream.Collectors;

public class KeyValueReadQuery extends KeyValueQuery {
    final private List<String[]> keyvalues;

    public KeyValueReadQuery(List<String[]> keyvalues, String tableId) {
        super(
            QueryType.READ,
            keyvalues == null ? "GET ALL;" : keyvalues.stream().map(
                k -> "GET(\"" + k[0] + "\");"
            ).collect(Collectors.joining("\n")),
            tableId
        );

        this.keyvalues = keyvalues;
    }

    @Override
    public List<String[]> getKeyValues() {
        return this.keyvalues;
    }
}
