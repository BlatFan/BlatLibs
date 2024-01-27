package com.olliez4.interface4.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FakeBlock {
   private Location location;
   private Material material;

   public FakeBlock(Location location, Material material) {
      this.location = location;
      this.material = material;
   }

   public Location getLocation() {
      return this.location;
   }

   public void addPlayer(Player player) {
      player.sendBlockChange(this.location, this.material.createBlockData());
   }

   public void removePlayer(Player player) {
      player.sendBlockChange(this.location, this.location.getBlock().getType().createBlockData());
   }
}
