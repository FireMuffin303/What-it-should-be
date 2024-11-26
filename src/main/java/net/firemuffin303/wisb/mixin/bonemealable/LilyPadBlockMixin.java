package net.firemuffin303.wisb.mixin.bonemealable;

import net.firemuffin303.wisb.Wisb;
import net.firemuffin303.wisb.common.WisbWorldComponent;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LilyPadBlock.class)
public abstract class LilyPadBlockMixin extends PlantBlock implements Fertilizable {
    public LilyPadBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        boolean bonemealable = false;
        if(isClient){
            bonemealable = ((WisbWorldComponent.WisbWorldComponentAccessor)world).wisb$getWisbWorldComponent().bonemealAbleLilyPad;
        }else if (world instanceof ServerWorld serverWorld){
            bonemealable = serverWorld.getGameRules().getBoolean(Wisb.BONEMEALABLE_LILLYPAD);
        }
        return bonemealable;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        dropStack(world,pos,new ItemStack((LilyPadBlock)(Object)this));
    }
}
