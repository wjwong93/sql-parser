import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KVGetQuery extends ReadQuery {
    final private List<String> keys;
    public KVGetQuery(String key, String tableId) {
        super("GET(\"" + key + "\");", tableId);
        this.keys = new ArrayList<>();
        this.keys.add(key);
    }

    public KVGetQuery(List<String> keys, String tableId) {
        super(keys.stream().map(
            k -> "GET(\"" + k + "\");"
        ).collect(Collectors.joining("\n")), tableId);

        this.keys = keys;
    }

    @Override
    void execute() {
        try (DB db = JniDBFactory.factory.open(new File("./leveldb"), new Options())) {
            for (String key : keys) {
                String value = JniDBFactory.asString(db.get(JniDBFactory.bytes(key)));
                System.out.println(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeAndStore(Connection conn) {

        try (Statement stmt = conn.createStatement()) {
            String createTableSql = "CREATE TABLE " + tableId + "(\n" +
                    "k TEXT PRIMARY KEY,\n" +
                    "v TEXT NOT NULL\n" +
                    ");";
            stmt.addBatch(createTableSql);

            try (DB db = JniDBFactory.factory.open(new File("./leveldb"), new Options())) {
                for (String key : keys) {
                    String value = JniDBFactory.asString(db.get(JniDBFactory.bytes(key)));
                    String insertDataSql = "INSERT INTO " + tableId + " VALUES (\"" + key + "\",\"" + value + "\");";
                    stmt.addBatch(insertDataSql);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            stmt.executeBatch();

            String selectSql = "SELECT * FROM " + tableId + ";";

            ResultSet rs = stmt.executeQuery(selectSql);

            System.out.println("Result:");
            while (rs.next()) {
                System.out.println(rs.getString("k") + ", " + rs.getString("v"));
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        KVGetQuery testQuery = new KVGetQuery("testKey", "t0");
        testQuery.execute();
    }
}
