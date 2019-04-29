package me.bloodskreaper.wantedpointssystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.logging.Level;

public class Language{
    private  HashMap<String, String> messages = new HashMap<>();

    public Language(){}

    public  void sendMessage(CommandSender sender, String messagekey, HashMap<String, String> placeholders){
        String message = messages.get(messagekey);
        if(message == null){
            Bukkit.getLogger().log(Level.SEVERE, "language String to key "+messagekey+" not found!");
            return;
        }
        message = ChatColor.translateAlternateColorCodes('&',message);
        message = message.replaceAll("/n", "\n");
        if(placeholders != null) {
            for (String placeholder : placeholders.keySet()) {
                message = message.replaceAll(placeholder, placeholders.get(placeholder));
            }
        }
        sender.sendMessage(message);
    }

    public void reloadLanguageData(FileConfiguration yamlconfig){
        messages.clear();
        for(String key : yamlconfig.getKeys(false)) {
            messages.put(key, yamlconfig.getString(key));
        }

    }
}
