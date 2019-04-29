package me.bloodskreaper.wantedpointssystem.commands;

import me.bloodskreaper.wantedpointssystem.Language;
import me.bloodskreaper.wantedpointssystem.WantedPointsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WPSClearCommand implements CommandExecutor {
    private Language lang;

    public WPSClearCommand(Language language){
        lang = language;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        String permission = "wps.clear";
        if(!sender.hasPermission(permission)){
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%PERMISSION%", permission);
            lang.sendMessage(sender, "command_noPermission", placeholders);
            return true;
        }
        if(args.length<1){
            lang.sendMessage(sender, "command_tooFewArguments", null);
            lang.sendMessage(sender, "command_usage_wpsclear", null);
            return true;
        }
        if(args.length>1){
            lang.sendMessage(sender, "command_tooManyArguments", null);
            lang.sendMessage(sender, "command_usage_wpsclear", null);
            return true;
        }
        String playername = args[0];
        Player target = Bukkit.getPlayer(args[0]);
        if(target ==null){
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%PLAYER%", playername);
            lang.sendMessage(sender, "command_playerNotOnline", placeholders);
            return true;
        }
        int points = WantedPointsManager.getWantedPoints(target.getUniqueId());
        if(points == 0){
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%PLAYER%", playername);
            lang.sendMessage(sender, "command_playerHasNoPoints", placeholders);
            return true;
        }
        WantedPointsManager.resetWantedPoints(target.getUniqueId());
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("%PLAYER%", target.getName());
        placeholders.put("%EXECUTOR%", sender.getName());
        placeholders.put("%POINTS%", String.valueOf(points));
        lang.sendMessage(sender, "player_wpscleared", placeholders);
        lang.sendMessage(target, "player_wpsclearedby", placeholders);
        for(Player police: Bukkit.getOnlinePlayers()){
            if(police.hasPermission("wps.clear.notify") && police != sender){
                lang.sendMessage(police, "player_wpsclearedBroadcast", placeholders);
            }
        }
        return true;
    }
}
