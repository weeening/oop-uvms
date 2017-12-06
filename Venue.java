package bookingSystem;
/**
 * Created by Wee Ning on 09-Sep-17.
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Venue extends Database{
    String venueID;

    public Venue() throws Exception
    {
        super();
    }

    public void addVenue(String venueID, String building, String room, int capacity) throws Exception
    {
        try {
            preparedStatement = connect.prepareStatement( "insert into uvms.venue values(?,?,?,?)");
            preparedStatement.setString(1, venueID);
            preparedStatement.setString(2, building);
            preparedStatement.setString(3,room);
            preparedStatement.setInt(4,capacity);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void deleteVenue(String venueID) throws Exception
    {
        try {
            preparedStatement = connect.prepareStatement( " delete from uvms.venue where venueID = ?");
            preparedStatement.setString(1, venueID);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void editVenue(String venueID, String editColumn, String editValue) throws Exception
    {
        try {
            if(editColumn.matches("building"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.venue SET building = ? WHERE venueID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setString(2, venueID);
                preparedStatement.executeUpdate();
            }
            else if(editColumn.matches("room"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.venue SET room = ? WHERE venueID = ?");
                preparedStatement.setString(1, editValue);
                preparedStatement.setString(2, venueID);
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void editVenue(String venueID, String editColumn, int editValue) throws Exception //polymorph editVenue method due to different values
    {
        try {
            if(editColumn.matches("capacity"))
            {
                preparedStatement = connect.prepareStatement("UPDATE uvms.venue SET capacity = ? WHERE venueID = ?");
                preparedStatement.setInt(1, editValue);
                preparedStatement.setString(2, venueID);
                preparedStatement.executeUpdate();
            }

        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public String venueDetails(String venueID) throws Exception
    {
        String venueVerify = "";
        try
        {
            PreparedStatement ps = connect.prepareStatement("SELECT building, room, capacity from uvms.venue WHERE venueID = ?");
            ps.setString(1,venueID);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) {
                for(int i = 1; i <= columnsNumber; i++)
                    venueVerify = venueVerify + ("  " + rs.getString(i));
            }

        } catch (Exception e) {
            throw e;
        }
        return venueVerify;
    }
}

//Final check 23 sept 11.22 pm