package net.firemuffin303.wisb.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.ItemCriterion;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(Items.ROTTEN_FLESH), RecipeCategory.MISC,Items.LEATHER,0.35f,200)
                .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH)).offerTo(consumer,getItemPath(Items.LEATHER) + "_from_furnace");

        CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(Items.ROTTEN_FLESH), RecipeCategory.MISC,Items.LEATHER,0.35f,100)
                .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH)).offerTo(consumer,getItemPath(Items.LEATHER) + "_from_smoking");

        CookingRecipeJsonBuilder.createCampfireCooking(Ingredient.ofItems(Items.ROTTEN_FLESH), RecipeCategory.MISC,Items.LEATHER,0.35f,600)
                .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH)).offerTo(consumer,getItemPath(Items.LEATHER) + "_from_campfire");
    }
}