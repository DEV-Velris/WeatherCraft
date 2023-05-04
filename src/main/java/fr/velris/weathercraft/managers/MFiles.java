package fr.velris.weathercraft.managers;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import fr.velris.weathercraft.WeatherCraft;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class MFiles {

    private final WeatherCraft plugin = WeatherCraft.getInstance();

    private YamlDocument config;

    public void initFiles() {
        try {
            YamlDocument.create(new File(plugin.getDataFolder(), "config.yml"), Objects.requireNonNull(plugin.getResource("config.yml")), GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).setAutoSave(true).build());
        } catch (IOException exception) {
            plugin.log("Error while loading config.yml", Level.SEVERE);
        }
    }

    public YamlDocument getConfig() {
        return config;
    }

}
