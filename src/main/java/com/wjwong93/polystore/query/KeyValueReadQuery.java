package com.wjwong93.polystore.query;

import com.wjwong93.polystore.dbExecutor.DBExecutor;
import com.wjwong93.polystore.dbExecutor.KeyValueDBExecutor;
import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeyValueReadQuery extends KeyValueQuery {
    final private List<String> keys;
    final private List<String[]> keyvalues;

    public KeyValueReadQuery(String key, String tableId) {
        super(QueryType.READ, "GET(\"" + key + "\");", tableId);
        this.keys = new ArrayList<>();
        this.keys.add(key);
        this.keyvalues = new ArrayList<>();
        this.keyvalues.add(new String[] {key});
    }

//    public KeyValueReadQuery(List<String> keys, String tableId) {
//        super(
//            QueryType.READ,
//            keys == null ? "GET ALL;" : keys.stream().map(
//                k -> "GET(\"" + k + "\");"
//            ).collect(Collectors.joining("\n")),
//            tableId
//        );
//
//        this.keys = keys;
//        this.keyvalues = keys == null ? null : keys.stream().map(k -> new String[] {k}).collect(Collectors.toList());
//    }

    public KeyValueReadQuery(List<String[]> keyvalues, String tableId) {
        super(
            QueryType.READ,
            keyvalues == null ? "GET ALL;" : keyvalues.stream().map(
                k -> "GET(\"" + k[0] + "\");"
            ).collect(Collectors.joining("\n")),
            tableId
        );

        this.keys = keyvalues == null ? null : keyvalues.stream().map(kv -> kv[0]).collect(Collectors.toList());
        this.keyvalues = keyvalues;
    }

    @Override
    public List<String[]> getKeyValues() {
        return this.keyvalues;
    }

    @Override
    public void execute() {
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
            String tableId = this.getTableId();
            String createTableSql = "CREATE TABLE " + tableId + "(\n" +
                    "\"key\" TEXT PRIMARY KEY,\n" +
                    "\"value\" TEXT NOT NULL\n" +
                    ");";
            stmt.addBatch(createTableSql);

            try (DB db = JniDBFactory.factory.open(new File("./leveldb"), new Options())) {
                if (keys == null) {
                    // GET ALL
                    try (DBIterator iterator = db.iterator()) {
                        for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                            String key = JniDBFactory.asString(iterator.peekNext().getKey());
                            String value = JniDBFactory.asString(iterator.peekNext().getValue());
                            String insertDataSql = "INSERT INTO " + tableId + " VALUES (\"" + key + "\",\"" + value + "\");";
                            stmt.addBatch(insertDataSql);
                        }
                    }
                } else {
                    for (String key : keys) {
                        String value = JniDBFactory.asString(db.get(JniDBFactory.bytes(key)));
                        String insertDataSql = "INSERT INTO " + tableId + " VALUES (\"" + key + "\",\"" + value + "\");";
                        stmt.addBatch(insertDataSql);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        KeyValueReadQuery testQuery = new KeyValueReadQuery("testKey", "t0");
        testQuery.execute();
    }
}
