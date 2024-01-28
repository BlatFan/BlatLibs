package ru.blatfan.blatlibs;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
public final class BlatLibs extends JavaPlugin {

    public static boolean TOWNY;
    public static boolean WORLDGUARD;
    public static boolean LANDS;
    public static boolean RESIDENCE;

    public static BlatLibs instance;

    @Override
    public void onEnable() {
        instance = this;

        TOWNY = getServer().getPluginManager().isPluginEnabled("towny");
        WORLDGUARD = getServer().getPluginManager().isPluginEnabled("worldguard");
        LANDS = getServer().getPluginManager().isPluginEnabled("lands");
        RESIDENCE = getServer().getPluginManager().isPluginEnabled("residence");

        getLogger().info(ChatColor.DARK_PURPLE + "[BlatLibs]" + ChatColor.GREEN + " Enabled!");
        getLogger().info("Towny: " + TOWNY);
        getLogger().info("WorldGuard: " + WORLDGUARD);
        getLogger().info("Lands: " + LANDS);
        getLogger().info("Residence: " + RESIDENCE);
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.DARK_PURPLE + "[BlatLibs]" + ChatColor.RED + " Disabled!");
    }
}
