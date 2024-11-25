package net.firemuffin303.wisb.mixin.bonemealable;

import net.firemuffin303.wisb.Wisb;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetherWartBlock.class)
public abstract class NetherWartBlockMixin extends PlantBlock implements Fertilizable {
    @Shadow @Final public static IntProperty AGE;

    public NetherWartBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        boolean bonemealable = false;
        if(!isClient){
            ServerWorld serverWorld = (ServerWorld) world;
            bonemealable = serverWorld.getGameRules().getBoolean(Wisb.BONEMEALABLE_NETHERWART);
        }
        return state.get(AGE) < 3 && bonemealable;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos,this.getDefaultState().with(AGE,state.get(AGE) + 1));
    }
}
