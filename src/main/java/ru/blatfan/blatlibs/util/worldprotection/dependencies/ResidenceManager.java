package ru.blatfan.blatlibs.util.worldprotection.dependencies;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import ru.blatfan.blatlibs.util.worldprotection.ProtectionBase;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ResidenceManager extends ProtectionBase {
   public boolean canBreak(JavaPlugin plugin, Player player, Location loc) {
      ResidencePlayer rPlayer = Residence.getInstance().getPlayerManager().getResidencePlayer(player);
      boolean canPlace = rPlayer.canBreakBlock(loc.getBlock(), false);
      return canPlace;
   }

   public boolean canPlace(JavaPlugin plugin, Player player, Location loc) {
      ResidencePlayer rPlayer = Residence.getInstance().getPlayerManager().getResidencePlayer(player);
      boolean canPlace = rPlayer.canPlaceBlock(loc.getBlock(), false);
      return canPlace;
   }

   public boolean canInteract(JavaPlugin plugin, Player player, Location loc) {
      FlagPermissions perms = Residence.getInstance().getPermsByLoc(loc);
      return perms.playerHas(player, Flags.use, true);
   }
}
