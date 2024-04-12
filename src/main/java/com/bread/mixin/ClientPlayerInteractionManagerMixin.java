package com.bread.mixin;

import com.bread.BreadConfig;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @ModifyConstant(method = "updateBlockBreakingProgress", constant = @Constant(intValue = 5))
    private int postBlockMine(int blockHitDelay) {
        return BreadConfig.clickBlockMining ? 0 : 5;
    }

}
