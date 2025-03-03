package ch.andu.cakeWars;

import ch.andu.cakeWars.commands.SetUp;
import ch.andu.cakeWars.commands.test;
import ch.andu.cakeWars.database.Hikari;
import ch.andu.cakeWars.database.SQL;
import ch.andu.cakeWars.files.*;
import ch.andu.cakeWars.listener.*;
import ch.andu.cakeWars.utils.Chunkgenerator;
import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.GameState;
import ch.andu.cakeWars.team.Teammanager;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class CakeWars extends JavaPlugin {

    private static CakeWars instance;
    private FileManager fileManager;
    private  HikariDataSource hikari;
    private SQL sql;
    private Hikari db;
    private GameManager gameManager;
    private Locations locationsconfig;
    String test;

    @Override
    public void onEnable() {

        instance = this;
        createEmptyWorld();
        locationsconfig = new Locations(new FileManager("locations"));
        new Language().loadLanguages();
        gameManager = new GameManager(this);
        gameManager.getVoteManager().loadJsonSchemetica();

        loaddb();
        //TEAMS LOAD BEFORE LISTENER VERY IMPORTENT!!!!!
        loadTeams();

        getCommand("test").setExecutor(new test(hikari));
        getCommand("setup").setExecutor(new SetUp(gameManager));
        gameManager.setGameState(GameState.LOBBY);
        Bukkit.getPluginManager().registerEvents(new test(hikari),this);
        Bukkit.getPluginManager().registerEvents((Listener) new JoinListener(gameManager,sql),this);
        Bukkit.getPluginManager().registerEvents((Listener) new LobbyListener(gameManager),this);
        Bukkit.getPluginManager().registerEvents((Listener) new UtilsListener(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new SetUp(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new LeaveListener(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(gameManager),this);
        Bukkit.getPluginManager().registerEvents(new ShopListener(),this);


        
    }

    @Override
    public void onDisable() {
/*
add unload world to confirm the map reset
 */
        //Map reset, world is not saved..
       Bukkit.unloadWorld("bedwars",false);

    }

    public static CakeWars getInstance() {
        return instance;
    }

    private void loaddb(){
        Mysql mysql = new Mysql(new FileManager("mysql"));
        mysql.addDatabaseConfig();
        db = new Hikari(mysql);
        db.setupDataSource();
        hikari = db.getDataSource();
        sql = new SQL(hikari,instance);
        sql.createTables();
    }

    private void loadTeams() {
        Config config = new Config(new FileManager("config"));
        config.addDefaults();
        List<String> teams = config.getteams();
        Teammanager teammanager = gameManager.getTeammanager();
        teams.forEach(team -> {
            teammanager.createTeam(team);
            teammanager.setTeamMax(team, 2);
            //Add something else....
        });

    }

    public Locations getLocationsconfig() {
        return locationsconfig;
    }


    private void createEmptyWorld(){
        if(Bukkit.getWorld("bedwars") == null){
            WorldCreator wc = new WorldCreator("bedwars");
            wc.generator(new Chunkgenerator());
            wc.createWorld();
        }
        Bukkit.getWorld("bedwars").setTime(2200);
        //Wichtig für die Entwicklung
        Bukkit.getWorld("bedwars").setAutoSave(false);
        Bukkit.getWorld("bedwars").setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
        Bukkit.getWorld("bedwars").setGameRule(GameRule.DO_WEATHER_CYCLE,false);
    }

}
