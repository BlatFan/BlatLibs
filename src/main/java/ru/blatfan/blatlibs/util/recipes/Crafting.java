package ru.blatfan.blatlibs.util.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Crafting {

    private final JavaPlugin plugin;
    private final ShapedRecipe recipe;
    private final Map<Character, RecipeIngredient> ingredients;

    public Crafting(JavaPlugin plugin, ItemStack result, String key) {
        NamespacedKey recipeKey = new NamespacedKey(plugin, key);
        this.recipe = new ShapedRecipe(recipeKey, result);
        this.ingredients = new HashMap();
        this.plugin = plugin;
    }

    public Crafting setShape(String... shape) {
        this.recipe.shape(shape);
        return this;
    }

    public Crafting setIngredient(char key, Material material, int amount) {
        RecipeIngredient recipeIngredient = new RecipeIngredient(material, amount);
        this.recipe.setIngredient(key, recipeIngredient.getMaterialData());
        this.ingredients.put(key, recipeIngredient);
        return this;
    }

    public Crafting setIngredient(char key, ItemStack itemStack, int amount) {
        RecipeIngredient recipeIngredient = new RecipeIngredient(itemStack, amount);
        this.recipe.setIngredient(key, recipeIngredient.getMaterialData());
        this.ingredients.put(key, recipeIngredient);
        return this;
    }

    public ShapedRecipe build() {
        Iterator var1 = this.ingredients.entrySet().iterator();

        while(var1.hasNext()) {
            Map.Entry<Character, RecipeIngredient> entry = (Map.Entry)var1.next();
            this.recipe.setIngredient((Character)entry.getKey(), ((RecipeIngredient)entry.getValue()).getMaterialData());
        }

        return this.recipe;
    }

    private static class RecipeIngredient {
        private final ItemStack itemStack;

        public RecipeIngredient(Material material, int amount) {
            this.itemStack = new ItemStack(material, amount);
        }

        public RecipeIngredient(ItemStack itemStack, int amount) {
            this.itemStack = itemStack.clone();
            this.itemStack.setAmount(amount);
        }

        public MaterialData getMaterialData() {
            return this.itemStack.getData();
        }

        public int getAmount() {
            return this.itemStack.getAmount();
        }

        public boolean isSameItem(ItemStack other) {
            return this.itemStack.isSimilar(other);
        }
    }

}
