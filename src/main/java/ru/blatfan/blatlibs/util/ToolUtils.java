package ru.blatfan.blatlibs.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ToolUtils {
    private static List<Material> weapon = Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD, Material.TRIDENT, Material.BOW, Material.CROSSBOW);
    private static List<Material> pickaxe = Arrays.asList(Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE);
    private static List<Material> axe = Arrays.asList(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE);
    private static List<Material> shovel = Arrays.asList(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL);
    private static List<Material> hoe = Arrays.asList(Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE);
    
    private static List<Material> wooden = Arrays.asList(Material.WOODEN_PICKAXE, Material.WOODEN_SHOVEL, Material.WOODEN_HOE, Material.WOODEN_AXE, Material.WOODEN_SWORD);
    private static List<Material> stone = Arrays.asList(Material.STONE_PICKAXE, Material.STONE_SHOVEL, Material.STONE_HOE, Material.STONE_AXE, Material.STONE_SWORD);
    private static List<Material> iron = Arrays.asList(Material.IRON_PICKAXE, Material.IRON_SHOVEL, Material.IRON_HOE, Material.IRON_AXE, Material.IRON_SWORD);
    private static List<Material> golden = Arrays.asList(Material.GOLDEN_PICKAXE, Material.GOLDEN_SHOVEL, Material.GOLDEN_HOE, Material.GOLDEN_AXE, Material.GOLDEN_SWORD);
    private static List<Material> diamond = Arrays.asList(Material.DIAMOND_PICKAXE, Material.DIAMOND_SHOVEL, Material.DIAMOND_HOE, Material.DIAMOND_AXE, Material.DIAMOND_SWORD);
    private static List<Material> netherite = Arrays.asList(Material.NETHERITE_PICKAXE, Material.NETHERITE_SHOVEL, Material.NETHERITE_HOE, Material.NETHERITE_AXE, Material.NETHERITE_SWORD);
    
    public static void addWeapon(Material mat) { weapon.add(mat);}
    public static void remWeapon(Material mat) { weapon.remove(mat);}
    public static void addPickaxe(Material mat) { pickaxe.add(mat);}
    public static void remPickaxe(Material mat) { pickaxe.remove(mat);}
    public static void addAxe(Material mat) { axe.add(mat);}
    public static void remAxe(Material mat) { axe.remove(mat);}
    public static void addShovel(Material mat) { shovel.add(mat);}
    public static void remShovel(Material mat) { shovel.remove(mat);}
    public static void addHoe(Material mat) { hoe.add(mat);}
    public static void remHoe(Material mat) { hoe.remove(mat);}
    public static void addWooden(Material mat) { wooden.add(mat);}
    public static void remWooden(Material mat) { wooden.remove(mat);}
    public static void addStone(Material mat) { stone.add(mat);}
    public static void remStone(Material mat) { stone.remove(mat);}
    public static void addIron(Material mat) { iron.add(mat);}
    public static void remIron(Material mat) { iron.remove(mat);}
    public static void addGolden(Material mat) { golden.add(mat);}
    public static void remGolden(Material mat) { golden.remove(mat);}
    public static void addDiamond(Material mat) { diamond.add(mat);}
    public static void remDiamond(Material mat) { diamond.remove(mat);}
    public static void addNetherite(Material mat) { netherite.add(mat);}
    public static void remNetherite(Material mat) { netherite.remove(mat);}
    
    public static boolean isWooden(ItemStack item){
        Material mat = item.getType();
        for (Material wood : wooden)
            if(wood==mat) return true;
        return false;
    }
    public static boolean isStone(ItemStack item){
        Material mat = item.getType();
        for (Material v : stone)
            if(v==mat) return true;
        return false;
    }
    
    
    public static boolean isIron(ItemStack item){
        Material mat = item.getType();
        for (Material v : iron)
            if(v==mat) return true;
        return false;
    }
    
    
    public static boolean isGolden(ItemStack item){
        Material mat = item.getType();
        for (Material v : golden)
            if(v==mat) return true;
        return false;
    }
    
    
    public static boolean isDiamond(ItemStack item){
        Material mat = item.getType();
        for (Material v : diamond)
            if(v==mat) return true;
        return false;
    }
    
    
    public static boolean isNetherite(ItemStack item){
        Material mat = item.getType();
        for (Material v : netherite)
            if(v==mat) return true;
        return false;
    }
    
    public static boolean isWeapon(ItemStack item){
        Material mat = item.getType();
        for (Material v : weapon)
            if(v==mat) return true;
        return false;
    }
    
    
    public static boolean isPickaxe(ItemStack item){
        Material mat = item.getType();
        for (Material v : pickaxe)
            if(v==mat) return true;
        return false;
    }
    
    
    public static boolean isAxe(ItemStack item){
        Material mat = item.getType();
        for (Material v : axe)
            if(v==mat) return true;
        return false;
    }
    
    
    public static boolean isShovel(ItemStack item){
        Material mat = item.getType();
        for (Material v : shovel)
            if(v==mat) return true;
        return false;
    }
    
    
    public static boolean isHoe(ItemStack item){
        Material mat = item.getType();
        for (Material v : hoe)
            if(v==mat) return true;
        return false;
    }
}
