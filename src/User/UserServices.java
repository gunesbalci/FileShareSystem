package User;

import java.sql.*;
import java.util.Objects;

import MyConnection.MyConnection;

import javax.xml.transform.Source;

public class UserServices
{
    private static MyConnection MC;

    public static void CreateUser(String username, String password)
    {
        User user = new User(username, password, true);

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            stmt.execute(user.getInsertString());


            connection.close();

            System.out.println("user created.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static User GetUser(String id)
    {
        String SelectString = "SELECT * FROM " + MyConnection.getUserTable();
        SelectString += " WHERE id = \"" + id + "\";";

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SelectString);

            String username;
            String password;

            if(rs.next())
            {
                username = rs.getString("username");
                password = rs.getString("password");
            }
            else
            {
                return null;
            }

            rs.close();
            connection.close();

            User user = new User(username, password, false);
            return user;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void DeleteUser(String id)
    {
        String DeleteString = "DELETE FROM " + MyConnection.getUserTable();
        DeleteString += " WHERE id = \"" + id + "\";";

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            stmt.execute(DeleteString);

            System.out.println("User deleted.");

            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void UpdateUser(String id, String columnToUpdate, String update)
    {
        String UpdateString = "UPDATE " + MyConnection.getUserTable();
        UpdateString += " SET " + columnToUpdate;
        if(!Objects.equals(columnToUpdate, "username") && !Objects.equals(columnToUpdate, "password"))
        {
            int updateINT = Integer.parseInt(update);

            UpdateString += " = " + updateINT;
        }
        else
        {
            UpdateString += " = \"" + update + "\"";
        }
        UpdateString += " WHERE id = \"" + id + "\";";

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            stmt.execute(UpdateString);

            System.out.println("User updated.");

            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
