package ru.blatfan.blatlibs.util.worldprotection;

import ru.blatfan.blatlibs.BlatLibs;
import ru.blatfan.blatlibs.util.worldprotection.dependencies.GriefPreventionManager;
import ru.blatfan.blatlibs.util.worldprotection.dependencies.TownyManager;
import ru.blatfan.blatlibs.util.worldprotection.dependencies.WorldGuardManager;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectionManager {
   public static ArrayList<ProtectionBase> protectables = new ArrayList();

   public ProtectionManager() {
      if (BlatLibs.TOWNY) {
         protectables.add(new TownyManager());
      }

      if (BlatLibs.WORLDGUARD) {
         protectables.add(new WorldGuardManager());
      }

      if (BlatLibs.GRIEFPREVENTION) {
         protectables.add(new GriefPreventionManager());
      }

   }

   public static boolean canBreak(JavaPlugin plugin, Location location, Player player) {
      Iterator var4 = protectables.iterator();

      while(var4.hasNext()) {
         ProtectionBase protect = (ProtectionBase)var4.next();
         if (!protect.canBreak(plugin, player, location)) {
            return false;
         }
      }

      return true;
   }

   public static boolean canInteract(JavaPlugin plugin, Location location, Player player) {
      Iterator var4 = protectables.iterator();

      while(var4.hasNext()) {
         ProtectionBase protect = (ProtectionBase)var4.next();
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
         Iterator var4 = protectables.iterator();

         while(var4.hasNext()) {
            ProtectionBase protect = (ProtectionBase)var4.next();
            if (!protect.canPlace(plugin, player, location)) {
               return false;
            }
         }

         return true;
      }
   }
}
