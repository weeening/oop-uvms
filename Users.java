package bookingSystem;

import java.sql.*;

/**
 * Created by Wee Ning on 09-Sep-17.
 */
public class Users extends Database{
    String userID;
    String userPass;

    public Users() throws Exception
    {
       super();
    }

    public void addUsers(String userID, String userPass, String userName, String contactNo, String userStatus) throws Exception
    {
        try {
            preparedStatement = connect.prepareStatement( "insert into uvms.users values(?,?,?,?,?)");
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, userPass);
            preparedStatement.setString(3,userName);
            preparedStatement.setString(4, contactNo);
            preparedStatement.setString(5, userStatus);
            preparedStatement.executeUpdate();

        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void deleteUsers(String userID) throws Exception
    {
        try {
            preparedStatement = connect.prepareStatement( " delete from uvms.users where userID = ?");
            preparedStatement.setString(1, userID);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void editUsers(String userID, String editColumn, String editValue) throws Exception
    {
        try {
            if(editColumn.matches("userPass"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.users SET userPass = ? WHERE userID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setString(2, userID);
                preparedStatement.executeUpdate();
            }
            else if(editColumn.matches("userName"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.users SET userName = ? WHERE userID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setString(2, userID);
                preparedStatement.executeUpdate();
            }

            else if(editColumn.matches("contactNo"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.users SET contactNo = ? WHERE userID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setString(2, userID);
                preparedStatement.executeUpdate();
            }

            else if(editColumn.matches("userStatus"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.users SET userStatus = ? WHERE userID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setString(2, userID);
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean login(String userID, String userPass) throws SQLException {
        boolean loginOK = false;
        try {
            if (userID != null && userPass != null) {
                PreparedStatement preparedStatement = connect.prepareStatement("SELECT * from uvms.users WHERE userID = ? AND userPass = ?");
                preparedStatement.setString(1, userID);
                preparedStatement.setString(2, userPass);
                ResultSet rs = preparedStatement.executeQuery();

                loginOK = rs.next();

            }

            // You can also validate user by result size if its comes zero user is invalid else user is valid

        } catch (SQLException err) {
            throw err;
        }
        return loginOK;
    }

    public boolean adminLogin(String adminID, String adminPass) throws Exception {
        boolean loginOK = false;
        try {
            if (adminID != null && adminPass != null) {
                PreparedStatement preparedStatement = connect.prepareStatement("SELECT * from uvms.users WHERE userID = ? AND userPass = ?");
                preparedStatement.setString(1, userID);
                preparedStatement.setString(2, userPass);
                ResultSet rs = preparedStatement.executeQuery();

                loginOK = adminID.equals("admin") && adminPass.equals("admin");

            }

            // You can also validate user by result size if its comes zero user is invalid else user is valid

        } catch (Exception err) {
            throw err;
        }
        return loginOK;
    }

    public String userDetails(String userID) throws Exception
    {
        String userVerify = "";
        try
        {
            PreparedStatement ps = connect.prepareStatement("SELECT userID, userName, userStatus from uvms.users WHERE userID = ?");
            ps.setString(1,userID);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) {
                for(int i = 1; i <= columnsNumber; i++)
                    userVerify = userVerify + (rs.getString(i) + "    ");
            }

        } catch (Exception e) {
            throw e;
        }
        return userVerify;
    }
}
//Final check 23 Sept 11.21 pm