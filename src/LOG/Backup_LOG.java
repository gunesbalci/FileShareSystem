package LOG;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Backup_LOG
{
    private static File backupLogs = new File("src/LOG/Files/BackupLOG.txt");

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
        long size = FileUtils.sizeOfDirectory(source);

        try
        {
            if(backupLogs != null)
            {
                FileWriter fileWriter = new FileWriter(backupLogs, true);

                String LogString = "backup" + "...";
                LogString += status + "...";
                LogString += source.getPath() + "...";
                LogString += size + "...";
                LogString += startTime + "...";
                LogString += finishTime + "\n";

                fileWriter.write(LogString);
                fileWriter.close();
            }
            else
            {
                System.out.println("Ben malım");
            }



        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
