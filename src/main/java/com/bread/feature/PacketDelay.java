package com.bread.feature;

import com.bread.BreadClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;

public class PacketDelay {

    private static KeyMapping.Category category;
    private static KeyMapping activateKey;

    public static void init() {
        category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(BreadClient.MOD_ID, "keys")
        );
        activateKey = KeyMappingHelper.registerKeyMapping(
                new KeyMapping("key.breadclient.packetDelay", InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), category)
        );
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (!activateKey.isDown()) releasePackets();
        });

        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.fromNamespaceAndPath(BreadClient.MOD_ID, "renderer"), (graphics, tickCounter) -> {
            if (isDelayingPackets())
                graphics.text(
                        Minecraft.getInstance().font,
                        "delaying packets",
                        4,
                        Minecraft.getInstance().getWindow().getGuiScaledHeight() - 4 - Minecraft.getInstance().font.lineHeight,
                        0xFFFFFFFF,
                        true
                );
        });
    }

    private static ArrayList<Packet<?>> delayedPackets = new ArrayList<>();

    public static final Class[] blockedPackets = {
            ServerboundPlayerActionPacket.class,
            ServerboundPlayerInputPacket.class,
            ServerboundUseItemOnPacket.class,
            ServerboundUseItemPacket.class,
            ServerboundSetCarriedItemPacket.class
    };

    public static boolean isDelayingPackets() {
        return activateKey.isDown();
    }

    public static void delayPacket(Packet<?> p) {
        delayedPackets.add(p);
    }

    public static void clearPackets() {
        delayedPackets.clear();
    }

    private static void releasePackets() {
        for (Packet<?> packet : delayedPackets) {
            Minecraft.getInstance().getConnection().send(packet);
        }
        delayedPackets.clear();
    }

}
