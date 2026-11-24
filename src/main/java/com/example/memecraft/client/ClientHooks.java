package com.example.memecraft.client;

import net.minecraft.client.MinecraftClient;

public class ClientHooks {
    public static void openDeck() { MinecraftClient.getInstance().setScreen(new MemeCardScreen()); }
    public static void openEncyclopedia() { MinecraftClient.getInstance().setScreen(new MemeEncyclopediaScreen()); }
}
