package User;

import java.util.UUID;

import MyConnection.MyConnection;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class User
{
    private String id;
    private String username;
    private String password;
    private Integer max_fileSize;
    private int max_fileNum;
    private int role;

    //Constructs user. Can hash the password.
    public User(String username, String password, boolean HashPassword, Integer max_fileSize)
    {
        setId(UUID.randomUUID().toString());
        setUsername(username);
        setRole(0);
        if(HashPassword)
        {
            HashandSetPassword(password);
        }
        else
        {
            setPassword(password);
        }
        setMax_fileSize(max_fileSize);
    }

    //Returns the query to insert this user into database.
    public String getInsertString()
    {
        String InsertIntoString = "INSERT INTO " + MyConnection.getUserTable();
        InsertIntoString += " (id, username, password, role) VALUES (";
        InsertIntoString += "\"" + getId() + "\"" + ", ";
        InsertIntoString += "\"" + getUsername() + "\"" + ", ";
        InsertIntoString += "\"" + getPassword() + "\"" + ", ";
        InsertIntoString += "\"" + getRole() + "\"" + ");";

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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    //First hashes the given password then sets the hashed password.
    public void HashandSetPassword(String password)
    {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.password = hashedPassword;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Integer getMax_fileSize()
    {
        return max_fileSize;
    }

    public void setMax_fileSize(Integer max_fileSize)
    {
        this.max_fileSize = max_fileSize;
    }

    public int getMax_fileNum()
    {
        return max_fileNum;
    }

    public void setMax_fileNum(int max_fileNum)
    {
        this.max_fileNum = max_fileNum;
    }

    public int getRole()
    {
        return role;
    }

    public void setRole(int role)
    {
        this.role = role;
    }
}
