package ch.andu.cakeWars.team;

import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class Team {
    private String name;
    private  Set<String> members;
    private boolean cake = true;
    private Location spawn;
    private Location cakeloc;
    private int maxplayer = 0;
    private int players = 0;


    public Team(String name){
        this.name = name;
       this.members = new HashSet<>();
    }

    public void addPlayer(String uuid){
        members.add(uuid);
        players +=1;
    }

    public void remouvePlayer(String uuid){
        members.remove(uuid);
        players -=1;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getCakeloc() {
        return cakeloc;
    }

    public void setCakeloc(Location cakeloc) {
        this.cakeloc = cakeloc;
    }

    public  Set<String> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    public  Boolean getCake(){
        return  cake;
    }

    public void setCake(boolean cake) {
        this.cake = cake;
    }

    public void setMaxplayer(int maxplayer) {
        this.maxplayer = maxplayer;
    }

    public int getMaxplayer() {
        return maxplayer;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getPlayers() {
        return players;
    }
}
