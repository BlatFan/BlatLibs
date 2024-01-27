package ru.blatfan.blatlibs.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class InventoryUtils {
   public static boolean slotFree(Player p) {
      return freeSlots(p) > 0;
   }

   public static int freeSlots(Player p) {
      int slots = 0;

      for(int i = 0; i < 36; ++i) {
         if (p.getInventory().getItem(i) == null) {
            ++slots;
         } else if (p.getInventory().getItem(i).getType().equals(Material.AIR)) {
            ++slots;
         }
      }

      return slots;
   }
}
