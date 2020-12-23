package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.phys.AABB;
import com.mojang.minecraft.phys.MathsHelper;
import com.mojang.minecraft.renderer.Tesselator;
import java.util.Random;

public class CrossTile extends Tile {
   protected CrossTile(int id, int tex) {
      super(id);
      this.tex = tex;
   }


   public void render(Tesselator t, Level level, int layer, int x, int y, int z) {
      if (!(level.isLit(x, y, z) ^ layer != 1)) {
         int tex = this.getTexture(15);
         float u0 = (float)(tex % 16) / 16.0F;
         float u1 = u0 + 0.0624375F;
         float v0 = (float)(tex / 16) / 16.0F;
         float v1 = v0 + 0.0624375F;
         int rots = 2;
         t.color(1.0F, 1.0F, 1.0F);

         for(int r = 0; r < rots; ++r) {
            float xa = (float)(Math.sin((double)r * 3.141592653589793D / (double)rots + 0.7853981633974483D) * 0.5D);
            float za = (float)(Math.cos((double)r * 3.141592653589793D / (double)rots + 0.7853981633974483D) * 0.5D);
            float x0 = (float)x + 0.5F - xa;
            float x1 = (float)x + 0.5F + xa;
            float y0 = (float)y + 0.0F;
            float y1 = (float)y + 1.0F;
            float z0 = (float)z + 0.5F - za;
            float z1 = (float)z + 0.5F + za;
            t.vertexUV(x0, y1, z0, u1, v0);
            t.vertexUV(x1, y1, z1, u0, v0);
            t.vertexUV(x1, y0, z1, u0, v1);
            t.vertexUV(x0, y0, z0, u1, v1);
            t.vertexUV(x1, y1, z1, u0, v0);
            t.vertexUV(x0, y1, z0, u1, v0);
            t.vertexUV(x0, y0, z0, u1, v1);
            t.vertexUV(x1, y0, z1, u0, v1);
         }

      }
   }
   
   private void render(Tesselator var1, float var2, float var3, float var4) {
	      int var15;
	      int var5 = (var15 = this.getTexture(15)) % 16 << 4;
	      int var6 = var15 / 16 << 4;
	      float var16 = (float)var5 / 256.0F;
	      float var17 = ((float)var5 + 15.99F) / 256.0F;
	      float var7 = (float)var6 / 256.0F;
	      float var18 = ((float)var6 + 15.99F) / 256.0F;

	      for(int var8 = 0; var8 < 2; ++var8) {
	         float var9 = (float)((double)MathsHelper.sin((float)var8 * 3.1415927F / 2.0F + 0.7853982F) * 0.5D);
	         float var10 = (float)((double)MathsHelper.cos((float)var8 * 3.1415927F / 2.0F + 0.7853982F) * 0.5D);
	         float var11 = var2 + 0.5F - var9;
	         var9 += var2 + 0.5F;
	         float var13 = var3 + 1.0F;
	         float var14 = var4 + 0.5F - var10;
	         var10 += var4 + 0.5F;
	         var1.vertexUV(var11, var13, var14, var17, var7);
	         var1.vertexUV(var9, var13, var10, var16, var7);
	         var1.vertexUV(var9, var3, var10, var16, var18);
	         var1.vertexUV(var11, var3, var14, var17, var18);
	         var1.vertexUV(var9, var13, var10, var17, var7);
	         var1.vertexUV(var11, var13, var14, var16, var7);
	         var1.vertexUV(var11, var3, var14, var16, var18);
	         var1.vertexUV(var9, var3, var10, var17, var18);
	      }

	   }
   
   public void renderItem(Tesselator var1) {
	      float var2 = 1F;
	      float var3 = 1F;
	      float var4 = 1F;
	      var1.color(var2, var2, var2);
	      this.renderInside(var1, -2, 0, 0, 0);
	      var1.color(1.0F, 1.0F, 1.0F);
	      this.renderInside(var1, -2, 0, 0, 1);
	      var1.color(var3, var3, var3);
	      this.renderInside(var1, -2, 0, 0, 2);
	      var1.color(var3, var3, var3);
	      this.renderInside(var1, -2, 0, 0, 3);
	      var1.color(var4, var4, var4);
	      this.renderInside(var1, -2, 0, 0, 4);
	      var1.color(var4, var4, var4);
	      this.renderInside(var1, -2, 0, 0, 5);
	   }
   
   public final void renderPreview(Tesselator var1) {
	      var1.normal(0.0F, 1.0F, 0.0F);
	      var1.init();
	      this.render(var1, 0.0F, 0.4F, -0.3F);
	      var1.flush();
	   }
   

   public AABB getAABB(int x, int y, int z) {
      return null;
   }

   public boolean blocksLight() {
      return false;
   }

   public boolean isSolid() {
      return false;
   }
}
