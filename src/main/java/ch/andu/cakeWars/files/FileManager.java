package ch.andu.cakeWars.files;

import ch.andu.cakeWars.CakeWars;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private File file;
    private FileConfiguration config;


    public FileManager(String fileName) {
        File folder = CakeWars.getInstance().getDataFolder();
        if (!folder.exists()) {
            folder.mkdir();
        }
        file = new File(folder, fileName + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {


            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
