package com.wjwong93.polystore;

public class MemgraphExecutor extends Neo4jExecutor {
    // Memgraph uses the Neo4j Driver
    // Limited support for Cypher,
    // see https://memgraph.com/docs/querying/differences-in-cypher-implementations

    public MemgraphExecutor(String uri, String username, String password) {
        super(uri, username, password);
    }
}
