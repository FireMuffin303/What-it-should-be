package net.firemuffin303.wisb.client.renderer;

import net.firemuffin303.wisb.common.TurtleAccessor;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.TurtleEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.Identifier;

public class TurtleBarnacleFeatureRenderer<T extends TurtleEntity,M extends TurtleEntityModel<T>> extends FeatureRenderer<T,M> {
    public M model;
    public Identifier texture;
    public TurtleBarnacleFeatureRenderer(FeatureRendererContext<T, M> context, Identifier texture) {
        super(context);
        this.model = this.getContextModel();
        this.texture = texture;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(((TurtleAccessor)entity).easierTurtleScute$getCover() && !entity.isInvisible()){
            this.model.animateModel(entity, limbAngle, limbDistance, tickDelta);
            this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(this.texture));
            this.model.render(matrices,vertexConsumer,light, OverlayTexture.DEFAULT_UV,1.0f,1.0f,1.0f,1.0f);
        }
    }
}
