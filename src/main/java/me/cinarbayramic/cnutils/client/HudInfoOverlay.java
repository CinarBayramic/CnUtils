package me.cinarbayramic.cnutils.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector4f;


import java.util.List;

public class HudInfoOverlay implements HudRenderCallback {
  boolean VecInArea(int x, int y, int aX, int aY, int aW, int aH) {
    return (x > aX) & (y > aY) & (x < (aX+aW)) & (y<(aY+aH));
  }
  void drawline(int x, int y, int Lx, int Ly,DrawContext drawContext,MinecraftClient client) {
    int dX = Lx - x;
    int dY = Ly - y;

    double length = Math.sqrt(dX * dX + dY * dY);
    double direction_x = dX / length;
    double direction_y = dY / length;

    double vx = x;
    double vy = y;
    double vlen = 0;

    while (vlen < length) {
      drawContext.drawText(client.textRenderer, Text.literal("."), (int) vx, (int) vy, 0xFFFFFF, true);
      vx += direction_x;
      vy += direction_y;
      vlen += 1.0;
    }

  }
  void drawminimap(MinecraftClient client, DrawContext drawContext) {
    List<AbstractClientPlayerEntity> players = client.world.getPlayers();
    int x = 10;
    int y = 10;
    int w = 100;
    int h = 100;
    drawContext.drawBorder(x,y,w,h,0);

    drawContext.drawBorder(x+1,y+1,w-1,h-1,0xFFFFFFFF);

    double Client_degx = client.player.getRotationClient().x;
    double Client_degy = client.player.getRotationClient().y;
    double Client_x = client.player.getPos().x;
    double Client_z = client.player.getPos().z;
    //double client_radian = Math.atan2(Client_degy,Client_degx);//pi ile olduğu için radyan
    double angle = client.player.getRotationClient().y;
    double client_radian = angle/57.296;
    //System.out.println("clirad:"+client_radian);
    for(AbstractClientPlayerEntity p:players) {

      double pX_relative = Client_x - p.getPos().x;
      double pZ_relative = Client_z - p.getPos().z;
      double rad = Math.atan2(pZ_relative,pX_relative);
      double dist = Math.sqrt((pX_relative*pX_relative)+(pZ_relative*pZ_relative));
      if(dist < 71) {
        double newRad = (rad - client_radian);// % (2 * Math.PI) - Math.PI;
        double pX = x + 50 + dist * Math.cos(newRad);
        double pY = y + 50 + dist * Math.sin(newRad);
        double r = 2;
        if(VecInArea((int)pX,(int)pY,x,y,100,100)) {
          drawContext.fill((int) (pX - r), (int) (pY - r), (int) (pX + r), (int) (pY + r), 0xFFFFFFFF);
          if (ClientUtilSettings.DrawPlayerNames) {
            //drawContext.drawText(client.textRenderer, p.getName(), (int) pX, (int) pY, 0xFFFFFF, true);
            renderText(drawContext, client, (int) pX, (int) pY, (p.getDisplayName()).getString(), 0.6);
          }
        }
      }
    }

  }
  void renderText(DrawContext drawContext,MinecraftClient client, int x, int y, String str,double Uscale) {
    TextRenderer textRenderer = client.textRenderer;
    //double guiScale = client.getWindow().getScaleFactor(); // Typically 1.0, 2.0, 3.0, etc.
    double guiScale = 1;
    guiScale *= Uscale;
// Apply scale to MatrixStack
    MatrixStack matrices = drawContext.getMatrices();
    matrices.push();
    matrices.scale((float) guiScale, (float) guiScale, 1.0f);

// Adjust position to compensate for scale
    float scaledX = x / (float) guiScale;
    float scaledY = y / (float) guiScale;

    drawContext.drawText(textRenderer, Text.of(str), (int) scaledX, (int) scaledY, 0xFFFFFF, true);
    matrices.pop();

  }
  @Override
  public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
    int x = 0;
    int y = 0;
    MinecraftClient client = MinecraftClient.getInstance();
    if(client != null) {
      //int width = client.getWindow().getWidth();
      //int height = client.getWindow().getHeight();
      Window window = client.getWindow();
        int width = window.getFramebufferWidth();
      int height = window.getFramebufferWidth();


    String Username;
    Username = client.getSession().getUsername();
      drawContext.drawText(client.textRenderer, Text.literal("press G to toggle FULLBRIGHT"), (int) 10, (int) 115, 0xFFFFFF, true);
      drawminimap(client,drawContext);
      if(ClientUtilSettings.Zooming) {
        renderText(drawContext,client,10,5,"Zooming",0.9);
      }
      //renderText(drawContext,client,30,30,"hello,world!",0.4);
    }
  }
}
