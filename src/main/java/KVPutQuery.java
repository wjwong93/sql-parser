import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KVPutQuery extends KVQuery {
    final private List<String[]> keyvalues;
    public KVPutQuery(String key, String value) {
        super("PUT(" + key + ", " + value + ");");
        this.keyvalues = new ArrayList<>();
        this.keyvalues.add(new String[] {key, value});
    }

    public KVPutQuery(List<String[]> keyvalues) {
        super(keyvalues.stream().map(
                kv -> "PUT(" + kv[0] + ", " + kv[1] + ");"
        ).collect(Collectors.joining("\n")));

        this.keyvalues = keyvalues;
    }

    @Override
    void execute() {
        try (
            DB db = JniDBFactory.factory.open(new File("./leveldb"), new Options());
            WriteBatch batch = db.createWriteBatch()
        ) {
            for (String[] kv : keyvalues) {
                batch.put(JniDBFactory.bytes(kv[0]), JniDBFactory.bytes(kv[1]));
            }
            db.write(batch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KVPutQuery testQuery = new KVPutQuery("testKey", "testValue");
        testQuery.execute();
    }
}
