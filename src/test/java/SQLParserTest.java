import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SQLParserTest {
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
    void oracle1() {
        /* Sample SQL/PGQ query from Oracle
         */
        try (
                FileInputStream inputStream = new FileInputStream("src/test/resources/input/oracle1.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/oracle1.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/oracle1.sql").replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @Test
    void oracle2() {
        /* Sample SQL/PGQ query from Oracle
         */
        try (
                FileInputStream inputStream = new FileInputStream("src/test/resources/input/oracle2.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/oracle2.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/oracle2.sql").replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @Test
    void oracle3() {
        /* Sample SQL/PGQ query from Oracle
         */
        try (
                FileInputStream inputStream = new FileInputStream("src/test/resources/input/oracle3.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/oracle3.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/oracle3.sql").replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @Test
    void yasuda1() {
        /* ノードの作成
         */
        try (
                FileInputStream inputStream = new FileInputStream("src/test/resources/input/yasuda1.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/yasuda1.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/yasuda1.sql").replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @Test
    void yasuda2() {
        /* リレーションの作成
         */
        try (
                FileInputStream inputStream = new FileInputStream("src/test/resources/input/yasuda2.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/yasuda2.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/yasuda2.sql").replaceAll("\\s+", " ")
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
                FileInputStream inputStream = new FileInputStream("src/test/resources/input/yasuda3.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/yasuda3.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/yasuda3.sql").replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @Test
    void yasuda4() {
        /* あるキーを持つノードに依存しているノードと、新しく計算し直す時に必要なノードの検索
         */
        try (
                FileInputStream inputStream = new FileInputStream("src/test/resources/input/yasuda4.sql");
        ) {
            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/yasuda4.cypher")).replaceAll("\\s+", " "),
                    SQLParser.extractCypherQuery(inputStream, "src/test/resources/input/yasuda4.sql").replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
