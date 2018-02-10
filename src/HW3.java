import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class HW3 {

    public static void main(String[] args) throws IOException, SQLException
    {
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

    private static void getReport() throws IOException, SQLException
    {
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
                    displayData(String.format("%sARTIST;", selectquery));
                    break;
                case 2:
                    displayData(String.format("%sARTWORK;", selectquery));
                    break;
                case 3:
                    displayData(String.format("%sARTBASE.GROUP;", selectquery));
                    break;
                case 4:
                    displayData(String.format("%sCLASSIFY;", selectquery));
                    break;
                case 5:
                    displayData(String.format("%sLIKE_GROUP;", selectquery));
                    break;
                case 6:
                    displayData(String.format("%sLIKE_ARTIST;", selectquery));
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

    private static void displayData(String query) throws SQLException
    {
        ResultSet rs;

        DBConnect.connect();

        rs = DBConnect.query(query, false);

        if (!rs.isBeforeFirst())
        {
            System.out.println("No data!");
        }
        else
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                if (i < rsmd.getColumnCount())
                {
                    System.out.print(rsmd.getColumnName(i).toUpperCase() + "  ");
                }
                else
                {
                    System.out.println(rsmd.getColumnName(i).toUpperCase());
                }
            }
            while (rs.next())
            {
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    if (i < rsmd.getColumnCount())
                    {
                        System.out.print(rs.getString(i) + "; ");
                    }
                    else
                    {
                        System.out.println(rs.getString(i));
                    }
                }
            }
        }

        DBConnect.disconnect();
    }

    private static void addData() throws IOException, SQLException
    {
        String insertquery = "INSERT INTO ";

        int choice;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do
        {
            System.out.println("Which data you want to get?");
            System.out.println("1. Add an artist");
            System.out.println("2. Add a customer");
            System.out.println("3. Add an artwork");
            System.out.println("4. Add records to LIKE_GROUP");
            System.out.println("5. Back to previous menu");
            System.out.print("Please enter your selection: ");

            choice = Integer.parseInt(reader.readLine());

            switch (choice)
            {
                case 1:
                    System.out.print("Enter artist's name: ");
                    String aname = reader.readLine();
                    System.out.print("Enter birthplace: ");
                    String birthplace = reader.readLine();
                    System.out.print("Enter age: ");
                    int age = Integer.parseInt(reader.readLine());
                    System.out.print("Enter style: ");
                    String style = reader.readLine();
                    exeQuery(String.format("%sARTIST VALUES ('%s', '%s', %d, '%s');", insertquery, aname, birthplace, age, style));
                    break;

                case 2:
                    System.out.print("Enter customer's name: ");
                    String cname = reader.readLine();
                    System.out.print("Enter address: ");
                    String address = reader.readLine();
                    System.out.print("Enter amount: ");
                    float amount = Float.parseFloat(reader.readLine());
                    exeQuery(String.format("%sCUSTOMER (cname, address, amount) VALUES ('%s', '%s', %f);", insertquery, cname, address, amount));
                    break;

                case 3:
                    System.out.print("Enter artwork's title: ");
                    String title = reader.readLine();
                    System.out.print("Enter year: ");
                    int year = Integer.parseInt(reader.readLine());
                    System.out.print("Enter type: ");
                    String type = reader.readLine();
                    System.out.print("Enter price: ");
                    float price = Float.parseFloat(reader.readLine());
                    System.out.print("Enter artist's name: ");
                    String art_aname = reader.readLine();
                    System.out.print("Enter the group name (seperate using \";\" if more than 1): ");
                    String groupname = reader.readLine();
                    String[] arr_groupname = groupname.split(";");


                    for (int i = 0; i < arr_groupname.length; i++)
                    {
                        boolean check = checkData(String.format("SELECT * FROM ARTBASE.GROUP WHERE gname = '%s';", arr_groupname[i].trim()));

                        if (!check)
                        {
                            exeQuery(String.format("%sARTBASE.GROUP VALUES ('%s');", insertquery, arr_groupname[i].trim()));
                        }
                    }

                    exeQuery(String.format("%sARTWORK VALUES ('%s', %d, '%s', %f, '%s');", insertquery, title, year, type, price, art_aname));


                    for (int i = 0; i < arr_groupname.length; i++)
                    {
                        exeQuery(String.format("%sCLASSIFY VALUES ('%s', '%s');", insertquery, title, arr_groupname[i].trim()));
                    }

                    break;

                case 4:
                    System.out.print("Enter customer name: ");
                    String cusname = reader.readLine();
                    System.out.print("Enter the group name: ");
                    String grname = reader.readLine();

                    int custid = Integer.parseInt(getData(String.format("SELECT custid FROM CUSTOMER WHERE cname = '%s';", cusname))[0]);

                    boolean check2 = checkData(String.format("SELECT * FROM LIKE_GROUP WHERE %d AND group_gname = '%s';", custid, grname));

                    if (!check2)
                    {
                        exeQuery(String.format("%sLIKE_GROUP VALUES (%d, '%s');", insertquery, custid, grname));
                    }

                    String[] artworks = getData(String.format("SELECT * FROM CLASSIFY WHERE group_gname = '%s';", grname));

                    String[] artists = new String[artworks.length];

                    for (int i = 0; i < artists.length; i++)
                    {
                        artists[i] = getData(String.format("SELECT artist_aname FROM ARTWORK WHERE title = '%s';", artworks[i]))[0];
                    }

                    for (int i = 0; i < artists.length; i++)
                    {
                        check2 = checkData(String.format("SELECT * FROM LIKE_ARTIST WHERE customer_custid = %d AND artist_aname = '%s';", custid, artists[i]));

                        if (!check2)
                        {
                            exeQuery(String.format("%sLIKE_ARTIST VALUES (%d, '%s');", insertquery, custid, artists[i]));
                        }
                    }
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

    private static void updateData() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter artist's name: ");
        String aname = reader.readLine();
        System.out.print("Enter the new style: ");
        String style = reader.readLine();

        exeQuery(String.format("UPDATE ARTIST SET style = '%s' WHERE aname = '%s';", style, aname));
    }

    private static void exeQuery(String query)
    {
        DBConnect.connect();

        DBConnect.query(query, true);

        System.out.println("Query done!");

        DBConnect.disconnect();
    }

    private static boolean checkData(String query) throws SQLException
    {
        ResultSet rs;
        boolean value = false;

        DBConnect.connect();

        rs = DBConnect.query(query, false);

        if (rs.isBeforeFirst())
        {
            value = true;
        }

        DBConnect.disconnect();

        return value;
    }

    private static String[] getData(String query) throws SQLException
    {
        ResultSet rs;
        String[] value = null;

        DBConnect.connect();

        rs = DBConnect.query(query, false);

        if (rs.isBeforeFirst())
        {
            rs.last();
            int count = rs.getRow();
            value = new String[count];
            rs.beforeFirst();
            for (int i = 0; i < count; i++)
            {
                rs.next();
                value[i] = rs.getString(1);
            }
        }

        DBConnect.disconnect();

        return value;
    }

}
