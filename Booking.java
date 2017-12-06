package bookingSystem;
import java.sql.*;

import static java.lang.System.*;

/**
 * Created by Wee Ning on 16-Sep-17.
 */
public class Booking extends Database{
    int bookingID;
    String bookingDate;
    String startTime;
    String endTime;
    Venue v = new Venue();
    Users u = new Users();
    String venueID = v.venueID;
    String userID = u.userID;
    Date transactDate;

    public Booking() throws Exception {
        super();
    }

    public void showBooking() throws Exception {
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from uvms.booking");
            while (resultSet.next()) {
                out.print(resultSet.getInt("bookingID") + " ");
                out.print(resultSet.getString("bookingDate") + " ");
                out.print(resultSet.getString("startTime") + " ");
                out.print(resultSet.getString("endTime") + " ");
                out.print(resultSet.getString("venueID") + " ");
                out.print(resultSet.getString("userID") + " ");
                out.print(resultSet.getDate("transactDate") + " ");
                out.println();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new Date(today.getTime());
    }

    public void addBooking(String bookingDate, String startTime, String endTime, String venueID, String userID) throws Exception
    {
        try {
                PreparedStatement ps = connect.prepareStatement("select MAX(bookingID) FROM uvms.booking");
                ResultSet rs = ps.executeQuery();
                rs.next();
                int x = rs.getInt(1);

                preparedStatement = connect.prepareStatement("insert into uvms.booking values(?,?,?,?,?,?,?)");
                preparedStatement.setInt(1,x+1);
                preparedStatement.setString(2, bookingDate);
                preparedStatement.setString(3, startTime);
                preparedStatement.setString(4, endTime);
                preparedStatement.setString(5, venueID);
                preparedStatement.setString(6, userID);
                preparedStatement.setDate(7, getCurrentDate());
                preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void deleteBooking(int bookingID) throws Exception
    {
        try {
            preparedStatement = connect.prepareStatement( " delete from uvms.booking where bookingID = ?");
            preparedStatement.setInt(1, bookingID);
            preparedStatement.executeUpdate();
            showBooking();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void editBooking(int bookingID, String editColumn, String editValue) throws Exception
    {
        try {
            if(editColumn.matches("bookingDate"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.booking SET bookingDate = ?, transactDate = ? WHERE bookingID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setDate(2, getCurrentDate());
                preparedStatement.setInt(3, bookingID);
                preparedStatement.executeUpdate();
            }
            else if(editColumn.matches("startTime"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.booking SET startTime = ?, transactDate = ? WHERE bookingID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setDate(2, getCurrentDate());
                preparedStatement.setInt(3, bookingID);
                preparedStatement.executeUpdate();
        }

            else if(editColumn.matches("endTime"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.booking SET endTime = ?, transactDate = ? WHERE bookingID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setDate(2, getCurrentDate());
                preparedStatement.setInt(3, bookingID);
                preparedStatement.executeUpdate();
            }

            else if(editColumn.matches("venueID"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.booking SET venueID = ?,transactDate = ? WHERE bookingID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setDate(2, getCurrentDate());
                preparedStatement.setInt(3, bookingID);
                preparedStatement.executeUpdate();
            }

            else if(editColumn.matches("userID"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.booking SET userID = ?,transactDate = ? WHERE bookingID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setDate(2, getCurrentDate());
                preparedStatement.setInt(3, bookingID);
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean checkBooking(String bookingDate, String startTime, String endTime) throws Exception {
        boolean canMakeBooking = true;
        try {
                preparedStatement = connect.prepareStatement("select bookingID from  booking where bookingDate = ? AND startTime <= ? AND endTime >= ?");
                preparedStatement.setString(1, bookingDate);
                preparedStatement.setString(2, startTime);
                preparedStatement.setString(3, endTime);
                ResultSet resultSet = preparedStatement.executeQuery(); //search for entries of each row

            canMakeBooking = !resultSet.next();
            }
         catch (Exception e) {
            throw e;
        }
        return canMakeBooking;
    }
}

// Final Check 23 sept 10.14 pm