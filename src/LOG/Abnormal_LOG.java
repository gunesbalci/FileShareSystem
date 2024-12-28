package LOG;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Abnormal_LOG
{
    private static File AbnormalLogs = new File("src/LOG/Files/AbnormalLOG.txt");

    public static void InitializeFile()
    {
        try
        {
            AbnormalLogs = new File("src/LOG/Files/AbnormalLOG.txt");
            AbnormalLogs.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void LogAnomalywithUserID(String userID, String behaviourCode)
    {
        String formattedDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        try
        {
            if (AbnormalLogs == null)
            {
                throw new IllegalStateException("AbnormalLogs is not initialized!");
            }

            FileWriter fileWriter = new FileWriter(AbnormalLogs, true);

            String LogString = userID + "...";
            LogString += "abnormalBehaviour" + "...";
            LogString += behaviourCode + "...";
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

    public static void LogAnomaly(String behaviourCode)
    {
        String formattedDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        try
        {
            if (AbnormalLogs == null)
            {
                throw new IllegalStateException("AbnormalLogs is not initialized!");
            }

            FileWriter fileWriter = new FileWriter(AbnormalLogs, true);

            String LogString = "abnormalBehaviour" + "...";
            LogString += behaviourCode + "...";
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
