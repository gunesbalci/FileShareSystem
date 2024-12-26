package AppFile;

import java.nio.file.Path;

public class AppFile
{
    private String path;
    private String ownerID;
    private String name;

    public AppFile(String ownerID, String name, boolean UserFile)
    {
        setOwnerID(ownerID);
        setName(name);
        if(UserFile)
        {
            setPath(ownerID, name);
        }
        else
        {
            setTeamPath(ownerID,name);
        }
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String ownerID, String name)
    {
        this.path = "src/UserFiles/" + ownerID + "/" + name;
    }

    public void setTeamPath(String teamID, String name)
    {
        this.path = "src/TeamFiles/" + teamID + "/" + name;
    }

    public String getOwnerID()
    {
        return ownerID;
    }

    public void setOwnerID(String ownerID)
    {
        this.ownerID = ownerID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}