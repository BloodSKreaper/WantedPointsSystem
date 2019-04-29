package me.bloodskreaper.wantedpointssystem.data;

import me.bloodskreaper.wantedpointssystem.WantedPoint;
import me.bloodskreaper.wantedpointssystem.WantedPointsManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WantedPointsFileHandler {
    private File datafile;
    private YamlConfiguration data;
    private Plugin pl;

    public WantedPointsFileHandler(Plugin plugin) {
        pl = plugin;
        datafile = new File(pl.getDataFolder(), "WantedPoints.yml");
        if (!datafile.exists() && datafile.isDirectory()) {
            try {
                datafile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data = YamlConfiguration.loadConfiguration(datafile);
    }

    public void loadWantedPointsData() {
        Set<String> wantedPointsData = data.getKeys(false);
        if (wantedPointsData.size() != 0) {
            for (String uuid : wantedPointsData) {
                ConfigurationSection section = data.getConfigurationSection(uuid);
                List<String> reasons = section.getStringList("reasons");
                int points = section.getInt("points");
                WantedPointsManager.addWantedPoint(UUID.fromString(uuid), points, reasons);
            }
        }
    }

    public void saveToYamlConfig(boolean async) {
        HashMap<UUID, WantedPoint> wantedPoints = WantedPointsManager.getWantedPlayers();
        for (String s : data.getKeys(false)) {
            data.set(s, null);
        }
        for (UUID uuid : wantedPoints.keySet()) {
            ConfigurationSection section = data.createSection(uuid.toString());
            section.set("points", wantedPoints.get(uuid).getWantedPoints());
            section.set("reasons", wantedPoints.get(uuid).getReasons());
        }
        saveConfigFile(async);
    }

    private void saveConfigFile(boolean async) {
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(pl, () -> {
                try {
                    data.save(datafile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            try {
                data.save(datafile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
