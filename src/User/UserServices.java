package User;

import org.springframework.security.crypto.bcrypt.BCrypt;

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
}
