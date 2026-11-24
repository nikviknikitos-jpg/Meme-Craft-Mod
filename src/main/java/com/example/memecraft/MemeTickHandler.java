package com.example.memecraft;

import net.minecraft.server.world.ServerWorld;

public class MemeTickHandler {
    public static void tick(ServerWorld world) {
        if (world.getTime() % 100 != 0) return;
    }
}
