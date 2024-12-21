package net.firemuffin303.wisb.mixin.scrollableSpyglass;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.firemuffin303.wisb.client.WisbClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {
    @ModifyReturnValue(method = "getFovMultiplier", at = @At(value = "RETURN",ordinal = 0))
    public float wisb$modifyspyGlassZoomScale(float original){
        if(WisbClient.spyGlassZoomScale != 0.1f){
            return WisbClient.spyGlassZoomScale;
        }
        return original;
    }
}
