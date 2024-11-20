package net.firemuffin303.wisb.client.tooltip;

import com.mojang.logging.LogUtils;
import net.fabricmc.loader.impl.util.log.Log;
import net.firemuffin303.wisb.Wisb;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class BeeNestTooltipComponent implements TooltipComponent {
    public static final Identifier BEEHIVE_TOOLTIP = Wisb.modId("textures/gui/beehive_tooltip.png");
    private final int honeyLevel;
    private final int bees;

    public BeeNestTooltipComponent(BeeNestTooltipData beeNestTooltipData){
        this.honeyLevel = beeNestTooltipData.honeyLevel;
        this.bees = beeNestTooltipData.bees;
    }

    @Override
    public int getHeight() {
        return 22;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 64;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        Text text = Text.translatable("wisb.beehive.tooltip.honey_level",this.honeyLevel,5);

        float progress = ((float)this.honeyLevel/5.0f) *32.0f;

        for(int i = 1; i <= 3 ;++i){
            context.drawTexture(BEEHIVE_TOOLTIP,x + ((i-1)*10),y,i <= bees ? 0 : 8,12,8,8);
        }

        context.drawTexture(BEEHIVE_TOOLTIP,x,y+12,0,6,32,6);
        context.drawTexture(BEEHIVE_TOOLTIP,x,y+12,0,0,(int) progress,6);
        context.drawText(textRenderer,text,x+36,y+12,0xFFFFFF,true);

    }

    public static class BeeNestTooltipData implements TooltipData{
        private final int honeyLevel;
        private final int bees;

        public BeeNestTooltipData(int honeyLevel,int bees){
            this.honeyLevel = honeyLevel;
            this.bees = bees;
        }
    }
}
