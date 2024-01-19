package com.wjwong93.polystore.query;

import com.wjwong93.polystore.dbExecutor.DBExecutor;
import com.wjwong93.polystore.dbExecutor.KeyValueDBExecutor;
import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeyValueUpdateQuery extends KeyValueQuery {
    final private List<String[]> keyvalues;
    public KeyValueUpdateQuery(String key, String value) {
        super(QueryType.UPDATE, "PUT(\"" + key + "\", \"" + value + "\");");
        this.keyvalues = new ArrayList<>();
        this.keyvalues.add(new String[] {key, value});
    }

//    public KeyValueUpdateQuery(List<String[]> keyvalues) {
//        super(QueryType.UPDATE, keyvalues.stream().map(
//                kv -> "PUT(\"" + kv[0] + "\", \"" + kv[1] + "\");"
//        ).collect(Collectors.joining("\n")));
//
//        this.keyvalues = keyvalues;
//    }

    public KeyValueUpdateQuery(List<String[]> keyvalues, String tableId) {
        super(
            QueryType.UPDATE,
            keyvalues.stream().map(
                kv -> "PUT(\"" + kv[0] + "\", \"" + kv[1] + "\");"
            ).collect(Collectors.joining("\n")),
            tableId
        );

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
            for (String[] kv : keyvalues) {
                String key = kv[0];
                String value = kv[1];
                batch.put(JniDBFactory.bytes(key), JniDBFactory.bytes(value));
            }
            db.write(batch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KeyValueUpdateQuery testQuery = new KeyValueUpdateQuery("testKey", "testValue");
        testQuery.execute();
    }
}
