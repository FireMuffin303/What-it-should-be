package net.firemuffin303.wisb.mixin.map;

import net.minecraft.item.map.MapBannerMarker;
import net.minecraft.item.map.MapState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(MapState.class)
public interface MapStateAccessor {
    @Accessor("banners")
    Map<String, MapBannerMarker> wisb$getBannerMap();
}
