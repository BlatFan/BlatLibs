package ru.blatfan.blatlibs;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.apihelper.APIManager;
import ru.blatfan.blatlibs.bossbar.BossBarAPI;

public final class BlatLibs extends JavaPlugin {

    public static boolean TOWNY;
    public static boolean WORLDGUARD;
    public static boolean LANDS;
    public static boolean RESIDENCE;

    public static BossBarAPI apiInstance;

    public static Plugin instance;
    @Override
    public void onLoad() {
        APIManager.registerAPI(apiInstance, this);
    }

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.DARK_PURPLE + "[BlatLibs]" + ChatColor.GREEN + " Enabled!");

        APIManager.initAPI(BossBarAPI.class);

        TOWNY = getServer().getPluginManager().isPluginEnabled("towny");
        WORLDGUARD = getServer().getPluginManager().isPluginEnabled("worldguard");
        LANDS = getServer().getPluginManager().isPluginEnabled("lands");
        RESIDENCE = getServer().getPluginManager().isPluginEnabled("residence");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.DARK_PURPLE + "[BlatLibs]" + ChatColor.RED + " Disabled!");
    }
}
