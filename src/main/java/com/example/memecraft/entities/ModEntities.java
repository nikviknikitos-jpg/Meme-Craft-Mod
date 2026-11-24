package com.example.memecraft.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<FlyingTireEntity> FLYING_TIRE;
    public static EntityType<VoidAnomalyEntity> VOID_ANOMALY;
    public static EntityType<AnomalyMinionEntity> ANOMALY_MINION;

    public static void register() {
        FLYING_TIRE = Registry.register(Registries.ENTITY_TYPE,
                new Identifier("memecraft", "flying_tire"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, FlyingTireEntity::new)
                        .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());
        VOID_ANOMALY = Registry.register(Registries.ENTITY_TYPE,
                new Identifier("memecraft", "void_anomaly"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, VoidAnomalyEntity::new)
                        .dimensions(EntityDimensions.fixed(5.0f, 5.0f)).build());
        ANOMALY_MINION = Registry.register(Registries.ENTITY_TYPE,
                new Identifier("memecraft", "anomaly_minion"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AnomalyMinionEntity::new)
                        .dimensions(EntityDimensions.fixed(0.45f, 0.9f)).build());
    }
}
