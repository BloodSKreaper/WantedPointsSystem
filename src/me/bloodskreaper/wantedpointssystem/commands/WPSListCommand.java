package me.bloodskreaper.wantedpointssystem.commands;

import me.bloodskreaper.wantedpointssystem.Language;
import me.bloodskreaper.wantedpointssystem.WantedPoint;
import me.bloodskreaper.wantedpointssystem.WantedPointsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.UUID;

public class WPSListCommand implements CommandExecutor {
    private Language lang;

    public WPSListCommand(Language language){
        lang = language;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        String permission = "wps.list";
        if(!sender.hasPermission(permission)){
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%PERMISSION%", permission);
            lang.sendMessage(sender, "command_noPermission", placeholders);
            return true;
        }
        if(args.length>0){
            lang.sendMessage(sender, "command_tooManyArguments", null);
            lang.sendMessage(sender, "command_usage_wpslist", null);
            return true;
        }
        HashMap<UUID, WantedPoint> wantedPlayers = WantedPointsManager.getWantedPlayers();
        if(wantedPlayers == null){
            lang.sendMessage(sender, "player_noWantedPlayers", null);
            return true;
        }
        HashMap<String, String> hplaceholders = new HashMap<>();
        hplaceholders.put("%AMOUNT%", String.valueOf(wantedPlayers.size()));
        lang.sendMessage(sender, "player_WantedPlayersHeader", hplaceholders);
        for(UUID uuid : wantedPlayers.keySet()){
            String playername = Bukkit.getOfflinePlayer(uuid).getName();
            String reason = WantedPointsManager.getWantedReason(uuid);
            int wps = WantedPointsManager.getWantedPoints(uuid);
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%PLAYER%", playername);
            placeholders.put("%REASON%", reason);
            placeholders.put("%WPS%", String.valueOf(wps));
            lang.sendMessage(sender, "player_WantedPlayersFormat", placeholders);
        }
        return true;
    }
}
