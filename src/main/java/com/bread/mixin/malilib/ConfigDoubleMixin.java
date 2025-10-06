package com.bread.mixin.malilib;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo

@Mixin(targets = "fi.dy.masa.malilib.config.options.ConfigDouble")
public class ConfigDoubleMixin {

    @Inject(method = "getClampedValue", at = @At("HEAD"), cancellable = true, remap = false)
    private void clampValue(double value, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(Math.max(value, 0));
    }

}
