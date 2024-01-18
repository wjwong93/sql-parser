package com.wjwong93.polystore;

import java.io.FileInputStream;
import java.sql.*;
import java.util.List;

public class SQLiteManager implements AutoCloseable {
    Connection conn;

    public SQLiteManager() {
        try {
            String url = "jdbc:sqlite::memory:";
            conn = DriverManager.getConnection(url);
            if (conn != null) {
//                System.out.println("Database created in memory.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConn() {
        return conn;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please specify input query file.");
            return;
        }

        String inputFile = args[0];
        try (
            FileInputStream inputStream = new FileInputStream(inputFile);
            SQLiteManager manager = new SQLiteManager()
        ) {
            Connection conn = manager.getConn();
            List<Query> queryList = SQLParser.parse(inputStream);
            for (Query query : queryList) {
                query.executeAndStore(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
