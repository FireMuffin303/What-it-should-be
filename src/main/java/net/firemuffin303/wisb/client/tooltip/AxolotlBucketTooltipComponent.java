package net.firemuffin303.wisb.client.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.sun.jna.platform.win32.Winspool;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class AxolotlBucketTooltipComponent implements TooltipComponent {
    final AxolotlEntity axolotlEntity;
    final float health;
    final AxolotlEntity.Variant variant;
    final Quaternionf quaternionf = (new Quaternionf()).rotationXYZ(0.43633232F, 0, 3.1415927F);

    final Identifier ICON = new Identifier("minecraft","textures/gui/icons.png");

    final Text variantText;
    final Text healthText;


    public AxolotlBucketTooltipComponent(AxolotlBucketTooltipData axolotlBucketTooltipData){
        this.axolotlEntity = EntityType.AXOLOTL.create(MinecraftClient.getInstance().world);
        this.axolotlEntity.age = axolotlBucketTooltipData.age;

        this.axolotlEntity.setYaw(210.0f);
        this.axolotlEntity.prevBodyYaw = this.axolotlEntity.getYaw();
        this.axolotlEntity.headYaw = this.axolotlEntity.getYaw();
        this.axolotlEntity.prevHeadYaw = this.axolotlEntity.getYaw();

        this.axolotlEntity.setVariant(AxolotlEntity.Variant.byId(axolotlBucketTooltipData.variant));
        this.axolotlEntity.setHealth(axolotlBucketTooltipData.health);


        this.health = axolotlBucketTooltipData.health;
        this.variant = AxolotlEntity.Variant.byId(axolotlBucketTooltipData.variant);

        this.healthText = Text.of((int)this.health +"/"+(int)this.axolotlEntity.getMaxHealth());
        this.variantText = Text.of(this.variant.getName().substring(0,1).toUpperCase() + this.variant.getName().substring(1));

    }



    @Override
    public int getHeight() {
        return 24;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return Math.max(textRenderer.getWidth(this.variantText) + 32, textRenderer.getWidth(this.healthText) +44) +2 ;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        InventoryScreen.drawEntity(context,x+18,y+10,25,quaternionf,null, this.axolotlEntity);
        RenderSystem.disableDepthTest();
        context.drawTexture(ICON,x+32,y,52,9,9,9);
        context.drawTexture(ICON,x+32,y,88,9,9,9);
        context.drawText(textRenderer,this.healthText,x+44,y, 11184810,false);
        context.drawText(textRenderer, this.variantText,x+32,y+12,11184810,false);
    }

    public static class AxolotlBucketTooltipData implements TooltipData {
        private final int age;
        private final float health;
        private final int variant;

        public AxolotlBucketTooltipData(int age,float health,int variant){
            this.age = age;
            this.health = health;
            this.variant = variant;
        }
    }
}
