package me.cinarbayramic.cnutils.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.*;
import net.minecraft.client.util.InputUtil;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import me.cinarbayramic.cnutils.client.ClientUtilSettings;
public class CnUtilsClient implements ClientModInitializer {
  public static KeyBinding ToggleFB;
  public static KeyBinding ToggleDN;

  @Override
  public void onInitializeClient() {
    HudRenderCallback.EVENT.register(new HudInfoOverlay());
    ToggleFB = KeyBindingHelper.registerKeyBinding(new KeyBinding(
      "key.cnutils.togglefb", // Translation key
      InputUtil.Type.KEYSYM,
      GLFW.GLFW_KEY_G,      // Default key
      "category.cnutils"      // Category in Controls menu
    ));
    ToggleDN = KeyBindingHelper.registerKeyBinding(new KeyBinding(
      "key.cnutils.togglern", // Translation key
      InputUtil.Type.KEYSYM,
      GLFW.GLFW_KEY_N,      // Default key
      "category.cnutils"      // Category in Controls menu
    ));
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if(ToggleFB.wasPressed()) {
        if(ClientUtilSettings.FullBright) {
          ClientUtilSettings.FullBright = false;
        }else {
          ClientUtilSettings.FullBright = true;
        }
      }
      if(ToggleDN.wasPressed()) {
        if(ClientUtilSettings.DrawPlayerNames) {
          ClientUtilSettings.DrawPlayerNames = false;
        }else {
          ClientUtilSettings.DrawPlayerNames = true;
        }
      }
    });

  }
}
