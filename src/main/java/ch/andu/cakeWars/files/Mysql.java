package ch.andu.cakeWars.files;

import org.bukkit.configuration.Configuration;

public class Mysql {

    private FileManager fileManager;
    private Configuration cfg;
    public Mysql(FileManager fileManager){
        this.fileManager = fileManager;
        cfg = fileManager.getConfig();
    }

    public void addDatabaseConfig(){
        cfg.addDefault("address","");
        cfg.addDefault("port",3306);
        cfg.addDefault("database","");
        cfg.addDefault("user","");
        cfg.addDefault("password","");
        cfg.options().copyDefaults(true);

        fileManager.saveConfig();
    }
    public String getAddress(){
        return   cfg.getString("address");
    }
    public int getPort(){
        return   cfg.getInt("port");
    }
    public String getDatabase(){
        return   cfg.getString("database");
    }
    public String getUser(){
        return   cfg.getString("user");
    }
    public String getPasswort(){
        return   cfg.getString("password");
    }


}
