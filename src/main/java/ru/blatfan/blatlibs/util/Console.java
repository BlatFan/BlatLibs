package ru.blatfan.blatlibs.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Console {
    private Logger logger;
    private String prefix;
    private JavaPlugin plugin;

    public Console(JavaPlugin plugin, String prefix){
        this.logger = Bukkit.getLogger();
        this.plugin = plugin;
        this.prefix = prefix+" ";
    }
    public Console(JavaPlugin plugin){
        this.logger = Bukkit.getLogger();
        this.plugin = plugin;
        this.prefix = plugin.getName()+" ";
    }
    public void send(Level level, String text){
        logger.log(level, prefix+text);
    }
    public void info(String text){
        logger.log(Level.INFO, prefix+text);
    }
    public void warn(String text){
        logger.log(Level.WARNING, prefix+text);
    }
    public void executeCommand(String command){
        plugin.getServer().getConsoleSender().sendMessage("/"+command);
    }
    public void broadcast(String text){
        plugin.getServer().broadcastMessage(text);
    }
}
