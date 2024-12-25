package AppFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileServices
{
    enum ErrorCodes
    {
        SUCCESSFUL,
        FAILED,
        INVALID_PASSWORD
    }

    public static int FileUpload(File fileToUpload, String ownerID, String name)
    {
        AppFile uploadedAppFile = new AppFile(ownerID, name);
        File uploadedFile = new File(uploadedAppFile.getPath());

        Path fileToUploadPath = Paths.get(fileToUpload.getPath());
        Path uploadedFilePath = Paths.get(uploadedAppFile.getPath());

        try
        {
            uploadedFile.mkdirs();
            uploadedFile.createNewFile();
            Files.copy(fileToUploadPath, uploadedFilePath, StandardCopyOption.REPLACE_EXISTING);
            return ErrorCodes.SUCCESSFUL.ordinal();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return ErrorCodes.FAILED.ordinal();
        }
    }

    public static File[] GetUserFiles(String ownerID)
    {
        String directoryPath = "src/UserFiles/" + ownerID;
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        return files;
    }
}
