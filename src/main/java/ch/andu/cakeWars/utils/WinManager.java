package ch.andu.cakeWars.utils;

import ch.andu.cakeWars.CakeWars;
import ch.andu.cakeWars.files.Locations;
import ch.andu.cakeWars.team.Team;
import org.bukkit.Bukkit;

public class WinManager {

    private GameManager gameManager;
    private Locations locations;
    public WinManager(GameManager gameManager){
        this.gameManager = gameManager;
        this.locations = CakeWars.getInstance().getLocationsconfig();
    }

    public void GameFinished(){
        long count = gameManager.getTeammanager().getAllTeams().stream()
                .filter(team -> gameManager.getTeammanager().getPlayersCount(team) > 0)
                .count();
      if(count == 1){

          //Game is finished, one team has win this game
          String teamName = "No Team Found";

          for (String team : gameManager.getTeammanager().getAllTeams()) {
              if (gameManager.getTeammanager().getPlayersCount(team) == 1) {
                  teamName = team;
                  break;
              }
          }
          Bukkit.getOnlinePlayers().forEach(player -> {

              gameManager.getPlayerManager().clearPlayer(player);
              player.teleport(locations.getLobbyspawn());
              //Has a bug, that dead player can't respand..
              gameManager.setGameState(GameState.END);

          });
      }
    }
}
