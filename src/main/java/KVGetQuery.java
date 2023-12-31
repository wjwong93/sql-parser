import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KVGetQuery extends KVQuery {
    final private List<String> keys;
    public KVGetQuery(String key) {
        super("GET(" + key + ");");
        this.keys = new ArrayList<>();
        this.keys.add(key);
    }

    public KVGetQuery(List<String> keys) {
        super(keys.stream().map(
            k -> "GET(" + k + ");"
        ).collect(Collectors.joining("\n")));

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

    public static void main(String[] args) {
        KVGetQuery testQuery = new KVGetQuery("testKey");
        testQuery.execute();
    }
}
