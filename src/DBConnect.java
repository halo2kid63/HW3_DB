import java.io.*;
import java.sql.*;
import java.util.*;

public class DBConnect
{

    private static Connection conn = null;

    public static void connect()
    {

        try
        {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("db.properties");
            props.load(in);
            in.close();

            String myUrl = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");

            // Setup the connection with the DB
            conn = DriverManager.getConnection(myUrl, username, password);

        }
        catch (Exception e)
        {
            System.err.println("Got an exception when connect DB!");
            System.err.println(e.getMessage());
        }
    }

    public static ResultSet query(String query, boolean type)
    {
        Statement st;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            if (type)
            {
                st.executeUpdate(query);
            }
            else
            {
                rs = st.executeQuery(query);
            }
        }
        catch (SQLException e)
        {
            System.err.println("Got an exception when query DB!");
            System.err.println(e.getMessage());
        }

        return rs;

    }

    public static void disconnect()
    {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Got an exception when disconnect DB!");
            System.err.println(e.getMessage());
        }
    }
}