package com.wjwong93.polystore;

import com.wjwong93.polystore.dbExecutor.GraphDBExecutor;
import com.wjwong93.polystore.query.GraphQuery;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.internal.types.TypeConstructor;
import org.neo4j.driver.internal.types.TypeRepresentation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
            Statement stmt = connection.createStatement();
            Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
            Session session = driver.session()
        ) {
            String tableId = query.getTableId();
            List<Record> recordList = session.executeRead(tx -> tx.run(query.getQuery()).list());

            if (recordList.isEmpty()) return;

            List<String> cols = recordList.get(0).keys();
            String createTableSql = "CREATE TABLE " + tableId + "(\n" +
                    cols.stream().map(c -> "\"" + c + "\" TEXT NOT NULL").collect(Collectors.joining(",\n")) +
                    "\n);";
            stmt.addBatch(createTableSql);

            for (Record record : recordList) {
                String insertDataSql = "INSERT INTO " + tableId + " VALUES(" +
                        record.values().stream().map(v -> {
                            if (new TypeRepresentation(TypeConstructor.STRING).isTypeOf(v)) {
                                return "\"" + v.asString() + "\"";
                            } else {
                                return "\"" + v.toString() + "\"";
                            }
                        }).collect(Collectors.joining(", ")) +
                        ");";
                stmt.addBatch(insertDataSql);
            }

            stmt.executeBatch();
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
