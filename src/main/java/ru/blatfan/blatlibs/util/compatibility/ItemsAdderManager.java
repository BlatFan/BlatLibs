package ru.blatfan.blatlibs.util.compatibility;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderManager {
    public static ItemStack getItem(String name){
        return CustomStack.getInstance(name).getItemStack();
    }
    public static boolean isCustomItem(ItemStack item){
        return ItemsAdder.isCustomItem(item);
    }
    public static boolean isCustomItem(String item){
        return ItemsAdder.isCustomItem(item);
    }
}
