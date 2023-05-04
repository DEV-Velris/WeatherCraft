package fr.velris.weathercraft;

import fr.velris.weathercraft.managers.MFiles;
import fr.velris.weathercraft.utils.UData;
import fr.velris.weathercraft.utils.UUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.json.JSONObject;

import java.util.logging.Level;

public final class WeatherCraft extends JavaPlugin {

    // Instance
    private static WeatherCraft instance;

    // Managers
    private MFiles files;

    // Utils
    private UUtils utils;
    private UData data;

    @Override
    public void onEnable() {
        // Instance
        instance = this;

        // Managers
        files = new MFiles();
        files.initFiles();

        // Utils
        utils = new UUtils();
        data = new UData();
        data.loadConfig();

        // Scheduler
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            JSONObject values = utils.getCityStats();
            if (data.WEATHER_CONDITIONS_ENABLED) {
                utils.setWeather(values);
            }
            if (data.DAY_NIGHT_ENABLED) {
                utils.setDay(values);
            }
        }, 0L, 2 * 60 * 20L);

        log("Plugin enabled", Level.INFO);
    }

    @Override
    public void onDisable() {
        log("Plugin disabled", Level.INFO);
    }

    public void log(String message, Level level) {
        getLogger().log(level, message);
    }

    public static WeatherCraft getInstance() {
        return instance;
    }

    public MFiles getFiles() {
        return files;
    }

    public UUtils getUtils() {
        return utils;
    }

    public UData getData() {
        return data;
    }
}
