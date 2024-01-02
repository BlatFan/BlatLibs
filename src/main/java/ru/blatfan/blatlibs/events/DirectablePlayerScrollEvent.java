package ru.blatfan.blatlibs.events;

import ru.blatfan.blatlibs.util.ScrollDirection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DirectablePlayerScrollEvent extends Event {
   private static final HandlerList HANDLERS = new HandlerList();
   private final Player player;
   private final ScrollDirection direction;
   private boolean isCancelled;

   public DirectablePlayerScrollEvent(Player player, ScrollDirection direction) {
      this.player = player;
      this.direction = direction;
   }

   public ScrollDirection getDirection() {
      return this.direction;
   }

   public Player getPlayer() {
      return this.player;
   }

   public void setCancelled(boolean isCancelled) {
      this.isCancelled = isCancelled;
   }

   public boolean isCancelled() {
      return this.isCancelled;
   }

   public static HandlerList getHandlerList() {
      return HANDLERS;
   }

   public HandlerList getHandlers() {
      return HANDLERS;
   }
}
