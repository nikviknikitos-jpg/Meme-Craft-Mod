package com.example.memecraft.command;

import com.example.memecraft.blocks.ModBlocks;
import com.example.memecraft.items.ModItems;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class MemeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("meme_give").executes(ctx -> {
            ServerPlayerEntity p = ctx.getSource().getPlayer();
            if (p != null) {
                p.getInventory().insertStack(new ItemStack(ModItems.INDUS_MOTOR_ITEM, 4));
                p.getInventory().insertStack(new ItemStack(ModItems.TIRE_ITEM, 16));
                p.getInventory().insertStack(new ItemStack(ModItems.RED_BUTTON_ITEM, 4));
                p.getInventory().insertStack(new ItemStack(ModItems.MEME_DECK));
                p.getInventory().insertStack(new ItemStack(ModItems.MEME_ENCYCLOPEDIA));
                p.getInventory().insertStack(new ItemStack(ModItems.ANOMALY_SPAWNER, 8));
                ctx.getSource().sendFeedback(() -> Text.literal("MemeCraft kit given"), false);
                return 1;
            }
            return 0;
        }));
    }
}
