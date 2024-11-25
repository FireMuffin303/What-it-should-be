package net.firemuffin303.wisb.mixin.hitbox;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.logging.LogUtils;
import net.firemuffin303.wisb.Wisb;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true,print = true)
@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {

    @Unique
    EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

    @Unique
    EntityAttributeModifier BABY_HEALTH_ID = new EntityAttributeModifier("Baby health modifier",-0.3, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    @Shadow public abstract boolean isBaby();

    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setBaby",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeInstance;removeModifier(Lnet/minecraft/entity/attribute/EntityAttributeModifier;)V"))
    public void wisb$removeModifierBaby(boolean baby, CallbackInfo ci){
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        if(serverWorld.getGameRules().getBoolean(Wisb.EASIER_BABY_ZOMBIE)){
            entityAttributeInstance.removeModifier(BABY_HEALTH_ID);
        }
    }

    @Inject(method = "setBaby",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeInstance;addTemporaryModifier(Lnet/minecraft/entity/attribute/EntityAttributeModifier;)V"))
    public void wisb$applyBabyHeath(boolean baby, CallbackInfo ci){
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        if(serverWorld.getGameRules().getBoolean(Wisb.EASIER_BABY_ZOMBIE)) {
            entityAttributeInstance.addTemporaryModifier(BABY_HEALTH_ID);
            this.setHealth((float) this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue());
        }
    }

    //Gamerule need to send packet
    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        EntityDimensions entityDimensions = super.getDimensions(pose);
        return this.isBaby() ? entityDimensions.scaled(1.8f,1.25f) : super.getDimensions(pose) ;
    }
}
