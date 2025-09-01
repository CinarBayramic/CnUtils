package me.cinarbayramic.cnutils.mixins;

import me.cinarbayramic.cnutils.client.ClientUtilSettings;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class GlowingMixin {
  @Inject(method = "isGlowing",at=@At("HEAD"),cancellable = true)
  void isGlowing(CallbackInfoReturnable<Boolean> cir) {
    if(ClientUtilSettings.SeeThrough) {
      cir.setReturnValue(true);
    }
  }
}
