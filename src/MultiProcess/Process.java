package MultiProcess;

import java.io.BufferedReader;
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

                ProcessBuilder process = new ProcessBuilder("java", "-cp", "C:\\3.Yıl\\1.Dönem\\YazılımGeliştirme1\\FileShareSystem\\out\\production\\FileShareSystem", "LOG.SignInOut_LOG");
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
}
