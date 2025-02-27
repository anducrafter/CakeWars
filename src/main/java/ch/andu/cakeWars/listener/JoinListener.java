package ch.andu.cakeWars.listener;

import ch.andu.cakeWars.database.SQL;
import ch.andu.cakeWars.files.Language;
import ch.andu.cakeWars.files.Locations;
import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.GameState;
import ch.andu.cakeWars.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private GameManager gameManager;
    private ItemBuilder builder;
    private SQL sql;
    public JoinListener(GameManager gameManager, SQL sql){
        this.gameManager = gameManager;
        this.sql = sql;
        this.builder = new ItemBuilder();
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        sql.createPlayer(event.getPlayer().getUniqueId().toString());
        event.setJoinMessage(new Language().getPlayerMessage(event.getPlayer(),"Join_message",null));

        if(gameManager.getGameState() != GameState.LOBBY){
          /*  CoreAPI coreAPI = new CoreAPI();
            Optional<CorePlayer> corePlayer = CoreAPI.getCorePlayer(event.getPlayer().getUniqueId());
           */
            //chick player if not admin.
        }
        Player player = event.getPlayer();
        //Set players
        gameManager.getPlayerManager().setJoinItems(player);
        Locations locationsconfig = gameManager.getLocationsconfig();
        Location location = locationsconfig.getLobbyspawn();
        player.teleport(location);
    }




}
