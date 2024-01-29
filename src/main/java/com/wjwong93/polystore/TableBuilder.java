package com.wjwong93.polystore;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TableBuilder implements AutoCloseable {
    private String tableName;
    private List<String> columns;
    private final List<String> rows;
    private final Statement stmt;

    public TableBuilder(Connection connection) throws SQLException {
        this.stmt = connection.createStatement();
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public TableBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public TableBuilder setColumnNames(String... columnNames) {
        this.columns = Arrays.stream(columnNames)
                .map(colName -> "\"" + colName + "\" TEXT NOT NULL")
                .collect(Collectors.toList());
        return this;
    }

    public TableBuilder insertRow(String... values) throws SQLException {
        if (values.length != columns.size()) {
            throw new SQLException("Number of values does not match number of columns");
        }

        String newRow = "(" + Arrays.stream(values)
                .map(value -> "\"" + value + "\"")
                .collect(Collectors.joining(", ")) + ")";
        rows.add(newRow);
        return this;
    }

    public void build() throws SQLException {
        if (tableName == null || tableName.isEmpty() || columns.isEmpty()) return;

        String createTable = "CREATE TABLE " + tableName + " (\n" +
                String.join(", ", columns) +
                "\n);";
        stmt.addBatch(createTable);

        String insertRow = "INSERT INTO " + tableName + " VALUES " +
                String.join(", ", rows) +
                ";";
        stmt.addBatch(insertRow);
        stmt.executeBatch();
    }

    @Override
    public void close() throws SQLException {
        this.stmt.close();
    }
}
