package net.firemuffin303.wisb.mixin;

import net.firemuffin303.wisb.client.tooltip.AxolotlBucketTooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(EntityBucketItem.class)
public abstract class EntityBucketItemMixin extends BucketItem {
    @Shadow @Final private EntityType<?> entityType;

    public EntityBucketItemMixin(Fluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
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
        }

        return super.getTooltipData(stack);
    }
}
