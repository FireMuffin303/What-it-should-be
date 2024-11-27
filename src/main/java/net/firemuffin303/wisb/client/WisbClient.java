package net.firemuffin303.wisb.client;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.firemuffin303.wisb.Wisb;
import net.firemuffin303.wisb.client.screen.EditNameTagScreen;
import net.firemuffin303.wisb.client.tooltip.AxolotlBucketTooltipComponent;
import net.firemuffin303.wisb.client.tooltip.BeeNestTooltipComponent;
import net.firemuffin303.wisb.client.tooltip.CrossbowTooltipComponent;
import net.firemuffin303.wisb.client.tooltip.TropicalfishTooltipComponent;
import net.firemuffin303.wisb.common.WisbWorldComponent;
import net.firemuffin303.wisb.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;

public class WisbClient implements ClientModInitializer {

    public static final Gson GSON = (new GsonBuilder()).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();


    @Override
    public void onInitializeClient() {
        ModConfig.load();

        HudRenderCallback.EVENT.register(ModHudRender::init);
        TooltipComponentCallback.EVENT.register(WisbClient::tooltipComponentEvent);

        ClientPlayNetworking.registerGlobalReceiver(Wisb.WISB_WORLD_SETTING_S2C,(wisbWorldSettingPacket, clientPlayerEntity, packetSender) -> {
            MinecraftClient.getInstance().execute(() ->{
                ((WisbWorldComponent.WisbWorldComponentAccessor)clientPlayerEntity.clientWorld).wisb$getWisbWorldComponent()
                        .setGameRule(
                                wisbWorldSettingPacket.bonemealAbleSugarCane,
                                wisbWorldSettingPacket.sugarCaneGrowHeight,
                                wisbWorldSettingPacket.bonemealAbleLilyPad,
                                wisbWorldSettingPacket.bonemealAbleNetherWart,
                                wisbWorldSettingPacket.easierBabyZombie,
                                wisbWorldSettingPacket.compassGUI,
                                wisbWorldSettingPacket.showTreasureInCompassGUI,
                                wisbWorldSettingPacket.clockGUI,
                                wisbWorldSettingPacket.renameCost
                        );
            });

        });

        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            ItemStack itemStack = playerEntity.getStackInHand(hand);
            if(world.isClient){
                if(itemStack.isOf(Items.NAME_TAG)){
                    ClientWorld clientWorld = (ClientWorld) world;
                    boolean isSettingOn = ModConfig.getSneakingToRenameNameTag();
                    if(isSettingOn){
                        if(playerEntity.isSneaking()){
                            MinecraftClient.getInstance().setScreen(new EditNameTagScreen(itemStack,hand,clientWorld));
                            return TypedActionResult.success(playerEntity.getStackInHand(hand));
                        }
                        return TypedActionResult.pass(ItemStack.EMPTY);
                    }

                    MinecraftClient.getInstance().setScreen(new EditNameTagScreen(itemStack,hand,clientWorld));

                    return TypedActionResult.success(playerEntity.getStackInHand(hand));
                }
            }
            return TypedActionResult.pass(ItemStack.EMPTY);
        });
    }

    private static TooltipComponent tooltipComponentEvent(TooltipData tooltipData){
        if(tooltipData instanceof BeeNestTooltipComponent.BeeNestTooltipData beeNestTooltipData){
            return new BeeNestTooltipComponent(beeNestTooltipData);
        } else if (tooltipData instanceof CrossbowTooltipComponent.CrossbowTooltipData crossbowData) {
            return new CrossbowTooltipComponent(crossbowData);
        } else if(tooltipData instanceof AxolotlBucketTooltipComponent.AxolotlBucketTooltipData axolotlBucketTooltipData){
            return new AxolotlBucketTooltipComponent(axolotlBucketTooltipData);
        }else if (tooltipData instanceof TropicalfishTooltipComponent.TropicalfishTooltipData tropicalFishBucketTooltipData) {
            return new TropicalfishTooltipComponent(tropicalFishBucketTooltipData);
        }
        return null;
    }
}
