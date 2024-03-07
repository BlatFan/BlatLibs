package ru.blatfan.blatlibs.util.compatibility;

import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.inventory.ItemStack;

public class OraxenManager {
    public ItemStack getItem(String name){
        return OraxenItems.getItemById(name).build();
    }
    public boolean isCustomItem(ItemStack item){
        return OraxenItems.exists(item);
    }
    public boolean isCustomItem(String item){
        return OraxenItems.exists(item);
    }
}
