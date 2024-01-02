import java.sql.Connection;

public abstract class ReadQuery extends Query {
    String tableId;
    public ReadQuery(String query, String tableId) {
        super(query);
        this.tableId = tableId;
    }

    @Override
    public void executeAndStore(Connection conn) {
        return;
    }
    public String getTableId() {
        return tableId;
    }

//    @Override
//    public String toString() {
//        return tableId + ":\n" + query;
//    }
}
