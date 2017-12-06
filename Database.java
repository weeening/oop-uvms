package bookingSystem;
import java.sql.*;

/**
 * Created by Wee Ning on 16-Sep-17.
 */
public class Database {
    Connection connect = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public Database() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://localhost/uvms","root","");
        } catch (Exception e) {
            throw e;
        }
    }

    public void close() throws Exception {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
//Final check 23 Sept 10.14 pm