package ru.blatfan.blatlibs.util.worldprotection;

import ru.blatfan.blatlibs.BlatLibs;
import ru.blatfan.blatlibs.util.worldprotection.dependencies.*;

import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectionManager {
   public static ArrayList<ProtectionBase> protectables = new ArrayList();

   public ProtectionManager() {
      if (BlatLibs.isTOWNY()) {
         protectables.add(new TownyManager());
      }

      if (BlatLibs.isWORLDGUARD()) {
         protectables.add(new WorldGuardManager());
      }

      if (BlatLibs.isLANDS()) {
         protectables.add(new LandsManager());
      }

      if (BlatLibs.isFACTIONS()) {
         protectables.add(new FactionsUUIDManager());
      }

   }

   public static boolean canBreak(JavaPlugin plugin, Location location, Player player) {
      Iterator protectablesIt = protectables.iterator();

      while(protectablesIt.hasNext()) {
         ProtectionBase protect = (ProtectionBase)protectablesIt.next();
         if (!protect.canBreak(plugin, player, location)) {
            return false;
         }
      }

      return true;
   }

   public static boolean canInteract(JavaPlugin plugin, Location location, Player player) {
      Iterator protectablesIt = protectables.iterator();

      while(protectablesIt.hasNext()) {
         ProtectionBase protect = (ProtectionBase)protectablesIt.next();
         if (!protect.canInteract(plugin, player, location)) {
            return false;
         }
      }

      return true;
   }

   public static boolean canPlace(JavaPlugin plugin, Location location, Player player) {
      if (location.getBlock().getType().isInteractable() & !player.isSneaking()) {
         return false;
      } else {
         Iterator protectablesIt = protectables.iterator();

         while(protectablesIt.hasNext()) {
            ProtectionBase protect = (ProtectionBase)protectablesIt.next();
            if (!protect.canPlace(plugin, player, location)) {
               return false;
            }
         }

         return true;
      }
   }
}
