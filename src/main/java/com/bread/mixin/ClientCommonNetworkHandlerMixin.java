package com.bread.mixin;

import com.bread.feature.PacketDelay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.protocol.Packet;

@Mixin(ClientCommonPacketListenerImpl.class)
public class ClientCommonNetworkHandlerMixin {

    @Inject(method = "send", at = @At("HEAD"), cancellable = true)
    private void delayPackets(Packet<?> packet, CallbackInfo ci) {
        if (PacketDelay.isDelayingPackets() && Arrays.stream(PacketDelay.blockedPackets).anyMatch(c -> c.isInstance(packet))) {
            PacketDelay.delayPacket(packet);
            ci.cancel();
        }
    }

}
