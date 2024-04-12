package com.bread.mixin.malilib;

import fi.dy.masa.malilib.config.options.ConfigDouble;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConfigDouble.class)
public class ConfigDoubleMixin {

    @Inject(method = "getClampedValue", at = @At("HEAD"), cancellable = true, remap = false)
    private void clampValue(double value, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(Math.max(value, 0));
    }

}
