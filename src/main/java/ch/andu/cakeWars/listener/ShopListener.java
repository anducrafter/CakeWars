package ch.andu.cakeWars.listener;

import ch.andu.cakeWars.inventory.ShopInventory;
import ch.andu.cakeWars.utils.ShopHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event){
        if(!event.getRightClicked().getType().equals( EntityType.WANDERING_TRADER)) {
            return;
        }
        event.setCancelled(true);

    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals("§7Shop"))return;
        event.setCancelled(true);
        ItemStack itemStack = event.getCurrentItem();
        ShopHandler sh = new ShopHandler(itemStack);
        sh.buy((Player) event.getWhoClicked());
    }

    @EventHandler
    public void onItneractEntity(PlayerInteractEntityEvent event){
        if(!event.getRightClicked().getType().equals( EntityType.WANDERING_TRADER)){
            return;
        }
        event.setCancelled(true);
        ShopInventory inventory = new ShopInventory();
        inventory.openInventoryShop(event.getPlayer());
    }

}
