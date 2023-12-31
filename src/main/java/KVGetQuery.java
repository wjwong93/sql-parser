import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import java.io.File;

public class KVGetQuery extends KVQuery {
    private String key;
    public KVGetQuery(String key) {
        super("GET(" + key + ")");
        this.key = key;
    }

    @Override
    void execute() {
        try (DB db = JniDBFactory.factory.open(new File("./leveldb"), new Options())) {
            String value = JniDBFactory.asString(db.get(JniDBFactory.bytes(key)));
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KVGetQuery testQuery = new KVGetQuery("testKey");
        testQuery.execute();
    }
}
