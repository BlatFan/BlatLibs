package ru.blatfan.blatlibs.util;

import org.bukkit.inventory.ItemStack;
import ru.blatfan.blatlibs.BlatLibs;
import ru.blatfan.blatlibs.util.compatibility.ItemsAdderManager;
import ru.blatfan.blatlibs.util.compatibility.OraxenManager;

public class ItemsUtils {
    private static ItemsAdderManager ia;
    private static OraxenManager oraxen;

    public static void setup(){
        if(BlatLibs.isITEMSADDER()) ia=new ItemsAdderManager();
        if(BlatLibs.isORAXEN()) oraxen=new OraxenManager();
    }

    public static ItemStack getItem(String id){
        String[] text = id.split(":");
        if(text[0]=="itemsadder" && text.length == 3) return ia.getItem(text[1]+":"+text[2]);
        else if(text[0]=="oraxen" && text.length == 2) return oraxen.getItem(text[1]);
        else if(text[0]=="minecraft" && text.length==2) return XMaterial.valueOf(text[1]).parseItem();

        return XMaterial.AIR.parseItem();
    }
}
