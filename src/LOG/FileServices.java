package LOG;

public class FileServices
{
    public static void InitializeAllFiles()
    {
        SignInOut_LOG.InitializeFile();
        Team_LOG.InitializeFile();
        Abnormal_LOG.InitializeFile();
    }
}