package LOG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LastReadPosition
{
    private static File LRP_backup_file = new File("src/LOG/LastReadPositions/LRP_backup.txt"); //0
    private static File LRP_sharedownload_file = new File("src/LOG/LastReadPositions/LRP_sharedownload.txt"); //1
    private static File LRP_signinout_file = new File("src/LOG/LastReadPositions/LRP_signinout.txt"); //2
    private static File LRP_request_file = new File("src/LOG/LastReadPositions/LRP_request.txt");//3
    private static String LRP_backup;
    private static String LRP_sharedownload;
    private static String LRP_signinout;
    private static String LRP_request;

    public static long GetLastReadPosition(int from)
    {
        long lastReadPosition;
        setLRP(from);

        switch (from)
        {
            case 0:
                lastReadPosition = Long.parseLong(LRP_backup);
                break;
            case 1:
                lastReadPosition = Long.parseLong(LRP_sharedownload);
                break;
            case 2:
                lastReadPosition = Long.parseLong(LRP_signinout);
                break;
            default:
                lastReadPosition = Long.parseLong(LRP_request);
        }

        return lastReadPosition;
    }

    public static void WriteLastReadPosition(int to, long lastReadPosition)
    {
        try
        {
            FileWriter fileWriter;
            switch (to)
            {
                case 0:
                    fileWriter = new FileWriter(LRP_backup_file);
                    break;
                case 1:
                    fileWriter = new FileWriter(LRP_sharedownload_file);
                    break;
                case 2:
                    fileWriter = new FileWriter(LRP_signinout_file);
                    break;
                default:
                    fileWriter = new FileWriter(LRP_request_file);
            }

            fileWriter.write(String.valueOf(lastReadPosition));
            fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void setAllLastReadPositions()
    {
        setLRP(0);
        setLRP(1);
        setLRP(2);
        setLRP(3);
    }

    public static void setLRP(int from)
    {
        try
        {
            Scanner fileReader;
            switch (from)
            {
                case 0:
                    fileReader = new Scanner(LRP_backup_file);
                    LRP_backup = fileReader.nextLine();
                    break;
                case 1:
                    fileReader = new Scanner(LRP_sharedownload_file);
                    LRP_sharedownload = fileReader.nextLine();
                    break;
                case 2:
                    fileReader = new Scanner(LRP_signinout_file);
                    LRP_signinout = fileReader.nextLine();
                    break;
                case 3:
                    fileReader = new Scanner(LRP_request_file);
                    LRP_request = fileReader.nextLine();
                    break;
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
