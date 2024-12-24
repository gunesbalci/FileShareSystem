package Team;

import java.util.ArrayList;
import java.util.List;

public class TeamServices
{
    enum ErrorCodes
    {
        SUCCESSFUL,
        INVALID_ID
    }

    public static int CreateTable(String name, List<String> memberIDList)
    {
        Team team = new Team(name);
        for(String memberID: memberIDList)
        {
            team.addMember(memberID);
        }

        if(!TeamDBServices.isIDExist(team.getId()))
        {
            TeamDBServices.InsertTeam(team);
            return ErrorCodes.SUCCESSFUL.ordinal();
        }
        else
        {
            return ErrorCodes.INVALID_ID.ordinal();
        }
    }

    public static List<Team> GetMemberTable(String memberID)
    {
        List<Team> teamList = TeamDBServices.GetAllTeam();
        List<Team> memberTeamList = new ArrayList<>();

        for(Team team: teamList)
        {
            if(team.hasMember(memberID))
            {
                memberTeamList.add(team);
            }
        }

        return memberTeamList;
    }
}
