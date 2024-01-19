package com.wjwong93.polystore.factory;

import com.wjwong93.polystore.query.Query;
import com.wjwong93.polystore.query.QueryType;

import java.util.List;

public interface QueryFactoryInterface {
    Query createGraphQuery(QueryType queryType, String tableId, String query);

    Query createKeyValueQuery(QueryType queryType, String tableId, List<String[]> keyvalues);
}
