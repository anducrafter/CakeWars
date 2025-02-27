package ch.andu.cakeWars.listener;

import ch.andu.cakeWars.files.Locations;
import ch.andu.cakeWars.inventory.TeamSelectInventory;
import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.GameState;
import ch.andu.cakeWars.inventory.VoteInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbyListener implements Listener {

    private GameManager gameManager;
    private VoteInventory inventory;
    private TeamSelectInventory teamSelectInventory;
    public LobbyListener(GameManager gameManager) {
        this.gameManager = gameManager;
        this.inventory = new VoteInventory(gameManager);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
       this.teamSelectInventory = new TeamSelectInventory(gameManager);

        if(gameManager.getGameState() != GameState.LOBBY)return;
        //open inventory
        if(event.getItem() == null)return;
        if(event.getItem().getType() == Material.COMPASS){
            inventory.open(event.getPlayer());
        }
        if(event.getItem().getType() == Material.GRAY_BED){
            teamSelectInventory.open(event.getPlayer());
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(gameManager.getGameState() != GameState.LOBBY)return;
        event.setCancelled(true);
        if(event.getCurrentItem() == null)return;

        //Need to be modified with the titel..
        if(event.getView().getTitle().equals(ChatColor.BLUE+"Select Teams")){
         String team = event.getCurrentItem().getItemMeta().getDisplayName();
            gameManager.getTeammanager().addPlayer(team,event.getWhoClicked().getUniqueId().toString());

            this.teamSelectInventory = new TeamSelectInventory(gameManager);
            teamSelectInventory.open((Player) event.getWhoClicked());
        }
        if(event.getView().getTitle().equals(ChatColor.BLUE+"Vote for Maps")){

            String map = event.getCurrentItem().getItemMeta().getDisplayName();
            gameManager.getVoteManager().voteMap(map,event.getWhoClicked().getUniqueId().toString());
            this.inventory = new VoteInventory(gameManager);
            inventory.open((Player) event.getWhoClicked());
        }



    }

}
