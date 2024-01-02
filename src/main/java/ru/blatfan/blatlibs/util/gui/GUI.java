package ru.blatfan.blatlibs.util.gui;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GUI implements Listener {
   private final int size;
   private String title;
   private final ArrayList<Player> players = new ArrayList();
   private final JavaPlugin plugin;
   private Inventory inventory;
   private boolean locked = false;
   private boolean isDragLocked = true;
   private int page = 1;
   private int maxPages = 1;
   private boolean isPaged = false;
   private boolean UNREGISTERED = true;
   private CloseAction closeAction = null;
   private ClickAction onClickAction = null;
   private DragAction onDragAction = null;
   private HashMap<ItemStack, ClickAction> clickables = new HashMap();

   public void setTitle(String title) {
      ArrayList<Player> playersClone = new ArrayList(this.players);
      playersClone.forEach((player) -> {
         this.players.remove(player);
      });
      this.title = title;
      Inventory oldInv = this.inventory;
      if (this.size == 5) {
         this.inventory = this.plugin.getServer().createInventory((InventoryHolder)null, InventoryType.HOPPER, ChatColor.translateAlternateColorCodes('&', title));
      } else {
         this.inventory = this.plugin.getServer().createInventory((InventoryHolder)null, this.size, ChatColor.translateAlternateColorCodes('&', title));
      }

      for(int i = 0; i < this.size; ++i) {
         this.inventory.setItem(i, oldInv.getItem(i));
      }

      playersClone.forEach((player) -> {
         this.addPlayer(player);
      });
   }

   public GUI(JavaPlugin plugin, int size, String title) {
      this.size = size;
      this.title = title;
      this.plugin = plugin;
      if (size == 5) {
         this.inventory = plugin.getServer().createInventory((InventoryHolder)null, InventoryType.HOPPER, ChatColor.translateAlternateColorCodes('&', title));
      } else {
         this.inventory = plugin.getServer().createInventory((InventoryHolder)null, size, ChatColor.translateAlternateColorCodes('&', title));
      }

   }

   public GUI(JavaPlugin plugin, int size, String title, Player... players) {
      this.size = size;
      this.title = title;
      this.plugin = plugin;
      if (size == 5) {
         this.inventory = plugin.getServer().createInventory((InventoryHolder)null, InventoryType.HOPPER, ChatColor.translateAlternateColorCodes('&', title));
      } else {
         this.inventory = plugin.getServer().createInventory((InventoryHolder)null, size, ChatColor.translateAlternateColorCodes('&', title));
      }

      Player[] var8 = players;
      int var7 = players.length;

      for(int var6 = 0; var6 < var7; ++var6) {
         Player player = var8[var6];
         this.addPlayer(player);
      }

   }

   public void setItem(int slot, ItemStack item, ClickAction action) {
      this.inventory.setItem(slot, item);
      this.clickables.put(item, action);
   }

   public void addItem(ItemStack item, ClickAction action) {
      this.inventory.addItem(new ItemStack[]{item});
      this.clickables.put(item, action);
   }

   public void setCloseAction(CloseAction closeAction) {
      this.closeAction = closeAction;
   }

   @EventHandler
   private void onInventoryClose(InventoryCloseEvent event) {
      if (event.getInventory().equals(this.inventory)) {
         if (this.closeAction != null) {
            this.closeAction.onClose(event);
         }

         this.players.remove(event.getPlayer());
         if (this.players.isEmpty()) {
            HandlerList.unregisterAll(this);
            this.UNREGISTERED = true;
         }

      }
   }

   @EventHandler
   private void onInventoryClick(InventoryClickEvent event) {
      if (event.getInventory().equals(this.inventory)) {
         if (event.getClickedInventory() != null) {
            if (this.onClickAction != null) {
               this.onClickAction.onClick(event);
            }

            if (!this.inventory.equals(event.getClickedInventory()) & this.locked) {
               if (!(event.getClick().equals(ClickType.RIGHT) | event.getClick().equals(ClickType.LEFT))) {
                  event.setCancelled(true);
               }

            } else {
               if (this.locked) {
                  event.setCancelled(true);
               }

               if (event.getCurrentItem() != null) {
                  if (this.clickables.containsKey(event.getCurrentItem()) && this.clickables.get(event.getCurrentItem()) != null) {
                     ((ClickAction)this.clickables.get(event.getCurrentItem())).onClick(event);
                  }

               }
            }
         }
      }
   }

   @EventHandler
   private void onInventoryDrag(InventoryDragEvent event) {
      if (event.getInventory().equals(this.inventory)) {
         if (this.onDragAction != null) {
            this.onDragAction.onDrag(event);
         }

         if (this.isDragLocked) {
            event.setCancelled(true);
         }

      }
   }

   public void setBorder(ItemStack item, BorderStyle style) {
      if (this.size != 5) {
         int rows = this.size / 9;
         ClickAction cancel = (event) -> {
            event.setCancelled(true);
         };
         int i;
         if (style.equals(BorderStyle.VERTICAL) | style.equals(BorderStyle.FULL) | style.equals(BorderStyle.LEFT_ONLY) | style.equals(BorderStyle.RIGHT_ONLY)) {
            for(i = 0; i < rows; ++i) {
               int index = i * 9;
               if (!style.equals(BorderStyle.RIGHT_ONLY) && this.inventory.getItem(index) == null) {
                  this.setItem(index, item, cancel);
               }

               if (!style.equals(BorderStyle.LEFT_ONLY) && this.inventory.getItem(index + 8) == null) {
                  this.setItem(index + 8, item, cancel);
               }
            }
         }

         if (style.equals(BorderStyle.HORIZONTAL) | style.equals(BorderStyle.FULL) | style.equals(BorderStyle.TOP_ONLY) | style.equals(BorderStyle.BOTTOM_ONLY)) {
            if (!style.equals(BorderStyle.BOTTOM_ONLY)) {
               for(i = 0; i < 9; ++i) {
                  if (this.inventory.getItem(i) == null) {
                     this.setItem(i, item, cancel);
                  }
               }
            }

            if (!style.equals(BorderStyle.TOP_ONLY)) {
               for(i = this.size - 9; i < this.size; ++i) {
                  if (this.inventory.getItem(i) == null) {
                     this.setItem(i, item, cancel);
                  }
               }
            }
         }

      }
   }

   public void setBackground(ItemStack item) {
      for(int i = 0; i < this.size; ++i) {
         if (this.inventory.getItem(i) == null) {
            this.setItem(i, item, (event) -> {
               event.setCancelled(true);
            });
         }
      }

   }

   public void addPlayer(Player player) {
      this.players.add(player);
      this.openInventory(player);
      if (this.UNREGISTERED) {
         this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
         this.UNREGISTERED = false;
      }

   }

   private void openInventory(Player player) {
      player.openInventory(this.inventory);
   }

   public void removePlayer(Player player) {
      this.players.remove(player);
      player.closeInventory();
   }

   public int getSize() {
      return this.size;
   }

   public String getTitle() {
      return this.title;
   }

   public ArrayList<Player> getPlayers() {
      return this.players;
   }

   public Inventory getRawInventory() {
      return this.inventory;
   }

   public JavaPlugin getPlugin() {
      return this.plugin;
   }

   public void setPaged() {
      this.isPaged = true;
      this.setPage(1);
   }

   public boolean isPaged() {
      return this.isPaged;
   }

   public int getMaxPages() {
      return this.maxPages;
   }

   public void setMaxPages(int maxPages) {
      this.maxPages = maxPages;
   }

   public int getPage() {
      return this.page;
   }

   public void clear() {
      for(int i = 0; i < this.size; ++i) {
         this.inventory.setItem(i, (ItemStack)null);
      }

      this.clickables.clear();
   }

   public void setPage(int page) {
      this.page = page;
      this.clear();
   }

   public boolean isLocked() {
      return this.locked;
   }

   public void setLocked(boolean locked) {
      this.locked = locked;
   }

   public boolean isDragLocked() {
      return this.isDragLocked;
   }

   public void setDragLocked(boolean isDragLocked) {
      this.isDragLocked = isDragLocked;
   }

   public void addClickAction(ClickAction onClickAction) {
      this.onClickAction = onClickAction;
   }

   public void addDragAction(DragAction onDragAction) {
      this.onDragAction = onDragAction;
   }
}
