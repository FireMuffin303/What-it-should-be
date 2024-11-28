package net.firemuffin303.wisb.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.logging.LogUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TranslatableOption;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

public class ModOption<T> {
    private final Text optionText;
    private final String saveKey;
    @Nullable
    private final Text tooltipText;
    private final T defaultValue;
    private T value;

    public ModOption(String optionKey,String saveKey,T defaultValue,@Nullable String tooltipText){
        this.optionText = Text.translatable(optionKey);
        this.saveKey = saveKey;
        this.defaultValue = defaultValue;
        this.tooltipText = tooltipText != null ? Text.translatable(tooltipText).formatted(Formatting.GRAY) : null;
        this.value = this.defaultValue;

    }

    public static void save(ModOption<?> modOptions){
        JsonObject jsonObject = new JsonObject();
        if(modOptions.value instanceof Boolean aBoolean){
            jsonObject.addProperty(modOptions.saveKey,aBoolean);
        } else if (modOptions.value instanceof Number number) {
            jsonObject.addProperty(modOptions.saveKey,number);
        } else if (modOptions.value instanceof String s) {
            jsonObject.addProperty(modOptions.saveKey,s);
        } else if (modOptions.value instanceof Character c) {
            jsonObject.addProperty(modOptions.saveKey,c);
        } else if (modOptions.value instanceof TranslatableOption translatableOption) {
            jsonObject.addProperty(modOptions.saveKey,translatableOption.getId());
        }
    }

    public static void load(JsonObject jsonObject) {
        JsonPrimitive jsonPrimitive = jsonObject.getAsJsonPrimitive(this.saveKey);
        if(jsonPrimitive == null){
            save(jsonObject);
            return;
        }
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Text getOptionText() {
        return optionText;
    }

    public String getSaveKey() {
        return saveKey;
    }

    public @Nullable Text getTooltipText() {
        return tooltipText;
    }



    public static abstract class ModOptionWidget<T> {
        final ModOption<T> modOption;

        public ModOptionWidget(ModOption<T> modOption){
            this.modOption = modOption;
        }

        void addWidgetToGrid(GridWidget gridWidget, TextRenderer textRenderer,int row) {
            GridWidget.Adder adder = new GridWidget().setRowSpacing(2).createAdder(1);
            TextWidget textWidget = new TextWidget(this.modOption.getOptionText(),textRenderer);
            adder.add(textWidget);
            if(modOption.getTooltipText() != null){
                TextWidget tooltipWidget = new TextWidget(this.modOption.getTooltipText(),textRenderer);
                adder.add(tooltipWidget);

            }
            gridWidget.add(adder.getGridWidget(),row,0);
        }
    }

    public static class BooleanOptionWidget extends ModOptionWidget<Boolean>{

        final int width;

        public BooleanOptionWidget(ModOption<Boolean> modOption,int width) {
            super(modOption);
            this.width = width;
        }

        @Override
        public void addWidgetToGrid(GridWidget gridWidget,TextRenderer textRenderer,int row) {
            super.addWidgetToGrid(gridWidget,textRenderer,row);
            CyclingButtonWidget<Boolean> cyclingButtonWidget = CyclingButtonWidget.onOffBuilder(this.modOption.defaultValue).omitKeyText().build(0,0,this.width,20,Text.empty());
            gridWidget.add(cyclingButtonWidget,row,1);

        }
    }
}
