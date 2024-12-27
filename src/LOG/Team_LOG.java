package LOG;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Team_LOG
{
    private static File TeamLogs = new File("src/LOG/Files/TeamLOG.txt");

    public static void InitializeFile()
    {
        try
        {
            TeamLogs = new File("src/LOG/Files/TeamLOG.txt");
            TeamLogs.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void LogTeam(String CreatorID, String TeamID)
    {
        String formattedDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        try
        {
            FileWriter fileWriter = new FileWriter(TeamLogs, true);

            String LogString = CreatorID + "...";
            LogString += TeamID + "...";
            LogString += "teamCreation" + "...";
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
