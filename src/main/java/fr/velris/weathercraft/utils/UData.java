package fr.velris.weathercraft.utils;

import dev.dejvokep.boostedyaml.YamlDocument;
import fr.velris.weathercraft.WeatherCraft;

import java.util.List;

public class UData {

    private final WeatherCraft plugin = WeatherCraft.getInstance();

    public String WEATHER_API_KEY;
    public String CITY;
    public Boolean DAY_NIGHT_ENABLED;
    public Boolean WEATHER_CONDITIONS_ENABLED;
    public List<String> RAIN_CONDITIONS;
    public List<String> SNOW_CONDITIONS;

    public void loadConfig() {
        YamlDocument config = plugin.getFiles().getConfig();

        WEATHER_API_KEY = config.getString("weather-api-key");
        CITY = config.getString("city");
        DAY_NIGHT_ENABLED = config.getBoolean("day-night.enabled");
        WEATHER_CONDITIONS_ENABLED = config.getBoolean("weather-conditions.enabled");
        RAIN_CONDITIONS = config.getStringList("weather-conditions.rain");
        SNOW_CONDITIONS = config.getStringList("weather-conditions.snow");
    }

}
