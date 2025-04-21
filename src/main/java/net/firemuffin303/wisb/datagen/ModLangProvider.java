package net.firemuffin303.wisb.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModLangProvider extends FabricLanguageProvider {
    protected ModLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("wisb.compass.coordinates","X : %s | Y : %s | Z : %s");
        translationBuilder.add("wisb.clock.worldtime","Time : %s:%s | Day : %s");
        translationBuilder.add("wisb.clock.worldtime.twelve_format","Time : %s:%s %s | Day : %s");
        translationBuilder.add("wisb.clock.playtime","Play time : %s");
        translationBuilder.add("wisb.beehive.tooltip.honey_level","%s / %s");
        translationBuilder.add("wisb.axolotl_bucket.tooltip.variant","Variant : %s");

        // Config
        translationBuilder.add("wisb.config.title","What It Should Be Config");
        translationBuilder.add("wisb.options.showMobBucketTooltip","Show Mob Bucket Tooltip");
        translationBuilder.add("wisb.options.showMobBucketTooltip.desc","Show mob's health & variant.");
        translationBuilder.add("wisb.options.showCrossbowTooltip","Show Crossbow Tooltip");
        translationBuilder.add("wisb.options.showCrossbowTooltip.desc","Show crossbow's ammo details.");
        translationBuilder.add("wisb.options.showBeehiveTooltip","Show Beehive Tooltip");
        translationBuilder.add("wisb.options.showBeehiveTooltip.desc","Show how many bees and honey's progresses.");
        translationBuilder.add("wisb.options.timeformat","Clock Time Format");
        translationBuilder.add("wisb.options.timeformat.desc","Choose between 24-Hour or 12-Hour (AM/PM) format");
        translationBuilder.add("wisb.options.preciseCoordinate","Compass Precise Coordinate");
        translationBuilder.add("wisb.options.preciseCoordinate.desc","Show decimal number of current coordinate");
        translationBuilder.add("wisb.options.time.full_format","24 Hour");
        translationBuilder.add("wisb.options.time.twelve_format","12 Hour");
        translationBuilder.add("wisb.options.sneakingToRenameNameTag","Sneak To Rename Name Tag");
        translationBuilder.add("wisb.options.toolItemDisplay","Tool Item Display");
        translationBuilder.add("wisb.options.toolItemDisplay.desc","Change how items special GUI show such as Compass, Clocks and Recovery Compass");
        translationBuilder.add("wisb.options.itemGuiDisplay.up","Up");
        translationBuilder.add("wisb.options.itemGuiDisplay.down","Down");
        translationBuilder.add("wisb.options.zoomableSpyglass","Zoomable Spyglass");

        //Rename Name Tag
        translationBuilder.add("wisb.renameNameTag.title","Rename Name Tag");

        // Game Rule
        translationBuilder.add("gamerule.wisb:sugarCaneGrowHeight","Sugar Cane grow height");
        translationBuilder.add("gamerule.wisb:bonemealableSugarCane","Sugar Cane can be bonemeal");
        translationBuilder.add("gamerule.wisb:bonemealableNetherwart","Nether Wart can be bonemeal");
        translationBuilder.add("gamerule.wisb:bonemealableLilyPad","Lily Pad can be bonemeal");
        translationBuilder.add("gamerule.wisb:easierBabyZombie","Easier Baby zombie");
        translationBuilder.add("gamerule.wisb:creeperExplosionDestroyBlock","Creeper Explosion Destroy Blocks");
        translationBuilder.add("gamerule.wisb:spawnArmorStandWithArm","Spawn Armor Stand with arms");
        translationBuilder.add("gamerule.wisb:shearAbleItemFrame","Shearable Item Frame");
        translationBuilder.add("gamerule.wisb:showCompassGUI","Show Compass GUI");
        translationBuilder.add("gamerule.wisb:showTreasureInCompassGUI","Show Treasure in Compass GUI");
        translationBuilder.add("gamerule.wisb:showClockGUI","Show Clock GUI");
        translationBuilder.add("gamerule.wisb:turtleBarnacleMaxTime","Turtle Barnacle spawns max time (ticks)");
        translationBuilder.add("gamerule.wisb:turtleBarnacleMinTime","Turtle Barnacle spawns min time (ticks)");
        translationBuilder.add("gamerule.wisb:turtleHasBarnacle","Turtle has barnacle");

        //ModMenu
        translationBuilder.add("modmenu.descriptionTranslation.wisb","A Minecraft mod which focus on revamping or reworking Vanilla mechanics to what it should be. At least from FireMuffin303's thought... Most of features are configurable by Mod config for client side and Game Rules for server side. Feel free to config to what you like! ");
        translationBuilder.add("modmenu.nameTranslation.wisb","What It Should Be");
        translationBuilder.add("modmenu.summaryTranslation.wisb","Revamping Minecraft to what it should be.");

        //JEI
        translationBuilder.add("wisb.jei.mobbrush.title","Wisb's Mob Brushing");
        translationBuilder.add("wisb.jei.mobbrush.turtle","Turtles swim for a while, then get barnacle on their back. Use brush to clean them off.");

        //Jade
        translationBuilder.add("jade.wisb.turtle.barnacle.tooltip","Next Barnacle : %s");
        translationBuilder.add("config.jade.plugin_wisb.item_tool_display_pos_x","Item Tool Display Pos X");
        translationBuilder.add("config.jade.plugin_wisb.item_tool_display_pos_y","Item Tool Display Pos Y");
        translationBuilder.add("config.jade.plugin_wisb.hud_render_side","Item Tool Display Side");
        translationBuilder.add("config.jade.plugin_wisb.hud_render_side_left","Left");
        translationBuilder.add("config.jade.plugin_wisb.hud_render_side_right","Right");
        translationBuilder.add("config.jade.plugin_wisb.turtle_barnacle","Turtle Barnacle");
    }

    public static class ThaiLangProvider  extends FabricLanguageProvider {
        protected ThaiLangProvider(FabricDataOutput dataOutput) {
            super(dataOutput,"th_th");
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            translationBuilder.add("wisb.compass.coordinates","X : %s | Y : %s | Z : %s");
            translationBuilder.add("wisb.clock.worldtime","เวลา : %s:%s | วัน : %s");
            translationBuilder.add("wisb.clock.worldtime.twelve_format","เวลา : %s:%s %s | วัน : %s");
            translationBuilder.add("wisb.clock.playtime","เวลาเล่น : %s");
            translationBuilder.add("wisb.beehive.tooltip.honey_level","%s / %s");
            translationBuilder.add("wisb.axolotl_bucket.tooltip.variant","ลาย : %s");

            // Options
            translationBuilder.add("wisb.config.title","การตั้งค่า What It Should Be");
            translationBuilder.add("wisb.options.showMobBucketTooltip","แสดงรายละเอียดสัตว์ในถัง");
            translationBuilder.add("wisb.options.showMobBucketTooltip.desc","แสดงเลือดและลายสีของสัตว์ในถัง");
            translationBuilder.add("wisb.options.showCrossbowTooltip","แสดงรายละเอียดหน้าไม้");
            translationBuilder.add("wisb.options.showCrossbowTooltip.desc","แสดงรายละเอียดลูกระสุนของหน้าไม้");
            translationBuilder.add("wisb.options.showBeehiveTooltip","แสดงรายละเอียดรังผึ้ง");
            translationBuilder.add("wisb.options.showBeehiveTooltip.desc","แสดงว่ามีผึ้งในรังกี่ตัวและระดับน้ำผึ่งภายในรัง");
            translationBuilder.add("wisb.options.timeformat","รูปแบบเวลาของนาฬิกา");
            translationBuilder.add("wisb.options.timeformat.desc","เลือกรูปแบบการแสดงเวลา 24 ชั่วโมงหรือ 12 ชั่วโมง (AM/PM)");
            translationBuilder.add("wisb.options.preciseCoordinate","เข็มทิศแสดงตำแหน่งอย่างแม่นยำ");
            translationBuilder.add("wisb.options.preciseCoordinate.desc","แสดงเลขทศนิยมของพิกัดในตำแหน่งปัจจุบัน");

            translationBuilder.add("wisb.options.time.full_format","24 ชั่วโมง");
            translationBuilder.add("wisb.options.time.twelve_format","12 ชั่วโมง");

            translationBuilder.add("wisb.options.sneakingToRenameNameTag","ย่องเพื่อเปลี่ยนชื่อของป้ายชื่อ");
            translationBuilder.add("wisb.options.toolItemDisplay","การแสดงหน้าต่างไอเทม");
            translationBuilder.add("wisb.options.toolItemDisplay.desc","เปลี่ยนหน้าต่างไอเทมพิเศษอย่างเข็มทิศ นาฬิกาและเข็มทิศหวนคืน");
            translationBuilder.add("wisb.options.itemGuiDisplay.up","บน");
            translationBuilder.add("wisb.options.itemGuiDisplay.down","ล่าง");
            translationBuilder.add("wisb.options.zoomableSpyglass","ปรับระด้บกล้องส่องทางไกล");

            // Game Rule
            translationBuilder.add("gamerule.wisb:sugarCaneGrowHeight","ระดับความสูงของต้นอ้อย");
            translationBuilder.add("gamerule.wisb:bonemealableSugarCane","ใช้ผงกระดูกกับต้นอ้อย");
            translationBuilder.add("gamerule.wisb:bonemealableNetherwart","ใช้ผงกระดูกกับหูดเนเธอร์");
            translationBuilder.add("gamerule.wisb:bonemealableLilyPad","ใช้ผงกระดูกกับใบบัว");
            translationBuilder.add("gamerule.wisb:easierBabyZombie","ซอมบี้เด็กง่ายกว่าเดิม");
            translationBuilder.add("gamerule.wisb:creeperExplosionDestroyBlock","ระเบิดครีปเปอร์ทำลายบล๊อค");
            translationBuilder.add("gamerule.wisb:spawnArmorStandWithArm","หุ่นโชว์ของมีแขน");
            translationBuilder.add("gamerule.wisb:shearAbleItemFrame","ตัดแผ่นโชว์ไอเทม");
            translationBuilder.add("gamerule.wisb:showCompassGUI","แสดงหน้าต่างเข็มทิศ");
            translationBuilder.add("gamerule.wisb:showTreasureInCompassGUI","แสดงสมบัติบนหน้าเข็มทิศ");
            translationBuilder.add("gamerule.wisb:showClockGUI","แสดงหน้าต่างนาฬิกา");
            translationBuilder.add("gamerule.wisb:turtleBarnacleMaxTime","เวลาสูงสุดที่เต่าติดเพรียงทะเล (ticks)");
            translationBuilder.add("gamerule.wisb:turtleBarnacleMinTime","เวลาต่ำสุดที่เต่าติดเพรียงทะเล (ticks)");
            translationBuilder.add("gamerule.wisb:turtleHasBarnacle","เต่าติดเพรียงทะเล");

            //Rename Name Tag
            translationBuilder.add("wisb.renameNameTag.title","เปลี่ยนชื่อของป้ายชื่อ");

            //ModMenu
            translationBuilder.add("modmenu.descriptionTranslation.wisb","ม๊อดไมน์คราฟที่จะปรับระดับของ Vanilla ให้เป็นในแบบที่ควรจะเป็น...ตามที่ FireMuffin303 คิดอ่ะนะ. ระบบส่วนใหญ่จะสามารถตั้งค่าได้จากหน้าต่างและ Game Rules. เพราะฉนั้นสามารถปรับได้ตามต้องการเลย!");
            translationBuilder.add("modmenu.nameTranslation.wisb","What It Should Be");
            translationBuilder.add("modmenu.summaryTranslation.wisb","ปรับไมน์คราฟให้เป็นในแบบที่ควรเป็น.");

            //JEI
            translationBuilder.add("wisb.jei.mobbrush.title","Wisb's ปัดแปรงสัตว์");
            translationBuilder.add("wisb.jei.mobbrush.turtle","เมื่อเต่าว่ายน้ำได้ระยะเวลานึง พวกมันจะติดเพรียงทะเลที่หลัง สามารถใช้แปรงขัดออกได้.");

            //Jade
            translationBuilder.add("jade.wisb.turtle.barnacle.tooltip","เพรียงทะเล : %s");
            translationBuilder.add("config.jade.plugin_wisb.item_tool_display_pos_x","ตำแหน่งแกน X ป้ายรายละเอียด");
            translationBuilder.add("config.jade.plugin_wisb.item_tool_display_pos_y","ตำแหน่งแกน Y ป้ายรายละเอียด");
            translationBuilder.add("config.jade.plugin_wisb.hud_render_side","ฝั่งแสดงป้ายรายละเอียด");
            translationBuilder.add("config.jade.plugin_wisb.hud_render_side_left","ซ้าย");
            translationBuilder.add("config.jade.plugin_wisb.hud_render_side_right","ขวา");
            translationBuilder.add("config.jade.plugin_wisb.turtle_barnacle","แสดงเวลาเพรียงทะเล");
        }
    }
}
