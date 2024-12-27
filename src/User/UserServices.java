package User;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Objects;

//User's services that will be used in GUI.
public class UserServices
{
    //The codes or error codes that functions will return.
    enum ErrorCodes
    {
        INVALID_USERNAME_ID,
        INVALID_PASSWORD,
        SUCCESSFUL
    }

    //Checks id and username then registers user.
    public static int Register(String username, String password)
    {
        User user = new User(username,password,true);

        if(!UserDBServices.isUserNameExist(username) && !UserDBServices.isUserIDExist(user.getId()))
        {
            UserDBServices.CreateUser(user);
            return ErrorCodes.SUCCESSFUL.ordinal();
        }
        else
        {
            return ErrorCodes.INVALID_USERNAME_ID.ordinal();
        }
    }

    //Checks username and password then signs in user.
    public static int SignIn(String username, String password)
    {
        User user = UserDBServices.GetUserWname(username);

        if(user != null && BCrypt.checkpw(password,user.getPassword()))
        {
            return ErrorCodes.SUCCESSFUL.ordinal();
        }
        else if(user == null)
        {
            return ErrorCodes.INVALID_USERNAME_ID.ordinal();
        }
        else
        {
            return ErrorCodes.INVALID_PASSWORD.ordinal();
        }
    }

    public static List<User> AllUsersExceptOne(String ID)
    {
        List<User> allUserList = UserDBServices.GetAllUser();

        allUserList.removeIf(user -> Objects.equals(user.getId(), ID));

        return allUserList;
    }

    public static int ChangeUsername(String ID, String username)
    {
        if(UserDBServices.isUserNameExist(username))
        {
            UserDBServices.UpdateUser(ID, "username", username);
            return ErrorCodes.SUCCESSFUL.ordinal();
        }
        else
        {
            return ErrorCodes.INVALID_USERNAME_ID.ordinal();
        }

    }

    public static int ChangePassword(String ID, String password)
    {
        User user = UserDBServices.GetUser(ID);
        if(user != null)
        {
            user.HashandSetPassword(password);

            UserDBServices.UpdateUser(ID, "password", user.getPassword());
            return ErrorCodes.SUCCESSFUL.ordinal();
        }
        else
        {
            return ErrorCodes.INVALID_USERNAME_ID.ordinal();
        }
    }
}
