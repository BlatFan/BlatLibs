package ru.blatfan.blatlibs.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.blatfan.blatlibs.util.compatibility.ItemsAdderManager;

public class ItemsUtils {
    public static ItemStack getItem(String id){
        if(ItemsAdderManager.isCustomItem(id)) {
            return ItemsAdderManager.getItem(id);
        }
        return new ItemStack(Material.valueOf(id.toUpperCase()));
    }
}
