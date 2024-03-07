package ru.blatfan.blatlibs.util.compatibility;

import com.olliez4.space.energy.Machine;
import com.olliez4.space.gui.guide.categories.Category;
import com.olliez4.space.gui.guide.categories.CategoryManager;
import com.olliez4.space.gui.guide.categories.CreativeCategory;
import com.olliez4.space.main.SpaceAPI;
import com.olliez4.space.managers.worlds.WorldManager;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.blatfan.blatlibs.util.XMaterial;

import java.util.List;

public class SpaceManager {

    public WorldManager getWorldManager(){
        return new SpaceAPI().getWorldManager();
    }
    public class CategotyBuilder {
        private Category category;
        public CategotyBuilder(ItemStack icon) {
            category = new Category(new ItemStack(XMaterial.AIR.parseItem()));
        }
        public CategotyBuilder addItem(ItemStack item){
            category.add(item);
            return this;
        }
        public CategotyBuilder addItems(List<ItemStack> items){
            for (ItemStack item : items){
                category.add(item);
            }
            return this;
        }
        public CategotyBuilder register(){
            CategoryManager.registerCategory(category);
            return this;
        }
        public Category build(){
            return category;
        }
    }
    public class CreativeCategotyBuilder {
        private CreativeCategory category;
        public CreativeCategotyBuilder(ItemStack icon) {
            category = new CreativeCategory(new ItemStack(XMaterial.AIR.parseItem()));
        }
        public CreativeCategotyBuilder addItem(ItemStack item){
            category.add(item);
            return this;
        }
        public CreativeCategotyBuilder addItems(List<ItemStack> items){
            for (ItemStack item : items){
                category.add(item);
            }
            return this;
        }
        public CreativeCategotyBuilder register(){
            CategoryManager.registerCategory(category);
            return this;
        }
        public CreativeCategory build(){
            return category;
        }
    }
}
