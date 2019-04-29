package me.bloodskreaper.wantedpointssystem.commands;

import me.bloodskreaper.wantedpointssystem.Language;
import me.bloodskreaper.wantedpointssystem.WantedPointsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WPSShowCommand implements CommandExecutor
{
    private Language lang;
    public WPSShowCommand(Language language){
        lang = language;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            lang.sendMessage(sender, "command_onlyPlayers", null);
            return true;
        }
        String permission = "wps.show";
        if(!sender.hasPermission(permission)){
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%PERMISSION%", permission);
            lang.sendMessage(sender, "command_noPermission", placeholders);
            return true;
        }
        if(args.length>1){
            lang.sendMessage(sender, "command_tooManyArguments", null);
            lang.sendMessage(sender, "command_usage_wpsshow", null);
            return true;
        }
        int points;
        String reasons;
        if(args.length == 1 && sender.hasPermission("wps.show.other")){
            String playername = args[0];
            Player target = Bukkit.getPlayer(playername);
            if(target ==null){
                HashMap<String, String> placeholders = new HashMap<>();
                placeholders.put("%PLAYER%", playername);
                lang.sendMessage(sender, "command_playerNotOnline", placeholders);
                return true;
            }
            points = WantedPointsManager.getWantedPoints(target.getUniqueId());
            reasons = WantedPointsManager.getWantedReason(target.getUniqueId());
            if(points == 0){
                HashMap<String, String> placeholders = new HashMap<>();
                placeholders.put("%PLAYER%", target.getName());
                lang.sendMessage(sender, "player_notWanted", placeholders);
                return true;
            }
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%WPS%", String.valueOf(points));
            placeholders.put("%PLAYER%", target.getName());
            placeholders.put("%REASON%", reasons);
            lang.sendMessage(sender, "player_Wanted", placeholders);
            return true;
        }
        Player player = (Player) sender;
         points = WantedPointsManager.getWantedPoints(player.getUniqueId());
         reasons = WantedPointsManager.getWantedReason(player.getUniqueId());
        if(points == 0){
            lang.sendMessage(sender, "player_youarenotWanted", null);
            return true;
        }
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("%WPS%", String.valueOf(points));
        placeholders.put("%REASON%", reasons);
        lang.sendMessage(sender, "player_youareWanted", placeholders);
        return true;
    }
}
