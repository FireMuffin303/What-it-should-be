package net.firemuffin303.wisb.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModLangProvider extends FabricLanguageProvider {
    protected ModLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("wisb.compass.coordinates","X : %.2d | Y : %.2d | Z : %.2d");
        translationBuilder.add("wisb.clock.worldtime","Time : %s | Day : %s");
        translationBuilder.add("wisb.clock.playtime","Play time : %s");
        translationBuilder.add("wisb.beehive.tooltip.honey_level","%s / %s");
        translationBuilder.add("wisb.axolotl_bucket.tooltip.variant","Variant : %s");
    }
}
