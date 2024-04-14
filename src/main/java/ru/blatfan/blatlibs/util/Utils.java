package ru.blatfan.blatlibs.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.blatfan.blatlibs.BlatLibs;
import ru.blatfan.blatlibs.util.colors.ColorAPI;

public class Utils {
   public static final double ATTACK_DAMAGE_CONSTANT = -1.0D;
   public static final double ATTACK_SPEED_CONSTANT = -4.0D;
   private static final Pattern VERSION_PATTERN = Pattern.compile("([1-9])\\.([1-9][0-9]|[1-9])(\\.([0-9]))?");
   private final BlatLibs plugin;
   
   public Utils(BlatLibs plugin) {
      this.plugin = plugin;
   }
   
   public static boolean isItemReal(@Nullable ItemStack item) {
      return item != null && item.getType() != Material.AIR && item.getAmount() >= 1;
   }
   
   public static void damageEntity(@Nonnull Damageable entity, double damage) {
      double points = 0.0D;
      double toughness = 0.0D;
      int resistance = 0;
      int epf = 0;
      if (entity instanceof LivingEntity) {
         LivingEntity living = (LivingEntity) entity;
         AttributeInstance armor = living.getAttribute(Attribute.GENERIC_ARMOR);
         AttributeInstance armorToughness = living.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
         EntityEquipment equipment = living.getEquipment();
         if (armor != null) {
            points = armor.getValue();
         }
         
         if (armorToughness != null) {
            toughness = armorToughness.getValue();
         }
         
         PotionEffect effect = living.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
         resistance = effect == null ? 0 : effect.getAmplifier();
         if (equipment != null) {
            epf = getEPF(equipment);
         }
      }
      
      entity.damage(calculateDamageApplied(damage, points, toughness, resistance, epf));
   }
   
   public static double calculateDamageApplied(double damage, double points, double toughness, int resistance, int epf) {
      double withArmorAndToughness = damage * (1.0D - Math.min(20.0D, Math.max(points / 5.0D, points - damage / (2.0D + toughness / 4.0D))) / 25.0D);
      double withResistance = withArmorAndToughness * (1.0D - (double) resistance * 0.2D);
      return withResistance * (1.0D - Math.min(20.0D, (double) epf) / 25.0D);
   }
   
   public static int getEPF(@Nonnull EntityEquipment inv) {
      ItemStack helm = inv.getHelmet();
      ItemStack chest = inv.getChestplate();
      ItemStack legs = inv.getLeggings();
      ItemStack boot = inv.getBoots();
      return (helm != null ? helm.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0) + (chest != null ? chest.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0) + (legs != null ? legs.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0) + (boot != null ? boot.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0);
   }
   
   public static int getRandomNum(int min, int max) {
      return (int) (Math.random() * (double) (max - min + 1)) + min;
   }
   
   public static double getRandomNum(double min, double max) {
      return Math.random() * (max - min) + min;
   }
   
   public static boolean roll(double chance) {
      return Math.random() <= chance;
   }
   
   public static void discoverRecipe(@Nonnull Player player, @Nonnull Recipe recipe) {
      if (recipe instanceof Keyed) {
         Keyed keyed = (Keyed) recipe;
         NamespacedKey key = keyed.getKey();
         if (!player.hasDiscoveredRecipe(key)) {
            player.discoverRecipe(key);
         }
      }
   }
   
   @Nullable
   public static ItemStack getMobLoot(@Nonnull ConfigurationSection section, @Nonnull ItemStack drop, @Nullable ItemStack tool, boolean checkLooting) {
      int lvl = 0;
      if (isItemReal(tool) && checkLooting) {
         lvl = tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
      }
      
      String var5 = section.getString("Type").toUpperCase();
      byte var6 = -1;
      switch (var5.hashCode()) {
         case 2507938:
            if (var5.equals("RARE")) {
               var6 = 0;
            }
            break;
         case 77742365:
            if (var5.equals("RANGE")) {
               var6 = 2;
            }
            break;
         case 1993481707:
            if (var5.equals("COMMON")) {
               var6 = 1;
            }
      }
      
      int amount;
      double chance;
      switch (var6) {
         case 0:
            chance = section.getDouble("Chance") + (double) lvl * 0.01D;
            if (roll(chance)) {
               return drop;
            }
            
            if (roll((double) lvl / ((double) lvl + 1.0D) / 100.0D)) {
               return drop;
            }
            break;
         case 1:
            chance = section.getDouble("Chance");
            if (roll(chance + (double) lvl * 0.01D)) {
               amount = lvl + 1;
               double rawAmount = chance * (double) amount;
               int actualAmount = (int) Math.floor(rawAmount);
               double dif = rawAmount - (double) actualAmount;
               if (roll(dif)) {
                  ++actualAmount;
               }
               
               if (actualAmount > 0) {
                  drop.setAmount(actualAmount);
                  return drop;
               }
            }
            break;
         case 2:
            int min = section.getInt("MinAmount");
            int max = section.getInt("MaxAmount");
            amount = getRandomNum(min, max);
            if (amount > 0) {
               drop.setAmount(amount);
               return drop;
            }
      }
      
      return null;
   }
   
   public static boolean dropLooting(@Nonnull ConfigurationSection section, @Nonnull ItemStack drop, @Nullable ItemStack tool, @Nonnull Location loc, boolean checkLooting) {
      ItemStack item = getMobLoot(section, drop, tool, checkLooting);
      if (isItemReal(item)) {
         loc.getWorld().dropItemNaturally(loc, item);
         return true;
      } else {
         return false;
      }
   }
   
   @Nonnull
   private static String format(@Nonnull String text, @Nonnull Map<String, Object> placeholders) {
      StringBuilder newTemplate = new StringBuilder(text);
      List<Object> valueList = new ArrayList();
      Matcher matcher = Pattern.compile("%(\\w+)%").matcher(text);
      
      while (matcher.find()) {
         String key = matcher.group(1);
         String paramName = "%" + key + "%";
         int index = newTemplate.indexOf(paramName);
         if (index != -1) {
            newTemplate.replace(index, index + paramName.length(), "%s");
            valueList.add(placeholders.get(key));
         }
      }
      
      return String.format(newTemplate.toString(), valueList.toArray());
   }
   
   @Nonnull
   public static String translateMsg(@Nonnull String text, @Nullable CommandSender sender, @Nullable Map<String, Object> placeholders) {
      String translatedText = text;
      if (BlatLibs.isPAPI()) {
         translatedText = PlaceholderAPI.setPlaceholders((OfflinePlayer) (sender instanceof Player ? (Player) sender : (sender instanceof OfflinePlayer ? (OfflinePlayer) sender : null)), text);
      }
      
      if (placeholders != null) {
         translatedText = format(translatedText, placeholders);
      }
      
      return  ColorAPI.process(translatedText);
   }
   
   @Nonnull
   public static List<String> translateMsgs(@Nonnull List<String> texts, @Nullable CommandSender sender, @Nullable Map<String, Object> placeholders) {
      List<String> translated = new ArrayList(texts);
      if (BlatLibs.isPAPI()) {
         translated = PlaceholderAPI.setPlaceholders((OfflinePlayer) (sender instanceof Player ? (Player) sender : (sender instanceof OfflinePlayer ? (OfflinePlayer) sender : null)), (List) translated);
      }
      
      for (int i = 0; i < ((List) translated).size(); ++i) {
         String temp = (String) ((List) translated).get(i);
         if (placeholders != null) {
            temp = format(temp, placeholders);
         }
         
         temp = ColorAPI.process(temp);
         ((List) translated).set(i, temp);
      }
      
      return (List) translated;
   }
   
   public static double round(double value, @Nonnegative int places) {
      BigDecimal bd = new BigDecimal(Double.toString(value));
      bd = bd.setScale(places, RoundingMode.HALF_UP);
      return bd.doubleValue();
   }
   
   public static double clamp(double val, double min, double max) {
      return Math.max(min, Math.min(max, val));
   }
   
   public static int clamp(int val, int min, int max) {
      return Math.max(min, Math.min(max, val));
   }
   
   @Nonnull
   public static String getMinecraftVersion(boolean impl) {
      Matcher m = VERSION_PATTERN.matcher(Bukkit.getVersion());
      if (m.find()) {
         String prefix = m.group(1);
         String major = m.group(2);
         String minor = m.group(4) == null ? "0" : m.group(4);
         return impl ? "v" + prefix + "_" + major + "_R" + (Integer.parseInt(minor) + 1) : prefix + "." + major + (minor.equals("0") ? "" : "." + minor);
      } else {
         return "";
      }
   }
   
   @Nonnull
   public static List<String> getAllWorldNames() {
      List<String> worldNames = new ArrayList();
      File[] var1 = Bukkit.getServer().getWorldContainer().listFiles();
      int var2 = var1.length;
      
      for (int var3 = 0; var3 < var2; ++var3) {
         File file = var1[var3];
         if (file.isDirectory() && Arrays.asList(file.list()).contains("level.dat")) {
            worldNames.add(file.getName());
         }
      }
      
      return worldNames;
   }
}