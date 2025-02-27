package ch.andu.cakeWars.inventory;

import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class TeamSelectInventory {

    private GameManager gameManager;
    private ItemBuilder builder;
    private Inventory inventory;
    public TeamSelectInventory(GameManager gameManager) {
        this.gameManager = gameManager;
        this.builder = new ItemBuilder();
        this.inventory = Bukkit.createInventory(null, 9 * 1, ChatColor.BLUE + "Select Teams");
        addItems(inventory);
    }



    private void addItems(Inventory inventory){
        Set<String> teams = gameManager.getTeammanager().getAllTeams();
        //Fast test, can be mondified for every map different.
        teams.forEach(team->{
            inventory.addItem(builder.item(Material.RED_DYE, team,gameManager.getTeammanager().getPlayersCount(team)+"/"+gameManager.getTeammanager().getMaxPlayers(team)));
        });
    }

    public void open(Player player){
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
