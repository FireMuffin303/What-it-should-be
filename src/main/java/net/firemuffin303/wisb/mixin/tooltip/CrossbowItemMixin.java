package net.firemuffin303.wisb.mixin.tooltip;

import net.firemuffin303.wisb.client.tooltip.CrossbowTooltipComponent;
import net.firemuffin303.wisb.config.ModConfig;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Debug(export = true)
@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends RangedWeaponItem {
    @Shadow
    private static List<ItemStack> getProjectiles(ItemStack crossbow) {
        return null;
    }

    @Shadow
    public static boolean isCharged(ItemStack stack) {
        return false;
    }

    public CrossbowItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendTooltip",at = @At("HEAD"), cancellable = true)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci){
        if(ModConfig.getShowCrossbowBucket()){
            ci.cancel();
        }
    }

    @Unique
    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        if(ModConfig.getShowCrossbowBucket()){
            List<ItemStack> list = getProjectiles(stack);
            if(isCharged(stack) && !list.isEmpty()){
                ItemStack itemStack = list.get(0);
                return Optional.of(new CrossbowTooltipComponent.CrossbowTooltipData(itemStack));
            }
        }
        return super.getTooltipData(stack);
    }
}
