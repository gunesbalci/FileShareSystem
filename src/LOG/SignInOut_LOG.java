package LOG;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Scanner;
import MultiProcess.Process;

public class SignInOut_LOG
{
    private static File SignInOutLogs = new File("src/LOG/Files/SignInOutLOG.txt");

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
        try(RandomAccessFile fileReader = new RandomAccessFile(SignInOutLogs, "r"))
        {
            fileReader.seek(LastReadPosition.GetLastReadPosition(0));

            Dictionary<String, Integer> failCount = new Hashtable<>();
            Dictionary<String, LocalDateTime> lastFailedTime = new Hashtable<>();

            String singleLog = fileReader.readLine();
            while(singleLog != null)
            {
                String[] logContent = singleLog.split("\\.\\.\\.");

                if(Objects.equals(logContent[1], "signIn") && Objects.equals(logContent[2], "failed"))
                {
                    LocalDateTime recentFailedTime = LocalDateTime.parse(logContent[3]);
                    if(lastFailedTime.get(logContent[0]) != null)
                    {
                        Duration duration = Duration.between(lastFailedTime.get(logContent[0]),recentFailedTime);
                        if(duration.toMinutes() <= 2)
                        {
                            Integer count = failCount.get(logContent[0]);
                            count++;
                            failCount.put(logContent[0], count);
                            if(count > 3)
                            {
                                Abnormal_LOG.LogAnomalywithUserID(logContent[0], "TOO_MANY_FAILED_SIGN_IN");
                                failCount.put(logContent[0], 0);
                            }
                        }
                        else
                        {
                            failCount.put(logContent[0], 1);
                        }
                        lastFailedTime.put(logContent[0], recentFailedTime);
                    }
                    else
                    {
                        lastFailedTime.put(logContent[0], recentFailedTime);
                        failCount.put(logContent[0], 1);
                    }
                }
                singleLog = fileReader.readLine();
            }
            LastReadPosition.WriteLastReadPosition(0, fileReader.getFilePointer());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        FileServices.InitializeAllFiles();
        readFile();
    }
}
