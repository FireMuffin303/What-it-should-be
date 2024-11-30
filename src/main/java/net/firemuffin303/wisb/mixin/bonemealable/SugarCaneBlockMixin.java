package net.firemuffin303.wisb.mixin.bonemealable;

import com.llamalad7.mixinextras.sugar.Local;
import net.firemuffin303.wisb.common.registry.ModGameRules;
import net.firemuffin303.wisb.common.WisbWorldComponent;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin extends Block implements Fertilizable {
    @Shadow @Final public static IntProperty AGE;

    public SugarCaneBlockMixin(Settings settings) {
        super(settings);
    }

    @ModifyConstant(method = "randomTick",constant = @Constant(intValue = 3))
    public int wisb$randomTick(int constant, @Local(argsOnly = true) ServerWorld serverWorld){
        return serverWorld.getGameRules().getInt(ModGameRules.SUGAR_CANE_HEIGHT);
    }


    @Unique
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        boolean bonemeal = false;
        int growHeight = 3;
        if(isClient){
            bonemeal = ((WisbWorldComponent.WisbWorldComponentAccessor)world).wisb$getWisbWorldComponent().bonemealAbleSugarCane;
            growHeight = ((WisbWorldComponent.WisbWorldComponentAccessor)world).wisb$getWisbWorldComponent().sugarCaneGrowHeight;
        }else if(world instanceof ServerWorld serverWorld){
            bonemeal  = serverWorld.getGameRules().getBoolean(ModGameRules.BONEMEAL_ABLE_SUGAR_CANE);
            growHeight = serverWorld.getGameRules().getInt(ModGameRules.SUGAR_CANE_HEIGHT);
        }

        if(!bonemeal){
            return false;
        }

        BlockPos sugarCaneCheckPos = pos;

        int height = 0;

        do{
            height++;
            sugarCaneCheckPos = sugarCaneCheckPos.down();
        }while(world.getBlockState(sugarCaneCheckPos).isOf(Blocks.SUGAR_CANE));

        if (height < growHeight){
            sugarCaneCheckPos = pos;
            do{
                sugarCaneCheckPos = sugarCaneCheckPos.up();
                if(world.getBlockState(sugarCaneCheckPos).isOf(Blocks.SUGAR_CANE)){
                    height++;
                }
            }while(world.getBlockState(sugarCaneCheckPos).isOf(Blocks.SUGAR_CANE));
        }


        return height < growHeight && world.isAir(sugarCaneCheckPos);
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
        //I add random in here because I playtest and found out it totally busted. so I need to nerf it down.
        if(random.nextInt(3) != 0){
            return;
        }

        do{
            airPos = airPos.up();
        }while(world.getBlockState(airPos).isOf(Blocks.SUGAR_CANE));

        if(world.isAir(airPos)){
            world.setBlockState(airPos,Blocks.SUGAR_CANE.getDefaultState());
            world.setBlockState(pos,state.with(AGE,0),4);
        }
    }
}
