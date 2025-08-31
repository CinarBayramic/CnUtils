package me.cinarbayramic.cnutils.mixins;

import net.minecraft.world.chunk.light.BlockLightStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import me.cinarbayramic.cnutils.client.ClientUtilSettings;

@Mixin(BlockLightStorage.class)
public class LightMixin {
  @Inject(method = "getLight", at = @At("HEAD"), cancellable = true)
  private void injectGetLight(long blockPos, CallbackInfoReturnable<Integer> cir) {
    // Example: override light level
    if(ClientUtilSettings.FullBright) {
      cir.setReturnValue(15); // max light
    }
  }

}
