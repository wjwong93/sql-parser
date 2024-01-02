import java.sql.Connection;

public abstract class GraphQuery extends Query {
    public GraphQuery(String query) {
        super(query);
    }

    @Override
    public void executeAndStore(Connection conn) {
        return;
    }
}

