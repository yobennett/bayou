import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by bennett on 3/19/15.
 */
public class BayouWrite {

/*
Bayou_Write (update, dependency_check, mergeproc) {
    IF (DB_Eval (dependency_check.query) <> dependency_check.expected_result)
    resolved_update = Interpret (mergeproc); ELSE
    resolved_update = update; DB_Apply (resolved_update);
}
*/
    private final String update;
    private final DependencyCheck dependencyCheck;
    private final MergeProc mergeProc;

    private Connection connection;

    public BayouWrite(String update, DependencyCheck dependencyCheck, MergeProc mergeProc) {
        this.update = update;
        this.dependencyCheck = dependencyCheck;
        this.mergeProc = mergeProc;

        connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
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

        run();
    }

    private void run() {
        System.out.println("BayouWrite run: " + update);

        // TODO run `update` in sqlite
        // i.e. statement.executeUpdate("???update???");

        if (update == null) {
            return;
        }

        // run query
        // compare to expected result
        // interpret mergeproc if they don't match
        // apply resolved update

        connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:bayou.db");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(dependencyCheck.query());
            while (rs.next()) {
                int rsSize = rs.getInt("count(*)");

                if (rsSize == dependencyCheck.expectedResult()) {
                    System.out.println("RUN " + update);
                    statement.executeUpdate(update);
                } else {
                    System.err.println("RUN MERGEPROC");
                }

            }

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

}
