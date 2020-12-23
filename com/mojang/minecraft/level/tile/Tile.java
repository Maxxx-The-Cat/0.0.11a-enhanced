package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.particle.Particle;
import com.mojang.minecraft.particle.ParticleEngine;
import com.mojang.minecraft.phys.AABB;
import com.mojang.minecraft.renderer.Tesselator;
import java.util.Random;

public class Tile {
   public static final Tile[] tiles = new Tile[256];
   public static final Tile empty = null;
   public static final Tile rock = new Tile(1, 1);
   public static final Tile grass = new GrassTile(2);
   public static final Tile dirt = new DirtTile(3, 2);
   public static final Tile stoneBrick = new Tile(4, 16);
   public static final Tile wood = new Tile(5, 4);
   public static final Tile bush = new Bush(6, 15);
   public static final Tile water = new LiquidTile(7, 1);
   public static final Tile calmWater = new CalmLiquidTile(8, 1);
   public static final Tile lava = new LiquidTile(9, 2);
   public static final Tile calmLava = new CalmLiquidTile(10, 2);
   public static final Tile glass = new NonSolidTile(11, 18);
   public static final Tile rbricks = new Tile(12, 19);
   public static final Tile book = new BookshelfTile(13, 20);
   public static final Tile moss = new Tile(14, 21);
   public static final Tile rwool = new Tile(15, 22);
   public static final Tile ywool = new Tile(16, 23);
   public static final Tile gwool = new Tile(17, 24);
   public static final Tile bwool = new Tile(18, 25);
   public static final Tile bark = new Tile(19, 26);
   public static final Tile leaf = new NonSolidTile(20, 27);
   public static final Tile redflower = new Bush(21, 28);
   public static final Tile yellowflower = new Bush(22, 29);
   public static final Tile bottle = new CrossTile(23, 32);
   public static final Tile beam = new CrossTile(24, 33);
   public static final Tile box = new Tile(25, 34);
   public static final Tile tnt = new TntTile(26, 35);
   public static final Tile log = new LogTile(27, 26);
   public static final Tile bbricks = new Tile(28, 38);
   public static final Tile gbricks = new Tile(29, 39);
   public static final Tile ybricks = new Tile(30, 40);
   public static final Tile blbricks = new Tile(31, 41);
   public static final Tile rglass = new NonSolidTile(32,43);
   public static final Tile bglass = new NonSolidTile(33,44);
   public static final Tile gglass = new NonSolidTile(34,45);
   public static final Tile yglass = new NonSolidTile(35,46);
   public static final Tile blglass = new NonSolidTile(36,47);
   public static final Tile wwool = new Tile(37, 8);
   public static final Tile blwool = new Tile(38, 9);
   public static final Tile wbricks = new Tile(39, 57);
   public static final Tile cyanflower = new Bush(40, 58);
   public static final Tile wplanks = new Tile(41, 48);
   public static final Tile rplanks = new Tile(42, 49);
   public static final Tile bplanks = new Tile(43, 50);
   public static final Tile gplanks = new Tile(44, 51);
   public static final Tile yplanks = new Tile(45, 52);
   public static final Tile blplanks = new Tile(46, 53);
   public static final Tile rdcobble = new Tile(47, 54);
   public static final Tile preclassicgrass = new Tile(48, 55);
   public static final Tile sponge = new Tile(49, 56);
   public static final Tile classicglass = new NonSolidTile(50,59);
   
   
   

   

   
   
   
   public int tex;
   public final int id;

   protected Tile(int id) {
      tiles[id] = this;
      this.id = id;
   }

   protected Tile(int id, int tex) {
      this(id);
      this.tex = tex;
   }

   public void render(Tesselator t, Level level, int layer, int x, int y, int z) {
      float c1 = 1.0F;
      float c2 = 0.8F;
      float c3 = 0.6F;
      if (this.shouldRenderFace(level, x, y - 1, z, layer)) {
         t.color(c1, c1, c1);
         this.renderFace(t, x, y, z, 0);
      }

      if (this.shouldRenderFace(level, x, y + 1, z, layer)) {
         t.color(c1, c1, c1);
         this.renderFace(t, x, y, z, 1);
      }

      if (this.shouldRenderFace(level, x, y, z - 1, layer)) {
         t.color(c2, c2, c2);
         this.renderFace(t, x, y, z, 2);
      }

      if (this.shouldRenderFace(level, x, y, z + 1, layer)) {
         t.color(c2, c2, c2);
         this.renderFace(t, x, y, z, 3);
      }

      if (this.shouldRenderFace(level, x - 1, y, z, layer)) {
         t.color(c3, c3, c3);
         this.renderFace(t, x, y, z, 4);
      }

      if (this.shouldRenderFace(level, x + 1, y, z, layer)) {
         t.color(c3, c3, c3);
         this.renderFace(t, x, y, z, 5);
      }

   }

   protected boolean shouldRenderFace(Level level, int x, int y, int z, int layer) {
      return !level.isSolidTile(x, y, z) && level.isLit(x, y, z) ^ layer == 1;
   }

   protected int getTexture(int face) {
      return this.tex;
   }

   public void renderFace(Tesselator t, int x, int y, int z, int face) {
      int tex = this.getTexture(face);
      float u0 = (float)(tex % 16) / 16.0F;
      float u1 = u0 + 0.0624375F;
      float v0 = (float)(tex / 16) / 16.0F;
      float v1 = v0 + 0.0624375F;
      float x0 = (float)x + 0.0F;
      float x1 = (float)x + 1.0F;
      float y0 = (float)y + 0.0F;
      float y1 = (float)y + 1.0F;
      float z0 = (float)z + 0.0F;
      float z1 = (float)z + 1.0F;
      if (face == 0) {
         t.vertexUV(x0, y0, z1, u0, v1);
         t.vertexUV(x0, y0, z0, u0, v0);
         t.vertexUV(x1, y0, z0, u1, v0);
         t.vertexUV(x1, y0, z1, u1, v1);
      }

      if (face == 1) {
         t.vertexUV(x1, y1, z1, u1, v1);
         t.vertexUV(x1, y1, z0, u1, v0);
         t.vertexUV(x0, y1, z0, u0, v0);
         t.vertexUV(x0, y1, z1, u0, v1);
      }

      if (face == 2) {
         t.vertexUV(x0, y1, z0, u1, v0);
         t.vertexUV(x1, y1, z0, u0, v0);
         t.vertexUV(x1, y0, z0, u0, v1);
         t.vertexUV(x0, y0, z0, u1, v1);
      }

      if (face == 3) {
         t.vertexUV(x0, y1, z1, u0, v0);
         t.vertexUV(x0, y0, z1, u0, v1);
         t.vertexUV(x1, y0, z1, u1, v1);
         t.vertexUV(x1, y1, z1, u1, v0);
      }

      if (face == 4) {
         t.vertexUV(x0, y1, z1, u1, v0);
         t.vertexUV(x0, y1, z0, u0, v0);
         t.vertexUV(x0, y0, z0, u0, v1);
         t.vertexUV(x0, y0, z1, u1, v1);
      }

      if (face == 5) {
         t.vertexUV(x1, y0, z1, u0, v1);
         t.vertexUV(x1, y0, z0, u1, v1);
         t.vertexUV(x1, y1, z0, u1, v0);
         t.vertexUV(x1, y1, z1, u0, v0);
      }

   }

   public void renderFaceNoTexture(Tesselator t, int x, int y, int z, int face) {
      float x0 = (float)x + 0.0F;
      float x1 = (float)x + 1.0F;
      float y0 = (float)y + 0.0F;
      float y1 = (float)y + 1.0F;
      float z0 = (float)z + 0.0F;
      float z1 = (float)z + 1.0F;
      if (face == 0) {
         t.vertex(x0, y0, z1);
         t.vertex(x0, y0, z0);
         t.vertex(x1, y0, z0);
         t.vertex(x1, y0, z1);
      }

      if (face == 1) {
         t.vertex(x1, y1, z1);
         t.vertex(x1, y1, z0);
         t.vertex(x0, y1, z0);
         t.vertex(x0, y1, z1);
      }

      if (face == 2) {
         t.vertex(x0, y1, z0);
         t.vertex(x1, y1, z0);
         t.vertex(x1, y0, z0);
         t.vertex(x0, y0, z0);
      }

      if (face == 3) {
         t.vertex(x0, y1, z1);
         t.vertex(x0, y0, z1);
         t.vertex(x1, y0, z1);
         t.vertex(x1, y1, z1);
      }

      if (face == 4) {
         t.vertex(x0, y1, z1);
         t.vertex(x0, y1, z0);
         t.vertex(x0, y0, z0);
         t.vertex(x0, y0, z1);
      }

      if (face == 5) {
         t.vertex(x1, y0, z1);
         t.vertex(x1, y0, z0);
         t.vertex(x1, y1, z0);
         t.vertex(x1, y1, z1);
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
   
   public final AABB getTileAABB(int x, int y, int z) {
      return new AABB((float)x, (float)y, (float)z, (float)(x + 1), (float)(y + 1), (float)(z + 1));
   }

   public AABB getAABB(int x, int y, int z) {
      return new AABB((float)x, (float)y, (float)z, (float)(x + 1), (float)(y + 1), (float)(z + 1));
   }

   public boolean blocksLight() {
      return true;
   }

   public boolean isSolid() {
      return true;
   }
   
   public boolean canExplode(){
	   return true;
   }
   
   public void renderPreview(Tesselator var1) {
      var1.init();

      for(int var2 = 0; var2 < 6; ++var2) {
         if(var2 == 0) {
            var1.normal(0.0F, 1.0F, 0.0F);
         }

         if(var2 == 1) {
            var1.normal(0.0F, -1.0F, 0.0F);
         }

         if(var2 == 2) {
            var1.normal(0.0F, 0.0F, 1.0F);
         }

         if(var2 == 3) {
            var1.normal(0.0F, 0.0F, -1.0F);
         }

         if(var2 == 4) {
            var1.normal(1.0F, 0.0F, 0.0F);
         }

         if(var2 == 5) {
            var1.normal(-1.0F, 0.0F, 0.0F);
         }

         this.renderInside(var1, 0, 0, 0, var2);
//         this.render(Minecraft.level, 0, 0, 0, var1, Block.TORCH);
      

      var1.flush();
   }
   }
   
   
   
   public void explode(Level var1, int var2, int var3, int var4) {}
   
   public void renderFullbright(Tesselator var1) {
	      float var2 = 0.5F;
	      float var3 = 0.5F;
	      float var4 = 0.6F;
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
   
   public void renderInside(Tesselator var1, int var2, int var3, int var4, int var5) {
	      int var6 = 35;
	      this.renderFace(var1, var2, var3, var4, var5);
	   }
   
   public void renderTnt1(Tesselator var1, int var2, int var3, int var4, int var5) {
	      int var6 = 35;
	      this.renderTnt2(var1, var2, var3, var4, var5);
	   }
   public void renderTnt2(Tesselator t, int x, int y, int z, int face) {
	      int tex = 35;
	      float u0 = (float)(tex % 16) / 16.0F;
	      float u1 = u0 + 0.0624375F;
	      float v0 = (float)(tex / 16) / 16.0F;
	      float v1 = v0 + 0.0624375F;
	      float x0 = (float)x + 0.0F;
	      float x1 = (float)x + 1.0F;
	      float y0 = (float)y + 0.0F;
	      float y1 = (float)y + 1.0F;
	      float z0 = (float)z + 0.0F;
	      float z1 = (float)z + 1.0F;
	      if (face == 0) {
	         t.vertexUV(x0, y0, z1, u0, v1);
	         t.vertexUV(x0, y0, z0, u0, v0);
	         t.vertexUV(x1, y0, z0, u1, v0);
	         t.vertexUV(x1, y0, z1, u1, v1);
	      }

	      if (face == 1) {
	         t.vertexUV(x1, y1, z1, u1, v1);
	         t.vertexUV(x1, y1, z0, u1, v0);
	         t.vertexUV(x0, y1, z0, u0, v0);
	         t.vertexUV(x0, y1, z1, u0, v1);
	      }

	      if (face == 2) {
	         t.vertexUV(x0, y1, z0, u1, v0);
	         t.vertexUV(x1, y1, z0, u0, v0);
	         t.vertexUV(x1, y0, z0, u0, v1);
	         t.vertexUV(x0, y0, z0, u1, v1);
	      }

	      if (face == 3) {
	         t.vertexUV(x0, y1, z1, u0, v0);
	         t.vertexUV(x0, y0, z1, u0, v1);
	         t.vertexUV(x1, y0, z1, u1, v1);
	         t.vertexUV(x1, y1, z1, u1, v0);
	      }

	      if (face == 4) {
	         t.vertexUV(x0, y1, z1, u1, v0);
	         t.vertexUV(x0, y1, z0, u0, v0);
	         t.vertexUV(x0, y0, z0, u0, v1);
	         t.vertexUV(x0, y0, z1, u1, v1);
	      }

	      if (face == 5) {
	         t.vertexUV(x1, y0, z1, u0, v1);
	         t.vertexUV(x1, y0, z0, u1, v1);
	         t.vertexUV(x1, y1, z0, u1, v0);
	         t.vertexUV(x1, y1, z1, u0, v0);
	      }

	   }
   
   public void tick(Level level, int x, int y, int z, Random random) {
   }

   public void destroy(Level level, int x, int y, int z, ParticleEngine particleEngine) {
      int SD = 4;

      for(int xx = 0; xx < SD; ++xx) {
         for(int yy = 0; yy < SD; ++yy) {
            for(int zz = 0; zz < SD; ++zz) {
               float xp = (float)x + ((float)xx + 0.5F) / (float)SD;
               float yp = (float)y + ((float)yy + 0.5F) / (float)SD;
               float zp = (float)z + ((float)zz + 0.5F) / (float)SD;
               particleEngine.add(new Particle(level, xp, yp, zp, xp - (float)x - 0.5F, yp - (float)y - 0.5F, zp - (float)z - 0.5F, this.tex));
            }
         }
      }

   }
   
   
}



