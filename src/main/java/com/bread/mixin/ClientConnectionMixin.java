package com.bread.mixin;

import com.bread.feature.PacketDelay;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class ClientConnectionMixin {

    @Inject(method = "disconnect*", at = @At("HEAD"))
    private void clearPackets(Component disconnectReason, CallbackInfo ci) {
        PacketDelay.clearPackets();
    }

}
