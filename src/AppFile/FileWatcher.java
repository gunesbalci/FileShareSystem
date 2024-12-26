package AppFile;

import org.apache.commons.io.monitor.*;

import java.io.File;

public class FileWatcher
{
    public static void main(String[] args) throws Exception
    {
        File directory = new File("src/User_Team_Files");

        FileAlterationObserver observer = getFileAlterationObserver(directory);

        FileAlterationMonitor monitor = new FileAlterationMonitor(500);
        monitor.addObserver(observer);

        monitor.start();
    }

    private static FileAlterationObserver getFileAlterationObserver(File directory)
    {
        FileAlterationObserver observer = new FileAlterationObserver(directory);

        observer.addListener(new FileAlterationListenerAdaptor()
        {
            @Override
            public void onFileCreate(File file)
            {
                FileServices.BackUpFile();
            }

            @Override
            public void onFileDelete(File file)
            {
                FileServices.BackUpFile();
            }

            @Override
            public void onFileChange(File file)
            {
                FileServices.BackUpFile();
            }
        });
        return observer;
    }
}
