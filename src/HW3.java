import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class HW3 {

    public static void main(String[] args) throws IOException, SQLException {
        int choice;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do
        {
            System.out.println("Welcome to ArtBase Gallery.");
            System.out.println("1. Add data");
            System.out.println("2. Update data");
            System.out.println("3. Get report");
            System.out.println("4. Exit program");
            System.out.print("Please enter your selection: ");

            choice = Integer.parseInt(reader.readLine());

            switch (choice)
            {
                case 1:
                    addData();
                    break;
                case 2:
                    updateData();
                    break;
                case 3:
                    getReport();
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
        while (choice != 4);
    }

    private static void getReport() throws IOException, SQLException {
        String selectquery = "SELECT * FROM ";
        int choice;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do
        {
            System.out.println("Which report you want to get?");
            System.out.println("1. The list of artists");
            System.out.println("2. The list of artworks");
            System.out.println("3. The list of groups");
            System.out.println("4. The list of classify entries");
            System.out.println("5. The list of like group entries");
            System.out.println("6. The list of like artist entries");
            System.out.println("7. Back to previous menu");
            System.out.print("Please enter your selection: ");

            choice = Integer.parseInt(reader.readLine());

            switch (choice)
            {
                case 1:
                    getData(selectquery);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
        while (choice != 7);


    }

    private static void getData(String query) throws SQLException {
        ResultSet rs;

        DBConnect.connect();

        rs = DBConnect.query(query, false);   // Running the query and get the set of result

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

        DBConnect.disconnect();
    }

    private static void addData() throws IOException {
        String insertquery = "INSERT INTO ";

        int choice;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do
        {
            System.out.println("Which data you want to get?");
            System.out.println("1. Add an artist");
            System.out.println("2. Add a customer");
            System.out.println("3. Add an artwork");
            System.out.println("4. Add records");
            System.out.println("5. Back to previous menu");
            System.out.print("Please enter your selection: ");

            choice = Integer.parseInt(reader.readLine());

            switch (choice)
            {
                case 1:
                    exeQuery(insertquery);
                    break;
                case 2:
                    exeQuery(insertquery);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
        while (choice != 5);
    }

    private static void updateData()
    {
        String updatequery = "UPDATE ";

        exeQuery(updatequery);
    }

    private static void exeQuery(String query)
    {
        DBConnect.connect();

        DBConnect.query(query, true);

        System.out.println("Query done!");

        DBConnect.disconnect();
    }
}
