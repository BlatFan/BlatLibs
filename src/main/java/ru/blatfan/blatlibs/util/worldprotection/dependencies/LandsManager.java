package ru.blatfan.blatlibs.util.worldprotection.dependencies;

import ru.blatfan.blatlibs.util.worldprotection.ProtectionBase;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.land.Area;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LandsManager extends ProtectionBase {
   public boolean canBreak(JavaPlugin plugin, Player player, Location loc) {
      Area area = LandsIntegration.of(plugin).getArea(loc);
      return area != null ? area.isTrusted(player.getUniqueId()) : true;
   }

   public boolean canPlace(JavaPlugin plugin, Player player, Location loc) {
      Area area = LandsIntegration.of(plugin).getArea(loc);
      return area != null ? area.isTrusted(player.getUniqueId()) : true;
   }

   public boolean canInteract(JavaPlugin plugin, Player player, Location loc) {
      Area area = LandsIntegration.of(plugin).getArea(loc);
      return area != null ? area.isTrusted(player.getUniqueId()) : true;
   }
}
