package ch.andu.cakeWars.utils;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockManager {

    private List<Block> blocks = new ArrayList<>();
    private GameManager gameManager;
    public  BlockManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addBlock(Block block){
        blocks.add(block);
    }

    public boolean canBreak(Block block){
        //Probably add something for admin permission to always break blocks.

        return blocks.contains(block);
    }
    public boolean canPlace(){
        if(gameManager.getGameState() == GameState.GAME) {
           return true;
        }
        return false;
    }






}
