package LOG;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

public class PasswordRequest_LOG
{
    private static File PasswordRequestLogs = new File("src/LOG/Files/PasswordRequestLOG.txt");

    public static void InitializeFile()
    {
        try
        {
            PasswordRequestLogs = new File("src/LOG/Files/PasswordRequestLOG.txt");
            PasswordRequestLogs.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void LogRequest(String id, String type)
    {
        String formattedDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        try
        {
            FileWriter fileWriter = new FileWriter(PasswordRequestLogs, true);

            String LogString = id + "...";
            LogString += type + "...";
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
        try(RandomAccessFile fileReader = new RandomAccessFile(PasswordRequestLogs, "r"))
        {
            fileReader.seek(LastReadPosition.GetLastReadPosition(3));

            Dictionary<String, Integer> requestCount = new Hashtable<>();
            Dictionary<String, LocalDateTime> lastRequestTime = new Hashtable<>();

            String singleLog = fileReader.readLine();
            while(singleLog != null)
            {
                String[] logContent = singleLog.split("\\.\\.\\.");

                if(Objects.equals(logContent[1], "password_request"))
                {
                    LocalDateTime recentFailedTime = LocalDateTime.parse(logContent[2]);
                    if(lastRequestTime.get(logContent[0]) != null)
                    {
                        Duration duration = Duration.between(lastRequestTime.get(logContent[0]),recentFailedTime);
                        if(duration.toMinutes() <= 2)
                        {
                            Integer count = requestCount.get(logContent[0]);
                            count++;
                            requestCount.put(logContent[0], count);
                            if(count > 3)
                            {
                                Abnormal_LOG.LogAnomalywithUserID(logContent[0], "TOO_MANY_SENT_REQUEST");
                                requestCount.put(logContent[0], 0);
                            }
                        }
                        else
                        {
                            requestCount.put(logContent[0], 1);
                        }
                        lastRequestTime.put(logContent[0], recentFailedTime);
                    }
                    else
                    {
                        lastRequestTime.put(logContent[0], recentFailedTime);
                        requestCount.put(logContent[0], 1);
                    }
                }
                singleLog = fileReader.readLine();
            }
            LastReadPosition.WriteLastReadPosition(3, fileReader.getFilePointer());
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
