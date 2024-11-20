package net.firemuffin303.wisb.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.*;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin extends Block implements Fertilizable {
    @Shadow @Final public static IntProperty AGE;

    public SugarCaneBlockMixin(Settings settings) {
        super(settings);
    }

    @Unique
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        BlockPos airPos = pos;
        do{
            airPos = airPos.up();
        }while(world.getBlockState(airPos).isOf(Blocks.SUGAR_CANE));

        return world.isAir(airPos);
    }

    @Unique
    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Unique
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos airPos = pos;
        do{
            airPos = airPos.up();
        }while(world.getBlockState(airPos).isOf(Blocks.SUGAR_CANE));

        if(world.isAir(airPos)){
            world.setBlockState(airPos,Blocks.SUGAR_CANE.getDefaultState());
            world.setBlockState(pos,state.with(AGE,0),4);
        }
    }
}
