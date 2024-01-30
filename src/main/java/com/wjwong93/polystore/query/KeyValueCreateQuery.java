package com.wjwong93.polystore.query;

import java.util.List;
import java.util.stream.Collectors;

public class KeyValueCreateQuery extends KeyValueQuery {
    final private List<String[]> keyvalues;
    public KeyValueCreateQuery(List<String[]> keyvalues, String tableId) {
        super(
                QueryType.CREATE,
                keyvalues.stream().map(
                        kv -> "PUT(\"" + kv[0] + "\", \"" + kv[1] + "\");"
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
