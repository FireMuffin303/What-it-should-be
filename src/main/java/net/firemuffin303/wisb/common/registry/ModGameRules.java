package net.firemuffin303.wisb.common.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.firemuffin303.wisb.common.WisbWorldComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;

import static net.firemuffin303.wisb.Wisb.MOD_ID;

public class ModGameRules {
    public static final GameRules.Key<GameRules.IntRule> SUGAR_CANE_HEIGHT = GameRuleRegistry.register(MOD_ID+":sugarCaneGrowHeight", GameRules.Category.MISC, GameRuleFactory.createIntRule(3,1,256,(server, intRule) -> gameRulePacket(server)));
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
    public static final GameRules.Key<GameRules.BooleanRule> TURTLE_HAS_BARNACLE = GameRuleRegistry.register(MOD_ID+":turtleHasBarnacle", GameRules.Category.MOBS,GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.IntRule> TURTLE_BARNACLE_MIN_TIME = GameRuleRegistry.register(MOD_ID+":turtleBarnacleMinTime", GameRules.Category.MOBS,GameRuleFactory.createIntRule(4800,1));
    public static final GameRules.Key<GameRules.IntRule> TURTLE_BARNACLE_MAX_TIME = GameRuleRegistry.register(MOD_ID+":turtleBarnacleMaxTime", GameRules.Category.MOBS,GameRuleFactory.createIntRule(12000,1));

    public static void init(){}


    public static void gameRulePacket(MinecraftServer server){
        for(ServerPlayerEntity serverPlayerEntity: server.getPlayerManager().getPlayerList()){
            ServerPlayNetworking.send(serverPlayerEntity,new WisbWorldComponent.WisbWorldSettingPacket(serverPlayerEntity.getServerWorld()));
        }
    }
}
