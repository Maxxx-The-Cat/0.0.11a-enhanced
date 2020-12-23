package com.mojang.minecraft.gui;

import com.mojang.minecraft.renderer.Tesselator;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Font;

import org.lwjgl.opengl.GL11;

public class Screen {

   protected float imgZ = 0.0F;
   public Font var0 = new Font("/default.gif", Minecraft.textures);;


   protected static void drawBox(int var0, int var1, int var2, int var3, int var4) {
      float var5 = (float)(var4 >>> 24) / 255.0F;
      float var6 = (float)(var4 >> 16 & 255) / 255.0F;
      float var7 = (float)(var4 >> 8 & 255) / 255.0F;
      float var9 = (float)(var4 & 255) / 255.0F;
      Tesselator var8 = Tesselator.instance;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(var6, var7, var9, var5);
      var8.init();
      var8.vertex((float)var0, (float)var3, 0.0F);
      var8.vertex((float)var2, (float)var3, 0.0F);
      var8.vertex((float)var2, (float)var1, 0.0F);
      var8.vertex((float)var0, (float)var1, 0.0F);
      var8.flush();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawFadingBox(int var0, int var1, int var2, int var3, int var4, int var5) { //how the fuck does colour work here?
      float var6 = (float)(var4 >>> 24) / 255.0F;
      float var7 = (float)(var4 >> 16 & 255) / 255.0F;
      float var8 = (float)(var4 >> 8 & 255) / 255.0F;
      float var12 = (float)(var4 & 255) / 255.0F;
      float var9 = (float)(var5 >>> 24) / 255.0F;
      float var10 = (float)(var5 >> 16 & 255) / 255.0F;
      float var11 = (float)(var5 >> 8 & 255) / 255.0F;
      float var13 = (float)(var5 & 255) / 255.0F;
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glBegin(7);
      GL11.glColor4f(var7, var8, var12, var6);
      GL11.glVertex2f((float)var2, (float)var1);
      GL11.glVertex2f((float)var0, (float)var1);
      GL11.glColor4f(var10, var11, var13, var9);
      GL11.glVertex2f((float)var0, (float)var3);
      GL11.glVertex2f((float)var2, (float)var3);
      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
   }
   
   public static void drawCenteredString(Font var0, String var1, int var2, int var3, int var4) {
	      var0.draw(var1, var2 - var0.width(var1) / 2, var3, var4);
	   }
}