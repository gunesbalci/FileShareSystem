package User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import MyConnection.MyConnection;

//User's database related services.
public class UserDBServices
{
    private static MyConnection MC;

    //Inserts the given user into database.
    public static void CreateUser(User user)
    {
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

    //Gets the user the given id belongs to from database.
    public static User GetUser(String id)
    {
        String SelectString = "SELECT * FROM " + MC.getUserTable();
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

    //Gets the user given username belongs to from database.
    public static User GetUserWname(String username)
    {
        String SelectString = "SELECT * FROM " + MC.getUserTable();
        SelectString += " WHERE username = \"" + username + "\";";

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SelectString);

            String id;
            String password;

            if(rs.next())
            {
                id = rs.getString("id");
                password = rs.getString("password");
            }
            else
            {
                return null;
            }

            rs.close();
            connection.close();

            User user = new User(username, password, false);
            user.setId(id);
            return user;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> GetAllUser()
    {
        String SelectString = "SELECT * FROM " + MC.getUserTable();

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SelectString);
            
            List<User> userList = new ArrayList<>();
            while(rs.next())
            {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String id = rs.getString("id");
                User user = new User(username, password, false);
                user.setId(id);
                userList.add(user);
            }

            rs.close();
            connection.close();

            return userList;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    //Deletes the user given id belongs to from database.
    public static void DeleteUser(String id)
    {
        String DeleteString = "DELETE FROM " + MC.getUserTable();
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

    //Updates users given attribute with given data from database.
    public static void UpdateUser(String id, String columnToUpdate, String update)
    {
        String UpdateString = "UPDATE " + MC.getUserTable();
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

    //Checks if the given id exist in the database or not.
    public static boolean isUserIDExist(String id)
    {
        return GetUser(id) != null;
    }

    //Checks if the given username exist in the database or not.
    public static boolean isUserNameExist(String name)
    {
        return GetUserWname(name) != null;
    }
}
