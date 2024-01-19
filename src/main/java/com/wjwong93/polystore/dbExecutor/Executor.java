package com.wjwong93.polystore.dbExecutor;

import com.wjwong93.polystore.LevelDBExecutor;
import com.wjwong93.polystore.Neo4jExecutor;
import com.wjwong93.polystore.SQLiteManager;
import com.wjwong93.polystore.factory.QueryFactory;
import com.wjwong93.polystore.parser.SQLParser;
import com.wjwong93.polystore.query.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.List;

public class Executor implements AutoCloseable {
    private Connection connection;
    private KeyValueDBExecutor keyValueDBExecutor;
    private GraphDBExecutor graphDBExecutor;

    public Executor() {
        try {
            String url = "jdbc:sqlite::memory:";
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setKeyValueDBExecutor(KeyValueDBExecutor dbExecutor) {
        this.keyValueDBExecutor = dbExecutor;
    }

    public void setGraphDBExecutor(GraphDBExecutor dbExecutor) {
        this.graphDBExecutor = dbExecutor;
    }
    public void executeQueryPlan(List<Query> queryPlan) {
        for (Query query : queryPlan) {
            executeQuery(query, connection);
        }
    }

    public void executeQuery(Query query, Connection connection) {
        if (query instanceof KeyValueQuery keyValueQuery) {
            switch (query.getType()) {
                case CREATE -> keyValueDBExecutor.executeCreateQuery(keyValueQuery);
                case READ -> keyValueDBExecutor.executeReadQuery(keyValueQuery, connection);
                case UPDATE -> keyValueDBExecutor.executeUpdateQuery(keyValueQuery);
                case DELETE -> keyValueDBExecutor.executeDeleteQuery(keyValueQuery);
            }
        } else if (query instanceof GraphQuery graphQuery) {
            switch (query.getType()) {
                case CREATE -> graphDBExecutor.executeCreateQuery(graphQuery);
                case READ -> graphDBExecutor.executeReadQuery(graphQuery, connection);
                case UPDATE -> graphDBExecutor.executeUpdateQuery(graphQuery);
                case DELETE -> graphDBExecutor.executeDeleteQuery(graphQuery);
            }
        } else if (query instanceof OuterReadQuery outerQuery) {
            try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(outerQuery.toString())
            ) {
                QueryUtils.printResultSet(rs);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please specify input query file.");
            return;
        }

        String inputFile = args[0];
        try (
            FileInputStream inputStream = new FileInputStream(inputFile);
            Executor executor = new Executor()
        ) {
            List<Query> queryPlan = SQLParser.parse(inputStream, new QueryFactory());
            executor.setKeyValueDBExecutor(new LevelDBExecutor("./leveldb"));
            executor.setGraphDBExecutor(new Neo4jExecutor("neo4j://localhost:7687", "neo4j", "password"));
            executor.executeQueryPlan(queryPlan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
