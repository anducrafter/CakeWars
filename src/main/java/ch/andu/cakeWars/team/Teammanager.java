package ch.andu.cakeWars.team;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class Teammanager {

    private static HashMap<String, String> players = new HashMap<>();
    private static HashMap<String, Team> teams = new HashMap<>();


    public void createTeam(String name){
        Team team = new Team(name);
        teams.put(name,team);
    }

    public void setTeamMax(String name,int maxpalyers){
        Team team = teams.get(name);
        team.setMaxplayer(maxpalyers);
    }


    public int getMaxPlayers(String name){
        Team team = teams.get(name);
    return team.getMaxplayer();
    }


    public void setTeamPlayer(String name,int players){
        Team team = teams.get(name);
        team.setPlayers(players);
    }

    public int getPlayersCount(String name){
        Team team = teams.get(name);
      return  team.getPlayers();
    }

    public void setTeamSpawn(String name, Location location){
        Team team = teams.get(name);
        team.setSpawn(location);
    }

    public Set<String> getAllTeams(){
        return teams.keySet();
    }

    public Location getTeamSpawn(String name){
        Team team = teams.get(name);
        return   team.getSpawn();
    }

    public Location getTeamCakeLoc(String name){
        Team team = teams.get(name);
        return   team.getCakeloc();
    }

    public void setTeamCakeLoc(String name, Location location){
        Team team = teams.get(name);
        team.setCakeloc(location);
    }



    public void addPlayer(String name,String uuid){
        if(players.containsKey(uuid)){
            remouvePlayer(uuid);

        }
        Team team = teams.get(name);
        team.addPlayer(uuid);
        players.put(uuid,name);
    }

    public void remouvePlayer(String uuid){
        if(!players.containsKey(uuid))return;
        Team team = teams.get(players.get(uuid));
        team.remouvePlayer(uuid);
        players.remove(uuid);
    }


    public boolean isInTeam(String uuid){
        if(players.containsKey(uuid)){
            return true;
        }
        return false;
    }

    public String getPlayerTeam(String uuid){
        if(isInTeam(uuid)){
            return players.get(uuid);
        }
        return null;
    }
    public boolean sameTeam(String uuid, String uuid2){
        //Hope that works xD
        if(Objects.equals(getPlayerTeam(uuid), getPlayerTeam(uuid2))){return true;}
        boolean b = false;
        return b;
    }

}
