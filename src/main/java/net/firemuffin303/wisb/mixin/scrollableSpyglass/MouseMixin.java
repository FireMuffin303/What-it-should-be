package net.firemuffin303.wisb.mixin.scrollableSpyglass;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.firemuffin303.wisb.client.WisbClient;
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

    @WrapOperation(method = "onMouseScroll",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;scrollInHotbar(D)V"))
    public void wisb$adjustingSpyglassZoom(PlayerInventory instance, double scrollAmount, Operation<Void> original, @Local int i){
        if(this.client.player.isUsingSpyglass()){
            float zoomScale = this.client.options.sprintKey.isPressed() ? 0.1f : 0.05f;
            WisbClient.spyGlassZoomScale =  MathHelper.clamp(WisbClient.spyGlassZoomScale + (-i * zoomScale),0.1f,0.8f);
        }else{
            original.call(instance,scrollAmount);
        }
    }
}
