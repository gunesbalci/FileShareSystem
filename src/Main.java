import GUI.GUI;
import LOG.SignInOut_LOG;
import MultiProcess.Process;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main
{
    public static void main(String[] args)
    {
        SignInOut_LOG.InitializeFile();
        GUI.InitializeFrame();
        Process.StartSignInOut();
    }
}