package ru.blatfan.blatlibs.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.blatfan.blatlibs.BlatLibs;
import ru.blatfan.blatlibs.util.compatibility.ItemsAdderManager;

public class ItemsUtils {
    public static ItemStack getItem(String id){
        String[] text = id.split(":");
        if(text[0]=="itemsadder" && text.length == 2) {
            if(!BlatLibs.isITEMSADDER()){
                BlatLibs.getInstance().getConsole().warn("Cannot get %s because ItemsAdder didn't found".formatted(text[1]));
                return new ItemStack(Material.AIR);
            }
            return ItemsAdderManager.getItem(text[1]);
        }
        else if((text[0]=="minecraft" && text.length==2)|| text.length==1) return new ItemStack(Material.valueOf(text[1]));

        return new ItemStack(Material.AIR);
    }
}
