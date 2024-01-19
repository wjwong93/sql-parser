package com.wjwong93.polystore;

import com.wjwong93.polystore.dbExecutor.DBExecutor;
import com.wjwong93.polystore.dbExecutor.KeyValueDBExecutor;
import com.wjwong93.polystore.query.KeyValueQuery;
import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class LevelDBExecutor implements KeyValueDBExecutor {
    private final File dbPath;
    private final Options dbOptions;

    public LevelDBExecutor(String dbPath) {
        this.dbPath = new File(dbPath);
        this.dbOptions = new Options();
    }
    @Override
    public void executeCreateQuery(KeyValueQuery query) {
        // Same as Update Query
        executeUpdateQuery(query);
    }

    @Override
    public void executeReadQuery(KeyValueQuery query, Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            String tableId = query.getTableId();
            String createTableSql = "CREATE TABLE " + tableId + "(\n" +
                    "\"key\" TEXT PRIMARY KEY,\n" +
                    "\"value\" TEXT NOT NULL\n" +
                    ");";
            stmt.addBatch(createTableSql);

            try (DB db = JniDBFactory.factory.open(dbPath, dbOptions)) {
                List<String[]> keyvalues = query.getKeyValues();
                if (keyvalues == null) {
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
                    for (String[] kv : keyvalues) {
                        String key = kv[0];
                        String value = JniDBFactory.asString(db.get(JniDBFactory.bytes(key)));
                        String insertDataSql = "INSERT INTO " + tableId + " VALUES (\"" + key + "\",\"" + value + "\");";
                        stmt.addBatch(insertDataSql);
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void executeUpdateQuery(KeyValueQuery query) {
        try (
            DB db = JniDBFactory.factory.open(dbPath, dbOptions);
            WriteBatch batch = db.createWriteBatch()
        ) {
            List<String[]> keyvalues = query.getKeyValues();
            for (String[] kv : keyvalues) {
                String key = kv[0];
                String value = kv[1];
                batch.put(JniDBFactory.bytes(key), JniDBFactory.bytes(value));
            }
            db.write(batch);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void executeDeleteQuery(KeyValueQuery query) {
        try (
            DB db = JniDBFactory.factory.open(dbPath, dbOptions);
            WriteBatch batch = db.createWriteBatch()
        ) {
            List<String[]> keyvalues = query.getKeyValues();
            if (keyvalues == null) {
                // DELETE ALL
                try (DBIterator iterator = db.iterator()) {
                    for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                        byte[] key = iterator.peekNext().getKey();
                        batch.delete(key);
                    }
                }
            } else {
                for (String[] kv : keyvalues) {
                    batch.delete(JniDBFactory.bytes(kv[0]));
                }
            }
            db.write(batch);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
