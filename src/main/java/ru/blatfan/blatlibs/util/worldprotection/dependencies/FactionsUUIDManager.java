package ru.blatfan.blatlibs.util.worldprotection.dependencies;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.perms.PermissibleActions;
import ru.blatfan.blatlibs.util.worldprotection.ProtectionBase;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionsUUIDManager extends ProtectionBase {
   public boolean canBreak(JavaPlugin plugin, Player player, Location location) {
      if (!FactionsPlugin.getInstance().worldUtil().isEnabled(player.getWorld())) {
         return true;
      } else {
         FLocation fLocation = new FLocation(location);
         Faction faction = Board.getInstance().getFactionAt(fLocation);
         FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
         if (faction.isWilderness() | fPlayer.isAdminBypassing()) {
            return true;
         } else {
            return faction.isWarZone() | faction.isSafeZone() ? false : faction.hasAccess(fPlayer, PermissibleActions.DESTROY, fLocation);
         }
      }
   }

   public boolean canPlace(JavaPlugin plugin, Player player, Location location) {
      if (!FactionsPlugin.getInstance().worldUtil().isEnabled(player.getWorld())) {
         return true;
      } else {
         FLocation fLocation = new FLocation(location);
         Faction faction = Board.getInstance().getFactionAt(fLocation);
         FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
         if (faction.isWilderness() | fPlayer.isAdminBypassing()) {
            return true;
         } else {
            return faction.isWarZone() | faction.isSafeZone() ? false : faction.hasAccess(fPlayer, PermissibleActions.BUILD, fLocation);
         }
      }
   }

   public boolean canInteract(JavaPlugin plugin, Player player, Location location) {
      if (!FactionsPlugin.getInstance().worldUtil().isEnabled(player.getWorld())) {
         return true;
      } else {
         FLocation fLocation = new FLocation(location);
         Faction faction = Board.getInstance().getFactionAt(fLocation);
         FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
         if (faction.isWilderness() | fPlayer.isAdminBypassing()) {
            return true;
         } else {
            return faction.isWarZone() | faction.isSafeZone() ? false : faction.hasAccess(fPlayer, PermissibleActions.CONTAINER, fLocation);
         }
      }
   }
}
