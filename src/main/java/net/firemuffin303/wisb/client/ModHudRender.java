package net.firemuffin303.wisb.client;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.firemuffin303.wisb.Wisb;
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
import net.minecraft.stat.Stats;
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

    public static void init(DrawContext drawContext,float delta){
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Objects.requireNonNull(minecraftClient.player);
        if(shouldRender(minecraftClient)){
            if(minecraftClient.player.isHolding(Items.COMPASS) || ModHudRender.isTrinketEquipped(minecraftClient.player,Items.COMPASS)){
                compassHUD(drawContext,delta,minecraftClient);
            }

            if(minecraftClient.player.isHolding(Items.CLOCK) || ModHudRender.isTrinketEquipped(minecraftClient.player,Items.CLOCK)){
                clockHUD(drawContext,delta,minecraftClient);
            }

            if(minecraftClient.player.isHolding(Items.RECOVERY_COMPASS) || ModHudRender.isTrinketEquipped(minecraftClient.player,Items.RECOVERY_COMPASS)){
                recoveryCompassHUD(drawContext,delta,minecraftClient);
            }
        }


    }

    public static void compassHUD(DrawContext drawContext, float delta, MinecraftClient minecraftClient){
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
        drawContext.drawTexture(COMPASS_HUD,x-(182/2),3,0,0,182,5);
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        int north = (int)  ((drawContext.getScaledWindowWidth()*bodyYaw) *-1) + drawContext.getScaledWindowWidth();

        drawContext.enableScissor(x-(182/2),0,x+(182/2),19);
        drawContext.drawText(textRenderer, Text.of("N"),north,2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("E"),(north+x/2),2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("W"),(north-x/2),2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("S"),(north+x),2,0xFFFFFF,true);
        drawContext.drawText(textRenderer, Text.of("S"),(north-x),2,0xFFFFFF,true);

        drawContext.setShaderColor(1f,1.0f,1.0f,0.5f);
        drawContext.drawTexture(COMPASS_HUD,x-1,2,8,10,8,8);
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        //Target Pointer
        if(target != null){
            Vec3d vec3d = Vec3d.ofCenter(target.getPos());
            double angleMarker = ModHudRender.calculateMarker(clientPlayerEntity,drawContext.getScaledWindowWidth(),bodyYaw,vec3d);

            drawContext.setShaderColor(1f,0.5f,0.5f,1.0f);
            drawContext.drawTexture(COMPASS_HUD,(int)angleMarker-2,2,0,10,8,8);
            drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        }

        int coordY = 12;
        if(ModHudRender.renderMap(textRenderer,world,clientPlayerEntity,drawContext,bodyYaw)){
            coordY = 20;
        }
        drawContext.disableScissor();

        //Coords
        Text coord = Text.translatable("wisb.compass.coordinates",String.format("%.2f",clientPlayerEntity.getX()) ,String.format("%.2f",clientPlayerEntity.getY()),String.format("%.2f",clientPlayerEntity.getZ()));
        int blockBorder = (textRenderer.getWidth(coord)/2) + 5;
        drawContext.fill(x-blockBorder,coordY,x+blockBorder,coordY+12,0x50000000);
        drawContext.drawCenteredTextWithShadow(textRenderer,coord,x,coordY+2,0xFFFFFF);
    }

    public static void clockHUD(DrawContext drawContext,float delta, MinecraftClient minecraftClient){
        Objects.requireNonNull(minecraftClient.world);
        TextRenderer textRenderer = minecraftClient.textRenderer;
        int x = drawContext.getScaledWindowWidth() /2;
        ClientWorld world = minecraftClient.world;
        drawContext.fill(x-60,1,x+60,24,0x50000000);

        double minuteTime = MathHelper.floorMod(world.getTimeOfDay() / 1000f,1f) * 60;
        double hourTime = MathHelper.floorMod( (world.getTimeOfDay()/24000f)-0.75f, 1f) * 24 ;

        Text AMPM = Text.translatable("wisb.clock.worldtime", String.format("%02d",hourTime < 12 ? (int)hourTime == 0 ? 12: (int)hourTime : (int)hourTime-12 == 0 ? 12: (int)hourTime-12),String.format("%02d",(int)minuteTime),world.getTimeOfDay() / 24000L);
        Text fullFormat = Text.translatable("wisb.clock.worldtime", String.format("%02d",(int)hourTime),String.format("%02d",(int)minuteTime),world.getTimeOfDay() / 24000L);

        drawContext.drawCenteredTextWithShadow(textRenderer,fullFormat ,x,3,0xFFFFFF);
        drawContext.drawCenteredTextWithShadow(textRenderer, Text.translatable("wisb.clock.playtime",
                minecraftClient.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME))
        ),x,13,0xFFFFFF);
    }

    public static void recoveryCompassHUD(DrawContext drawContext,float delta,MinecraftClient minecraftClient){
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

    private static boolean renderMap(TextRenderer textRenderer,ClientWorld world,ClientPlayerEntity clientPlayerEntity,DrawContext drawContext,double bodyYaw){
        boolean isRenderBannerName = false;
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
                drawContext.drawTexture(MAP_ICONS_TEXTURE,(int)mapDestinationAngle-8,1,g,h,8,8,128,128);
                drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

                if(bannerMarker.getName() != null){
                    double textAngle = (mapDestinationAngle -2f) - (textRenderer.getWidth(bannerMarker.getName())/2.0f);

                    int blockBorder = (textRenderer.getWidth(bannerMarker.getName())/2) + 2;
                    drawContext.fill((int)(mapDestinationAngle-2f)-blockBorder,9,(int)(mapDestinationAngle-2f)+blockBorder,18,0x50000000);

                    drawContext.drawText(textRenderer,bannerMarker.getName(),(int)textAngle,10,0xFFFFFF,false);
                    if(textAngle > ((drawContext.getScaledWindowWidth()/2f)-(182f/2f) - textRenderer.getWidth(bannerMarker.getName())) && textAngle < (drawContext.getScaledWindowWidth()/2f)+(182f/2f)){
                        isRenderBannerName = true;
                    }

                }
            }


            if(nbtCompound != null && nbtCompound.contains("Decorations",9)){
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
                        drawContext.drawTexture(MAP_ICONS_TEXTURE,(int)mapDestinationAngle-8,1,g,h,8,8,128,128);
                        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);
                    }
                }
            }
        }

        return isRenderBannerName;

    }
}
