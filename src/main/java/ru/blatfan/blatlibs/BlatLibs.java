package ru.blatfan.blatlibs;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlatLibs extends JavaPlugin {

    public static boolean TOWNY;
    public static boolean WORLDGUARD;
    public static boolean LANDS;
    public static boolean RESIDENCE;


    @Override
    public void onEnable() {
        getLogger().info(ChatColor.DARK_PURPLE + "[BlatLibs]" + ChatColor.GREEN + " Enabled!");

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
