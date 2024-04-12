package com.bread.feature;

import com.bread.BreadConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class EasyBedrockBreaker {

    public static void init() {
        activateKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bread.easyBedrockBreaker", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.bread.breadclient"));
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (!activateKey.isPressed()) releasePackets();
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (isDelayingPackets() && BreadConfig.easyBedrockBreaker)
                MinecraftClient.getInstance().textRenderer.draw("delaying packets", 4, drawContext.getScaledWindowHeight() - 4 - MinecraftClient.getInstance().textRenderer.fontHeight, 0xffffffff, true, drawContext.getMatrices().peek().getPositionMatrix(), drawContext.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0x00000000, 1);
        });
    }

    private static ArrayList<Packet<?>> delayedPackets = new ArrayList<>();

    private static KeyBinding activateKey;

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

    private static void releasePackets() {
        for (Packet<?> packet : delayedPackets) {
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
        }
        delayedPackets.clear();
    }

}
