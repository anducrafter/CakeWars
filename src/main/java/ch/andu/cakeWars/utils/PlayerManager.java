package ch.andu.cakeWars.utils;

import ch.andu.cakeWars.CakeWars;
import ch.andu.cakeWars.files.FileManager;
import ch.andu.cakeWars.files.Locations;
import ch.andu.cakeWars.team.Team;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerManager {

private ItemBuilder builder;
private GameManager gameManager;
    public PlayerManager(GameManager gameManager){
        this.gameManager =gameManager;
        this.builder = new ItemBuilder();
    }

    public void setJoinItems(Player player){
        clearPlayer(player);
        Inventory inv = player.getInventory();
        inv.setItem(8,builder.item(Material.GRAY_BED, "Mapselector"));
        inv.setItem(1,builder.item(Material.COMPASS, "Teamselector"));
    }

    public void TeleportToMap(String map){
        //Also need to get the Team from the player
        Bukkit.getOnlinePlayers().forEach(player -> {
            String team =  gameManager.getTeammanager().getPlayerTeam(player.getUniqueId().toString());
            Locations locationsconfig = CakeWars.getInstance().getLocationsconfig();
            Location location = locationsconfig.getTeamSpawn(map.replace(".json",""),team);
            clearPlayer(player);
            player.teleport(location);
        });

    }


    public void clearPlayer(Player player){
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    public void setSpectator(Player player){
        player.setGameMode(GameMode.SPECTATOR);
        //Probably add something more here....
    }





}
