package com.example.memecraft.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class CardEffectPacket {
    public static final Identifier CHANNEL = new Identifier("memecraft", "card_effect");

    public static void registerCommon() {
        ServerPlayNetworking.registerGlobalReceiver(CHANNEL, (server, player, handler, buf, sender) -> {
            int cardId = buf.readInt();
            server.execute(() -> CardEffects.apply(player, cardId));
        });
    }
}
