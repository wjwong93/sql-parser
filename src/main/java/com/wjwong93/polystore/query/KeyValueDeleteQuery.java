package com.wjwong93.polystore.query;

import java.util.List;
import java.util.stream.Collectors;

public class KeyValueDeleteQuery extends KeyValueQuery {
    final private List<String[]> keyvalues;

    public KeyValueDeleteQuery(List<String[]> keyvalues, String tableId) {
        super(
            QueryType.DELETE,
            keyvalues == null ? "DELETE ALL;" : keyvalues.stream().map(
                k -> "DELETE(\"" + k[0] + "\");"
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
