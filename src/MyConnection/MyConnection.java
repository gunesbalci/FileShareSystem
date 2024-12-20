package MyConnection;

public class MyConnection
{
    private static String connection = "jdbc:mysql://localhost:3306/FileSystem";
    private static String username = "root";
    private static String password = "Etherkalis44";
    private static String userTable = "user";

    public static String getMyConnection()
    {
        return connection;
    }

    public static String getUserTable()
    {
        return userTable;
    }

    public static String getUsername()
    {
        return username;
    }

    public static String getPassword()
    {
        return password;
    }
}
