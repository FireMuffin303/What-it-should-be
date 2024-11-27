package net.firemuffin303.wisb.mixin.turtle;

import net.firemuffin303.wisb.Wisb;
import net.firemuffin303.wisb.client.renderer.TurtleBarnacleFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.TurtleEntityRenderer;
import net.minecraft.client.render.entity.model.TurtleEntityModel;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TurtleEntityRenderer.class)
public abstract class TurtleEntityRendererMixin extends MobEntityRenderer<TurtleEntity, TurtleEntityModel<TurtleEntity>> {
    @Unique
    private final Identifier BARNACLE_TEXTURE = new Identifier(Wisb.MOD_ID,"textures/entity/turtle/barnacle.png");

    public TurtleEntityRendererMixin(EntityRendererFactory.Context context, TurtleEntityModel<TurtleEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void mod$init(EntityRendererFactory.Context context, CallbackInfo ci){
        this.addFeature(new TurtleBarnacleFeatureRenderer<>(this,this.BARNACLE_TEXTURE));
    }
}
