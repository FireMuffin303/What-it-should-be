package net.firemuffin303.wisb.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.firemuffin303.wisb.client.tooltip.AxolotlBucketTooltipComponent;
import net.firemuffin303.wisb.client.tooltip.BeeNestTooltipComponent;

public class WisbClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(ModHudRender::init);

        TooltipComponentCallback.EVENT.register(tooltipData -> {
            if(tooltipData instanceof BeeNestTooltipComponent.BeeNestTooltipData beeNestTooltipData){
                return new BeeNestTooltipComponent(beeNestTooltipData);
            } else if (tooltipData instanceof AxolotlBucketTooltipComponent.AxolotlBucketTooltipData axolotlBucketTooltipData) {
                return new AxolotlBucketTooltipComponent(axolotlBucketTooltipData);
            }
            return null;
        });
    }
}
