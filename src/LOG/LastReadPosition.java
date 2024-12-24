package LOG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LastReadPosition
{
    private static File lastReadPositionFile = new File("src/LOG/Files/LastReadPositionFile.txt");
    private static String[] allLastReadPositions = new String[2];

    public static long GetLastReadPosition(int from)
    {
        long lastReadPosition;
        setAllLastReadPositions();

        lastReadPosition = Long.parseLong(allLastReadPositions[from]);

        return lastReadPosition;
    }

    public static void WriteLastReadPosition(int to, long lastReadPosition)
    {
        setAllLastReadPositions();

        try
        {
            FileWriter fileWriter = new FileWriter(lastReadPositionFile);

            allLastReadPositions[to] = String.valueOf(lastReadPosition);
            String stringPositions = String.join("...", allLastReadPositions);

            fileWriter.write(stringPositions);
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
        try
        {
            Scanner fileReader = new Scanner(lastReadPositionFile);
            allLastReadPositions = fileReader.nextLine().split("\\.\\.\\.");
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
