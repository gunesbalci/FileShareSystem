package MultiProcess;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Process
{
    static java.lang.Process process_Signinout;
    static java.lang.Process process_Backup;
    static java.lang.Process process_Watch;
    static java.lang.Process process_DownloadLOG;

    static ScheduledExecutorService scheduler_SignInOut;
    static ScheduledExecutorService scheduler_Backup;
    static ScheduledExecutorService scheduler_DownloadLOG;


    public static java.lang.Process StartSignInOut()
    {
        scheduler_SignInOut = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "java", "-cp",
                        System.getProperty("java.class.path"),
                        "LOG.SignInOut_LOG");

                processBuilder.directory(new File("C:\\3.Yıl\\1.Dönem\\YazılımGeliştirme1\\FileShareSystem"));
                processBuilder.inheritIO();
                process_Signinout = processBuilder.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler_SignInOut.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        return process_Signinout;
    }

    public static java.lang.Process StartBackup()
    {
        scheduler_Backup = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "java", "-cp",
                        System.getProperty("java.class.path"),
                        "AppFile.BackupFiles");

                processBuilder.directory(new File("C:\\3.Yıl\\1.Dönem\\YazılımGeliştirme1\\FileShareSystem"));
                processBuilder.inheritIO();
                process_Backup = processBuilder.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler_Backup.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        return process_Backup;
    }

    public static java.lang.Process StartWatcher()
    {
        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "java", "-cp",
                    System.getProperty("java.class.path"),
                    "AppFile.FileWatcher");

            processBuilder.directory(new File("C:\\3.Yıl\\1.Dönem\\YazılımGeliştirme1\\FileShareSystem"));
            processBuilder.inheritIO();
            process_Watch = processBuilder.start();
        }
        catch (RuntimeException | IOException e)
        {
            throw new RuntimeException(e);
        }

        return process_Watch;
    }

    public static java.lang.Process StartDownloadLOG()
    {
        scheduler_DownloadLOG = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "java", "-cp",
                        System.getProperty("java.class.path"),
                        "LOG.ShareUpload_LOG");

                processBuilder.directory(new File("C:\\3.Yıl\\1.Dönem\\YazılımGeliştirme1\\FileShareSystem"));
                processBuilder.inheritIO();
                process_DownloadLOG = processBuilder.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler_DownloadLOG.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        return process_DownloadLOG;
    }

    public static void StartAllProcess()
    {
        StartSignInOut();
        StartBackup();
        StartWatcher();
        StartDownloadLOG();
    }

    public static void KillAllProcess()
    {
        if (scheduler_SignInOut != null)
        {
            scheduler_SignInOut.shutdownNow();
        }
        if (scheduler_Backup != null)
        {
            scheduler_Backup.shutdownNow();
        }
        if (scheduler_DownloadLOG != null)
        {
            scheduler_DownloadLOG.shutdownNow();
        }

        if (process_Signinout != null)
        {
            process_Signinout.destroy();
        }
        if (process_Backup != null)
        {
            process_Backup.destroy();
        }
        if (process_Watch != null)
        {
            process_Watch.destroy();
        }
        if (process_DownloadLOG != null)
        {
            process_DownloadLOG.destroy();
        }
    }
}
