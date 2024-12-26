package Team;

import MyConnection.MyConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Team
{
    private String id;
    private String name;
    private List<String> memberIDList;

    public Team(){}

    public Team(String name)
    {
        memberIDList = new ArrayList<>();
        setId(UUID.randomUUID().toString());
        setName(name);
    }

    public Team(String name, String CreatorID)
    {
        memberIDList = new ArrayList<>();
        setId(UUID.randomUUID().toString());
        setName(name);
        addMember(CreatorID);
    }

    public String getTeamInsertString()
    {
        String InsertIntoString = "INSERT INTO " + MyConnection.getTeamTable();
        InsertIntoString += " (id, name) VALUES (";

        InsertIntoString += "\"" + getId() + "\"" + ", ";
        InsertIntoString += "\"" + getName() + "\"" + ");";

        return InsertIntoString;
    }

    public String getTeamMemberInsertString()
    {
        String InsertIntoString = "INSERT INTO " + MyConnection.getTeamMemberTable();
        InsertIntoString += " (id, memberID) VALUES ";

        for(String memberID: getMemberIDList())
        {
            InsertIntoString += "(\"" + getId() + "\"" + ", ";
            InsertIntoString += "\"" + memberID + "\"" + "),";
        }
        InsertIntoString = InsertIntoString.substring(0, InsertIntoString.length()-1);
        InsertIntoString += ";";

        return InsertIntoString;
    }

    public void addMember(String memberID)
    {
        memberIDList.add(memberID);
    }

    public boolean hasMember(String memberID)
    {
        for(String memberID_inList: getMemberIDList())
        {
            if (Objects.equals(memberID_inList, memberID))
            {
                return true;
            }
        }
        return false;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<String> getMemberIDList()
    {
        return memberIDList;
    }
}
