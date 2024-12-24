package Team;

import MyConnection.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDBServices
{
    private static MyConnection MC;

    public static void InsertTeam(Team team)
    {
        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();
            stmt.execute(team.getTeamInsertString());
            stmt.execute(team.getTeamMemberInsertString());

            connection.close();

            System.out.println("team created.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Team GetTeam(String id)
    {
        String SelectTeamString = "SELECT * FROM " + MC.getTeamTable();
        SelectTeamString += " WHERE id = \"" + id + "\";";

        String SelectMemberString = "SELECT * FROM " + MC.getTeamMemberTable();
        SelectMemberString += " WHERE id = \"" + id + "\";";

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(SelectTeamString);
            String name;
            if (rs.next())
            {
                name = rs.getString("name");
            }
            else
            {
                return null;
            }
            rs.close();

            Team team = new Team(name);
            team.setId(id);

            rs = stmt.executeQuery(SelectMemberString);
            while(rs.next())
            {
                String memberID = rs.getString("memberID");
                team.addMember(memberID);
            }
            rs.close();

            connection.close();
            return team;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static List<Team> GetAllTeam()
    {
        String SelectTeamString = "SELECT id FROM " + MC.getTeamTable();

        try(Connection connection = DriverManager.getConnection(MC.getMyConnection(),MC.getUsername(),MC.getPassword()))
        {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(SelectTeamString);
            if(!rs.next())
            {
                return null;
            }

            List<Team> teamList = new ArrayList<>();
            while (rs.next())
            {
                String id = rs.getString("id");
                Team team = GetTeam(id);
                team.setId(id);
                teamList.add(team);
            }
            rs.close();

            connection.close();

            return teamList;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean isIDExist(String id)
    {
        return GetTeam(id) != null;
    }
}
