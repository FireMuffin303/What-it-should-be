package net.firemuffin303.wisb.client;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.firemuffin303.wisb.Wisb;
import net.firemuffin303.wisb.common.WisbWorldComponent;
import net.firemuffin303.wisb.config.ModConfig;
import net.firemuffin303.wisb.mixin.BossBarHudAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.*;
import net.minecraft.item.map.MapBannerMarker;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class ModHudRender {
    private static final Identifier COMPASS_HUD = Wisb.modId("textures/gui/compass_bar.png");
    private static final Identifier MAP_ICONS_TEXTURE = new Identifier("textures/map/map_icons.png");

    public static boolean isRenderBannerName = false;
    public static boolean isRendering = false;

    public static void init(DrawContext drawContext,float delta){
        isRendering = false;
        boolean isDown = ModConfig.TOOL_ITEM_DISPLAY.getValue() == ModConfig.ItemGUIDisplay.DOWN;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Objects.requireNonNull(minecraftClient.player);
        Objects.requireNonNull(minecraftClient.world);
        ClientWorld clientWorld = minecraftClient.world;
        if(shouldRender(minecraftClient)){
            if(minecraftClient.player.isHolding(Items.COMPASS) || ModHudRender.isTrinketEquipped(minecraftClient.player,Items.COMPASS) && ((WisbWorldComponent.WisbWorldComponentAccessor)clientWorld).wisb$getWisbWorldComponent().compassGUI){
                compassHUD(drawContext,delta,minecraftClient,isDown);
                isRendering = true;
            }

            if(minecraftClient.player.isHolding(Items.CLOCK) || ModHudRender.isTrinketEquipped(minecraftClient.player,Items.CLOCK) && ((WisbWorldComponent.WisbWorldComponentAccessor)clientWorld).wisb$getWisbWorldComponent().clockGUI){
                clockHUD(drawContext,delta,minecraftClient,isDown);
                isRendering = true;
            }

            if(minecraftClient.player.isHolding(Items.RECOVERY_COMPASS) || ModHudRender.isTrinketEquipped(minecraftClient.player,Items.RECOVERY_COMPASS) && ((WisbWorldComponent.WisbWorldComponentAccessor)clientWorld).wisb$getWisbWorldComponent().compassGUI){
                recoveryCompassHUD(drawContext,delta,minecraftClient,isDown);
                isRendering = true;
            }
        }


    }

    public static void compassHUD(DrawContext drawContext, float delta, MinecraftClient minecraftClient,boolean isDown){
        Objects.requireNonNull(minecraftClient.player);
        Objects.requireNonNull(minecraftClient.world);
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;


        TextRenderer textRenderer = minecraftClient.textRenderer;
        ClientWorld world = minecraftClient.world;

        boolean isHoldingOffhand = clientPlayerEntity.getStackInHand(Hand.OFF_HAND).isOf(Items.COMPASS);
        int x = drawContext.getScaledWindowWidth() /2;

        ItemStack compass = clientPlayerEntity.getStackInHand(isHoldingOffhand ? Hand.OFF_HAND : Hand.MAIN_HAND);
        GlobalPos target = CompassItem.hasLodestone(compass) ? CompassItem.createLodestonePos(compass.getOrCreateNbt()) : CompassItem.createSpawnPos(world);
        double bodyYaw = getBodyYaw(clientPlayerEntity);

        drawContext.setShaderColor(1.0f,1.0f,1.0f,0.95f);
        drawContext.drawTexture(COMPASS_HUD,x-(182/2), isDown ? drawContext.getScaledWindowHeight() - 45 : 3,0,0,182,5);
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        int north = (int)  (((drawContext.getScaledWindowWidth()*bodyYaw) *-1) + drawContext.getScaledWindowWidth()) - 2;

        drawContext.enableScissor(x-(182/2), isDown ? drawContext.getScaledWindowHeight() - 58 : 0,x+(182/2), isDown ? (drawContext.getScaledWindowHeight() - 50) + 19 : 19);
        drawContext.drawText(textRenderer, Text.of("N"),north, isDown ? drawContext.getScaledWindowHeight() - 46 : 2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("E"),(north+x/2), isDown ? drawContext.getScaledWindowHeight() - 46 : 2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("W"),(north-x/2), isDown ? drawContext.getScaledWindowHeight() - 46 : 2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("S"),(north+x), isDown ? drawContext.getScaledWindowHeight() - 46 : 2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("S"),(north-x), isDown ? drawContext.getScaledWindowHeight() - 46 : 2,0xFFFFFF,true);

        drawContext.setShaderColor(1f,1.0f,1.0f,0.5f);
        drawContext.drawTexture(COMPASS_HUD,x-4,2,8,10,8,8);
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        //Target Pointer
        if(target != null){
            Vec3d vec3d = Vec3d.ofCenter(target.getPos());
            double angleMarker = ModHudRender.calculateMarker(clientPlayerEntity,drawContext.getScaledWindowWidth(),bodyYaw,vec3d);

            drawContext.setShaderColor(1f,0.5f,0.5f,1.0f);
            drawContext.drawTexture(COMPASS_HUD,(int)angleMarker-2,isDown ? drawContext.getScaledWindowHeight() - 46 : 2,0,10,8,8);
            drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        }

        int coordY = isDown ? drawContext.getScaledWindowHeight() - 58 : 12;
        if(ModHudRender.renderMap(textRenderer,world,clientPlayerEntity,drawContext,bodyYaw,isDown)){
            coordY = isDown ? drawContext.getScaledWindowHeight() - 70 : 20;
        }
        drawContext.disableScissor();

        boolean isPrecise = ModConfig.preciseCoordinate.getValue();
        Vec3d vec3d = clientPlayerEntity.getPos();

        //Coords
        Text coord = Text.translatable("wisb.compass.coordinates",
                String.format(isPrecise ? "%.2f": "%.0f",isPrecise ? vec3d.getX() : Math.floor(vec3d.getX())),
                String.format(isPrecise ? "%.2f": "%.0f",isPrecise ? vec3d.getY() : Math.floor(vec3d.getY())),
                String.format(isPrecise ? "%.2f": "%.0f",isPrecise ? vec3d.getZ() : Math.floor(vec3d.getZ())));
        int blockBorder = (textRenderer.getWidth(coord)/2) + 5;
        drawContext.fill(x-blockBorder,coordY,x+blockBorder,coordY+12,0x50000000);
        drawContext.drawCenteredTextWithShadow(textRenderer,coord,x,coordY+2,0xFFFFFF);
    }

    public static void clockHUD(DrawContext drawContext,float delta, MinecraftClient minecraftClient,boolean isDown){
        Objects.requireNonNull(minecraftClient.world);
        TextRenderer textRenderer = minecraftClient.textRenderer;
        int x = drawContext.getScaledWindowWidth() /2;
        ClientWorld world = minecraftClient.world;
        double minuteTime = MathHelper.floorMod(world.getTimeOfDay() / 1000f,1f) * 60;
        double hourTime = MathHelper.floorMod( (world.getTimeOfDay()/24000f)-0.75f, 1f) * 24 ;
        Text AMPM = Text.translatable("wisb.clock.worldtime.twelve_format", String.format("%02d",hourTime < 12 ? (int)hourTime == 0 ? 12: (int)hourTime : (int)hourTime-12 == 0 ? 12: (int)hourTime-12),String.format("%02d",(int)minuteTime),hourTime > 12 ? "PM":"AM",world.getTimeOfDay() / 24000L);
        Text fullFormat = Text.translatable("wisb.clock.worldtime", String.format("%02d",(int)hourTime),String.format("%02d",(int)minuteTime),world.getTimeOfDay() / 24000L);

        Text currentText = ModConfig.timeFormat.getValue() == ModConfig.TimeFormat.FULL_FORMAT ? fullFormat : AMPM;

        int xPadding = (textRenderer.getWidth(currentText) /2) +5;
        drawContext.fill(x-xPadding,1,x+xPadding,12,0x50000000);

        drawContext.drawCenteredTextWithShadow(textRenderer, currentText ,x,3,0xFFFFFF);
    }

    public static void recoveryCompassHUD(DrawContext drawContext,float delta,MinecraftClient minecraftClient,boolean isDown){
        Objects.requireNonNull(minecraftClient.player);
        Objects.requireNonNull(minecraftClient.world);

        TextRenderer textRenderer = minecraftClient.textRenderer;
        ClientWorld world = minecraftClient.world;
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;

        int x = drawContext.getScaledWindowWidth() /2;
        drawContext.enableScissor(x-(182/2),0,x+(182/2),12);

        drawContext.setShaderColor(1.0f,1.0f,1.0f,0.95f);
        drawContext.drawTexture(COMPASS_HUD,x-(182/2),3,0,5,182,5);
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        drawContext.setShaderColor(1f,1.0f,1.0f,0.5f);
        drawContext.drawTexture(COMPASS_HUD,x-1,2,8,10,8,8);
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        double bodyYaw = getBodyYaw(clientPlayerEntity);
        if(clientPlayerEntity.getLastDeathPos().isPresent()){
            Vec3d vec3d = clientPlayerEntity.getLastDeathPos().get().getPos().toCenterPos();
            double angleMarker = ModHudRender.calculateMarker(clientPlayerEntity,drawContext.getScaledWindowWidth(),bodyYaw,vec3d);

            drawContext.setShaderColor(1f,1.0f,1.0f,1.0f);
            drawContext.drawTexture(COMPASS_HUD,(int)angleMarker-4,2,16,10,8,8);
            drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        }

        drawContext.disableScissor();

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

    private static double getBodyYaw(ClientPlayerEntity clientPlayerEntity){
        return MathHelper.floorMod(clientPlayerEntity.getBodyYaw() / 360.0f,1.0f);
    }

    private static double calculateMarker(ClientPlayerEntity clientPlayerEntity,int screenWidth,double bodyYaw,Vec3d vec3d){
        double angle = (Math.atan2(vec3d.getZ() - clientPlayerEntity.getZ(), vec3d.getX() - clientPlayerEntity.getX()) / 6.2831854820251465);
        return ((MathHelper.floorMod (-angle + (bodyYaw-0.25f),1.0f) * screenWidth) * -1) + screenWidth;
    }

    private static boolean renderMap(TextRenderer textRenderer,ClientWorld world,ClientPlayerEntity clientPlayerEntity,DrawContext drawContext,double bodyYaw,boolean isDown){
        isRenderBannerName = false;
        if(clientPlayerEntity.isHolding(Items.FILLED_MAP)){
            ItemStack map = clientPlayerEntity.getStackInHand(Hand.MAIN_HAND).isOf(Items.FILLED_MAP) ? clientPlayerEntity.getStackInHand(Hand.MAIN_HAND) : clientPlayerEntity.getStackInHand(Hand.OFF_HAND);
            MapState mapState = FilledMapItem.getMapState(map,world);

            if(!map.hasNbt()){
                return false;
            }

            NbtCompound nbtCompound = map.getNbt();

            if(mapState == null){
              return false;
            }

            MapIcon mapIcon = null;

            for (MapBannerMarker bannerMarker : mapState.getBanners()) {

                Vec3d vec3d = bannerMarker.getPos().toCenterPos();
                double mapDestinationAngle = ModHudRender.calculateMarker(clientPlayerEntity,drawContext.getScaledWindowWidth(),bodyYaw,vec3d);
                int b = bannerMarker.getIconType().getId();
                int g = (b%16) * 8;
                int h = (b/16) * 8;

                drawContext.setShaderColor(1f,1f,1f,1.0f);
                drawContext.drawTexture(MAP_ICONS_TEXTURE,(int)mapDestinationAngle-4,isDown ? drawContext.getScaledWindowHeight() - 46 : 1 ,g,h,8,8,128,128);
                drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

                if(bannerMarker.getName() != null){
                    double textAngle = (mapDestinationAngle -2f) - (textRenderer.getWidth(bannerMarker.getName())/2.0f);

                    int blockBorder = (textRenderer.getWidth(bannerMarker.getName())/2) + 2;
                    drawContext.fill((int)(mapDestinationAngle-2f)-blockBorder, isDown ? drawContext.getScaledWindowHeight() - 56 : 9,(int)(mapDestinationAngle-2f)+blockBorder, isDown ? (drawContext.getScaledWindowHeight() - 56) + 10 :18,0x50000000);

                    drawContext.drawText(textRenderer,bannerMarker.getName(),(int)textAngle, isDown ? drawContext.getScaledWindowHeight() - 54 : 10,0xFFFFFF,false);
                    if(textAngle > ((drawContext.getScaledWindowWidth()/2f)-(182f/2f) - (textRenderer.getWidth(bannerMarker.getName()) - 40)) && textAngle < (drawContext.getScaledWindowWidth()/2f)+((182f/2f) -40)){
                        isRenderBannerName = true;
                    }

                }
            }


            if(nbtCompound != null && nbtCompound.contains("Decorations",9) && ((WisbWorldComponent.WisbWorldComponentAccessor)world).wisb$getWisbWorldComponent().showTressureInCompassGUI){
                NbtList nbtList = nbtCompound.getList("Decorations",10);
                for(int j = 0; j < nbtList.size(); j++){
                    NbtCompound decoData = nbtList.getCompound(j);
                    double mapPosX = decoData.getDouble("x");
                    double mapPosZ = decoData.getDouble("z");
                    byte mapType = decoData.getByte("type");

                    Iterator<MapIcon> mapIconIterator = mapState.getIcons().iterator();

                    do {
                        if(!mapIconIterator.hasNext()){
                            break;
                        }
                        mapIcon = mapIconIterator.next();

                    }while ((mapIcon.getTypeId() == 0 || mapIcon.getTypeId() != mapType) && !mapIcon.isAlwaysRendered());


                    if(mapIcon != null){
                        int b = mapIcon.getTypeId();
                        int g = (b%16) * 8;
                        int h = (b/16) * 8;


                        Vec3d destination = new Vec3d(mapPosX,60d,mapPosZ);
                        double mapDestinationAngle = ModHudRender.calculateMarker(clientPlayerEntity,drawContext.getScaledWindowWidth(),bodyYaw,destination);

                        drawContext.setShaderColor(1f,1f,1f,1.0f);
                        drawContext.drawTexture(MAP_ICONS_TEXTURE,(int)mapDestinationAngle-8,isDown ? drawContext.getScaledWindowHeight() - 46 : 1,g,h,8,8,128,128);
                        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);
                    }
                }
            }
        }

        return isRenderBannerName;

    }
}
