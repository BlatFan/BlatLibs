package ru.blatfan.blatlibs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.blatfan.blatlibs.commandframework.CommandFramework;
import ru.blatfan.blatlibs.util.Console;
import ru.blatfan.blatlibs.util.Updater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BlatPlugin extends JavaPlugin {
    
    public static List<BlatPlugin> blatplugins= new ArrayList<>();
    @Getter
    private Console console;
    @Getter
    @Setter
    private int projectID;
    @Getter
    private CommandFramework commandFramework;
    @Getter
    private String prefix;
    /**
     * Add plugin to the display system
     */
    @Setter
    @Getter
    private boolean addToSystem = true;
    
    public void setPrefix(String prefix1){
        prefix=prefix1;
        console = new Console(this, prefix + " &7");
    }

    public abstract void onEnabling();
    public abstract void onStartEnabling();
    public abstract void onEndEnabling();

    public abstract void onDisabling();
    public abstract void onStartDisabling();
    public abstract void onEndDisabling();


    @Override
    public void onEnable(){
        commandFramework=new CommandFramework(this);
        setPrefix(getDescription().getName());
        onStartEnabling();
        if(addToSystem)blatplugins.add(this);
        checkForUpdate();
        onEnabling();
        onEndEnabling();
    }


    @Override
    public void onDisable() {
        onStartDisabling();
        onDisabling();
        onEndDisabling();
    }
    private void checkForUpdate() {
        Updater updater = new Updater(this, projectID);
        try {
            if (updater.checkForUpdates()) {
                getConsole().info("-------------------------------------------");
                getConsole().info("New Version: " + updater.getNewVersion());
                getConsole().info("Current Version: " + updater.getCurrentVersion());
                getConsole().info("Download link: " + updater.getResourceURL());
                getConsole().info("--------------------------------------------");
            }
        } catch (IOException e) {
            getConsole().info("Could not check for updates! Error log will follow if debug is enabled.");
            getConsole().info(String.valueOf(e.getCause()));
        }
    }
}
