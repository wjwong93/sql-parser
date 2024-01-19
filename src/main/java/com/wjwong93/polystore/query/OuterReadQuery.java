package com.wjwong93.polystore.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OuterReadQuery extends ReadQuery {
    public OuterReadQuery(String query, String tableId) {
        super(query, tableId);
    }

    @Override
    public void execute() {

    }

    @Override
    public void executeAndStore(Connection conn) {
        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(this.toString())
        ) {
            printResultSet(rs);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
