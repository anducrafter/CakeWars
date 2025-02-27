package ch.andu.cakeWars.database;
import ch.andu.cakeWars.CakeWars;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class SQL {

    private final HikariDataSource hikari;
    private final CakeWars instance;

    public SQL(HikariDataSource hikari, CakeWars instacne){
        this.hikari = hikari;
        this.instance = instacne;

    }

    public void createTables(){
        createTableCakeWars();
        createTableSchemeticas();
    }
    private void createTableCakeWars() {
        String sql = "CREATE TABLE IF NOT EXISTS cakewars(" +
                "player_uuid VARCHAR(64), " +
                "player_kills INT(64), " +
                "player_deaths INT(64), " +
                "player_xp INT(64), " +
                "player_wins INT(64));";
        executeUpdate(sql);
    }

    private void createTableSchemeticas() {
        //Probably change to LONGTEXT, because of 16mb limit of JSON in db.
        String sql = "CREATE TABLE IF NOT EXISTS schemetics(" +
                "name VARCHAR(64), " +
                "schema_data LONGTEXT);";
        executeUpdate(sql);
    }

    public void addSchemetic(String name, String data){
       Bukkit.getConsoleSender().sendMessage(data);
        String sql = "INSERT INTO schemetics (name,schema_data) VALUES (?,?)";
        executeUpdate(sql,"name","gamingv3");
    }
    public void testdd(){
        String sql = "INSERT INTO schemetics (name,schema_data) VALUES ('gg', 'Schematic[dataVersion=2, minecraftVersion=1.21.4, dimensions=3.0,0.0,1.0, palette=[CraftBlockData{minecraft:grass_block[snowy=false]}, CraftBlockData{minecraft:air}], blocks=[0, 0, 0, 0, 0, 0, 0, 1]]')";
        executeUpdate(sql);
    }

    public Object  getschemetica(String name){
        String sql = "SELECT schema_data FROM schemetics WHERE name =?";
       return executeQuery(sql,name);
    }

    private boolean existPlayer(String uuid){
        String sql = "SELECT EXISTS (SELECT player_uuid FROM cakewars WHERE player_uuid=?)";
        return recordExists(sql, uuid);
    }

    public void createPlayer(String uuid){
        if(!existPlayer(uuid)){
            String sql = "INSERT INTO cakewars (player_uuid, player_kills, player_deaths, player_xp, player_wins) " +
                    "VALUES (?, ?, 0, 0, 0); ";
           executeUpdate(sql,uuid,10);
        }


    }

    public void addKills(String uuid){
        String sql = "UPDATE cakewars SET player_kills= player_kills + 1 WHERE player_uuid=?";
        executeUpdate(sql,uuid);
    }

    public void adddeaths(String uuid){
        String sql = "UPDATE cakewars SET player_deaths= player_deaths + 1 WHERE player_uuid=?";
        executeUpdate(sql,uuid);
    }

    public void addWins(String uuid){
        String sql = "UPDATE cakewars SET player_wins= player_wins + 1 WHERE player_uuid=?";
        executeUpdate(sql,uuid);
    }

    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }

    private void executeUpdate(String sql, Object... params) {
        try (Connection connection = hikari.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, params);
            Bukkit.broadcastMessage(statement+"");
            statement.executeUpdate();

        } catch (SQLException e) {
            instance.getLogger().log(Level.WARNING,"Database execute error",e);
        }
    }

    private List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection connection = hikari.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), resultSet.getObject(i));
                    }
                    results.add(row);
                }
            }
        } catch (SQLException e) {
            instance.getLogger().log(Level.WARNING, "Database query error", e);
        }
        return results;
    }

    public boolean recordExists(String sql, Object... params) {
        try (Connection connection = hikari.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, params);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            instance.getLogger().log(Level.WARNING, "Database query error", e);
        }
        return false;
    }



}
