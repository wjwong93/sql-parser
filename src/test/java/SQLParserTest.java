import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLParserTest {
    @Test
    void sampleSQLPGQQuery() {
        assertEquals(SQLParser.main(new String[]{"pgq_read.sql"}));
    }
}
