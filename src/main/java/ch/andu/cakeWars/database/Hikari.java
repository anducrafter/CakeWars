package ch.andu.cakeWars.database;

import ch.andu.cakeWars.files.Mysql;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Hikari {

    private final String address;
    private final String name;
    private final String username;
    private final String password;
    private final int port;

    public HikariDataSource hikari;

    public Hikari(Mysql file) {
        this.address = file.getAddress();
        this.name = file.getDatabase();
        this.username = file.getUser();
        this.password = file.getPasswort();
        this.port = file.getPort();
    }



    public void setupDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://"+address+":"+port+"/"+name);
        config.setUsername(username);
        config.setPassword(password);

        // Create the data source
        hikari = new HikariDataSource(config);

        // Now you can set the maximum pool size
        hikari.setMaximumPoolSize(10);
    }

    public HikariDataSource getDataSource() {
        return hikari;
    }
}
