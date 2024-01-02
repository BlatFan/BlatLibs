package ru.blatfan.blatlibs.util.worldprotection.dependencies;

import ru.blatfan.blatlibs.util.worldprotection.ProtectionBase;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGuardManager extends ProtectionBase {
   public boolean canBreak(JavaPlugin plugin, Player player, Location location) {
      com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
      RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
      RegionQuery query = container.createQuery();
      ApplicableRegionSet set = query.getApplicableRegions(loc);
      LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
      boolean state = set.testState(localPlayer, new StateFlag[]{Flags.BUILD});
      return state;
   }

   public boolean canPlace(JavaPlugin plugin, Player player, Location location) {
      com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
      RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
      RegionQuery query = container.createQuery();
      ApplicableRegionSet set = query.getApplicableRegions(loc);
      LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
      boolean state = set.testState(localPlayer, new StateFlag[]{Flags.BUILD});
      return state;
   }

   public boolean canInteract(JavaPlugin plugin, Player player, Location location) {
      com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
      RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
      RegionQuery query = container.createQuery();
      ApplicableRegionSet set = query.getApplicableRegions(loc);
      LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
      boolean state = set.testState(localPlayer, new StateFlag[]{Flags.BUILD});
      return state;
   }
}
