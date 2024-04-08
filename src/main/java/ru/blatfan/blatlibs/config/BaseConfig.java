package ru.blatfan.blatlibs.config;

import org.bukkit.ChatColor;
import org.bukkit.Tag;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseConfig {
    private JavaPlugin plugin;
    private File file;
    private YamlConfiguration config;

    public BaseConfig(JavaPlugin plugin, String path, boolean hasInResource, Configuration defaultConfig){
        this.file = new File(plugin.getDataFolder(), path);
        this.plugin=plugin;
        if(!file.exists())
            if(hasInResource) {
                plugin.saveResource(path, false);
            } else {
                try{
                    file.createNewFile();
                } catch (IOException ignore) {}
            }
        config = YamlConfiguration.loadConfiguration(file);
        config.addDefaults(defaultConfig);
        this.save();
    }

    public BaseConfig(JavaPlugin plugin, String path, boolean hasInResource){
        this.file = new File(plugin.getDataFolder(), path);
        this.plugin=plugin;
        if(!file.exists())
            if(hasInResource) {
                plugin.saveResource(path, false);
            } else {
                try{
                    file.createNewFile();
                } catch (IOException ignore) {}
            }
        config = YamlConfiguration.loadConfiguration(file);
        this.save();
    }

    public BaseConfig(JavaPlugin plugin, String path){
        this.file = new File(plugin.getDataFolder(), path);
        this.plugin=plugin;
        if(!file.exists())
            try{
                file.createNewFile();
            } catch (IOException ignore) {}
        config = YamlConfiguration.loadConfiguration(file);
        this.save();
    }
    public BaseConfig(JavaPlugin plugin, String path, Configuration defaultConfig){
        this.file = new File(plugin.getDataFolder(), path);
        this.plugin=plugin;
        if(!file.exists())
            try{
                file.createNewFile();
            } catch (IOException ignore) {}
        config = YamlConfiguration.loadConfiguration(file);
        config.addDefaults(defaultConfig);
        this.save();
    }


    public void load(){
        config = YamlConfiguration.loadConfiguration(file);
    }
    public void save(){
        try{
            config.save(file);
        } catch (IOException ignore) {}
    }

    public void addDefault(String path, Object value){
        config.addDefault(path, value);
    }

    public Object get(String key){
        return config.get(key);
    }
    public String getString(String key){
        return ChatColor.translateAlternateColorCodes('&', config.getString(key));
    }
    public List<String> getStringList(String key){
        List<String> list = new ArrayList<>();
        for (String obj : config.getStringList(key)) list.add(ChatColor.translateAlternateColorCodes('&', obj));
        return list;
    }
    public Integer getInt(String key){
        return config.getInt(key);
    }
    public List<Integer> getIntList(String key){
        return config.getIntegerList(key);
    }
    public Boolean getBoolean(String key){
        return config.getBoolean(key);
    }
    public List<Boolean> getBooleanList(String key){
        return config.getBooleanList(key);
    }
    public List getList(String key){
        return config.getList(key);
    }
    public Map<String, Object> getMap(String key, Boolean b){
        return config.getConfigurationSection(key).getValues(b);
    }
}