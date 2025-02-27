package ch.andu.cakeWars.vote;

import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

public class VoteManager {
    public static HashMap<String, Integer> maps = new HashMap<>();
    public static HashMap<String, String> players = new HashMap<>();
    private String mapname = "";
    public void loadJsonSchemetica(){
        String path = "plugins/CakeWars/schemeticas";
        maps = getAllJsonFileNames(path);
    }
    public void voteMap(String name,String uuid){
        if (players.containsKey(uuid)) {
            hasVoted(uuid);
        }
        addVote(name,uuid);
    }

    public String getMapname() {
        return mapname;
    }

    public void setMapname(String mapname) {
        this.mapname = mapname;
    }

    public Set<String> getMapNames(){
        return maps.keySet();
    }
    public Integer getMapVotes(String name){
        return maps.get(name);
    }

    public String[] getVoteWinner(){
        int max = Collections.max(maps.values());

        return maps.entrySet().stream()
                .filter(entry -> entry.getValue() == max)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList()).toArray(new String[0]);
    }

    private void addVote(String name,String uuid){
        maps.put(name, maps.getOrDefault(name, 0) + 1);
        players.put(uuid,name);
    }

    private void hasVoted(String uuid){

        if(!players.containsKey(uuid))return;
           String map = players.get(uuid);
           maps.put(map,maps.get(map)-1);
           players.remove(uuid);
    }


    private static HashMap<String,Integer> getAllJsonFileNames(String folderPath) {
        HashMap<String,Integer> jsonFiles = new HashMap<>();
        Path path = Paths.get(folderPath);

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.toString().endsWith(".json")) {
                        jsonFiles.put(file.getFileName().toString().replace(".json",""),0);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonFiles;
    }
}
