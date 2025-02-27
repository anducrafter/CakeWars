package ch.andu.cakeWars.utils;

import ch.andu.cakeWars.CakeWars;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class ShopHandler {

    private ItemStack itemStack;
    private  Material material;
    public ShopHandler(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public void buy(Player player){
        //Get cost from ItemStack
        ItemMeta im = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(CakeWars.getInstance(),"shop-cost");
        assert im != null;
        String data = im.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        String[] array = data.split("x");
        int amount = Integer.valueOf(array[0]);
        String type = array[1];
        if(!hasenoghitems(player,amount,type))return;
        removeItems(player,material,amount);
        giveItem(player);
    }


    private boolean hasenoghitems(Player player,int amount, String type){
      material  = switch (type) {
            case "Bronze" -> Material.BRICK;
            case "Gold" -> Material.GOLD_INGOT;
            case "Emerald" -> Material.EMERALD;
            //Default variable for probably BRICK...
            default -> null;
        };
        return Arrays.stream(player.getInventory().getContents())
                .filter(item -> item != null && item.getType() == material)
                .mapToInt(ItemStack::getAmount)
                .sum() >= amount;
    }

    private void giveItem(Player player){
        player.getInventory().addItem(itemStack);
    }

    public void removeItems(Player player, Material material, int amountToRemove) {
        int remainingToRemove = amountToRemove;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == material) {
                int itemAmount = item.getAmount();

                if (itemAmount > remainingToRemove) {
                    item.setAmount(itemAmount - remainingToRemove);
                    break;
                } else {
                    player.getInventory().remove(item);
                    remainingToRemove -= itemAmount;
                }

                if (remainingToRemove <= 0) break;
            }
        }
    }

}
