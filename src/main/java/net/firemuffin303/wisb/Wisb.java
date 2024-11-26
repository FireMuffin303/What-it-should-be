package net.firemuffin303.wisb;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.firemuffin303.wisb.client.screen.EditNameTagScreen;
import net.firemuffin303.wisb.common.ModCauldronBehavior;
import net.firemuffin303.wisb.common.WisbWorldComponent;
import net.firemuffin303.wisb.common.packet.RenameNameTagPacket;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.GameRules;

public class Wisb implements ModInitializer {
    public static final String MOD_ID = "wisb";

    public static boolean isTrinketsInstall = false;

    public static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();

    public static final GameRules.Key<GameRules.IntRule> SUGAR_CANE_HEIGHT = GameRuleRegistry.register(MOD_ID+":sugarCaneGrowHeight", GameRules.Category.MISC,GameRuleFactory.createIntRule(3,1,256,(server, intRule) -> gameRulePacket(server)));
    public static final GameRules.Key<GameRules.BooleanRule> BONEMEAL_ABLE_SUGAR_CANE = GameRuleRegistry.register(MOD_ID+":bonemealableSugarCane", GameRules.Category.MISC,GameRuleFactory.createBooleanRule(true,(server, booleanRule) -> gameRulePacket(server)));
    public static final GameRules.Key<GameRules.BooleanRule> BONEMEALABLE_NETHERWART = GameRuleRegistry.register(MOD_ID+":bonemealableNetherwart", GameRules.Category.MISC,GameRuleFactory.createBooleanRule(false, (server, booleanRule) -> gameRulePacket(server)));
    public static final GameRules.Key<GameRules.BooleanRule> BONEMEALABLE_LILLYPAD = GameRuleRegistry.register(MOD_ID+":bonemealableLilyPad", GameRules.Category.MISC,GameRuleFactory.createBooleanRule(false, (server, booleanRule) -> gameRulePacket(server)));
    public static final GameRules.Key<GameRules.BooleanRule> EASIER_BABY_ZOMBIE = GameRuleRegistry.register(MOD_ID+":easierBabyZombie", GameRules.Category.MOBS,GameRuleFactory.createBooleanRule(true,(server, booleanRule) -> gameRulePacket(server)));
    public static final GameRules.Key<GameRules.BooleanRule> CREEPER_EXPLOSION_DESTROY_BLOCK = GameRuleRegistry.register(MOD_ID+":creaperExplosionDestroyBlock",GameRules.Category.MOBS,GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> SPAWN_ARMOR_STAND_WITH_ARMS = GameRuleRegistry.register(MOD_ID+":spawnArmorStandWithArm", GameRules.Category.SPAWNING,GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> SHEARABLE_ITEM_FRAME = GameRuleRegistry.register(MOD_ID+":shearAbleItemFrame", GameRules.Category.MISC,GameRuleFactory.createBooleanRule(true));

    public static final GameRules.Key<GameRules.BooleanRule> COMPASS_GUI = GameRuleRegistry.register(MOD_ID+":showCompassGUI", GameRules.Category.PLAYER,GameRuleFactory.createBooleanRule(true,(server, booleanRule) -> gameRulePacket(server)));
    public static final GameRules.Key<GameRules.BooleanRule> SHOW_TREASURE_IN_COMPASS_GUI = GameRuleRegistry.register(MOD_ID+":showTreasureInCompassGUI", GameRules.Category.PLAYER,GameRuleFactory.createBooleanRule(false,(server, booleanRule) -> gameRulePacket(server)));
    public static final GameRules.Key<GameRules.BooleanRule> CLOCK_GUI = GameRuleRegistry.register(MOD_ID+":showClockGUI", GameRules.Category.PLAYER,GameRuleFactory.createBooleanRule(true,(server, booleanRule) -> gameRulePacket(server)));
    public static final GameRules.Key<GameRules.IntRule> RENAME_NAMETAG_COST = GameRuleRegistry.register(MOD_ID+":renameNameTagCost", GameRules.Category.PLAYER,GameRuleFactory.createIntRule(1,(server, booleanRule) -> gameRulePacket(server)));

    public static final PacketType<WisbWorldComponent.WisbWorldSettingPacket> WISB_WORLD_SETTING_S2C = PacketType.create(new Identifier(MOD_ID,"wisb_world_setting_s2c"), WisbWorldComponent.WisbWorldSettingPacket::new);
    public static final PacketType<RenameNameTagPacket> WISB_RENAME_NAMETAG = PacketType.create(new Identifier(MOD_ID,"rename_nametag"), RenameNameTagPacket::new);

    public static final FeatureFlag WISB_RECIPE = new FeatureManager.Builder("wisb").addFlag(new Identifier(MOD_ID,"wisb_recipes"));

    @Override
    public void onInitialize() {

        ModCauldronBehavior.init();
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(Items.ROTTEN_FLESH,0.65F);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(Items.POISONOUS_POTATO,0.65F);
        //BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD,Items.POISONOUS_POTATO,Potions.POISON);

        if(FabricLoader.getInstance().isModLoaded("trinkets")){
            isTrinketsInstall = true;
        }

        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            minecraftServer.execute(() ->{
                ServerPlayNetworking.send(serverPlayNetworkHandler.player,new WisbWorldComponent.WisbWorldSettingPacket(serverPlayNetworkHandler.player.getServerWorld()));
            });
        });

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((serverPlayerEntity, serverWorld, serverWorld1) -> {
            ServerPlayNetworking.send(serverPlayerEntity,new WisbWorldComponent.WisbWorldSettingPacket(serverWorld1));

        });

        ServerPlayNetworking.registerGlobalReceiver(WISB_RENAME_NAMETAG,(renameNameTagPacket, serverPlayerEntity, packetSender) -> {
            if(serverPlayerEntity.getServer() == null){
                return;
            }
            serverPlayerEntity.getServer().execute(() ->{
                ItemStack itemStack = serverPlayerEntity.getStackInHand(renameNameTagPacket.hand);
                String name = renameNameTagPacket.name;
                if(name.isBlank() || name.isEmpty()){
                    itemStack.setCustomName(null);
                }else{
                    itemStack.setCustomName(Text.of(name));
                }
                if(!serverPlayerEntity.getAbilities().creativeMode){
                    serverPlayerEntity.addExperienceLevels(-renameNameTagPacket.xpCost);
                }
            });
        });
    }

    public static Identifier modId(String name){
        return new Identifier(MOD_ID,name);
    }

    public static void gameRulePacket(MinecraftServer server){
        for(ServerPlayerEntity serverPlayerEntity: server.getPlayerManager().getPlayerList()){
            ServerPlayNetworking.send(serverPlayerEntity,new WisbWorldComponent.WisbWorldSettingPacket(serverPlayerEntity.getServerWorld()));
        }
    }




}
