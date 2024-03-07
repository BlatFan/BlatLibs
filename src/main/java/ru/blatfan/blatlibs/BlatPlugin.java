package ru.blatfan.blatlibs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.blatfan.blatlibs.commandframework.CommandFramework;
import ru.blatfan.blatlibs.util.Console;

public abstract class BlatPlugin extends JavaPlugin {
    @Getter
    @Setter
    private Console console;
    @Getter
    @Setter
    private CommandFramework commandFramework;
    @Getter
    @Setter
    public String prefix;

    public abstract void onEnabling();
    public abstract void onStartEnabling();
    public abstract void onEndEnabling();

    public abstract void onDisabling();
    public abstract void onStartDisabling();
    public abstract void onEndDisabling();


    @Override
    public void onEnable(){
        onStartEnabling();
        onEnabling();
        onEndEnabling();
    }


    @Override
    public void onDisable() {
        onStartDisabling();
        onDisabling();
        onEndDisabling();
    }
}
