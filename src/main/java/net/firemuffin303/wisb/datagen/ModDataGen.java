package net.firemuffin303.wisb.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.server.command.SpawnArmorTrimsCommand;

public class ModDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModLangProvider::new);
        pack.addProvider(ModTagProvider.ModItemTagProvider::new);
        pack.addProvider(ModRecipeProvider::new);
    }
}
