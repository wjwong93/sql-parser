package com.wjwong93.polystore;

import org.fusesource.leveldbjni.JniDBFactory;
import org.fusesource.leveldbjni.internal.JniDB;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KVDeleteQuery extends KVQuery {
    final private List<String> keys;
    public KVDeleteQuery(String key) {
        super("DELETE(\"" + key + "\");");
        this.keys = new ArrayList<>();
        this.keys.add(key);
    }

    public KVDeleteQuery(List<String> keys) {
        super(
            keys == null ? "DELETE ALL;" : keys.stream().map(
                k -> "DELETE(\"" + k + "\");"
            ).collect(Collectors.joining("\n"))
        );

        this.keys = keys;
    }

    @Override
    void execute() {
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
        KVDeleteQuery testQuery = new KVDeleteQuery("testKey");
        testQuery.execute();
    }
}
