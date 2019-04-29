package me.bloodskreaper.wantedpointssystem.commands;

import me.bloodskreaper.wantedpointssystem.Language;
import me.bloodskreaper.wantedpointssystem.WantedPointsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WPSGiveCommand implements CommandExecutor {
    private Language lang;

    public WPSGiveCommand(Language language){
        lang = language;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            lang.sendMessage(sender, "command_onlyPlayers", null);
            return true;
        }
        String permission = "wps.report";
        if(!sender.hasPermission(permission)){
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%PERMISSION%", permission);
            lang.sendMessage(sender, "command_noPermission", placeholders);
            return true;
        }
        if(args.length < 3){
            lang.sendMessage(sender, "command_tooFewArguments", null);
            lang.sendMessage(sender, "command_usage_wpsgive", null);
            return true;
        }
        if(args.length > 3){
            lang.sendMessage(sender, "command_tooManyArguments", null);
            lang.sendMessage(sender, "command_usage_wpsgive", null);
            return true;
        }
        String playername = args[0];
        String pointsinput = args[1];
        int points;
        String reason = args[2];
        for(int i = 3; i< args.length; i++){
            reason += " "+args[i];
        }
        Player target = Bukkit.getPlayer(playername);
        if (target == null){
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%PLAYER%", playername);
            lang.sendMessage(sender, "command_playerNotOnline", placeholders);
            return true;
        }
        try{
            points = Integer.valueOf(pointsinput);
        }
        catch(NumberFormatException e){
            lang.sendMessage(sender, "command_noIntegerInput", null);
            return true;
        }
        if(points < 1){
            lang.sendMessage(sender, "command_IntegerOverOne", null);
            return true;
        }
        WantedPointsManager.addWantedPoints(target.getUniqueId(), points, reason);
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("%PLAYER%", target.getName());
        placeholders.put("%REPORTER%", sender.getName());
        placeholders.put("%REASON%", reason);
        placeholders.put("%WPS%", String.valueOf(WantedPointsManager.getWantedPoints(target.getUniqueId())));
        lang.sendMessage(target, "player_pointsReceived", placeholders);
        lang.sendMessage(sender, "player_playerReported", placeholders);
        for(Player police: Bukkit.getOnlinePlayers()){
            if(police.hasPermission("wps.report.notify") && police != sender){
                lang.sendMessage(police, "player_playerReported_broadcast", placeholders);
            }
        }

        return true;
    }
}
