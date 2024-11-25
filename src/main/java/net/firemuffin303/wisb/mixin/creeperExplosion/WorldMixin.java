package net.firemuffin303.wisb.mixin.creeperExplosion;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.firemuffin303.wisb.Wisb;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(World.class)
public abstract class WorldMixin {

    @WrapOperation(
            method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/World$ExplosionSourceType;Z)Lnet/minecraft/world/explosion/Explosion;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z")
    )
    public boolean wisb$CheckGamerule(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule, Operation<Boolean> original){
        return instance.getBoolean(Wisb.CREEPER_EXPLOSION_DESTROY_BLOCK) && original.call(instance, rule);
    }
}
