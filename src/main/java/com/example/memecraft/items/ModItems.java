package com.example.memecraft.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item INDUS_MOTOR_ITEM, TIRE_ITEM, RED_BUTTON_ITEM, MEME_DECK, MEME_ENCYCLOPEDIA, ANOMALY_SPAWNER;
    public static ItemGroup TAB;

    public static void register() {
        INDUS_MOTOR_ITEM = Registry.register(Registries.ITEM, new Identifier("memecraft", "indus_motor"),
                new BlockItem(com.example.memecraft.blocks.ModBlocks.INDUS_MOTOR, new Item.Settings().maxCount(64)));
        TIRE_ITEM = reg("tire_item", new Item(new Item.Settings()));
        RED_BUTTON_ITEM = Registry.register(Registries.ITEM, new Identifier("memecraft", "red_button_box"),
                new BlockItem(com.example.memecraft.blocks.ModBlocks.RED_BUTTON_BOX, new Item.Settings().maxCount(64)));
        MEME_DECK = reg("meme_deck", new MemeDeckItem(new Item.Settings().maxCount(1)));
        MEME_ENCYCLOPEDIA = reg("meme_encyclopedia", new MemeEncyclopediaItem(new Item.Settings().maxCount(1)));
        ANOMALY_SPAWNER = reg("anomaly_spawner", new AnomalySpawnerItem(new Item.Settings().maxCount(16)));

        TAB = Registry.register(Registries.ITEM_GROUP, new Identifier("memecraft", "memecraft_tab"),
                FabricItemGroup.builder().displayName(Text.translatable("itemGroup.memecraft.memecraft_tab"))
                        .icon(() -> new ItemStack(INDUS_MOTOR_ITEM))
                        .entries((ctx, entries) -> {
                            entries.add(INDUS_MOTOR_ITEM);
                            entries.add(TIRE_ITEM);
                            entries.add(RED_BUTTON_ITEM);
                            entries.add(MEME_DECK);
                            entries.add(MEME_ENCYCLOPEDIA);
                            entries.add(ANOMALY_SPAWNER);
                        }).build());
    }

    private static Item reg(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("memecraft", name), item);
    }
}
