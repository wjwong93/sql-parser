package com.wjwong93.polystore;

import com.wjwong93.polystore.factory.QueryFactory;
import com.wjwong93.polystore.parser.SQLParser;
import com.wjwong93.polystore.query.Query;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SQLParserTest {
    private final QueryFactory queryFactory;

    SQLParserTest() {
        this.queryFactory = new QueryFactory();
    }

    @Test
    void pureSQLQuery() {
        /* No Graph query in SQL
        */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/test.sql")
        ) {
            List<Query> queryList = SQLParser.parse(inputStream, queryFactory);
            StringBuilder parseResult = new StringBuilder();
            for (Query query : queryList) {
                parseResult.append(query.getQuery()).append("\n");
            }

            assertEquals(
                Files.readString(Path.of("src/test/resources/output/test.txt")).replaceAll("\\s+", " "),
                parseResult.toString().replaceAll("\\s+", " ")
            );

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void oracle1() {
        /* Sample SQL/PGQ query from Oracle
         */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/oracle1.sql")
        ) {
            List<Query> queryList = SQLParser.parse(inputStream, queryFactory);
            StringBuilder parseResult = new StringBuilder();
            for (Query query : queryList) {
                parseResult.append(query.getQuery()).append("\n");
            }

            assertEquals(
                Files.readString(Path.of("src/test/resources/output/oracle1.txt")).replaceAll("\\s+", " "),
                parseResult.toString().replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void oracle2() {
        /* Sample SQL/PGQ query from Oracle
         */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/oracle2.sql")
        ) {
            List<Query> queryList = SQLParser.parse(inputStream, queryFactory);
            StringBuilder parseResult = new StringBuilder();
            for (Query query : queryList) {
                parseResult.append(query.getQuery()).append("\n");
            }

            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/oracle2.txt")).replaceAll("\\s+", " "),
                    parseResult.toString().replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void oracle3() {
        /* Sample SQL/PGQ query from Oracle
         */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/oracle3.sql")
        ) {
            List<Query> queryList = SQLParser.parse(inputStream, queryFactory);
            StringBuilder parseResult = new StringBuilder();
            for (Query query : queryList) {
                parseResult.append(query.getQuery()).append("\n");
            }

            assertEquals(
                Files.readString(Path.of("src/test/resources/output/oracle3.txt")).replaceAll("\\s+", " "),
                parseResult.toString().replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void yasuda1() {
        /* ノードの作成
         */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/yasuda1.sql")
        ) {
            List<Query> queryList = SQLParser.parse(inputStream, queryFactory);
            StringBuilder parseResult = new StringBuilder();
            for (Query query : queryList) {
                parseResult.append(query.getQuery()).append("\n");
            }

            assertEquals(
                    Files.readString(Path.of("src/test/resources/output/yasuda1.txt")).replaceAll("\\s+", " "),
                    parseResult.toString().replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void yasuda2() {
        /* リレーションの作成
         */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/yasuda2.sql")
        ) {
            List<Query> queryList = SQLParser.parse(inputStream, queryFactory);
            StringBuilder parseResult = new StringBuilder();
            for (Query query : queryList) {
                parseResult.append(query.getQuery()).append("\n");
            }

            assertEquals(
                Files.readString(Path.of("src/test/resources/output/yasuda2.txt")).replaceAll("\\s+", " "),
                parseResult.toString().replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void yasuda3() {
        /* あるキーを持つノードに依存しているノードの検索(例えば間違ったデータの影響を受けているノードn)
        */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/yasuda3.sql")
        ) {
            List<Query> queryList = SQLParser.parse(inputStream, queryFactory);
            StringBuilder parseResult = new StringBuilder();
            for (Query query : queryList) {
                parseResult.append(query.getQuery()).append("\n");
            }

            assertEquals(
                Files.readString(Path.of("src/test/resources/output/yasuda3.txt")).replaceAll("\\s+", " "),
                parseResult.toString().replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void yasuda4() {
        /* あるキーを持つノードに依存しているノードと、新しく計算し直す時に必要なノードの検索
         */
        try (
            FileInputStream inputStream = new FileInputStream("src/test/resources/input/yasuda4.sql")
        ) {
            List<Query> queryList = SQLParser.parse(inputStream, queryFactory);
            StringBuilder parseResult = new StringBuilder();
            for (Query query : queryList) {
                parseResult.append(query.getQuery()).append("\n");
            }

            assertEquals(
                Files.readString(Path.of("src/test/resources/output/yasuda4.txt")).replaceAll("\\s+", " "),
                parseResult.toString().replaceAll("\\s+", " ")
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
