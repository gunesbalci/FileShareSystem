package Notification;

public class NotificationServices
{
    enum ErrorCodes
    {
        SUCCESSFUL,
        FAILED
    }
    public static int SendNotification(String receiver, String type, String sender)
    {
        Notification notification = new Notification(receiver,type,sender);
        if(!NotificationDBServices.isIDExist(notification.getId()))
        {
            NotificationDBServices.CreateNotification(notification);
            return ErrorCodes.SUCCESSFUL.ordinal();
        }
        else
        {
            return ErrorCodes.FAILED.ordinal();
        }
    }
}
