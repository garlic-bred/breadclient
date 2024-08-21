package com.bread.mixin;

import com.bread.feature.PacketDelay;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void delayPackets(Packet<?> packet, CallbackInfo ci) {
        if (PacketDelay.isDelayingPackets() && Arrays.stream(PacketDelay.blockedPackets).anyMatch(c -> c.isInstance(packet))) {
            PacketDelay.delayPacket(packet);
            ci.cancel();
        }
    }

}
