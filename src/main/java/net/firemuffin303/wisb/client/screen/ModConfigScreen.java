package net.firemuffin303.wisb.client.screen;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.firemuffin303.wisb.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class ModConfigScreen extends Screen {
    private final Screen previous;
    private OptionListWidget list;

    public ModConfigScreen(Screen previous) {
        super(Text.translatable("wisb.config.title"));
        this.previous = previous;
    }

    @Override
    protected void init() {
        super.init();

        this.list = new OptionListWidget(this.client,this.width,this.height,32,this.height-50,25);
        this.list.addAll(new SimpleOption[]{ModConfig.showWisbMobBucketTooltip,
                ModConfig.showWisbCrossbowBucketTooltip,
                ModConfig.showBeehiveTooltip,
                ModConfig.timeFormat,
                ModConfig.preciseCoordinate,
                ModConfig.SNEAKING_TO_RENAME_NAME_TAG,
                ModConfig.ZOOMABLE_SPYGLASS
        });
        this.addSelectableChild(this.list);
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE,button -> {
            ModConfig.save();
            this.client.setScreen(this.previous);
        }).position(this.width/2 - 100,this.height - 27).size(200,20).build());

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.list.render(context,mouseX,mouseY,delta);
        context.drawCenteredTextWithShadow(this.textRenderer,this.title,this.width/2,8,0xffffff);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void removed() {
        ModConfig.save();
    }

    @Override
    public void close() {
        this.client.setScreen(this.previous);
    }
}
