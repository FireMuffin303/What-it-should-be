package net.firemuffin303.wisb.mixin.map;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.map.MapState;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "onMapUpdate",at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/MapUpdateS2CPacket;apply(Lnet/minecraft/item/map/MapState;)V"))
    public void wisb$debugOnMapUpdate(MapUpdateS2CPacket packet, CallbackInfo ci, @Local MapState mapState){
        //This solely exist because I need to know how map packet work.
        LOGGER.info(mapState.getBanners().toString());
    }
}
