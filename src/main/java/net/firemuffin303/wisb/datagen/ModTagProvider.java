package net.firemuffin303.wisb.datagen;

import dev.emi.trinkets.TrinketsMain;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider {
    public static final TagKey<Item> TRINKET_BELT = TagKey.of(RegistryKeys.ITEM,new Identifier(TrinketsMain.MOD_ID,"legs/belt"));
    public static final TagKey<Item> TRINKET_HEAD = TagKey.of(RegistryKeys.ITEM,new Identifier(TrinketsMain.MOD_ID,"legs/belt"));

    public static class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture, null);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            this.getOrCreateTagBuilder(TRINKET_BELT)
                    .add(Items.COMPASS)
                    .add(Items.CLOCK)
                    .add(Items.RECOVERY_COMPASS);
        }
    }
}
