import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by bennett on 3/18/15.
 */
public class TupleStore {

    private long timestamp;
    private Connection connection;

    public TupleStore() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        connection = null;
        try {

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:bayou.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // servers
            statement.executeUpdate("drop table if exists servers");
            statement.executeUpdate("create table servers (alias string)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public long insertionPoint(WriteStamp writeStamp) {
        return 0;
    }

    public void rollback(long timestamp) {
        // TODO undo to timestamp
    }

}
