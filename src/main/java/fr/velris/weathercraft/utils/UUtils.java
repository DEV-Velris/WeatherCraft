package fr.velris.weathercraft.utils;

import fr.velris.weathercraft.WeatherCraft;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class UUtils {

    private final WeatherCraft plugin = WeatherCraft.getInstance();

    private String readAll(Reader rd) {
        try {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        } catch (Exception e) {
            plugin.log("Error while reading JSON, contact developer on discord !", Level.SEVERE);
            plugin.log("Here is the error:", Level.SEVERE);
            plugin.log(e.getMessage(), Level.SEVERE);
        }
        return null;
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    public JSONObject getCityStats() {
        try {
            return readJsonFromUrl("http://api.weatherapi.com/v1/current.json?key="+plugin.getData().WEATHER_API_KEY+"&q="+plugin.getData().CITY + "&aqi=no");
        } catch (Exception e) {
            plugin.log("Error while getting city stats, contact developer on discord !", Level.SEVERE);
            plugin.log("Here is the error:", Level.SEVERE);
            plugin.log(e.getMessage(), Level.SEVERE);
            return null;
        }
    }

    public void setWeather(JSONObject weatherObject) {

        if (weatherObject == null) {
            return;
        }

        String currentWeather = weatherObject.getJSONObject("current").getJSONObject("condition").getString("text");

        if (plugin.getData().RAIN_CONDITIONS.contains(currentWeather)) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "weather rain");
            if (currentWeather.contains("thunder")) {
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "weather thunder");
            }
        } else if (plugin.getData().SNOW_CONDITIONS.contains(currentWeather)) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "weather snow");
        } else {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "weather clear");
        }


    }

    public void setDay(JSONObject dayObject) {

        if (dayObject == null) {
            return;
        }

        int isDay = dayObject.getJSONObject("current").getInt("is_day");

        if (isDay == 1) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "time set day");
        } else {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "time set night");
        }

    }
}
