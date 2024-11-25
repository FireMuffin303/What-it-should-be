package net.firemuffin303.wisb.mixin.tooltip.bucketTooltip;

import net.firemuffin303.wisb.client.tooltip.AxolotlBucketTooltipComponent;
import net.firemuffin303.wisb.client.tooltip.TropicalfishTooltipComponent;
import net.firemuffin303.wisb.config.ModConfig;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(EntityBucketItem.class)
public abstract class EntityBucketItemMixin extends BucketItem {
    @Shadow @Final private EntityType<?> entityType;

    public EntityBucketItemMixin(Fluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Unique
    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        if(ModConfig.getShowMobBucket()){
            if(this.entityType == EntityType.AXOLOTL){
                if(stack.hasNbt()){
                    NbtCompound nbtCompound = stack.getNbt();
                    if(nbtCompound.contains("Variant") && nbtCompound.contains("Health") && nbtCompound.contains("Age")){
                        int variant = nbtCompound.getInt("Variant");
                        float health = nbtCompound.getFloat("Health");
                        int age = nbtCompound.getInt("Age");
                        return Optional.of(new AxolotlBucketTooltipComponent.AxolotlBucketTooltipData(age,health,variant));
                    }
                }
            } else if (this.entityType == EntityType.TROPICAL_FISH) {
                if(stack.hasNbt()){
                    NbtCompound nbtCompound = stack.getNbt();
                    if(nbtCompound.contains("BucketVariantTag",3)){
                        int bucketVariantTag = nbtCompound.getInt("BucketVariantTag");
                        int health = nbtCompound.getInt("Health");
                        return Optional.of(new TropicalfishTooltipComponent.TropicalfishTooltipData(bucketVariantTag,health));
                    }

                }
            }
        }
        return super.getTooltipData(stack);
    }

    @Inject(method = "appendTooltip",at = @At(value = "HEAD"), cancellable = true)
    public void wisb$appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
       if(ModConfig.getShowMobBucket()){
           ci.cancel();
       }
    }
}
