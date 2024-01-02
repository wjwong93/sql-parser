import java.sql.Connection;

public abstract class KVQuery extends Query {
    public KVQuery(String query) {
        super(query);
    }

    @Override
    public void executeAndStore(Connection conn) {
        return;
    }
}
