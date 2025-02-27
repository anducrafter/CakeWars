package ch.andu.cakeWars.files;

import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private FileManager fileManager;
    private Configuration cfg;
    public Config(FileManager fileManager){
        this.fileManager = fileManager;
        cfg = fileManager.getConfig();
    }

    public void addDefaults(){
        cfg.addDefault("lobbycooldown",60);
        //Feature that needs to be added! Multiple gametypes
        cfg.addDefault("gametype","1x2");
        List<String> teams = new ArrayList<>();
        teams.add("test1");
        teams.add("test2");
        teams.add("test3");
        teams.add("test4");
        teams.add("test5");
        cfg.addDefault("teams",teams);
        cfg.options().copyDefaults(true);
        fileManager.saveConfig();
    }

    /**
     * @return sadf
     */
    public List<String> getteams(){
        return  cfg.getStringList("teams");
    }

}
