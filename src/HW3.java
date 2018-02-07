import java.sql.ResultSet;
import java.sql.SQLException;

public class HW3 {

    public static void main(String[] args) throws SQLException
    {
        String query = "SELECT * FROM ARTIST";

        ResultSet rs;

        DBConnect.connect(); //Making connection to DB "ARTBASE"

        rs = DBConnect.query(query); // Running the query and get the set of result

        if (!rs.isBeforeFirst()) // Checking if there is any result return
        {
            System.out.println("No data");
        }
        else
        {
            while (rs.next()) // Print out result base on condition (here is column name "aname")
            {
                System.out.println(rs.getString("aname"));
            }
        }
        DBConnect.disconnect(); // Closing connection to DB
    }
}
