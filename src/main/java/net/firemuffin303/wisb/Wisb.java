package net.firemuffin303.wisb;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.firemuffin303.wisb.common.ModCauldronBehavior;
import net.firemuffin303.wisb.common.TurtleDispenserBehavior;
import net.firemuffin303.wisb.common.registry.ModGameRules;
import net.firemuffin303.wisb.common.WisbWorldComponent;
import net.firemuffin303.wisb.common.packet.RenameNameTagPacket;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Wisb implements ModInitializer {
    public static final String MOD_ID = "wisb";

    public static boolean isTrinketsInstall = false;


    public static final PacketType<WisbWorldComponent.WisbWorldSettingPacket> WISB_WORLD_SETTING_S2C = PacketType.create(new Identifier(MOD_ID,"wisb_world_setting_s2c"), WisbWorldComponent.WisbWorldSettingPacket::new);
    public static final PacketType<RenameNameTagPacket> WISB_RENAME_NAMETAG = PacketType.create(new Identifier(MOD_ID,"rename_nametag"), RenameNameTagPacket::new);

    public static final Identifier TURTLE_BRUSH_DROPS = new Identifier(MOD_ID,"turtle_brush_drops");

    @Override
    public void onInitialize() {
        ModGameRules.init();
        ModCauldronBehavior.init();
        DispenserBlock.registerBehavior(Items.BRUSH, new TurtleDispenserBehavior());
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(Items.ROTTEN_FLESH,0.65F);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(Items.POISONOUS_POTATO,0.65F);

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






}
