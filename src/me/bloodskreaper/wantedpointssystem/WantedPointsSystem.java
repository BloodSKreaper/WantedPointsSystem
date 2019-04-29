package me.bloodskreaper.wantedpointssystem;

import me.bloodskreaper.wantedpointssystem.commands.WPSClearCommand;
import me.bloodskreaper.wantedpointssystem.commands.WPSGiveCommand;
import me.bloodskreaper.wantedpointssystem.commands.WPSListCommand;
import me.bloodskreaper.wantedpointssystem.commands.WPSShowCommand;
import me.bloodskreaper.wantedpointssystem.data.WantedPointsFileHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class WantedPointsSystem extends JavaPlugin {
    private Language lang;
    private WantedPointsFileHandler wpfh;

    @Override
    public void onEnable(){
        saveDefaultConfig();
        lang = new Language();
        lang.reloadLanguageData(getConfig());
        wpfh = new WantedPointsFileHandler(this);
        wpfh.loadWantedPointsData();
        registerCommands();
    }

    @Override
    public void onDisable(){
        wpfh.saveToYamlConfig(false);
    }

    public void registerCommands(){
        getCommand("wpsclear").setExecutor(new WPSClearCommand(lang));
        getCommand("su").setExecutor(new WPSGiveCommand(lang));
        getCommand("wpsshow").setExecutor(new WPSShowCommand(lang));
        getCommand("wpslist").setExecutor(new WPSListCommand(lang));

    }

}
