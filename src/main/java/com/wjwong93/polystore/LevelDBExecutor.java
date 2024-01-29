package com.wjwong93.polystore;

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
import java.util.ArrayList;
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
        try (
            TableBuilder tableBuilder = new TableBuilder(connection);
            DB db = JniDBFactory.factory.open(dbPath, dbOptions)
        ) {
            int rowCount = 0;
            tableBuilder.setTableName(query.getTableId());
            tableBuilder.setColumnNames("key", "value");

            List<String[]> keyvalues = query.getKeyValues();
            if (keyvalues == null) {
                // GET ALL
                try (DBIterator iterator = db.iterator()) {
                    for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                        String key = JniDBFactory.asString(iterator.peekNext().getKey());
                        String value = JniDBFactory.asString(iterator.peekNext().getValue());
                        tableBuilder.insertRow(key, value);
                        rowCount++;
                    }
                }
            } else {
                for (String[] kv : keyvalues) {
                    String key = kv[0];
                    String value = JniDBFactory.asString(db.get(JniDBFactory.bytes(key)));
                    if (value != null) {
                        tableBuilder.insertRow(key, value);
                        rowCount++;
                    }
                }
            }

            if (rowCount > 0) tableBuilder.build();

        } catch (IOException | SQLException e) {
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
