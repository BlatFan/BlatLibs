package ru.blatfan.blatlibs;

import lombok.Getter;
import org.bukkit.ChatColor;
import ru.blatfan.blatlibs.commandframework.CommandFramework;
import ru.blatfan.blatlibs.util.Console;
import ru.blatfan.blatlibs.util.ItemsUtils;

public class BlatLibs extends BlatPlugin {
    @Getter
    private static BlatLibs instance;
    @Getter
    private static boolean TOWNY;
    @Getter
    private static boolean WORLDGUARD;
    @Getter
    private static boolean LANDS;
    @Getter
    private static boolean FACTIONS;
    @Getter
    private static boolean ITEMSADDER;
    @Getter
    private static boolean SPACE;
    @Getter
    private static boolean PAPI;

    @Override
    public void onStartEnabling(){
        instance = this;
        setPrefix("[BlatLibs]");
        setConsole(new Console(this, prefix+ChatColor.GRAY));
        getConsole().info(ChatColor.GREEN + "Enabled!");

        setCommandFramework(new CommandFramework(this));
    }

    @Override
    public void onEnabling() {
        TOWNY = getServer().getPluginManager().isPluginEnabled("towny");
        WORLDGUARD = getServer().getPluginManager().isPluginEnabled("worldguard");
        LANDS = getServer().getPluginManager().isPluginEnabled("lands");
        FACTIONS = getServer().getPluginManager().isPluginEnabled("factions");

        ITEMSADDER = getServer().getPluginManager().isPluginEnabled("itemsadder");

        PAPI = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");

        getConsole().info("Towny: " + TOWNY);
        getConsole().info("WorldGuard: " + WORLDGUARD);
        getConsole().info("Lands: " + LANDS);
        getConsole().info("Factions: " + FACTIONS);
        getConsole().info("ItemsAdder: " + ITEMSADDER);
        getConsole().info("PlaceholderAPI: " + PAPI);

        ItemsUtils.setup();
        getCommandFramework().registerCommands(new Commands());
    }

    @Override
    public void onEndEnabling() {

    }

    @Override
    public void onDisabling() {

    }

    @Override
    public void onStartDisabling() {

    }

    @Override
    public void onEndDisabling() {

    }
}
