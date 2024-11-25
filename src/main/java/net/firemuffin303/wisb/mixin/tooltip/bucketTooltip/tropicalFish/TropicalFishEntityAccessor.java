package net.firemuffin303.wisb.mixin.tooltip.bucketTooltip.tropicalFish;

import net.minecraft.entity.passive.TropicalFishEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TropicalFishEntity.class)
public interface TropicalFishEntityAccessor {

    @Invoker("setTropicalFishVariant")
    void wisb$setTropicalFishVariant(int variant);
}
