package ru.blatfan.blatlibs.util.worldprotection.dependencies;

import ru.blatfan.blatlibs.util.worldprotection.ProtectionBase;
import com.palmergames.bukkit.towny.object.TownyPermission.ActionType;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TownyManager extends ProtectionBase {
   public boolean canBreak(JavaPlugin plugin, Player player, Location loc) {
      return PlayerCacheUtil.getCachePermission(player, loc, loc.getBlock().getType(), ActionType.DESTROY);
   }

   public boolean canPlace(JavaPlugin plugin, Player player, Location loc) {
      return PlayerCacheUtil.getCachePermission(player, loc, loc.getBlock().getType(), ActionType.BUILD);
   }

   public boolean canInteract(JavaPlugin plugin, Player player, Location loc) {
      return PlayerCacheUtil.getCachePermission(player, loc, loc.getBlock().getType(), ActionType.ITEM_USE);
   }
}
