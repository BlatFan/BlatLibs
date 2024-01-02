package ru.blatfan.blatlibs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDoubleSneakEvent extends Event {
   private static final HandlerList HANDLERS = new HandlerList();
   private final Player player;
   private final double delay;

   public PlayerDoubleSneakEvent(Player player, double delay) {
      this.player = player;
      this.delay = delay;
   }

   public double getDelay() {
      return this.delay;
   }

   public Player getPlayer() {
      return this.player;
   }

   public static HandlerList getHandlerList() {
      return HANDLERS;
   }

   public HandlerList getHandlers() {
      return HANDLERS;
   }
}
