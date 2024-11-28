package net.firemuffin303.wisb.client.screen;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.firemuffin303.wisb.config.ModConfig;
import net.firemuffin303.wisb.config.ModOption;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.io.Serializable;

public class ModConfigScreen extends Screen {
    private final Screen previous;
    private OptionListWidget list;
    private final ThreePartsLayoutWidget threePartsLayoutWidget = new ThreePartsLayoutWidget(this);

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
                ModConfig.SNEAKING_TO_RENAME_NAME_TAG
        });
        //this.addSelectableChild(this.list);

        this.threePartsLayoutWidget.addHeader(new TextWidget(this.title,this.textRenderer));

        GridWidget.Adder adder = this.threePartsLayoutWidget.addBody(new GridWidget()).createAdder(1);

        GridWidget gridWidget = new GridWidget().setRowSpacing(10);
        gridWidget.add(EmptyWidget.ofWidth(310),0,0);
        gridWidget.add(EmptyWidget.ofWidth(44),0,1);



        ModOption.BooleanOptionWidget booleanOptionWidget = new ModOption.BooleanOptionWidget(ModConfig.booleanModOption,44);
        booleanOptionWidget.addWidgetToGrid(gridWidget,this.textRenderer,1);


        adder.add(gridWidget);

        gridWidget.refreshPositions();



        this.threePartsLayoutWidget.addFooter(ButtonWidget.builder(ScreenTexts.DONE,button -> {
            ModConfig.save();
            this.client.setScreen(this.previous);
        }).position(this.width/2 - 100,this.height - 27).size(200,20).build());

        this.threePartsLayoutWidget.forEachChild(this::addDrawableChild);

        this.initTabNavigation();

    }

    @Override
    protected void initTabNavigation() {
        this.threePartsLayoutWidget.refreshPositions();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        //this.list.render(context,mouseX,mouseY,delta);
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
