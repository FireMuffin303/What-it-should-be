package net.firemuffin303.wisb.mixin.map;

import net.firemuffin303.wisb.common.MapUpdateS2CMapMarkerAccessor;
import net.minecraft.item.map.MapBannerMarker;
import net.minecraft.item.map.MapState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Debug(export = true)
@Mixin(MapUpdateS2CPacket.class)
public abstract class MapUpdateS2CPacketMixin implements MapUpdateS2CMapMarkerAccessor {
    @Unique
    @Nullable List<MapBannerMarker> bannerMarkers;

    @Inject(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V",at = @At("TAIL"))
    public void wisb$injectMapBanner(PacketByteBuf buf, CallbackInfo ci){
        this.bannerMarkers = buf.readNullable(packetByteBuf -> packetByteBuf .readList(markerBuf -> {
            BlockPos blockPos = markerBuf.readBlockPos();
            DyeColor dyeColor = markerBuf.readEnumConstant(DyeColor.class);
            Text name = markerBuf.readText();
            return new MapBannerMarker(blockPos,dyeColor,name);
        }));
    }

    @Inject(method = "write",at = @At("TAIL"))
    public void wisb$writeMapMarker(PacketByteBuf buf, CallbackInfo ci){
        buf.writeNullable(this.bannerMarkers,((markerBuf, mapBannerMarkers) -> {
            markerBuf.writeCollection(mapBannerMarkers,(b, mapBannerMarker) ->{
                b.writeBlockPos(mapBannerMarker.getPos());
                b.writeEnumConstant(mapBannerMarker.getColor());
                b.writeNullable(mapBannerMarker.getName() , PacketByteBuf::writeText);
            });
        }));
    }

    @Inject(method = "apply(Lnet/minecraft/item/map/MapState;)V",at = @At("TAIL"))
    public void wisb$applyMapMarker(MapState mapState, CallbackInfo ci){
        if(this.bannerMarkers != null){
            ((MapStateAccessor)mapState).wisb$getBannerMap().clear();
            this.bannerMarkers.forEach(mapBannerMarker -> {
                ((MapStateAccessor)mapState).wisb$getBannerMap().put(mapBannerMarker.getKey(),mapBannerMarker);
            });
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> wisb$setMapMarker(List<MapBannerMarker> bannerMarkers) {
        this.bannerMarkers =bannerMarkers;
        return (MapUpdateS2CPacket)((Object)this);
    }
}

