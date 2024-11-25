package net.firemuffin303.wisb.mixin.armorstand;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandItem.class)
public abstract class AmorStandItemMixin {

    @Inject(method = "useOnBlock",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(F)I"))
    public void wisb$setShowArmWhenSpawn(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, @Local ArmorStandEntity armorStandEntity){
        armorStandEntity.setShowArms(true);
    }
}
