package LOG;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class SignInOut_LOG
{
    private static File SignInOutLogs;

    //Creates and initializes the log file.
    public static void InitializeFile()
    {
        try
        {
            SignInOutLogs = new File("src/LOG/Files/SignInOutLOG.txt");
            SignInOutLogs.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //Logs sign in action with user id, date, and action's status.
    public static void LogSignIn(String id, String status)
    {
        String formattedDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        try
        {
            FileWriter fileWriter = new FileWriter(SignInOutLogs, true);

            String LogString = id + "...";
            LogString += "signIn" + "...";
            LogString += status + "...";
            LogString += formattedDateTime + "\n";

            fileWriter.write(LogString);
            fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //Logs sign out action with user id, date, and action's status.
    public static void LogSignOut(String id, String status)
    {
        String formattedDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        try
        {
            FileWriter fileWriter = new FileWriter(SignInOutLogs, true);

            String LogString = id + "...";
            LogString += "signOut" + "...";
            LogString += status + "...";
            LogString += formattedDateTime + "\n";

            fileWriter.write(LogString);
            fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
