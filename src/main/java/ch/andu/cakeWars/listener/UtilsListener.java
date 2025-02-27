package ch.andu.cakeWars.listener;

import ch.andu.cakeWars.files.Language;
import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Cake;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.HashMap;

public class UtilsListener implements Listener {
    private GameManager gameManager;

    public UtilsListener(GameManager gameManager ){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.END){
            //only in GAME damage is possbily
            event.setCancelled(true);
        }
        //Check if entity is player
        //Check if damager is a player
        if(!(event.getEntity() instanceof Player))return;
        if(!(event.getDamager() instanceof Player))return;
        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();
        if(gameManager.getTeammanager().sameTeam(player.getUniqueId().toString(),damager.getUniqueId().toString())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.END){
            //only in GAME damage is possbily
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onUse(PlayerItemConsumeEvent event){

        if(gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.END){
            //only in GAME damage is possbily
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.END){
            //only in GAME damage is possbily
            event.setCancelled(true);
        }
    }
@EventHandler
public void onMopSpawning(CreatureSpawnEvent event){

      if(event.getSpawnReason().equals( CreatureSpawnEvent.SpawnReason.NATURAL)){
          event.setCancelled(true);
      }
}

    @EventHandler
    public void onWeather(WeatherChangeEvent event){
        event.setCancelled(true);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(gameManager.getGameState() != GameState.GAME)return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK)return;
        if(event.getClickedBlock().getType() != Material.CAKE)return;
        //check if its own cake
        Player player = event.getPlayer();
        String team = gameManager.getTeammanager().getPlayerTeam(player.getUniqueId().toString());
        Location loc = gameManager.getTeammanager().getTeamCakeLoc(team);

        if(event.getClickedBlock().getLocation().equals(loc)){
            event.setCancelled(true);
            return;
        };

        Block block = event.getClickedBlock();
        Cake cake = (Cake) block.getBlockData();
        if (cake.getBites() < 6) {
            cake.setBites(cake.getBites() + 1);
            block.setBlockData(cake);
            event.getPlayer().playSound(block.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, 1.0f);
        } else {
            //Send all Players that the cake is breaked
            String teamname = gameManager.getTeammanager().getAllTeams().stream()
                    .filter(teams -> gameManager.getTeammanager().getTeamCakeLoc(teams).equals(block.getLocation()))
                    .findFirst()
                    .orElse(null);
            Bukkit.getOnlinePlayers().forEach(players ->{
                HashMap<String,String> replace = new HashMap<>();
                replace.put("%team%",teamname);
                players.sendMessage(new Language().getPlayerMessage(players,"Cake_break_message",replace));
                gameManager.getTeammanager().getPlayersCount(teamname);
            });
            block.setType(Material.AIR);
        }
        event.setCancelled(true);


    }

}
