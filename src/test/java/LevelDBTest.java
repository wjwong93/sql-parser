import org.iq80.leveldb.*;
import org.fusesource.leveldbjni.JniDBFactory;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LevelDBTest {
    @Test
    void initialState() {
        StringBuilder data = new StringBuilder();
        Options options = new Options();
        try (
            DB db = JniDBFactory.factory.open(new File("./leveldb"), options);
            DBIterator iterator = db.iterator()
        ) {
            for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                String key = JniDBFactory.asString(iterator.peekNext().getKey());
                String value = JniDBFactory.asString(iterator.peekNext().getValue());
//                System.out.println(key + ", " + value);
                data.append(key).append(": ").append(value).append("\n");
            }

            assertEquals(
                Files.readString(Path.of("src/test/resources/data/leveldb_data.txt")).replaceAll("\\s+", " "),
                data.toString().replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
