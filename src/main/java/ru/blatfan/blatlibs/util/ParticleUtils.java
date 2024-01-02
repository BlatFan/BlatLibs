package ru.blatfan.blatlibs.util;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class ParticleUtils {
   public static void drawEffect(Player player, Location loc1, Location loc2, double frequency, Effect effect, Object data) {
      Vector vector = getVectorBetweenLocations(loc1, loc2);

      for(double i = 1.0D; i <= loc1.distance(loc2); i += frequency) {
         vector.multiply(i);
         loc1.add(vector);
         player.playEffect(loc1, effect, data);
         loc1.subtract(vector);
         vector.normalize();
      }

   }

   public static void drawMovingEffect(final Player player, Location loc1, Location loc2, double time, double frequency, final Effect effect, JavaPlugin plugin, final Object data) {
      final ArrayList<Location> lineLoc = new ArrayList();
      Vector vector = getVectorBetweenLocations(loc1, loc2);

      double repetition;
      for(repetition = 1.0D; repetition <= loc1.distance(loc2); repetition += frequency) {
         vector.multiply(repetition);
         loc1.add(vector);
         lineLoc.add(loc1.clone());
         loc1.subtract(vector);
         vector.normalize();
      }

      repetition = (double)lineLoc.size() / time;
      int ticks = (int)repetition;
      final int ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
         int i = 0;

         public void run() {
            if (this.i != lineLoc.size()) {
               player.playEffect((Location)lineLoc.get(this.i), effect, data);
               ++this.i;
            }

         }
      }, 0L, (long)ticks);
      Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         public void run() {
            Bukkit.getScheduler().cancelTask(ID);
         }
      }, (long)((int)time));
   }

   public static void drawLine(Location loc1, Location loc2, double frequency, Particle particle, DustOptions dustOptions) {
      Vector vector = getVectorBetweenLocations(loc1, loc2);

      for(double i = 1.0D; i <= loc1.distance(loc2); i += frequency) {
         vector.multiply(i);
         loc1.add(vector);
         loc1.getWorld().spawnParticle(particle, loc1, 0, dustOptions);
         loc1.subtract(vector);
         vector.normalize();
      }

   }

   public static void drawMovingLine(Location loc1, Location loc2, double time, double frequency, final Particle particle, JavaPlugin plugin, final DustOptions dustOptions) {
      final ArrayList<Location> lineLoc = new ArrayList();
      Vector vector = getVectorBetweenLocations(loc1, loc2);

      double repetition;
      for(repetition = 1.0D; repetition <= loc1.distance(loc2); repetition += frequency) {
         vector.multiply(repetition);
         loc1.add(vector);
         lineLoc.add(loc1.clone());
         loc1.subtract(vector);
         vector.normalize();
      }

      repetition = (double)lineLoc.size() / time;
      int ticks = (int)repetition;
      final int ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
         int i = 0;

         public void run() {
            if (this.i != lineLoc.size()) {
               ((Location)lineLoc.get(this.i)).getWorld().spawnParticle(particle, (Location)lineLoc.get(this.i), 0, dustOptions);
               ++this.i;
            }

         }
      }, 0L, (long)ticks);
      Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         public void run() {
            Bukkit.getScheduler().cancelTask(ID);
         }
      }, (long)((int)time));
   }

   public static void drawLine(Location loc1, Location loc2, double frequency, Particle particle) {
      Vector vector = getVectorBetweenLocations(loc1, loc2);

      for(double i = 1.0D; i <= loc1.distance(loc2); i += frequency) {
         vector.multiply(i);
         loc1.add(vector);
         loc1.getWorld().spawnParticle(particle, loc1, 0);
         loc1.subtract(vector);
         vector.normalize();
      }

   }

   public static void drawMovingLine(Location loc1, Location loc2, double time, double frequency, final Particle particle, JavaPlugin plugin) {
      final ArrayList<Location> lineLoc = new ArrayList();
      Vector vector = getVectorBetweenLocations(loc1, loc2);

      double repetition;
      for(repetition = 1.0D; repetition <= loc1.distance(loc2); repetition += frequency) {
         vector.multiply(repetition);
         loc1.add(vector);
         lineLoc.add(loc1.clone());
         loc1.subtract(vector);
         vector.normalize();
      }

      repetition = (double)lineLoc.size() / time;
      int ticks = (int)repetition;
      final int ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
         int i = 0;

         public void run() {
            if (this.i != lineLoc.size()) {
               ((Location)lineLoc.get(this.i)).getWorld().spawnParticle(particle, (Location)lineLoc.get(this.i), 0);
               ++this.i;
            }

         }
      }, 0L, (long)ticks);
      Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         public void run() {
            Bukkit.getScheduler().cancelTask(ID);
         }
      }, (long)((int)time));
   }

   public static Vector getVectorBetweenLocations(Location fromLoc, Location toLoc) {
      Vector from = fromLoc.toVector();
      Vector to = toLoc.toVector();
      return to.subtract(from);
   }
}
