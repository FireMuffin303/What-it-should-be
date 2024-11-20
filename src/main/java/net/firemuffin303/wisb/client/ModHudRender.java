package net.firemuffin303.wisb.client;

import com.mojang.logging.LogUtils;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.impl.util.log.Log;
import net.firemuffin303.wisb.Wisb;
import net.firemuffin303.wisb.mixin.BossBarHudAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.CompassItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;

public class ModHudRender {
    private static final Identifier COMPASS_HUD = Wisb.modId("textures/gui/compass_bar.png");

    public static void init(DrawContext drawContext,float delta){
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Objects.requireNonNull(minecraftClient.player);
        if(shouldRender(minecraftClient)){
            if(ModHudRender.isHoldingItem(minecraftClient.player,Items.COMPASS) || ModHudRender.isTrinketEquipped(minecraftClient.player,Items.COMPASS)){
                compassHUD(drawContext,delta,minecraftClient);
            }

            if(ModHudRender.isHoldingItem(minecraftClient.player,Items.CLOCK) || ModHudRender.isTrinketEquipped(minecraftClient.player,Items.CLOCK)){
                clockHUD(drawContext,delta,minecraftClient);
            }
        }


    }

    public static void compassHUD(DrawContext drawContext, float delta, MinecraftClient minecraftClient){
        Objects.requireNonNull(minecraftClient.player);
        Objects.requireNonNull(minecraftClient.world);

        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;

        TextRenderer textRenderer = minecraftClient.textRenderer;
        boolean isHoldingOffhand = clientPlayerEntity.getStackInHand(Hand.OFF_HAND).isOf(Items.COMPASS);
        int x = drawContext.getScaledWindowWidth() /2;

        ItemStack compass = clientPlayerEntity.getStackInHand(isHoldingOffhand ? Hand.OFF_HAND : Hand.MAIN_HAND);
        ClientWorld world = minecraftClient.world;

        GlobalPos target = CompassItem.hasLodestone(compass) ? CompassItem.createLodestonePos(compass.getOrCreateNbt()) : CompassItem.createSpawnPos(world);

        float bodyYaw = MathHelper.floorMod(clientPlayerEntity.getBodyYaw() / 360.0f,1.0f);

        drawContext.setShaderColor(1.0f,1.0f,1.0f,0.95f);
        drawContext.drawTexture(COMPASS_HUD,x-(182/2),3,0,0,182,5);
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        int north = (int)  ((drawContext.getScaledWindowWidth()*bodyYaw) *-1) + drawContext.getScaledWindowWidth();

        drawContext.enableScissor(x-(182/2),0,x+(182/2),12);
        drawContext.drawText(textRenderer, Text.of("N"),north,2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("E"),(north+x/2),2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("W"),(north-x/2),2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("S"),(north+x),2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("S"),(north-x),2,0xFFFFFF,true);

        drawContext.setShaderColor(1f,1.0f,1.0f,0.5f);
        drawContext.drawTexture(COMPASS_HUD,x,2,8,10,8,8);
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        if(target != null){
            Vec3d vec3d = target.getPos().toCenterPos();
            double angle = Math.atan2(vec3d.getZ() - clientPlayerEntity.getZ(), vec3d.getX() - clientPlayerEntity.getX()) / 6.2831854820251465;
                /*
                drawContext.setShaderColor(0.5f,1.0f,1.0f,1.0f);
                drawContext.drawTexture(COMPASS_HUD,x,2,0,10,8,8);
                drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

                 */

            //drawContext.drawText(textRenderer, Text.of("A"), (int)(north+(angle*360)),2,0xFFFFFF,true);

        }

        drawContext.disableScissor();

        //Coords

        drawContext.fill(x-85,10,x+85,22,0x50000000);
        drawContext.drawCenteredTextWithShadow(textRenderer, Text.translatable("wisb.compass.coordinates",String.format("%.2f",clientPlayerEntity.getX()) ,String.format("%.2f",clientPlayerEntity.getY()),String.format("%.2f",clientPlayerEntity.getZ())),x,12,0xFFFFFF);


    }

    public static void clockHUD(DrawContext drawContext,float delta, MinecraftClient minecraftClient){
        Objects.requireNonNull(minecraftClient.world);
        TextRenderer textRenderer = minecraftClient.textRenderer;
        int x = drawContext.getScaledWindowWidth() /2;
        ClientWorld world = minecraftClient.world;
        drawContext.fill(x-60,1,x+60,24,0x50000000);

        drawContext.drawCenteredTextWithShadow(textRenderer, Text.translatable("wisb.clock.worldtime",world.getTimeOfDay(),world.getTimeOfDay() / 24000L),x,3,0xFFFFFF);
        drawContext.drawCenteredTextWithShadow(textRenderer, Text.translatable("wisb.clock.playtime",
                minecraftClient.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME))
        ),x,13,0xFFFFFF);
    }

    private static boolean isHoldingItem(ClientPlayerEntity clientPlayerEntity,Item item){
        return clientPlayerEntity.getStackInHand(Hand.MAIN_HAND).isOf(item) || clientPlayerEntity.getStackInHand(Hand.OFF_HAND).isOf(item);
    }

    private static boolean isTrinketEquipped(ClientPlayerEntity clientPlayerEntity, Item item){
        if(Wisb.isTrinketsInstall){
            Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(clientPlayerEntity);
            if(trinketComponent.isPresent() && trinketComponent.get().isEquipped(item)){
                return  true;

            }
        }
        return false;
    }

    private static boolean shouldRender(MinecraftClient minecraftClient){
        return !minecraftClient.options.debugEnabled && ((BossBarHudAccessor)minecraftClient.inGameHud.getBossBarHud()).getBossBar().isEmpty();
    }
}
