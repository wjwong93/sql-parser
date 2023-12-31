import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import java.io.File;

public class KVPutQuery extends KVQuery {
    private String key, value;
    public KVPutQuery(String key, String value) {
        super("PUT(" + key + ", " + value + ")");
        this.key = key;
        this.value = value;
    }

    @Override
    void execute() {
        try (DB db = JniDBFactory.factory.open(new File("./leveldb"), new Options())) {
            db.put(JniDBFactory.bytes(key), JniDBFactory.bytes(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KVPutQuery testQuery = new KVPutQuery("testKey", "testValue");
        testQuery.execute();
    }
}
