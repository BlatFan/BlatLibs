package ru.blatfan.blatlibs.util.worldprotection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectionBase {
   public boolean canBreak(JavaPlugin plugin, Player player, Location loc) {
      return true;
   }

   public boolean canPlace(JavaPlugin plugin, Player player, Location loc) {
      return true;
   }

   public boolean canInteract(JavaPlugin plugin, Player player, Location loc) {
      return true;
   }
}
