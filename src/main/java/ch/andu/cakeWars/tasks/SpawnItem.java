package ch.andu.cakeWars.tasks;

import ch.andu.cakeWars.CakeWars;
import ch.andu.cakeWars.files.Locations;
import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnItem extends BukkitRunnable {

    private Material material;
    private GameManager gameManager;
    private String type;
    public SpawnItem(Material material,GameManager gameManager,String type){

        this.type = type;
        this.material = material;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if(gameManager.getGameState() == GameState.END){
            cancel();
        }
        Locations locations = CakeWars.getInstance().getLocationsconfig();
        locations.getMapGenerators(gameManager.getVoteManager().getVoteWinner()[0],type).forEach(loc->{
            Bukkit.getScheduler().runTask(CakeWars.getInstance(), () ->  loc.getWorld().dropItem(loc,new ItemStack(material)));

        });
    }
}
