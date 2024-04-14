package ru.blatfan.blatlibs.util;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Updater {

    @Getter
    private int project;
    private URL checkURL;
    @Getter
    private String newVersion;
    @Getter
    private String currentVersion;
    @Getter
    private JavaPlugin plugin;

    public Updater(JavaPlugin plugin, int project) {
        this.plugin = plugin;
        this.currentVersion = plugin.getDescription().getVersion();
        this.project=project;
        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + project);
        } catch (MalformedURLException ex) {}
    }
    
    public String getResourceURL() {
        return "https://www.spigotmc.org/resources/" + project;
    }

    public boolean checkForUpdates() throws IOException {
        URLConnection con = checkURL.openConnection();
        this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        return !currentVersion.equals(newVersion);
    }

}
