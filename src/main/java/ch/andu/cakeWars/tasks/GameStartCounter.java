package ch.andu.cakeWars.tasks;

import ch.andu.cakeWars.CakeWars;
import ch.andu.cakeWars.files.Language;
import ch.andu.cakeWars.files.Locations;
import ch.andu.cakeWars.team.Team;
import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStartCounter extends BukkitRunnable {

    private GameManager gameManager;
    private int timeleft;
    public GameStartCounter(GameManager gameManager, int timeleft){
        this.gameManager = gameManager;
        this.timeleft = timeleft;
    }
    @Override
    public void run() {
        timeleft--;
        if(timeleft<=0){
            gameManager.setGameState(GameState.GAME);
            new Language().getAllPlayerMessage("Game_start_message",null);
            //Locations for all Teams, Spawn and Cake
            setTeamsLocations();
            new BukkitRunnable() {
                @Override
                public void run() {
                  gameManager.getPlayerManager().TeleportToMap(gameManager.getVoteManager().getVoteWinner()[0]);
                }
            }.runTask(CakeWars.getInstance());
            cancel();
        }
        if(gameManager.getGameState() == GameState.SETUP){
            cancel();
        }
        //add Voting
        if(timeleft == 20){
            if(!enoghplayer(2))return;
            String mapname = gameManager.getVoteManager().getVoteWinner()[0];
            gameManager.getVoteManager().setMapname(mapname);
            HashMap<String,String> replace = new HashMap<>();
            replace.put("%map%",mapname);
            new Language().getAllPlayerMessage("Map_getvoted_message",replace);
            gameManager.loadSchemetica(mapname);
        }
        //add
        if(timeleft < 10){
            HashMap<String,String> replace = new HashMap<>();
            replace.put("%map%",timeleft+"");
            new Language().getAllPlayerMessage("Game_countdown_message",replace);
        }


    }


    private boolean enoghplayer(int i){
        if(Bukkit.getOnlinePlayers().size() >= i){
            return true;
        }
        return false;
    }

    private void setTeamsLocations(){
        Locations locationsconfig = CakeWars.getInstance().getLocationsconfig();
        String mapname = gameManager.getVoteManager().getVoteWinner()[0];
      ArrayList<String> teamlist = new ArrayList<>(gameManager.getTeammanager().getAllTeams());
      teamlist.forEach(team ->{
          gameManager.getTeammanager().setTeamSpawn(team,locationsconfig.getTeamSpawn(mapname,team));
          gameManager.getTeammanager().setTeamCakeLoc(team,locationsconfig.getTeamCake(mapname,team));
      });
    }

}
