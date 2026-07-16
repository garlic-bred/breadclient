package com.bread.mixin;

import net.minecraft.world.inventory.ArmorSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorSlot.class)
public class ArmorSlotMixin {

    @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
    public void acceptItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

}
