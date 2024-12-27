package LOG;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileShare_LOG
{
    private static File fileShareLogs = new File("src/LOG/Files/fileShareLOG.txt");

    //Creates and initializes the log file.
    public static void InitializeFile()
    {
        try
        {
            fileShareLogs = new File("src/LOG/Files/fileShareLOG.txt");
            fileShareLogs.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void LogFileShare(String ID, String status, File sharedFile)
    {
        String finishTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        long size = FileUtils.sizeOfDirectory(sharedFile);

        try
        {
            if(fileShareLogs != null)
            {
                FileWriter fileWriter = new FileWriter(fileShareLogs, true);

                String LogString = ID + "...";
                LogString += "fileShared" + "...";
                LogString += status + "...";
                LogString += sharedFile.getPath() + "...";
                LogString += size + "...";
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
}
