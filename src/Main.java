import GUI.GUI;
import LOG.SignInOut_LOG;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main
{
    public static void main(String[] args)
    {
        SignInOut_LOG.InitializeFile();
        GUI.InitializeFrame();
    }
}