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
                System.out.println("Database created in memory.");
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
        String inputFile = "src/test/resources/input/leveldb_get.sql";
        try (
            FileInputStream inputStream = new FileInputStream(inputFile);
            SQLiteManager manager = new SQLiteManager()
        ) {
            Connection conn = manager.getConn();
            List<Query> queryList = SQLParser.parse(inputStream, inputFile);
            for (Query query : queryList) {
                query.executeAndStore(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
