package net.firemuffin303.wisb;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.firemuffin303.wisb.client.tooltip.BeeNestTooltipComponent;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.Identifier;

public class Wisb implements ModInitializer {
    public static final String MOD_ID = "wisb";

    public static boolean isTrinketsInstall = false;

    @Override
    public void onInitialize() {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(Items.ROTTEN_FLESH,0.65F);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(Items.POISONOUS_POTATO,0.65F);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD,Items.POISONOUS_POTATO,Potions.POISON);



        if(FabricLoader.getInstance().isModLoaded("trinkets")){
            isTrinketsInstall = true;
        }
    }

    public static Identifier modId(String name){
        return new Identifier(MOD_ID,name);
    }
}
