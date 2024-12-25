package AppFile;

import java.nio.file.Path;

public class AppFile
{
    private String path;
    private String ownerID;
    private String name;

    public AppFile(String ownerID, String name)
    {
        setOwnerID(ownerID);
        setName(name);
        setPath(ownerID, name);
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String ownerID, String name)
    {
        this.path = "src/UserFiles/" + ownerID + "/" + name + ".txt";
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


