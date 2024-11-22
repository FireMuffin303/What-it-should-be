package net.firemuffin303.wisb;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.firemuffin303.wisb.client.tooltip.BeeNestTooltipComponent;
import net.firemuffin303.wisb.common.ModCauldronBehavior;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.event.GameEvent;

public class Wisb implements ModInitializer {
    public static final String MOD_ID = "wisb";

    public static boolean isTrinketsInstall = false;

    public static final GameRules.Key<GameRules.IntRule> SUGAR_CANE_HEIGHT = GameRuleRegistry.register(MOD_ID+":sugar_cane_grow_height", GameRules.Category.MISC,GameRuleFactory.createIntRule(3,1));
    public static final GameRules.Key<GameRules.BooleanRule> SUGAR_CANE_CAN_BE_BONEMEAL = GameRuleRegistry.register(MOD_ID+":sugar_cane_can_be_bonemeal", GameRules.Category.MISC,GameRuleFactory.createBooleanRule(true));




    @Override
    public void onInitialize() {

        ModCauldronBehavior.init();
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
