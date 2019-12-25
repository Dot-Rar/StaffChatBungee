package com.perkelle.dev.staffchatbungee;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class ConfigManager {

    private static Configuration config;
    private static File file;

    private static void setConfig(Configuration config) {
        ConfigManager.config = config;
    }

    public static Configuration getConfig() {
        return config;
    }

    private final Plugin pl;

    public ConfigManager(Plugin pl) {
        this.pl = pl;
    }

    public void load() throws IOException {
        file = new File(pl.getDataFolder(), "config.yml");
        saveDefaultConfig();
        setConfig(ConfigurationProvider.getProvider(YamlConfiguration.class).load(file));
    }

    private void saveDefaultConfig() throws IOException {
        // Ensure the plugins/StaffChatBungee folder exists
        if(!pl.getDataFolder().exists()) {
            pl.getDataFolder().mkdir();
        }

        if(!file.exists()) {
            // Create the file and copy the default contents from the jar
            file.createNewFile();

            InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml");
            FileOutputStream out = new FileOutputStream(file);
            ByteStreams.copy(in, out);
            in.close();
            out.close();
        }
    }
}
