package ch.andu.cakeWars.commands;

import ch.andu.cakeWars.CakeWars;
import ch.andu.cakeWars.files.Locations;
import ch.andu.cakeWars.utils.GameManager;
import ch.andu.cakeWars.utils.GameState;
import dev.efnilite.neoschematic.Schematic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetUp implements CommandExecutor, Listener {
    private GameManager gameManager;
    private Locations locations;
    private String map;
    private static ArrayList<String> teamlist;
    private  static int teamcount = 0;
    private static Location location;
    private static String type;
    public SetUp(GameManager gameManager){
        this.gameManager = gameManager;
        this.locations = CakeWars.getInstance().getLocationsconfig();
        teamlist = new ArrayList<>(gameManager.getTeammanager().getAllTeams());

    }

    @Override
    public boolean onCommand( CommandSender sender, Command command,  String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!player.isOp()) return false;
        if (args.length == 0) {
            gameManager.setGameState(GameState.SETUP);
            player.sendMessage("§7Gamesetup has started..");
            player.sendMessage("§7please set the LOBBY spawnpoint, with /setup setlobbyspawn");
            player.sendMessage(" ");
            player.sendMessage("§7after seting the LOBBY spawn please select a map and load the map to the right location, with /setup loadmap {mapname}");
            player.sendMessage("§7The team name will be given now...");
            player.sendMessage("§7after load the map,please continiun with setting the spawnpoint for the teams, with /setup setteamspawn, confirm with /setup setteamspawn confirm");
            player.sendMessage("§7after setting the teamspawn please set the teamcake, with rightclick to the teamcake, confirm with /setup setteamcake confirm");
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("setlobbyspawn")) {
                locations.setLobbySpawn(player.getLocation());
                player.sendMessage("§7Lobbyspawn sucessfull added..");
                player.sendMessage("§7Please load now the map to the right location");

            }
            if (args[0].equalsIgnoreCase("setteamspawn")) {

                this.location = player.getLocation();
                player.sendMessage("§8set location for the Team §c" + teamlist.get(teamcount));
                player.sendMessage("§7please confirm with, /setup setteamspawn confirm");
            }

            if (args[0].equalsIgnoreCase("setteamcake")) {
                teamcount = 0;
                player.sendMessage("§8set location for the Teamcake for §c" + teamlist.get(teamcount) + " click on the cake for this");
                player.sendMessage("§7please confirm with location with clicktocake");
            }
            if (args[0].equalsIgnoreCase("setgenerator")) {
                player.sendMessage("§7please set bronze, gold, smaragt");
            }
            if(args[0].equalsIgnoreCase("setshop")){
                this.location = player.getLocation();
                player.sendMessage("§7location for a shop is set..");
                player.sendMessage("§7please confirm with, /setup setteamspawn confirm");

            }


        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("loadmap")) {
                String map = args[1];
                Schematic.loadAsync("plugins/CakeWars/schemeticas/" + args[1] + ".json", CakeWars.getInstance()).thenAccept(schematic -> {
                    Bukkit.getScheduler().runTask(CakeWars.getInstance(), () -> schematic.paste(player.getLocation(), true));
                });
                locations.setMapSpawn(map, player.getLocation());
                player.sendMessage("§7Map  sucessfull added..");
                this.map = map;
                teamcount = 0;
                player.sendMessage("§7set Teamspawn for the Team §c" + teamlist.get(teamcount));
            }

            if (args[0].equalsIgnoreCase("setteamcake")) {
                if (!args[1].equalsIgnoreCase("confirm")) return false;
                String map = this.map;
                player.sendMessage(this.location + "");
                locations.setMapTeamCake(map, teamlist.get(teamcount).toString(), this.location);
                player.sendMessage("§7Cake spawn §c" + teamlist.get(teamcount) + " " + map + " §9erfolgreich!");
                teamcount++;
                if (teamcount == teamlist.size()) {
                    player.sendMessage("§7All teamcake are set, please continous with the shop");
                    return false;
                }
                player.sendMessage("§7Next is" + teamlist.get(teamcount));
            }
            if (args[0].equalsIgnoreCase("setshop")) {
                if (!args[1].equalsIgnoreCase("confirm")) return false;
                String map = this.map;
                locations.setMapGenerator(map, "shop", this.location);
                player.sendMessage("§7Shop successfull added...");
            }

            if (args[0].equalsIgnoreCase("setteamspawn")) {
                if (!args[1].equalsIgnoreCase("confirm")) return false;

                String map = this.map;
                locations.setMapTeamSpawn(map, teamlist.get(teamcount).toString(), this.location);
                player.sendMessage("§7Map spawn " + teamlist.get(teamcount) + " " + map + " erfolgreich!");
                teamcount++;
                if (teamcount == teamlist.size()) {
                    player.sendMessage("§7All Teamsspawn are set, please continous with the teamcakes");
                    return false;
                }

                player.sendMessage("§7Next is" + teamlist.get(teamcount));
            }

            if (args[0].equalsIgnoreCase("setgenerator")) {
                if (args[1].equalsIgnoreCase("confirm")) {
                    String map = this.map;
                    locations.setMapGenerator(map, type, this.location);
                    player.sendMessage("§7Generator " + type + " successfull added..");

                    return false;
                }
                if (args[1].equalsIgnoreCase("bronze") || args[1].equalsIgnoreCase("gold") || args[1].equalsIgnoreCase("smaragt")) {
                    this.location = player.getLocation();
                    type = args[1].toLowerCase();
                    player.sendMessage("§8set location for the Shop..");
                    player.sendMessage("§7please confirm with location with /setup setshop confirm");
                }
            }

        }
            return false;

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(gameManager.getGameState()!= GameState.SETUP)return;
        event.getPlayer().sendMessage(event.getBlock().getType()+"");
        if(event.getBlock().getType() != Material.CAKE)return;
        this.location = event.getBlock().getLocation();
        Player player = event.getPlayer();
        player.sendMessage(this.location+"");
        player.sendMessage("§8Location for the Teamcake for §a"+teamlist.get(teamcount)+" §7is selected");
        player.sendMessage("§7please confirm with location with /setup setteamcake confirm");
        event.setCancelled(true);
    }
}
