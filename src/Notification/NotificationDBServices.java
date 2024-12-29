package Notification;

import MyConnection.MyConnection;
import User.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NotificationDBServices
{
    private static MyConnection MC;

    public static void CreateNotification(Notification notification)
    {
        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            stmt.execute(notification.getInsertString());

            connection.close();

            System.out.println("notification created.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Notification GetNotification(String id)
    {
        String SelectString = "SELECT * FROM " + MC.getNotificationTable();
        SelectString += " WHERE ID = \"" + id + "\";";

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SelectString);

            String receiver;
            String type;
            String sender;

            if(rs.next())
            {
                receiver = rs.getString("receiver");
                type = rs.getString("type");
                sender = rs.getString("sender");
            }
            else
            {
                return null;
            }

            rs.close();
            connection.close();

            return new Notification(id,receiver,type,sender);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Notification> GetNotification_byReceiver(String receiver)
    {
        List<Notification> notificationList = new ArrayList<>();

        String SelectString = "SELECT * FROM " + MC.getNotificationTable();
        SelectString += " WHERE receiver = \"" + receiver + "\";";

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SelectString);

            String id;
            String type;
            String sender;

            while (rs.next())
            {
                id = rs.getString("ID");
                type = rs.getString("type");
                sender = rs.getString("sender");
                Notification notification = new Notification(id,receiver,type,sender);
                notificationList.add(notification);
            }

            rs.close();
            connection.close();

            return notificationList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void DeleteNotification(String id)
    {
        String DeleteString = "DELETE FROM " + MC.getNotificationTable();
        DeleteString += " WHERE ID = \"" + id + "\";";

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            stmt.execute(DeleteString);

            System.out.println("Notification deleted.");

            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static boolean isIDExist(String id)
    {
        return GetNotification(id) != null;
    }
}
