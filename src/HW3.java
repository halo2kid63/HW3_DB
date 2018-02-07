import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class HW3 {

    public static void main(String[] args) throws SQLException
    {
        String query = "SELECT * FROM ARTIST";
        String query2 = "INSERT INTO ARTIST VALUES ('Picasco', 'Italia', 70, 'paint')";
        String query3 = "INSERT INTO CUSTOMER (cname, address, amount) VALUES ('Customer1', '3178 Pomona Ave, Pomona, CA, 96880', 100000.00)";
        boolean type = false;
        ResultSet rs;

        DBConnect.connect(); //Making connection to DB "ARTBASE"

        rs = DBConnect.query(query, type); // Running the query and get the set of result

        if (type)   //true == insert value; false == get value
        {
            System.out.println("Query done!");
        }
        else
        {
            if (!rs.isBeforeFirst()) // Checking if there is any result return
            {
                System.out.println("No data!");
            }
            else
            {
                ResultSetMetaData rsmd = rs.getMetaData();  //get the column name and print it
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    if (i < rsmd.getColumnCount())
                    {
                        System.out.print(rsmd.getColumnName(i) + "  ");
                    }
                    else
                    {
                        System.out.println(rsmd.getColumnName(i));
                    }
                }
                while (rs.next()) // Print out result
                {
                    for (int j = 1; j <= rsmd.getColumnCount(); j++)
                    {
                        if (j < rsmd.getColumnCount())
                        {
                            System.out.print(rs.getString(j) + "; ");
                        }
                        else
                        {
                            System.out.println(rs.getString(j));
                        }
                    }
                }
            }
        }

        DBConnect.disconnect(); // Closing connection to DB
    }
}
