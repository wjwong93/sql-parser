import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SQLParserTest {
    @Test
    void sampleSQLPGQQuery() {
        /* Sample SQL/PGQ query from Oracle
        */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/pgq_read.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/pgq_read.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/pgq_read.sql").replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @Test
    void pureSQLQuery() {
        /* No Graph query in SQL
        */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/test.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/test.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/test.sql").replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @Test
    void yasuda3() {
        /* あるキーを持つノードに依存しているノードの検索(例えば間違ったデータの影響を受けているノードn)
        */
        try (
                FileInputStream inputStream = new FileInputStream("src/test/resources/input/3.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/3.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/3.sql").replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
