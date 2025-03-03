package ch.andu.cakeWars.listener;


import ch.andu.cakeWars.CakeWars;
import ch.andu.cakeWars.files.Language;
import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.GameState;
import ch.andu.cakeWars.utils.WinManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {
    private GameManager gameManager;

    public DeathListener(GameManager gameManager){
        this.gameManager = gameManager;
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(gameManager.getGameState() != GameState.GAME)return;
        Player player = event.getEntity();
        String team = gameManager.getTeammanager().getPlayerTeam(player.getUniqueId().toString());
        Location loc = gameManager.getTeammanager().getTeamCakeLoc(team);
        if(loc.getBlock().getType() == Material.CAKE){
            //respawn
            Bukkit.getScheduler().runTaskLater(CakeWars.getInstance(), () -> {
                player.spigot().respawn();
            }, 1L);
            return;
        }
        event.setDeathMessage(new Language().getPlayerMessage(event.getEntity(),"Death_message",null));

        gameManager.getPlayerManager().setSpectator(player);
        gameManager.getTeammanager().playerDied(player.getUniqueId().toString());
        new WinManager(gameManager).GameFinished();
        //Else death....

    }

    @EventHandler
    public void onSpawn(PlayerRespawnEvent event){
        if(gameManager.getGameState() != GameState.GAME)return;
        Player player = event.getPlayer();
        String team = gameManager.getTeammanager().getPlayerTeam(player.getUniqueId().toString());
        Location loc = gameManager.getTeammanager().getTeamSpawn(team);
        event.setRespawnLocation(loc);
    }
}
