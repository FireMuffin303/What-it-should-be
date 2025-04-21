package net.firemuffin303.wisb.mixin.scrollableSpyglass;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.firemuffin303.wisb.client.WisbClient;
import net.firemuffin303.wisb.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @ModifyExpressionValue(method = "updateMouse", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;cursorDeltaX:D",ordinal = 2))
    public double wisb$modifyXValue(double original){
        return ModConfig.ZOOMABLE_SPYGLASS.getValue() ? original * (8.0 * WisbClient.spyGlassZoomScale) : original;
    }

    @ModifyExpressionValue(method = "updateMouse", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;cursorDeltaY:D",ordinal = 2))
    public double wisb$modifyYValue(double original){
        return ModConfig.ZOOMABLE_SPYGLASS.getValue() ? original * (8.0 * WisbClient.spyGlassZoomScale) : original;
    }

    @WrapOperation(method = "onMouseScroll",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;scrollInHotbar(D)V"))
    public void wisb$adjustingSpyglassZoom(PlayerInventory instance, double scrollAmount, Operation<Void> original, @Local int i){
        if(this.client.player.isUsingSpyglass() && ModConfig.ZOOMABLE_SPYGLASS.getValue()){
            float zoomScale = this.client.options.sprintKey.isPressed() ? 0.1f : 0.05f;
            WisbClient.spyGlassZoomScale =  MathHelper.clamp(WisbClient.spyGlassZoomScale + (-i * zoomScale),0.1f,0.8f);
        }else{
            original.call(instance,scrollAmount);
        }
    }
}
