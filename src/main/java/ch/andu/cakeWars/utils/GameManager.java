package ch.andu.cakeWars.utils;

import ch.andu.cakeWars.CakeWars;
import ch.andu.cakeWars.database.SQL;
import ch.andu.cakeWars.files.Locations;
import ch.andu.cakeWars.tasks.GameStartCounter;
import ch.andu.cakeWars.tasks.SpawnItem;
import ch.andu.cakeWars.team.Teammanager;
import ch.andu.cakeWars.vote.VoteManager;
import dev.efnilite.neoschematic.Schematic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;

public class GameManager {

    private final CakeWars instacne;
    private GameState gameState = GameState.LOBBY;
    private GameStartCounter gameStartCounter;
    private BlockManager blockManager;
    private Teammanager teammanager;
    private VoteManager voteManager;
    private PlayerManager playerManager;
    private Locations locationsconfig;



    public GameManager(CakeWars instacne){
        this.instacne = instacne;
        this.blockManager = new BlockManager(this);
        this.teammanager = new Teammanager();
        this.voteManager = new VoteManager();
        this.playerManager = new PlayerManager(this);
        this.locationsconfig = instacne.getLocationsconfig();
    }

    public void setGameState(GameState gameState){
        this.gameState = gameState;
        switch (gameState){
            case LOBBY :
                this.gameStartCounter = new GameStartCounter(this,40);
                this.gameStartCounter.runTaskTimerAsynchronously(instacne,0,20);
                //Voting
                //Select Teams
                break;
            case GAME:
                //set the items spawner

                //The time can be modified by testing with the team ;D
                SpawnItem bronze = new SpawnItem(Material.BRICK,this,"bronze");
                bronze.runTaskTimerAsynchronously(instacne,0,20);

                SpawnItem gold = new SpawnItem(Material.GOLD_INGOT,this,"gold");
                gold.runTaskTimerAsynchronously(instacne,0,80);

                SpawnItem smaragt = new SpawnItem(Material.EMERALD,this,"smaragt");
                smaragt.runTaskTimerAsynchronously(instacne,0,120);
                //Game mechanics
                break;
            case END:
                //
                break;
            case SETUP:
                break;
        }

    }


    public BlockManager getBlockManager() {
        return blockManager;
    }


    public GameState getGameState() {
        return gameState;
    }

    public Teammanager getTeammanager() {
        return teammanager;
    }

    public VoteManager getVoteManager() {
        return voteManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void loadSchemetica(String map){
        Location location = CakeWars.getInstance().getLocationsconfig().getMapSpawn(map);
        Schematic.loadAsync("plugins/CakeWars/schemeticas/"+map+".json", CakeWars.getInstance()).thenAccept(schematic -> {
            Bukkit.getScheduler().runTask(CakeWars.getInstance(), () -> schematic.paste(location, true));
            Bukkit.getScheduler().runTask(CakeWars.getInstance(), () -> addShops(map));
        });
    }

    private void addShops(String map){
         CakeWars.getInstance().getLocationsconfig().getMapGenerators(map,"shop").forEach(shops->{
           WanderingTrader wb = (WanderingTrader) shops.getWorld().spawnEntity(shops,EntityType.WANDERING_TRADER);
           wb.setAI(false);
           wb.setInvulnerable(false);
           wb.setCollidable(false);

         });
    }

    public Locations getLocationsconfig() {
        return locationsconfig;
    }

}
