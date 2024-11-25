package net.firemuffin303.wisb.mixin.map;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.logging.LogUtils;
import net.firemuffin303.wisb.common.MapUpdateS2CMapMarkerAccessor;
import net.minecraft.item.map.MapBannerMarker;
import net.minecraft.item.map.MapState;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;
import java.util.List;

@Debug(export = true)
@Mixin(MapState.PlayerUpdateTracker.class)
public abstract class MapStatePlayerUpdateTrackerMixin {
    @Final
    @Shadow MapState field_132;

    @Shadow private boolean iconsDirty;

    @Shadow private int emptyPacketsRequested;

    @ModifyReturnValue(method = "getPacket",at = @At("RETURN"))
    public Packet<?> wisb$getPacket(Packet<?> original){
        if(original instanceof MapUpdateS2CMapMarkerAccessor){
            Collection<MapBannerMarker> mapBannerMarkers = field_132.getBanners();

            if(mapBannerMarkers != null){
                return ((MapUpdateS2CMapMarkerAccessor) original).wisb$setMapMarker(List.copyOf(mapBannerMarkers));
            }
        }
        return original;
    }
}
