package ru.blatfan.blatlibs.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public interface CustomBlock {
   void attemptPlace(JavaPlugin var1, BlockPlaceEvent var2);

   void attemptBreak(JavaPlugin var1, PlayerInteractEvent var2);

   void attemptBreak(JavaPlugin var1, EntityDamageByEntityEvent var2);

   void attemptClickStand(JavaPlugin var1, PlayerInteractAtEntityEvent var2);

   void attemptClick(JavaPlugin var1, PlayerInteractEvent var2);

   default void setBarrier(JavaPlugin main, final Block b) {
      Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
         public void run() {
            b.setType(Material.BARRIER);
         }
      });
   }

   default void setAboveBarrier(JavaPlugin main, final Block b) {
      Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
         public void run() {
            b.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().setType(Material.BARRIER);
         }
      });
   }
}
