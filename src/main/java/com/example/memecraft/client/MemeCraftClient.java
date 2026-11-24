package com.example.memecraft.client;

import com.example.memecraft.blocks.ModBlocks;
import com.example.memecraft.entities.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class MemeCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.FLYING_TIRE, FlyingTireRenderer::new);
        EntityRendererRegistry.register(ModEntities.VOID_ANOMALY, VoidAnomalyRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANOMALY_MINION, ctx -> new MobEntityRenderer<com.example.memecraft.entities.AnomalyMinionEntity, BipedEntityModel<com.example.memecraft.entities.AnomalyMinionEntity>>(
                ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0.45f) {
            @Override public Identifier getTexture(com.example.memecraft.entities.AnomalyMinionEntity e) {
                return new Identifier("minecraft", "textures/entity/steve.png");
            }
        });
        BlockEntityRendererRegistry.register(ModBlocks.INDUS_MOTOR_BE, IndusMotorBlockEntityRenderer::new);
        HudRenderCallback.EVENT.register((ctx, delta) -> {});
    }
}
