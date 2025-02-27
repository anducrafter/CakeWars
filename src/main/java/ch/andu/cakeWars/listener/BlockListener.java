package ch.andu.cakeWars.listener;

import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.GameState;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    private GameManager gameManager;
    public BlockListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        if(gameManager.getGameState() == GameState.SETUP)return;
        if(!gameManager.getBlockManager().canBreak(block)){
           event.setCancelled(true);
        }
        //Block can't be brocken
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
       //Probably add admin blockplace
        if(gameManager.getGameState() == GameState.SETUP)return;
        if(!gameManager.getBlockManager().canPlace()){
            event.setCancelled(true);
        }
        gameManager.getBlockManager().addBlock(event.getBlock());
        //Block can't be placed
    }
}
