package net.firemuffin303.wisb.common;

import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;

public class ModCauldronBehavior {

    public static void init(){
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.WHITE_CONCRETE_POWDER,concrete(Items.WHITE_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_GRAY_CONCRETE_POWDER,concrete(Items.LIGHT_GRAY_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.GRAY_CONCRETE_POWDER,concrete(Items.GRAY_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.BLACK_CONCRETE_POWDER,concrete(Items.BLACK_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.BROWN_CONCRETE_POWDER,concrete(Items.BROWN_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.RED_CONCRETE_POWDER,concrete(Items.RED_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.ORANGE_CONCRETE_POWDER,concrete(Items.ORANGE_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.YELLOW_CONCRETE_POWDER,concrete(Items.YELLOW_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.LIME_CONCRETE_POWDER,concrete(Items.LIME_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.GREEN_CONCRETE_POWDER,concrete(Items.GREEN_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.CYAN_CONCRETE_POWDER,concrete(Items.CYAN_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_BLUE_CONCRETE_POWDER,concrete(Items.LIGHT_BLUE_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.BLUE_CONCRETE_POWDER,concrete(Items.BLUE_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.PURPLE_CONCRETE_POWDER,concrete(Items.PURPLE_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.MAGENTA_CONCRETE_POWDER,concrete(Items.MAGENTA_CONCRETE));
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.PINK_CONCRETE_POWDER,concrete(Items.PINK_CONCRETE));

    }


    public static CauldronBehavior concrete(Item concrete){
        return  (state, world, pos, player, hand, stack) -> {
            if(!world.isClient){
                Item item = stack.getItem();
                //player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BLACK_CONCRETE)));
                player.setStackInHand(hand,exchangeEntireStack(stack,player,new ItemStack(concrete),true));
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.playSound(null, pos, SoundEvents.BLOCK_SAND_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }
            return ActionResult.success(world.isClient);

        };
    }

    public static ItemStack exchangeEntireStack(ItemStack inputStack, PlayerEntity player, ItemStack outputStack, boolean creativeOverride) {
        boolean bl = player.getAbilities().creativeMode;
        int count = inputStack.getCount();
        if (creativeOverride && bl) {
            if (!player.getInventory().contains(outputStack)) {
                outputStack.setCount(count);
                player.getInventory().insertStack(outputStack);
            }

            return inputStack;
        } else {
            if (!bl) {
                inputStack.decrement(count);
            }
            outputStack.setCount(count);
            if (inputStack.isEmpty()) {
                return outputStack;
            } else {
                if (!player.getInventory().insertStack(outputStack)) {
                    player.dropItem(outputStack, false);
                }

                return inputStack;
            }
        }
    }
}
