package net.firemuffin303.wisb.mixin;

import com.mojang.logging.LogUtils;
import net.firemuffin303.wisb.client.tooltip.BeeNestTooltipComponent;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {

    @Shadow @Final public static String BLOCK_STATE_TAG_KEY;

    @Shadow @Final public static String BLOCK_ENTITY_TAG_KEY;

    public BlockItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        if(stack.getItem() instanceof BlockItem item && item.getBlock() instanceof BeehiveBlock){
            int honeyLevel = 0;
            int bees = 0;
            if(stack.hasNbt()){
                NbtCompound nbtCompound = stack.getNbt();
                NbtCompound blockStateTag = nbtCompound.getCompound(BLOCK_STATE_TAG_KEY);
                NbtCompound blockEntityTag = nbtCompound.getCompound(BLOCK_ENTITY_TAG_KEY);



                if(blockStateTag != null){
                    //WHY THE FUCK DO YOU STORE IN STRING
                    String honeyData = blockStateTag.getString("honey_level");
                    honeyLevel = Integer.parseInt(honeyData.isEmpty() || honeyData.isBlank() ? "0" : blockStateTag.getString("honey_level"));
                }

                if(blockEntityTag != null){
                    NbtList nbtList = blockEntityTag.getList("Bees",10);
                    bees = nbtList.isEmpty() ? 0 : nbtList.size();
                }

            }
            return Optional.of(new BeeNestTooltipComponent.BeeNestTooltipData(honeyLevel,bees));
        }
        return super.getTooltipData(stack);
    }
}
