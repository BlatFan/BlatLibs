package ru.blatfan.blatlibs;

import dev.lone.itemsadder.api.ItemsAdder;
import lombok.Getter;
import org.bukkit.ChatColor;
import ru.blatfan.blatlibs.commandframework.CommandFramework;
import ru.blatfan.blatlibs.util.Console;
import ru.blatfan.blatlibs.util.ItemsUtils;
import ru.blatfan.blatlibs.util.Updater;
import ru.blatfan.blatlibs.util.compatibility.ItemsAdderManager;

import java.io.IOException;

public class BlatLibs extends BlatPlugin {
    @Getter
    private static BlatLibs instance;
    @Getter
    private static boolean TOWNY=false;
    @Getter
    private static boolean WORLDGUARD=false;
    @Getter
    private static boolean LANDS=false;
    @Getter
    private static boolean FACTIONS=false;
    @Getter
    private static boolean ITEMSADDER=false;
    @Getter
    private static boolean SPACE=false;
    @Getter
    private static boolean PAPI=false;

    @Override
    public void onStartEnabling(){
        instance = this;
        setPrefix(ChatColor.DARK_PURPLE+"[BlatLibs]");
        getConsole().info(ChatColor.GREEN + "Enabled!");
        
        setProjectID(114257);
        
        getCommandFramework().registerCommands(new Commands());
        
        TOWNY=attemptHook("towny");
        WORLDGUARD=attemptHook("worldguard");
        LANDS=attemptHook("lands");
        FACTIONS=attemptHook("factions");
        ITEMSADDER=attemptHook("ItemsAdder");
        PAPI=attemptHook("PlaceholderAPI");
    }

    @Override
    public void onEnabling() {
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
    
    private boolean attemptHook(String name) {
        if (this.getServer().getPluginManager().getPlugin(name) != null) {
            getConsole().info(name+": " + ChatColor.GREEN +true);
            return true;
        } else {
            getConsole().info(name+": " + ChatColor.RED +false);
            return false;
        }
    }
}
