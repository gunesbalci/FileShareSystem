//position 0 belongs to SignInOut_LOG
//position 1 belongs to Backup_LOG

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

public class ShareUpload_LOG
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
        long size = sharedFile.length();

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

    public static void LogFileDownload(String ID, String status, File downloadedFile)
    {
        String finishTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        long size = downloadedFile.length();

        try
        {
            if(fileShareLogs != null)
            {
                FileWriter fileWriter = new FileWriter(fileShareLogs, true);

                String LogString = ID + "...";
                LogString += "fileDownloaded" + "...";
                LogString += status + "...";
                LogString += downloadedFile.getPath() + "...";
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

    public static void readFile()
    {
        try(RandomAccessFile fileReader = new RandomAccessFile(fileShareLogs, "r"))
        {
            fileReader.seek(LastReadPosition.GetLastReadPosition(1));

            Dictionary<String, Integer> downloadCount = new Hashtable<>();
            Dictionary<String, LocalDateTime> lastDownloadedTime = new Hashtable<>();

            String singleLog = fileReader.readLine();
            while(singleLog != null)
            {
                String[] logContent = singleLog.split("\\.\\.\\.");

                if(Objects.equals(logContent[1], "fileDownloaded") && Objects.equals(logContent[2], "successful"))
                {
                    LocalDateTime recentFailedTime = LocalDateTime.parse(logContent[5]);
                    if(lastDownloadedTime.get(logContent[0]) != null)
                    {
                        Duration duration = Duration.between(lastDownloadedTime.get(logContent[0]),recentFailedTime);
                        if(duration.toMinutes() <= 2)
                        {
                            Integer count = downloadCount.get(logContent[0]);
                            count++;
                            downloadCount.put(logContent[0], count);
                            if(count > 3)
                            {
                                Abnormal_LOG.LogAnomalywithUserID(logContent[0], "TOO_MANY_DOWNLOADS");
                                downloadCount.put(logContent[0], 0);
                            }
                        }
                        else
                        {
                            downloadCount.put(logContent[0], 1);
                        }
                        lastDownloadedTime.put(logContent[0], recentFailedTime);
                    }
                    else
                    {
                        lastDownloadedTime.put(logContent[0], recentFailedTime);
                        downloadCount.put(logContent[0], 1);
                    }
                }
                singleLog = fileReader.readLine();
            }
            LastReadPosition.WriteLastReadPosition(1, fileReader.getFilePointer());
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
