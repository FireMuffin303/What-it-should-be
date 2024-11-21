package net.firemuffin303.wisb.client.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import net.firemuffin303.wisb.mixin.BucketTooltip.tropicalFish.TropicalFishEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;

public class TropicalfishTooltipComponent implements TooltipComponent {
    final Identifier ICON = new Identifier("minecraft","textures/gui/icons.png");

    final TropicalFishEntity tropicalFishEntity;
    final int bucketVariantTag;
    final Text healthText;
    final Text variantText;

    final Quaternionf quaternionf = (new Quaternionf()).rotationXYZ(-2.8f, -0.8f, 1.6f);


    public TropicalfishTooltipComponent(TropicalfishTooltipData tropicalfishTooltipData){
        this.tropicalFishEntity = EntityType.TROPICAL_FISH.create(MinecraftClient.getInstance().world);
        this.tropicalFishEntity.prevBodyYaw = this.tropicalFishEntity.getYaw();
        this.tropicalFishEntity.headYaw = this.tropicalFishEntity.getYaw();
        this.tropicalFishEntity.prevHeadYaw = this.tropicalFishEntity.getYaw();
        this.bucketVariantTag = tropicalfishTooltipData.bucketVariantTag;
        DyeColor baseColor = TropicalFishEntity.getBaseDyeColor(this.bucketVariantTag);
        DyeColor patternColor = TropicalFishEntity.getPatternDyeColor(this.bucketVariantTag);

        int variantId = TropicalFishEntity.getVariantId(TropicalFishEntity.getVariety(this.bucketVariantTag),baseColor,patternColor);
        ((TropicalFishEntityAccessor)this.tropicalFishEntity).wisb$setTropicalFishVariant(variantId);


        int id = 0;
        for(int j = 0; j < TropicalFishEntity.COMMON_VARIANTS.size(); ++j) {
            if (this.bucketVariantTag == TropicalFishEntity.COMMON_VARIANTS.get(j).getId()) {
                id = j;
                break;
            }
        }
        this.healthText = Text.translatable(tropicalfishTooltipData.health + "/" + (int)this.tropicalFishEntity.getMaxHealth()).formatted(Formatting.GRAY);

        this.variantText = Text.translatable(TropicalFishEntity.getToolTipForVariant(id)).formatted(Formatting.ITALIC,Formatting.GRAY);

    }


    @Override
    public int getHeight() {
        return 24;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return Math.max(textRenderer.getWidth(this.variantText) + 32,textRenderer.getWidth(this.healthText) + 44) + 2 ;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        InventoryScreen.drawEntity(context,x+16,y+14,25,quaternionf,null, this.tropicalFishEntity);
        RenderSystem.disableDepthTest();
        context.drawTexture(ICON,x+32,y,52,9,9,9);
        context.drawTexture(ICON,x+32,y,88,9,9,9);

        context.drawText(textRenderer,this.healthText,x+44,y, 0xFFFFFF,false);
        context.drawText(textRenderer,this.variantText,x+32,y+12,11184810,false);

    }

    public static class TropicalfishTooltipData implements TooltipData{
        final int bucketVariantTag;
        final int health;

        public TropicalfishTooltipData(int bucketVariantTag,int health){
            this.bucketVariantTag = bucketVariantTag;
            this.health = health;
        }
    }
}
