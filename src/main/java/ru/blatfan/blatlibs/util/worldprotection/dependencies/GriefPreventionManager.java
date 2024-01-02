package ru.blatfan.blatlibs.util.worldprotection.dependencies;

import ru.blatfan.blatlibs.util.worldprotection.ProtectionBase;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GriefPreventionManager extends ProtectionBase {
   public boolean canBreak(JavaPlugin plugin, Player player, Location loc) {
      Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, true, (Claim)null);
      if (claim == null) {
         return true;
      } else if (player.getUniqueId().equals(claim.ownerID)) {
         return true;
      } else {
         return claim.allowBreak(player, loc.getBlock().getType()) == null;
      }
   }

   public boolean canPlace(JavaPlugin plugin, Player player, Location loc) {
      Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, true, (Claim)null);
      if (claim == null) {
         return true;
      } else if (player.getUniqueId().equals(claim.ownerID)) {
         return true;
      } else {
         return claim.allowBuild(player, loc.getBlock().getType()) == null;
      }
   }

   public boolean canInteract(JavaPlugin plugin, Player player, Location loc) {
      Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, true, (Claim)null);
      if (claim == null) {
         return true;
      } else if (player.getUniqueId().equals(claim.ownerID)) {
         return true;
      } else {
         return claim.allowContainers(player) == null;
      }
   }
}
