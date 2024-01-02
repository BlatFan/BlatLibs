package ru.blatfan.blatlibs.util.worldprotection.dependencies;

import ru.blatfan.blatlibs.util.worldprotection.ProtectionBase;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GriefDefenderManager extends ProtectionBase {
   public boolean canBreak(JavaPlugin plugin, Player player, Location location) {
      return true;
   }

   public boolean canPlace(JavaPlugin plugin, Player player, Location location) {
      return true;
   }

   public boolean canInteract(JavaPlugin plugin, Player player, Location location) {
      return true;
   }
}
