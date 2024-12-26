package AppFile;

import Team.Team;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
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
        AppFile uploadedAppFile = new AppFile(ownerID, name, true);
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

    public static int FileDownload(File fileToDownload, String userID)
    {
        String path = "src/Downloads/" + userID + "/" + fileToDownload.getName();
        File downloadedFile = new File(path);

        Path fileToDownloadPath = Paths.get(fileToDownload.getPath());
        Path downloadedFilePath = Paths.get(downloadedFile.getPath());

        try
        {
            downloadedFile.mkdirs();
            downloadedFile.createNewFile();
            Files.copy(fileToDownloadPath, downloadedFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("indirildi");
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

    public static List<File> GetTeamFiles(List<Team> TeamList)
    {
        List<File> files = new ArrayList<>();

        for(Team team : TeamList)
        {
            String directoryPath = "src/TeamFiles/" + team.getId();
            File directory = new File(directoryPath);

            File[] fileArray = directory.listFiles();
            if(fileArray != null)
            {
                files.addAll(Arrays.asList(fileArray));
            }
        }

        return files;
    }

    public static int EditFile(File file)
    {
        try
        {
            Desktop.getDesktop().edit(file);
            return ErrorCodes.SUCCESSFUL.ordinal();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static int ShareFile(File fileToShare, String teamID, String name)
    {
        AppFile sharedAppFile = new AppFile(teamID, name, false);
        File sharedFile = new File(sharedAppFile.getPath());

        Path fileToSharePath = Paths.get(fileToShare.getPath());
        Path sharedFilePath = Paths.get(sharedFile.getPath());

        try
        {
            sharedFile.mkdirs();
            sharedFile.createNewFile();
            Files.copy(fileToSharePath, sharedFilePath, StandardCopyOption.REPLACE_EXISTING);
            return ErrorCodes.SUCCESSFUL.ordinal();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return ErrorCodes.FAILED.ordinal();
        }
    }
}
