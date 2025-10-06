package com.bread.feature;

import com.bread.BreadClient;
import com.bread.BreadConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class PacketDelay {

    private static KeyBinding activateKey;

    public static void init() {
        activateKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bread.packetDelay", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), BreadClient.CATEGORY));
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (!activateKey.isPressed()) releasePackets(client);
        });
        HudElementRegistry.attachElementAfter(Identifier.of("subtitles"), Identifier.of("breadclient", "packet-delay-text-layer"), (context, tickCounter) -> {
            if (isDelayingPackets() && BreadConfig.packetDelay) {
                context.drawText(MinecraftClient.getInstance().textRenderer, "Delaying Packets", 4, context.getScaledWindowHeight() - 4 - MinecraftClient.getInstance().textRenderer.fontHeight, 0xffffffff, false);
            }
        });
    }

    private static final ArrayList<Packet<?>> delayedPackets = new ArrayList<>();

    public static final Class[] blockedPackets = {
            PlayerActionC2SPacket.class,
            PlayerInputC2SPacket.class,
            PlayerInteractBlockC2SPacket.class,
            PlayerInteractItemC2SPacket.class,
            UpdateSelectedSlotC2SPacket.class
    };

    public static boolean isDelayingPackets() {
        return activateKey.isPressed();
    }

    public static void delayPacket(Packet<?> p) {
        delayedPackets.add(p);
    }

    public static void clearPackets() {
        delayedPackets.clear();
    }

    private static void releasePackets(MinecraftClient client) {
        for (Packet<?> packet : delayedPackets) {
            client.getNetworkHandler().sendPacket(packet);
        }
        delayedPackets.clear();
    }

}
