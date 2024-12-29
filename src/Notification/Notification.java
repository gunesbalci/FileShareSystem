package Notification;

import MyConnection.MyConnection;

import java.util.UUID;

public class Notification
{
    private String id;
    private String receiver;
    private String type;
    private String sender;

    public Notification(String receiver, String type, String sender)
    {
        setId(UUID.randomUUID().toString());
        setReceiver(receiver);
        setType(type);
        setSender(sender);
    }

    public Notification(String ID, String receiver, String type, String sender)
    {
        setId(ID);
        setReceiver(receiver);
        setType(type);
        setSender(sender);
    }

    public String getInsertString()
    {
        String InsertIntoString = "INSERT INTO " + MyConnection.getNotificationTable();
        InsertIntoString += " (ID, receiver, type, sender) VALUES (";
        InsertIntoString += "\"" + getId() + "\"" + ", ";
        InsertIntoString += "\"" + getReceiver() + "\"" + ", ";
        InsertIntoString += "\"" + getType() + "\"" + ", ";
        InsertIntoString += "\"" + getSender() + "\"" + ");";

        return InsertIntoString;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }
}
