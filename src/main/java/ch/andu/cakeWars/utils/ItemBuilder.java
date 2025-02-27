package ch.andu.cakeWars.utils;
import ch.andu.cakeWars.CakeWars;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemBuilder {

    public ItemStack item(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(lore));

        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack itemno(final Material material, String cost) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta itemMeta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(CakeWars.getInstance(),"shop-cost");
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING,cost);
        ArrayList<String> list = new ArrayList<>();
        list.add(" ");
        list.add(cost);
        list.add(" ");
        itemMeta.setLore(list);

        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack Armor(Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);

        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

}
