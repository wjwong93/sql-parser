package com.wjwong93.polystore;

import com.wjwong93.polystore.dbExecutor.GraphDBExecutor;
import com.wjwong93.polystore.query.GraphQuery;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.internal.types.TypeConstructor;
import org.neo4j.driver.internal.types.TypeRepresentation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Neo4jExecutor implements GraphDBExecutor {
    private final String uri;
    private final String username;
    private final String password;

    public Neo4jExecutor(String uri, String username, String password) {
        this.uri = uri;
        this.username = username;
        this.password = password;
    }

    @Override
    public void executeCreateQuery(GraphQuery query) {
        executeUpdateQuery(query);
    }

    @Override
    public void executeReadQuery(GraphQuery query, Connection connection) {
        try (
            TableBuilder tableBuilder = new TableBuilder(connection);
            Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
            Session session = driver.session()
        ) {
            List<Record> recordList = session.executeRead(tx -> tx.run(query.getQuery()).list());

            if (recordList.isEmpty()) return;

            tableBuilder.setTableName(query.getTableId());

            List<String> cols = recordList.get(0).keys();
            tableBuilder.setColumnNames(cols.toArray(new String[0]));

            for (Record record : recordList) {
                List<String> values = new ArrayList<>();
                for (Value value : record.values()) {
                    if (new TypeRepresentation(TypeConstructor.STRING).isTypeOf(value)) {
                        values.add(value.asString());
                    } else {
                        values.add(value.toString());
                    }
                }
                tableBuilder.insertRow(values.toArray(new String[0]));
            }

            tableBuilder.build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void executeUpdateQuery(GraphQuery query) {
        try (
            Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
            Session session = driver.session()
        ) {
            session.executeWriteWithoutResult(tx -> tx.run(query.getQuery()));
        }
    }

    @Override
    public void executeDeleteQuery(GraphQuery query) {
        executeUpdateQuery(query);
    }
}
