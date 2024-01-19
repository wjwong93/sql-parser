package com.wjwong93.polystore.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class ReadQuery extends Query {
    String tableId;
    public ReadQuery(String query, String tableId) {
        super(QueryType.READ, query);
        this.tableId = tableId;
    }

    @Override
    public void executeAndStore(Connection conn) {
        return;
    }
    public String getTableId() {
        return tableId;
    }

    void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colCount = rsmd.getColumnCount();

        System.out.println("Result:");
        System.out.println("---");
        for (int i=1; i<=colCount; i++) {
            if (i>1) System.out.print(", ");
            System.out.print(rsmd.getColumnName(i));
        }

        System.out.println();
        System.out.println("---");

        while (resultSet.next()) {
            for (int i=1; i<=colCount; i++) {
                if (i>1) System.out.print(", ");
                System.out.print(resultSet.getString(i));
            }
            System.out.println();
        }

        System.out.println();
    }

//    @Override
//    public String toString() {
//        return tableId + ":\n" + query;
//    }
}
