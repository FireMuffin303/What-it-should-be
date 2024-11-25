package net.firemuffin303.wisb.client.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class CrossbowTooltipComponent implements TooltipComponent {
    private final ItemStack itemStack;
    private final Text itemText;
    private final List<Text> detailedText;


    public CrossbowTooltipComponent(CrossbowTooltipData crossbowTooltipData){
        this.itemStack = crossbowTooltipData.ammo;
        this.itemText = Text.translatable(itemStack.getTranslationKey()).formatted(Formatting.GRAY);

        this.detailedText = new ArrayList<>();
        if(this.itemStack.isOf(Items.TIPPED_ARROW)){
            PotionUtil.buildTooltip(this.itemStack,this.detailedText,0.125F);

        } else if (this.itemStack.isOf(Items.FIREWORK_ROCKET)) {
            if (this.itemStack.hasNbt()){
                Items.FIREWORK_ROCKET.appendTooltip(this.itemStack, MinecraftClient.getInstance().world, this.detailedText, TooltipContext.ADVANCED);
            }
        }
    }

    @Override
    public int getHeight() {
        return this.detailedText.isEmpty() ? 18 : Math.max(18,(this.detailedText.size() *12)+12);
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        int detailWidth = 0;
        if(!this.detailedText.isEmpty()){
            for (Text text : this.detailedText) {
                detailWidth = textRenderer.getWidth(text) + 26;
            }
        }
        return Math.max(textRenderer.getWidth(this.itemText) + 26,detailWidth) + 2 ;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.drawItem(this.itemStack,x+2,y);
        int ny = y;
        if(this.detailedText.isEmpty()){
            ny += 4;
        }
        context.drawText(textRenderer,this.itemText,x + 26,ny,0xffffff,false);

        if(!this.detailedText.isEmpty()){
            int f = y + 12;
            for(int i = 0; i < this.detailedText.size(); i++){
                context.drawText(textRenderer, this.detailedText.get(i) ,x + 26,(i*12)+f,0xffffff,false);
            }
        }
    }

    public static class CrossbowTooltipData implements TooltipData{
        final ItemStack ammo;

        public CrossbowTooltipData(ItemStack itemStack){
            this.ammo = itemStack;
        }
    }
}
