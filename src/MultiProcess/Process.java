package MultiProcess;

import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Process
{
    public static void StartSignInOut()
    {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder process = new ProcessBuilder(
                        "java", "-cp",
                        System.getProperty("java.class.path"),
                        "LOG.SignInOut_LOG");

                process.directory(new File("C:\\3.Yıl\\1.Dönem\\YazılımGeliştirme1\\FileShareSystem"));
                process.inheritIO();
                process.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }

    public static void StartBackup()
    {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder process = new ProcessBuilder(
                        "java", "-cp",
                        System.getProperty("java.class.path"),
                        "AppFile.BackupFiles");

                process.directory(new File("C:\\3.Yıl\\1.Dönem\\YazılımGeliştirme1\\FileShareSystem"));
                process.inheritIO();
                process.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }

    public static void StartWatcher()
    {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder process = new ProcessBuilder(
                        "java", "-cp",
                        System.getProperty("java.class.path"),
                        "AppFile.FileWatcher");

                process.directory(new File("C:\\3.Yıl\\1.Dönem\\YazılımGeliştirme1\\FileShareSystem"));
                process.inheritIO();
                process.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }

    public static void StartAllProcess()
    {
        StartSignInOut();
        StartBackup();
        StartWatcher();
    }
}
