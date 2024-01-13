import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RelationalReadQuery extends ReadQuery {
    public RelationalReadQuery(String query, String tableId) {
        super(query, tableId);
    }

    @Override
    void execute() {

    }

    @Override
    void executeAndStore(Connection conn) {
        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ) {
            printResultSet(rs);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
