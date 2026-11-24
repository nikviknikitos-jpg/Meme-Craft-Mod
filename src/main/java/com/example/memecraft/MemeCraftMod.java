package com.example.memecraft;

import com.example.memecraft.blocks.ModBlocks;
import com.example.memecraft.command.MemeCommand;
import com.example.memecraft.entities.ModEntities;
import com.example.memecraft.items.ModItems;
import com.example.memecraft.network.CardEffectPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemeCraftMod implements ModInitializer {
    public static final String MODID = "memecraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModItems.register();
        ModBlocks.register();
        FabricDefaultAttributeRegistry.register(ModEntities.ANOMALY_MINION, com.example.memecraft.entities.AnomalyMinionEntity.createAttributes());
        CardEffectPacket.registerCommon();
        CommandRegistrationCallback.EVENT.register((d, ra, e) -> MemeCommand.register(d));
        ServerTickEvents.END_WORLD_TICK.register(com.example.memecraft.MemeTickHandler::tick);
        LOGGER.info("MemeCraftMod initialized");
    }
}
