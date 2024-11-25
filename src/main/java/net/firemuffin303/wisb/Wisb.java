package net.firemuffin303.wisb;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.firemuffin303.wisb.common.ModCauldronBehavior;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public class Wisb implements ModInitializer {
    public static final String MOD_ID = "wisb";

    public static boolean isTrinketsInstall = false;

    public static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();

    public static final GameRules.Key<GameRules.IntRule> SUGAR_CANE_HEIGHT = GameRuleRegistry.register(MOD_ID+":sugarCaneGrowHeight", GameRules.Category.MISC,GameRuleFactory.createIntRule(3,1));
    public static final GameRules.Key<GameRules.BooleanRule> SUGAR_CANE_CAN_BE_BONEMEAL = GameRuleRegistry.register(MOD_ID+":bonemealableSugarCane", GameRules.Category.MISC,GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> BONEMEALABLE_NETHERWART = GameRuleRegistry.register(MOD_ID+":bonemealableNetherwart", GameRules.Category.MISC,GameRuleFactory.createBooleanRule(false));
    public static final GameRules.Key<GameRules.BooleanRule> BONEMEALABLE_LILLYPAD = GameRuleRegistry.register(MOD_ID+":bonemealableLilyPad", GameRules.Category.MISC,GameRuleFactory.createBooleanRule(false));
    public static final GameRules.Key<GameRules.BooleanRule> EASIER_BABY_ZOMBIE = GameRuleRegistry.register(MOD_ID+":easierBabyZombie", GameRules.Category.MOBS,GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> CREEPER_EXPLOSION_DESTROY_BLOCK = GameRuleRegistry.register(MOD_ID+":creaperExplosionDestroyBlock",GameRules.Category.MOBS,GameRuleFactory.createBooleanRule(true));

    public static final GameRules.Key<GameRules.BooleanRule> COMPASS_GUI = GameRuleRegistry.register(MOD_ID+":showCompassGUI", GameRules.Category.PLAYER,GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> CLOCK_GUI = GameRuleRegistry.register(MOD_ID+":showClockGUI", GameRules.Category.PLAYER,GameRuleFactory.createBooleanRule(true));

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
