package me.cinarbayramic.cnutils.mixins;

import me.cinarbayramic.cnutils.client.ClientUtilSettings;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class ZoomMixin {
  @Inject(method = "getFov", at = @At("HEAD"),cancellable = true)
  private void ZoomIn(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Float> cir) {
    if(ClientUtilSettings.Zooming) {
      cir.setReturnValue(30.0F);
    }
  }
}
