import org.neo4j.driver.Record;
import org.neo4j.driver.Value;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class GraphReadQuery extends ReadQuery {
    public GraphReadQuery(String query, String tableId) {
        super(query, tableId);
    }

    @Override
    void execute() {
        try (GraphDBExecutor graphDBExecutor = new GraphDBExecutor("neo4j://localhost:7687", "neo4j", "password")) {
            graphDBExecutor.executeReadQuery(query);
        }
    }

    @Override
    void executeAndStore(Connection conn) {
        List<Record> recordList = null;
        try (GraphDBExecutor graphDBExecutor = new GraphDBExecutor("neo4j://localhost:7687", "neo4j", "password")) {
            recordList = graphDBExecutor.executeQuery(query);
        }
        List<String> cols = recordList.get(0).keys();
        try (Statement stmt = conn.createStatement()) {
            String createTableSql = "CREATE TABLE " + tableId + "(\n" +
                    cols.stream().map(c -> c + " TEXT NOT NULL").collect(Collectors.joining(",\n")) +
                    "\n);";
//            System.out.println(createTableSql);
            stmt.addBatch(createTableSql);

            for (Record record : recordList) {
                String insertDataSql = "INSERT INTO " + tableId + " VALUES(" +
                        record.values().stream().map(v -> "\"" + v.asString() + "\"").collect(Collectors.joining(", ")) +
                        ");";
//                System.out.println(insertDataSql);
                stmt.addBatch(insertDataSql);
            }

            stmt.executeBatch();

            String selectSql = "SELECT * FROM " + tableId + ";";

            ResultSet rs = stmt.executeQuery(selectSql);
            printResultSet(rs);

            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String testQuery = "MATCH (n:TestNode) RETURN n;";
        GraphReadQuery graphReadQuery = new GraphReadQuery(testQuery, "t0");
        graphReadQuery.execute();
    }
}
