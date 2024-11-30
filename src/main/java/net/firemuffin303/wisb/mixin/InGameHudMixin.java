package net.firemuffin303.wisb.mixin;

import net.firemuffin303.wisb.client.ModHudRender;
import net.firemuffin303.wisb.config.ModConfig;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @ModifyConstant(method = "renderHeldItemTooltip", constant = @Constant(intValue = 59))
    public int wisb$adjustItemNameHeight(int constant){
        if(ModConfig.TOOL_ITEM_DISPLAY.getValue() == ModConfig.ItemGUIDisplay.DOWN){
            return ModHudRender.isRenderBannerName ? 80 : 68;
        }
        return constant;
    }
}
