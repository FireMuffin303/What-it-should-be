package net.firemuffin303.wisb.mixin.blockmodify;

import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.class)
public abstract interface AbstractBlockAccessor {
    @Accessor("settings")
    AbstractBlock.Settings getSettings();
}
