import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
Jenna Hofseth
CPSC 2810 - Fall 2021
Gradebook Project
 */

public class DBUtil {

    private static Connection connection;

    private DBUtil() {}

    public static synchronized Connection getConnection(String user, String pass, boolean printConn) throws SQLException {
        if (connection != null) {
            return connection;
        }
        else {
            try {
                if(printConn) System.out.println("\nAttempting to connect... ");
                // set the db url, username, and password
                //schema was not specified in directions? was told by a classmate that /sys ending will auto choose default schema, so hopefully this works...
                String url = "jdbc:mysql://myawsdb.cno2zewghfii.us-east-1.rds.amazonaws.com/sys";
                Class.forName("com.mysql.cj.jdbc.Driver");

                // get and return connection
                connection = DriverManager.getConnection(
                        url, user, pass);
                if(printConn) System.out.println("Connected!");

                return connection;
            } catch (SQLException | ClassNotFoundException e) {
                throw new SQLException(e);
            }
        }
    }

    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("\nConnection closed.");
            } catch (SQLException e) {
                System.out.println("There was a SQL exception when attempting to close the connection.");
            } finally {
                connection = null;
            }
        }
    }
}