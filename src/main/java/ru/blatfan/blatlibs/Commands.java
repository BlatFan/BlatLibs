package ru.blatfan.blatlibs;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import ru.blatfan.blatlibs.commandframework.Command;
import ru.blatfan.blatlibs.commandframework.CommandArguments;
import ru.blatfan.blatlibs.commandframework.Completer;
import ru.blatfan.blatlibs.commandframework.Cooldown;
import ru.blatfan.blatlibs.util.campath.CamPath;
import ru.blatfan.blatlibs.util.JsonMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands {
    @Command(
            name="blatlibs.bl_plugins",
            aliases = "bl_pl",
            permission = "bf.libs.plugins",
            usage = "/bl_plugins",
            senderType = Command.SenderType.BOTH
    )
    @SuppressWarnings("unused")
    public void bl_plugins(CommandArguments arguments) {
        JsonMessage plugins = new JsonMessage();
        int s = BlatPlugin.blatplugins.size();
        BlatPlugin[] pl = BlatPlugin.blatplugins.toArray(new BlatPlugin[s]);
        plugins.append(ChatColor.DARK_PURPLE+"BlatLibs Plugins:\n").save();
        plugins.append(" - ").save();
        for (int i=0; i<s; i++){
            PluginDescriptionFile dec = pl[i].getDescription();
            String t = ", ";
            if(i+1==s) t = "";
            plugins.append(ChatColor.LIGHT_PURPLE+dec.getName()+t).setHoverAsTooltip("§7Description: "+dec.getDescription(), "§7Authors: "+dec.getAuthors(), "§7Version: "+dec.getVersion(), "§7MC version: "+dec.getAPIVersion()).save();
        }
        plugins.send(arguments.getSender());
    }
    @Command(
            name="test",
            senderType = Command.SenderType.PLAYER
    )
    @SuppressWarnings("unused")
    public void test(CommandArguments arguments){
        Player player = arguments.getSender();
        Location start = player.getLocation();
        start.setYaw(0);
        Location end = start.clone();
        end.setYaw(90);
        end.add(new Location(start.getWorld(), 50, 5, 0));
        CamPath cp = new CamPath("Test", Arrays.asList(player.getUniqueId()), start, end, 10);
        cp.generatePath();
    }
    @Command(
            name="bl_cam",
            min=5,
            max = 6,
            permission = "bf.libs.cam",
            senderType = Command.SenderType.PLAYER,
            usage = "/bl_cam {player} {x} {y} {z} {time} {returned}"
    )
    @SuppressWarnings("unused")
    public void bl_cam(CommandArguments arguments){
        Player player = arguments.getPlayer(0).get();
        double x = arguments.getArgumentAsDouble(1);
        double y = arguments.getArgumentAsDouble(2);
        double z = arguments.getArgumentAsDouble(3);
        int t = arguments.getArgumentAsInt(4);
        Location start = player.getLocation();
        Location end = start.clone();
        end.add(new Location(end.getWorld(), x, y, z));
        CamPath cp = new CamPath("CommandFrom_"+player.getUniqueId(), Arrays.asList(player.getUniqueId()), start, end, t);
        if(arguments.getArgumentAsBoolean(5))
            cp.setEndAction((player2, camPath)->{
                CamPath cp2 = new CamPath("CommandFrom_"+player2.getUniqueId()+"_End", Arrays.asList(player2.getUniqueId()), end, start, camPath.getDurationInTicks()/20);
                cp2.generatePath();
            });
        cp.generatePath();
    }
    @Completer(
            name="bl_cam"
    )
    @SuppressWarnings("unused")
    public List<String> bl_camCompletion(CommandArguments arguments) {
        List<String> players = new ArrayList<>();

        for (Player player : BlatLibs.getInstance().getServer().getOnlinePlayers())
            players.add(player.getName());

        if(arguments.getLength()==1) return players;
        else if(arguments.getLength()==2) return List.of("x");
        else if(arguments.getLength()==3) return List.of("y");
        else if(arguments.getLength()==4) return List.of("z");
        else if(arguments.getLength()==5) return List.of("time");
        else if(arguments.getLength()==6) return Arrays.asList("true", "false");

        return List.of("");
    }
    @Command(
            name="bl_broadcast",
            allowInfiniteArgs = true,
            permission = "bf.libs.broadcast",
            usage = "/bl_broadcast text"
    )
    @Cooldown(
            cooldown = 5,
            bypassPerm = "bf.libs.bypass.broadcast"
    )
    @SuppressWarnings("unused")
    public void bl_broadcast(CommandArguments arguments){
        StringBuilder text = new StringBuilder();
        for (String t1 : arguments.getArguments()){
            text.append(" ").append(t1);
        }
        if(BlatLibs.isPAPI()) text = new StringBuilder(PlaceholderAPI.setPlaceholders(arguments.getSender(), text.toString()));
        text = new StringBuilder(ChatColor.translateAlternateColorCodes('&', text.toString()));
        BlatLibs.getInstance().getConsole().broadcast(text.toString());
    }
    @Completer(
            name="bl_broadcast"
    )
    @SuppressWarnings("unused")
    public List<String> bl_broadcastCompletion() {
        return Arrays.asList("");
    }
}
