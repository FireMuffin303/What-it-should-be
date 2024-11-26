package net.firemuffin303.wisb.common.packet;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.firemuffin303.wisb.Wisb;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;

public class RenameNameTagPacket implements FabricPacket {
    public final String name;
    public final int xpCost;
    public final Hand hand;

    public RenameNameTagPacket(String name,int xpCost,Hand hand){
        this.name = name;
        this.xpCost = xpCost;
        this.hand = hand;
    }

    public RenameNameTagPacket(PacketByteBuf packetByteBuf){
        this.name = packetByteBuf.readString();
        this.xpCost = packetByteBuf.readInt();
        this.hand = packetByteBuf.readEnumConstant(Hand.class);
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeString(this.name);
        packetByteBuf.writeInt(this.xpCost);
        packetByteBuf.writeEnumConstant(this.hand);
    }

    @Override
    public PacketType<?> getType() {
        return Wisb.WISB_RENAME_NAMETAG;
    }
}
