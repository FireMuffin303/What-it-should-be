package net.firemuffin303.wisb.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.firemuffin303.wisb.client.tooltip.AxolotlBucketTooltipComponent;
import net.firemuffin303.wisb.client.tooltip.BeeNestTooltipComponent;
import net.firemuffin303.wisb.client.tooltip.TropicalfishTooltipComponent;
import net.firemuffin303.wisb.config.ModConfig;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;

public class WisbClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(ModHudRender::init);
        TooltipComponentCallback.EVENT.register(WisbClient::tooltipComponentEvent);
    }

    private static TooltipComponent tooltipComponentEvent(TooltipData tooltipData){
        if(tooltipData instanceof BeeNestTooltipComponent.BeeNestTooltipData beeNestTooltipData){
            return new BeeNestTooltipComponent(beeNestTooltipData);
        } else if (!ModConfig.disableWisbMobBucketTooltip) {
            if(tooltipData instanceof AxolotlBucketTooltipComponent.AxolotlBucketTooltipData axolotlBucketTooltipData){
                return new AxolotlBucketTooltipComponent(axolotlBucketTooltipData);
            }else if (tooltipData instanceof TropicalfishTooltipComponent.TropicalfishTooltipData tropicalFishBucketTooltipData) {
                return new TropicalfishTooltipComponent(tropicalFishBucketTooltipData);
            }
        }
        return null;
    }
}
