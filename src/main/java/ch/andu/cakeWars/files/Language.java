package ch.andu.cakeWars.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Language {

    private FileManager fileManager;
    private Configuration cfg;
    private static Map<Locale, ResourceBundle> languageBundles = new HashMap<>();

    public void loadLanguages() {
        //Für zukünftiges zeug... füge andere Sprachen hinzu...
        String[] supportedLanguages = {"EN","DE"};
        for (String lang : supportedLanguages) {
            Locale locale = new Locale(lang);
            languageBundles.put(locale, ResourceBundle.getBundle("language", locale));
        }
    }

    public String getPlayerMessage(Player player, String key, Map<String, String> replacements) {
        Locale playerLocale = new Locale("en"); // Hier PLAYERCORE hinzufügen
        ResourceBundle bundle = languageBundles.getOrDefault(playerLocale, languageBundles.get(new Locale("en")));
        if (bundle == null) {
            throw new IllegalStateException("Language bundle is null for locale: " + playerLocale);
        }
        String message = bundle.getString(key);

        if (replacements == null) {
            replacements = new HashMap<>();
        }
        replacements.putIfAbsent("%player%", player.getName());
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        return message.replace("&", "§");
    }

    public void getAllPlayerMessage(String key, Map<String,String> replacments){
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(getPlayerMessage(onlinePlayer,key,replacments));
        }
    }









}
