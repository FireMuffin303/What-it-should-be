package net.firemuffin303.wisb.mixin;

import net.firemuffin303.wisb.common.WisbWorldComponent;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Debug(export = true)
@Mixin(ClientWorld.class)
public class ClientWorldComponentMixin implements WisbWorldComponent.WisbWorldComponentAccessor {
    @Unique
    final WisbWorldComponent wisbWorldComponent = new WisbWorldComponent();


    @Override
    public WisbWorldComponent wisb$getWisbWorldComponent() {
        return this.wisbWorldComponent;
    }
}
