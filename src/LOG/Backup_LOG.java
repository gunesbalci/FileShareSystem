package LOG;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Backup_LOG
{
    private static File backupLogs;

    //Creates and initializes the log file.
    public static void InitializeFile()
    {
        try
        {
            backupLogs = new File("src/LOG/Files/BackupLOG.txt");
            backupLogs.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void LogBackup(String status, String startTime)
    {
        String finishTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        File source = new File("src/User_Team_Files");

        try
        {
            FileWriter fileWriter = new FileWriter(backupLogs, true);

            String LogString = "backup" + "...";
            LogString += status + "...";
            LogString += source.getPath() + "...";
            LogString += source.length() + "...";
            LogString += startTime + "...";
            LogString += finishTime + "\n";

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
