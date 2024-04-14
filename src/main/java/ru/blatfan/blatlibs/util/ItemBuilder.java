package ru.blatfan.blatlibs.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder {
    private ItemStack originalItem;
    private ItemStack endItem;

    public ItemBuilder(ItemStack originalItem){
        this.originalItem = originalItem;
        this.endItem = originalItem;
    }

    public ItemBuilder setDisplayName(String name) {
        ItemMeta meta = endItem.getItemMeta();
        meta.setDisplayName(name);
        endItem.setItemMeta(meta);
        return this;
    }
    public String getDisplayName() {
        ItemMeta meta = endItem.getItemMeta();
        return meta.getDisplayName();
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = endItem.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        endItem.setItemMeta(meta);
        return this;
    }
    public List<String> getLore() {
        ItemMeta meta = endItem.getItemMeta();
        return meta.getLore();
    }

    public ItemBuilder setModelData(int data) {
        ItemMeta meta = endItem.getItemMeta();
        meta.setCustomModelData(data);
        endItem.setItemMeta(meta);
        return this;
    }
    public int getModelData() {
        ItemMeta meta = endItem.getItemMeta();
        return meta.getCustomModelData();
    }

    public ItemBuilder setAttribute(Attribute attribute, AttributeModifier modifier){
        ItemMeta meta = endItem.getItemMeta();
        var attributes = meta.getAttributeModifiers();
        attributes.put(attribute, modifier);
        meta.setAttributeModifiers(attributes);
        endItem.setItemMeta(meta);
        return this;
    }

    public Map<Attribute, Collection<AttributeModifier>> getAttributes(){
        ItemMeta meta = endItem.getItemMeta();
        var attributes = meta.getAttributeModifiers();
        return attributes.asMap();
    }

    public ItemBuilder setUnbreakable(boolean b){
        ItemMeta meta = endItem.getItemMeta();
        meta.setUnbreakable(b);
        endItem.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        endItem.addEnchantment(enchantment, level);
        return this;
    }

    public ItemStack build(){
        return endItem;
    }
    public ItemStack getOriginalItem(){
        return originalItem;
    }
}
