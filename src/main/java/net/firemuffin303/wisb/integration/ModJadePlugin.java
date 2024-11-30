package net.firemuffin303.wisb.integration;

import net.firemuffin303.wisb.Wisb;
import net.firemuffin303.wisb.client.ModHudRender;
import net.firemuffin303.wisb.common.TurtleAccessor;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.config.IWailaConfig;
import snownee.jade.api.theme.IThemeHelper;

public class ModJadePlugin implements IWailaPlugin {

    public static final Identifier JADE_OPTION_POS_X = new Identifier(Wisb.MOD_ID,"item_tool_display_pos_x");
    public static final Identifier JADE_OPTION_POS_Y = new Identifier(Wisb.MOD_ID,"item_tool_display_pos_y");
    public static final Identifier JADE_OPTION_HUD_RENDER_SIDE = new Identifier(Wisb.MOD_ID,"hud_render_side");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerEntityDataProvider(TurtleComponentProvider.INSTANCE, TurtleEntity.class);

    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.addConfig(JADE_OPTION_POS_X,0.0f,0f,1f,true);
        registration.addConfig(JADE_OPTION_POS_Y,0.0f,0f,1f,true);
        registration.addConfig(JADE_OPTION_HUD_RENDER_SIDE,HUDRenderSide.LEFT);
        registration.markAsClientFeature(JADE_OPTION_POS_X);
        registration.markAsClientFeature(JADE_OPTION_POS_Y);
        registration.markAsClientFeature(JADE_OPTION_HUD_RENDER_SIDE);
        registration.addBeforeRenderCallback((iTooltip, rect2i, drawContext, accessor, colorSetting) -> {
            if(ModHudRender.isRendering){
                float x = IWailaConfig.get().getPlugin().getFloat(JADE_OPTION_POS_X);
                float y = IWailaConfig.get().getPlugin().getFloat(JADE_OPTION_POS_Y);

                int ix = IWailaConfig.get().getPlugin().getEnum(JADE_OPTION_HUD_RENDER_SIDE) == HUDRenderSide.LEFT ? (int)(x * 30) : (int) ((drawContext.getScaledWindowHeight()*2 - rect2i.getWidth()*1.3225f) - (x * 30));
                rect2i.setX(ix);
                rect2i.setY((int)(y * (drawContext.getScaledWindowHeight() - rect2i.getHeight())));
            }
            return false;
        });

        registration.registerEntityComponent(TurtleComponentProvider.INSTANCE, TurtleEntity.class);
    }

    public enum TurtleComponentProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor>{
        INSTANCE;

        @Override
        public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
            if(entityAccessor.getServerData().contains("NextBarnacle")){

                iTooltip.add((Text.translatable("jade.wisb.turtle.barnacle.tooltip", IThemeHelper.get().seconds(entityAccessor.getServerData().getInt("NextBarnacle")))));
            }
        }



        @Override
        public Identifier getUid() {
            return new Identifier(Wisb.MOD_ID,"turtle_barnacle");
        }

        @Override
        public void appendServerData(NbtCompound nbtCompound, EntityAccessor entityAccessor) {
            if(entityAccessor.getEntity() instanceof TurtleAccessor turtleAccessor){
                int i = turtleAccessor.getCoverTime();
                nbtCompound.putInt("NextBarnacle",i);
            }
        }
    }

    enum HUDRenderSide{
        LEFT,
        RIGHT
    }
}
