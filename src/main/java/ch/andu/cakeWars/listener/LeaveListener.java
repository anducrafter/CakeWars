package ch.andu.cakeWars.listener;

import ch.andu.cakeWars.files.Language;
import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.WinManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
    private GameManager gameManager;
    public LeaveListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onLEave(PlayerQuitEvent event){
        event.setQuitMessage(new Language().getPlayerMessage(event.getPlayer(),"Quit_message",null));
        gameManager.getTeammanager().remouvePlayer(event.getPlayer().getUniqueId().toString());
        new WinManager(gameManager).GameFinished();
    }
}
