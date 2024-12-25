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

    public static Team CreateTable(String name, List<String> memberIDList)
    {
        Team team = new Team(name);
        for(String memberID: memberIDList)
        {
            team.addMember(memberID);
        }

        if(!TeamDBServices.isIDExist(team.getId()))
        {
            TeamDBServices.InsertTeam(team);
            return team;
        }
        else
        {
            return null;
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
