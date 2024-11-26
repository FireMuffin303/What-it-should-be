package net.firemuffin303.wisb.common;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.firemuffin303.wisb.Wisb;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;

public class WisbWorldComponent {
    public boolean bonemealAbleSugarCane = true;
    public int sugarCaneGrowHeight = 3;
    public boolean bonemealAbleLilyPad = true;
    public boolean bonemealAbleNetherWart = false;
    public boolean easierBabyZombie = true;
    public boolean compassGUI = true;
    public boolean showTressureInCompassGUI = false;
    public boolean clockGUI = true;
    public int renameCost = 1;

    public void setGameRule(boolean bonemealAbleSugarCane,int sugarCaneGrowHeight,boolean bonemealAbleLilyPad,boolean bonemealAbleNetherWart,boolean easierBabyZombie,boolean compassGUI,boolean showTressureInCompassGUI, boolean clockGUI,int renameCost){
        this.bonemealAbleSugarCane = bonemealAbleSugarCane;
        this.sugarCaneGrowHeight = sugarCaneGrowHeight;
        this.bonemealAbleLilyPad = bonemealAbleLilyPad;
        this.bonemealAbleNetherWart = bonemealAbleNetherWart;
        this.easierBabyZombie = easierBabyZombie;
        this.compassGUI = compassGUI;
        this.showTressureInCompassGUI = showTressureInCompassGUI;
        this.clockGUI = clockGUI;
        this.renameCost = renameCost;
    }

    public interface WisbWorldComponentAccessor{
        WisbWorldComponent wisb$getWisbWorldComponent();
    }

    public static class WisbWorldSettingPacket implements FabricPacket{

        public boolean bonemealAbleSugarCane;
        public int sugarCaneGrowHeight;
        public boolean bonemealAbleLilyPad;
        public boolean bonemealAbleNetherWart;
        public boolean easierBabyZombie;
        public boolean compassGUI;
        public boolean showTreasureInCompassGUI;
        public boolean clockGUI;
        public int renameCost;

        public WisbWorldSettingPacket(ServerWorld world){
            this.bonemealAbleSugarCane = world.getGameRules().getBoolean(Wisb.BONEMEAL_ABLE_SUGAR_CANE);
            this.sugarCaneGrowHeight = world.getGameRules().getInt(Wisb.SUGAR_CANE_HEIGHT);
            this.bonemealAbleLilyPad = world.getGameRules().getBoolean(Wisb.BONEMEALABLE_LILLYPAD);
            this.bonemealAbleNetherWart = world.getGameRules().getBoolean(Wisb.BONEMEALABLE_NETHERWART);
            this.easierBabyZombie = world.getGameRules().getBoolean(Wisb.EASIER_BABY_ZOMBIE);
            this.compassGUI = world.getGameRules().getBoolean(Wisb.COMPASS_GUI);
            this.showTreasureInCompassGUI = world.getGameRules().getBoolean(Wisb.SHOW_TREASURE_IN_COMPASS_GUI);
            this.clockGUI = world.getGameRules().getBoolean(Wisb.CLOCK_GUI);
            this.renameCost = world.getGameRules().getInt(Wisb.RENAME_NAMETAG_COST);
        }

        public WisbWorldSettingPacket(PacketByteBuf packetByteBuf){
            this.bonemealAbleSugarCane = packetByteBuf.readBoolean();
            this.sugarCaneGrowHeight = packetByteBuf.readInt();
            this.bonemealAbleLilyPad = packetByteBuf.readBoolean();
            this.bonemealAbleNetherWart = packetByteBuf.readBoolean();
            this.easierBabyZombie = packetByteBuf.readBoolean();
            this.compassGUI = packetByteBuf.readBoolean();
            this.showTreasureInCompassGUI = packetByteBuf.readBoolean();
            this.clockGUI = packetByteBuf.readBoolean();
            this.renameCost = packetByteBuf.readInt();
        }

        @Override
        public void write(PacketByteBuf packetByteBuf) {
            packetByteBuf.writeBoolean(this.bonemealAbleSugarCane);
            packetByteBuf.writeInt(this.sugarCaneGrowHeight);
            packetByteBuf.writeBoolean(this.bonemealAbleLilyPad);
            packetByteBuf.writeBoolean(this.bonemealAbleNetherWart);
            packetByteBuf.writeBoolean(this.easierBabyZombie);
            packetByteBuf.writeBoolean(this.compassGUI);
            packetByteBuf.writeBoolean(this.showTreasureInCompassGUI);
            packetByteBuf.writeBoolean(this.clockGUI);
            packetByteBuf.writeInt(this.renameCost);
        }

        @Override
        public PacketType<?> getType() {
            return Wisb.WISB_WORLD_SETTING_S2C;
        }
    }
}
