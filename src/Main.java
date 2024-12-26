import GUI.GUI;
import LOG.FileServices;

import LOG.SignInOut_LOG;
import MultiProcess.Process;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main
{
    public static void main(String[] args)
    {
        FileServices.InitializeAllFiles();
        GUI.InitializeFrame();
        Process.StartAllProcess();
    }
}