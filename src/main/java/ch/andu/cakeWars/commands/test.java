package ch.andu.cakeWars.commands;

import ch.andu.cakeWars.CakeWars;
import ch.andu.cakeWars.database.SQL;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zaxxer.hikari.HikariDataSource;
import dev.efnilite.neoschematic.Schematic;
import netscape.javascript.JSObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class test implements CommandExecutor, Listener {

    private static Location pos1;
    private static Location pos2;
    private HikariDataSource hikari;
    public test(HikariDataSource hikari){
        this.hikari = hikari;
    }
    @Override
    public boolean onCommand( CommandSender sender,  Command command, String label,  String[] args) {
        Player player = (Player) sender;

        if(args.length == 2){

            if(args[0].equalsIgnoreCase("load")){
                Schematic.loadAsync("plugins/CakeWars/schemeticas/"+args[1]+".json", CakeWars.getInstance()).thenAccept(schematic -> {
                    Bukkit.getScheduler().runTask(CakeWars.getInstance(), () -> schematic.paste(player.getLocation(), true));
                });
            }

            if(args[0].equalsIgnoreCase("save")){
                Schematic.createAsync(pos1, pos2, CakeWars.getInstance()).thenAccept(schematic ->
                        schematic.saveAsync("plugins/CakeWars/schemeticas/"+args[1]+".json", CakeWars.getInstance())
                );
            }

        }
        if(args.length == 0){
            player.teleport(Bukkit.getWorld("bedwars").getSpawnLocation());
        }






        // Ensure it runs async
        Bukkit.broadcastMessage("yay");
        return false;
    }

    @EventHandler
    public void onListen(PlayerInteractEvent e) {
        if(e.getPlayer().getItemInHand().getType() == Material.STICK){

            //Test mit Schemetica funktioniert ;D
            //ist in JSON vormat
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK){

                pos1 = e.getClickedBlock().getLocation();
            }else if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                pos2 = e.getClickedBlock().getLocation();

            }

        }


    }
}
