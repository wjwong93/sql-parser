package com.wjwong93.polystore.factory;

import com.wjwong93.polystore.dbExecutor.DBExecutor;
import com.wjwong93.polystore.dbExecutor.KeyValueDBExecutor;
import com.wjwong93.polystore.query.*;

import java.util.List;

public class QueryFactory implements QueryFactoryInterface {
    public QueryFactory() {

    }

    @Override
    public Query createGraphQuery(QueryType queryType, String tableId, String query) {
        return null;
    }

    public Query createKeyValueQuery(QueryType queryType, String tableId, List<String[]> keyvalues) {
        switch (queryType) {
            case CREATE -> {
                return new KeyValueCreateQuery(keyvalues, tableId);
            }

            case READ -> {
                return new KeyValueReadQuery(keyvalues, tableId);
            }

            case UPDATE -> {
                return new KeyValueUpdateQuery(keyvalues, tableId);
            }

            case DELETE -> {
                return new KeyValueDeleteQuery(keyvalues, tableId);
            }
        }

        return null;
    }
}
