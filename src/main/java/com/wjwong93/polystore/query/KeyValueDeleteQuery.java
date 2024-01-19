package com.wjwong93.polystore.query;

import com.wjwong93.polystore.dbExecutor.DBExecutor;
import com.wjwong93.polystore.dbExecutor.KeyValueDBExecutor;
import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeyValueDeleteQuery extends KeyValueQuery {
    final private List<String> keys;
    final private List<String[]> keyvalues;

    public KeyValueDeleteQuery(String key) {
        super(QueryType.DELETE, "DELETE(\"" + key + "\");");
        this.keys = new ArrayList<>();
        this.keys.add(key);
        this.keyvalues = new ArrayList<>();
        this.keyvalues.add(new String[] {key});
    }

//    public KeyValueDeleteQuery(List<String> keys) {
//        super(
//            QueryType.DELETE,
//            keys == null ? "DELETE ALL;" : keys.stream().map(
//                k -> "DELETE(\"" + k + "\");"
//            ).collect(Collectors.joining("\n"))
//        );
//
//        this.keys = keys;
//        this.keyvalues = keys == null ? null : keys.stream().map(k -> new String[] {k}).collect(Collectors.toList());
//    }

    public KeyValueDeleteQuery(List<String[]> keyvalues, String tableId) {
        super(
            QueryType.DELETE,
            keyvalues == null ? "DELETE ALL;" : keyvalues.stream().map(
                k -> "DELETE(\"" + k[0] + "\");"
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
        try (
            DB db = JniDBFactory.factory.open(new File("./leveldb"), new Options());
            WriteBatch batch = db.createWriteBatch()
        ) {
            if (keys == null) {
                // DELETE ALL
                try (DBIterator iterator = db.iterator()) {
                    for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                        byte[] key = iterator.peekNext().getKey();
                        batch.delete(key);
                    }
                }
            } else {
                for (String key : keys) {
                    batch.delete(JniDBFactory.bytes(key));
                }
            }
            db.write(batch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KeyValueDeleteQuery testQuery = new KeyValueDeleteQuery("testKey");
        testQuery.execute();
    }
}
