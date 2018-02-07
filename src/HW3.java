import java.sql.ResultSet;
import java.sql.SQLException;

public class HW3 {

    public static void main(String[] args) throws SQLException
    {
        String query = "SELECT * FROM ARTIST";

        ResultSet rs;

        DBConnect.connect();

        rs = DBConnect.query(query);

        if (!rs.next())
        {
            System.out.println("No data");
        }
        else
        {
            while (rs.next())
            {
                System.out.println(rs.getString("aname"));
            }
        }
        DBConnect.disconnect();
    }
}
