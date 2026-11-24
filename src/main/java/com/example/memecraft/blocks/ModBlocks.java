package com.example.memecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Block INDUS_MOTOR, RED_BUTTON_BOX;
    public static BlockEntityType<IndusMotorBlockEntity> INDUS_MOTOR_BE;

    public static void register() {
        INDUS_MOTOR = Registry.register(Registries.BLOCK, new Identifier("memecraft", "indus_motor"),
                new IndusMotorBlock(Block.Settings.create().strength(3f).nonOpaque()));
        RED_BUTTON_BOX = Registry.register(Registries.BLOCK, new Identifier("memecraft", "red_button_box"),
                new RedButtonBoxBlock(Block.Settings.create().strength(2f).nonOpaque()));
        INDUS_MOTOR_BE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier("memecraft", "indus_motor"),
                BlockEntityType.Builder.create(IndusMotorBlockEntity::new, INDUS_MOTOR).build(null));
    }
}
