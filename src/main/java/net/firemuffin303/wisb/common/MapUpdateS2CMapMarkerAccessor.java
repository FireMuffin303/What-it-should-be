package net.firemuffin303.wisb.common;

import net.minecraft.item.map.MapBannerMarker;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;

import java.util.List;

public interface MapUpdateS2CMapMarkerAccessor {

    Packet<ClientPlayPacketListener> wisb$setMapMarker(List<MapBannerMarker> bannerMarkers);
}
