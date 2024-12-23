package LOG;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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

    public static void readFile()
    {
        try
        {
            Scanner fileReader = new Scanner(SignInOutLogs);
            int failCount = 0;
            LocalDateTime lastFailedTime = null;

            while(fileReader.hasNextLine())
            {
                String singleLog =fileReader.nextLine();
                String[] logContent = singleLog.split("\\.\\.\\.");

                if(logContent[1] == "signIn" && logContent[2] == "failed")
                {
                    LocalDateTime recentFailedTime = LocalDateTime.parse(logContent[3]);
                    if(lastFailedTime != null)
                    {
                        Duration duration = Duration.between(lastFailedTime,recentFailedTime);
                        if(duration.toMinutes() <= 2)
                        {
                            failCount++;
                            if(failCount > 3)
                            {
                                System.out.println("ANOMALY");
                            }
                        }
                        else
                        {
                            failCount = 0;
                        }
                    }
                    else
                    {
                        lastFailedTime = recentFailedTime;
                    }

                }
            }

        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        readFile();
    }
}
