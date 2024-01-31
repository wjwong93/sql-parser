package com.wjwong93.polystore;

import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultSet;
import com.arcadedb.remote.RemoteDatabase;
import com.arcadedb.remote.RemoteServer;
import com.wjwong93.polystore.dbExecutor.GraphDBExecutor;
import com.wjwong93.polystore.dbExecutor.KeyValueDBExecutor;
import com.wjwong93.polystore.query.GraphQuery;
import com.wjwong93.polystore.query.KeyValueQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ArcadeDBExecutor implements KeyValueDBExecutor, GraphDBExecutor {
    private final String server, username, password;
    private final int port;
    private String graphDBName, keyValueDBName;
    public ArcadeDBExecutor(String server, int port, String username, String password) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void setGraphDatabase(String dbName) {
        if (graphDBName != null) return;
        RemoteServer remoteServer = new RemoteServer(server, port, username, password);
        if (!remoteServer.exists(dbName)) return;
        graphDBName = dbName;
    }

    public void setKeyValueDatabase(String dbName) {
        if (keyValueDBName != null) return;
        RemoteServer remoteServer = new RemoteServer(server, port, username, password);
        if (!remoteServer.exists(dbName)) return;
        keyValueDBName = dbName;
    }

    @Override
    public void executeCreateQuery(GraphQuery query) {
        executeUpdateQuery(query);
    }

    @Override
    public void executeReadQuery(GraphQuery query, Connection connection) {
        if (graphDBName == null) return;
        try (
            RemoteDatabase db = new RemoteDatabase(server, port, graphDBName, username, password);
            TableBuilder tableBuilder = new TableBuilder(connection)
        ) {
            ResultSet resultSet = db.query("cypher", query.getQuery());
            if (!resultSet.hasNext()) return;

            tableBuilder.setTableName(query.getTableId());
            Result result = resultSet.next();
            String[] columns = result.getPropertyNames().toArray(new String[0]);
            tableBuilder.setColumnNames(columns);

            String[] newRow = new String[columns.length];
            for (int i=0; i<columns.length; i++) {
                newRow[i] = result.getProperty(columns[i]);
            }
            tableBuilder.insertRow(newRow);

            while (resultSet.hasNext()) {
                result = resultSet.next();
                newRow = new String[columns.length];
                for (int i=0; i<columns.length; i++) {
                    newRow[i] = result.getProperty(columns[i]);
                }
                tableBuilder.insertRow(newRow);
            }

            tableBuilder.build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void executeUpdateQuery(GraphQuery query) {
        if (graphDBName == null) return;
        try (RemoteDatabase db = new RemoteDatabase(server, port, graphDBName, username, password)) {
            db.begin();
            db.command("cypher", query.getQuery());
            db.commit();
        }
    }

    @Override
    public void executeDeleteQuery(GraphQuery query) {
        executeUpdateQuery(query);
    }

    @Override
    public void executeCreateQuery(KeyValueQuery query) {
        if (keyValueDBName == null) return;
        try (RemoteDatabase db = new RemoteDatabase(server, port, keyValueDBName, username, password)) {
            List<String[]> keyvalues = query.getKeyValues();
            db.begin();
            if (keyvalues != null) {
                String q = "INSERT INTO KVS (key, value) VALUES " +
                        keyvalues.stream()
                                .map(kv -> "(\"" + kv[0] + "\", \"" + kv[1] + "\")")
                                .collect(Collectors.joining(", "));
                db.command("sql", q);
            }
            db.commit();
        }
    }

    @Override
    public void executeReadQuery(KeyValueQuery query, Connection connection) {
        if (keyValueDBName == null) return;
        try (
            RemoteDatabase db = new RemoteDatabase(server, port, keyValueDBName, username, password);
            TableBuilder tableBuilder = new TableBuilder(connection)
        ) {
            int rowCount = 0;
            tableBuilder.setTableName(query.getTableId());
            tableBuilder.setColumnNames("key", "value");

            List<String[]> keyvalues = query.getKeyValues();
            if (keyvalues == null) {
                // GET ALL
                ResultSet resultSet = db.query("sql", "SELECT * FROM KVS");
                while (resultSet.hasNext()) {
                    Result result = resultSet.next();
                    String key = result.getProperty("key");
                    String value = result.getProperty("value");
                    tableBuilder.insertRow(key, value);
                    rowCount++;
                }
            } else {
                String q = "SELECT * FROM KVS WHERE " +
                        keyvalues.stream()
                            .map(kv -> "key=\"" + kv[0] + "\"")
                            .collect(Collectors.joining(" OR "));

                ResultSet resultSet = db.query("sql", q);
                while (resultSet.hasNext()) {
                    Result result = resultSet.next();
                    String key = result.getProperty("key");
                    String value = result.getProperty("value");
                    tableBuilder.insertRow(key, value);
                    rowCount++;
                }
            }

            if (rowCount > 0) tableBuilder.build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void executeUpdateQuery(KeyValueQuery query) {
        if (keyValueDBName == null) return;
        try (RemoteDatabase db = new RemoteDatabase(server, port, keyValueDBName, username, password)) {
            List<String[]> keyvalues = query.getKeyValues();
            db.begin();
            if (keyvalues != null) {
                for (String[] kv : keyvalues) {
                    String key = kv[0];
                    String newValue = kv[1];
                    String q = "UPDATE KVS SET value=\"" + newValue + "\" WHERE key=\"" + key + "\"";
                    db.command("sql", q);
                }
            }
            db.commit();
        }
    }

    @Override
    public void executeDeleteQuery(KeyValueQuery query) {
        if (keyValueDBName == null) return;
        try (RemoteDatabase db = new RemoteDatabase(server, port, keyValueDBName, username, password)) {
            List<String[]> keyvalues = query.getKeyValues();
            db.begin();
            if (keyvalues == null) {
                // DELETE ALL
                db.command("sql", "DELETE FROM KVS");
            } else {
                String q = "DELETE FROM KVS WHERE " +
                        keyvalues.stream()
                            .map(kv -> "key=\"" + kv[0] + "\"")
                            .collect(Collectors.joining(" OR "));
                db.command("sql", q);
            }
            db.commit();
        }
    }
}
