package LOG;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class SignInOut_LOG
{
    private static File SignInOutLogs;

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

    public static void LogSignIn(String id)
    {
        String formattedDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        try
        {
            FileWriter fileWriter = new FileWriter(SignInOutLogs, true);

            String LogString = id + "...";
            LogString += "signIn" + "...";
            LogString += formattedDateTime;

            fileWriter.write(LogString);
            fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void LogSignOut(String id)
    {
        String formattedDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        try
        {
            FileWriter fileWriter = new FileWriter(SignInOutLogs, true);

            String LogString = id + "...";
            LogString += "signOut" + "...";
            LogString += formattedDateTime;

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
