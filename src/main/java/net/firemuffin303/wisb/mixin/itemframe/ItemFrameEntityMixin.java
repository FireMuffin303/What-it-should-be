package net.firemuffin303.wisb.mixin.itemframe;

import net.firemuffin303.wisb.common.registry.ModGameRules;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntityMixin extends AbstractDecorationEntity {
    protected ItemFrameEntityMixin(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interact",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"), cancellable = true)
    public void wisb$shearInteraction(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        ItemStack itemStack = player.getStackInHand(hand);
        if(itemStack.isOf(Items.SHEARS) && player.isSneaking() && this.getWorld() instanceof ServerWorld serverWorld && serverWorld.getGameRules().getBoolean(ModGameRules.SHEARABLE_ITEM_FRAME)){
            this.setFlag(5,true);
            this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR,1f,1f);
            this.emitGameEvent(GameEvent.BLOCK_CHANGE,player);
            cir.setReturnValue(ActionResult.CONSUME);
        }
    }

    @Inject(method = "damage",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;dropHeldStack(Lnet/minecraft/entity/Entity;Z)V"))
    public void wisb$invisibleDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        this.setFlag(5,false);
    }
}
