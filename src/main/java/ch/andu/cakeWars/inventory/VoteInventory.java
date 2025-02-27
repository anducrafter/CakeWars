package ch.andu.cakeWars.inventory;

import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class VoteInventory {

    private GameManager gameManager;
    private ItemBuilder builder;
    private Inventory inventory;
    public VoteInventory(GameManager gameManager){
        this.gameManager = gameManager;
        this.builder = new ItemBuilder();
        this.inventory = Bukkit.createInventory(null,9*1, ChatColor.BLUE+"Vote for Maps");
        addItems(inventory);
    }



    private void addItems(Inventory inventory){
       Set<String> maps = gameManager.getVoteManager().getMapNames();
       //Fast test, can be mondified for every map different.
       maps.forEach(map->{
           inventory.addItem(builder.item(Material.GRAY_BED, map,gameManager.getVoteManager().getMapVotes(map)+""));
        });
    }

    public void open(Player player){
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }


}
