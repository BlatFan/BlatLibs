package ru.blatfan.blatlibs.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.blatfan.blatlibs.BlatLibs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageBuilder {
    private final List<String> messages;

    public MessageBuilder(String message){
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public MessageBuilder(List<String> message){
        this.messages = message;
    }

    public void replacePlaceholder(String placeholder, Object value) {
        messages.replaceAll(message -> message.replace(placeholder, value.toString()));
    }

    public void replacePlaceholders(Map<String, Object> values){
        for(Map.Entry<String, Object> entry : values.entrySet()){
            replacePlaceholder(entry.getKey(), entry.getValue());
        }
    }

    public void parsePapi(Player player) {
        messages.replaceAll(message -> PlaceholderAPI.setPlaceholders(player, message));
    }

    public void send(Player player) {
        for(String message : messages) {
            player.sendMessage(Colorize(message));
        }
    }

    public void sendToConsole() {
        for(String message : messages) {
            BlatLibs.getInstance().getConsole().info(Colorize(message));
        }
    }

    public static String Colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
