package net.firemuffin303.wisb.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import net.fabricmc.loader.api.FabricLoader;
import net.firemuffin303.wisb.Wisb;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.TranslatableOption;
import net.minecraft.util.math.MathHelper;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ModConfig {
    private static File file;

    public static final SimpleOption<Boolean> showWisbMobBucketTooltip = SimpleOption.ofBoolean("wisb.options.showMobBucketTooltip",
            SimpleOption.constantTooltip(Text.translatable("wisb.options.showMobBucketTooltip.desc")),true);

    public static final SimpleOption<Boolean> showWisbCrossbowBucketTooltip = SimpleOption.ofBoolean("wisb.options.showCrossbowTooltip",
            SimpleOption.constantTooltip(Text.translatable("wisb.options.showCrossbowTooltip.desc")),true);

    public static final SimpleOption<Boolean> showBeehiveTooltip = SimpleOption.ofBoolean("wisb.options.showBeehiveTooltip",
            SimpleOption.constantTooltip(Text.translatable("wisb.options.showBeehiveTooltip.desc")),true);

    public static final SimpleOption<TimeFormat> timeFormat = new SimpleOption<>("wisb.options.timeformat",
            SimpleOption.constantTooltip(Text.translatable("wisb.options.timeformat.desc")),
            SimpleOption.enumValueText(),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(TimeFormat.values()),Codec.INT.xmap(TimeFormat::byId, TimeFormat::getId)),
            TimeFormat.FULL_FORMAT,(value) -> {}
    );

    public static final SimpleOption<Boolean> preciseCoordinate = SimpleOption.ofBoolean("wisb.options.preciseCoordinate",
            SimpleOption.constantTooltip(Text.translatable("wisb.options.preciseCoordinate.desc")),false);

    public static boolean getShowMobBucket(){
        return showWisbMobBucketTooltip.getValue();
    }

    public static boolean getShowCrossbowBucket(){
        return showWisbCrossbowBucketTooltip.getValue();
    }

    public static boolean getShowBeehiveTooltip(){
        return showBeehiveTooltip.getValue();
    }


    public static void load(){
        if(file == null){
            file = new File(FabricLoader.getInstance().getConfigDir().toFile(),"wisb.json");
        }

        try{
            if(!file.exists()){
                save();
            }

            if(file.exists()){
                BufferedReader br = new BufferedReader(new FileReader(file));
                JsonObject jsonObject = JsonParser.parseReader(br).getAsJsonObject();

                showWisbMobBucketTooltip.setValue(jsonObject.getAsJsonPrimitive("showMobBucketTooltip").getAsBoolean());
                showWisbCrossbowBucketTooltip.setValue(jsonObject.getAsJsonPrimitive("showCrossbowBucketTooltip").getAsBoolean());
                showBeehiveTooltip.setValue(jsonObject.getAsJsonPrimitive("showBeehiveTooltip").getAsBoolean());
                timeFormat.setValue(TimeFormat.byId(jsonObject.getAsJsonPrimitive("clockGUI_timeformat").getAsInt()));
                preciseCoordinate.setValue(jsonObject.getAsJsonPrimitive("compassPreciseCoordinate").getAsBoolean());

            }
        }catch (FileNotFoundException var){
            System.err.println("Couldn't load WISB configuration file.");
            var.printStackTrace();
        }


    }

    public static void save(){
        if(file == null){
            file = new File(FabricLoader.getInstance().getConfigDir().toFile(),"wisb.json");
        }

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("showMobBucketTooltip",showWisbMobBucketTooltip.getValue());
        jsonObject.addProperty("showCrossbowBucketTooltip",showWisbCrossbowBucketTooltip.getValue());
        jsonObject.addProperty("showBeehiveTooltip",showBeehiveTooltip.getValue());
        jsonObject.addProperty("clockGUI_timeformat",timeFormat.getValue().getId());
        jsonObject.addProperty("compassPreciseCoordinate",preciseCoordinate.getValue());

        String jsonString =  Wisb.GSON.toJson(jsonObject);

        try{
            FileWriter fileWriter = new FileWriter(file);
            try {
                fileWriter.write(jsonString);
            }catch (Throwable var){
                try {
                    fileWriter.close();
                }catch (Throwable var2){
                    var.addSuppressed(var2);
                }
                throw var;
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public enum TimeFormat implements TranslatableOption {
        FULL_FORMAT(0,"wisb.options.time.full_format"),
        TWELVE_FORMAT(1,"wisb.options.time.twelve_format");

        int id;
        String translationKey;

        TimeFormat (int id,String translationKey){
            this.id = id;
            this.translationKey = translationKey;
        }

        @Override
        public int getId() {
            return this.id;
        }

        @Override
        public String getTranslationKey() {
            return this.translationKey;
        }

        public static TimeFormat byId(int id){
            List<TimeFormat> timeFormats = List.of(values());
            int i = MathHelper.clamp(id,0,timeFormats.size()-1);
            return timeFormats.get(i);
        }
    }
}
