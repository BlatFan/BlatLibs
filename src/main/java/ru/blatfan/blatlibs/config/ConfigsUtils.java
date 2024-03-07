package ru.blatfan.blatlibs.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConfigsUtils {
    private static Map<String, Map<String, BaseConfig>> configs = new HashMap();
    private static Map<String, List<String>> configNames = new HashMap();
    public static void addConfig(String plugin, String name, BaseConfig config){
        Map<String, BaseConfig> configMap = new HashMap<>();
        configMap.put(name, config);
        List<String> pluginConfigNames = configNames.get(plugin);
        pluginConfigNames.add(name);
        configNames.remove(plugin);
        configNames.put(plugin, pluginConfigNames);
        configs.put(plugin, configMap);
    }
    public static void remConfig(String plugin, String config){
        Map<String, BaseConfig> configMap = configs.get(plugin);
        configMap.remove(config);
        List<String> pluginConfigNames = configNames.get(plugin);
        pluginConfigNames.remove(config);
        configNames.remove(plugin);
        configNames.put(plugin, pluginConfigNames);
        configs.put(plugin, configMap);
    }
    public static void remPlugin(String plugin){
        configs.remove(plugin);
        configNames.remove(plugin);
    }

    public static void reloadConfig(String plugin, String config){
        Map<String, BaseConfig> configMap = configs.get(plugin);
        configMap.get(config).load();
    }
    public static void reloadConfigs(String plugin, String... configNames){
        Map<String, BaseConfig> configMap = configs.get(plugin);
        for(String config : configNames)
            configMap.get(config).load();
    }
    public static void reloadConfigs(String plugin){
        Map<String, BaseConfig> configMap = configs.get(plugin);

        for(String config : configNames.get(plugin)){
            configMap.get(config).load();
        }
    }
}
