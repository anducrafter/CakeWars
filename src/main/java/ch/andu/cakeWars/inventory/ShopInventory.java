package ch.andu.cakeWars.inventory;

import ch.andu.cakeWars.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopInventory {


    private Inventory inventory;
    public ShopInventory(){
        this.inventory = Bukkit.createInventory(null,9*6,"§7Shop");
        addItemsToInventory();
    }

    public void openInventoryShop(Player player){
        player.openInventory(inventory);
        player.sendMessage(inventory+"");
    }

    private void addItemsToInventory(){
        ItemBuilder ib = new ItemBuilder();
        inventory.setItem(10,ib.itemno(Material.WOODEN_SWORD,"4xBronze"));
        inventory.setItem(11,ib.itemno(Material.SANDSTONE,"4xBronze"));
        inventory.setItem(12,ib.itemno(Material.WOODEN_PICKAXE,"4xBronze"));
        inventory.setItem(14,ib.itemno(Material.LEGACY_CHAINMAIL_HELMET,"4xBronze"));
        inventory.setItem(15,ib.itemno(Material.IRON_HELMET,"4xBronze"));
        inventory.setItem(16,ib.itemno(Material.DIAMOND_HELMET,"4xBronze"));

        inventory.setItem(19,ib.itemno(Material.IRON_SWORD,"4xBronze"));
        inventory.setItem(20,ib.itemno(Material.END_STONE,"4xBronze"));
        inventory.setItem(21,ib.itemno(Material.IRON_PICKAXE,"4xBronze"));
        inventory.setItem(23,ib.itemno(Material.LEGACY_CHAINMAIL_CHESTPLATE,"4xBronze"));
        inventory.setItem(24,ib.itemno(Material.IRON_CHESTPLATE, "4xBronze"));
        inventory.setItem(25,ib.itemno(Material.DIAMOND_CHESTPLATE,"4xBronze"));

        inventory.setItem(28,ib.itemno(Material.DIAMOND_SWORD,"4xBronze"));
        inventory.setItem(29,ib.itemno(Material.OBSIDIAN,"4xBronze"));
        inventory.setItem(30,ib.itemno(Material.DIAMOND_PICKAXE,"4xBronze"));
        inventory.setItem(32,ib.itemno(Material.CHAINMAIL_LEGGINGS,"4xBronze"));
        inventory.setItem(33,ib.itemno(Material.IRON_LEGGINGS, "4xBronze"));
        inventory.setItem(34,ib.itemno(Material.DIAMOND_LEGGINGS,"4xBronze"));

        inventory.setItem(37,ib.itemno(Material.CARROT,"4xBronze"));
        inventory.setItem(38,ib.itemno(Material.GOLDEN_APPLE,"4xBronze"));
        inventory.setItem(39,ib.itemno(Material.ENCHANTED_GOLDEN_APPLE,"4xBronze"));
        inventory.setItem(41,ib.itemno(Material.CHAINMAIL_BOOTS,"4xBronze"));
        inventory.setItem(42,ib.itemno(Material.IRON_BOOTS, "4xBronze"));
        inventory.setItem(43,ib.itemno(Material.DIAMOND_BOOTS,"4xBronze"));
    }



}
