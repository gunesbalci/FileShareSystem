package LOG;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void readFile()
    {
        try(RandomAccessFile fileReader = new RandomAccessFile(backupLogs, "r"))
        {
            fileReader.seek(LastReadPosition.GetLastReadPosition(0));

            String singleLog = fileReader.readLine();
            while(singleLog != null)
            {
                String[] logContent = singleLog.split("\\.\\.\\.");

                if(Objects.equals(logContent[1], "failed"))
                {
                    Abnormal_LOG.LogAnomaly("FAILED_BACKUP");
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
        readFile();
    }
}
