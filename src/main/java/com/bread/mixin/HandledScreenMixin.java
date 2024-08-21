package com.bread.mixin;

import com.bread.feature.BookDupe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {

    @Inject(method = "init", at = @At("RETURN"))
    public void writeBookContainer(CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (BookDupe.shouldSavestate && player.currentScreenHandler.slots.size() >= 63) {
            BookDupe.shouldSavestate = false;
            BookDupe.dupe(2);
        }
    }
}
