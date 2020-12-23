package com.mojang.minecraft.level;

import com.mojang.minecraft.entity.Entity;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.phys.AABB;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Level {
   private static final int TILE_UPDATE_INTERVAL = 400;
   public final int width;
   public final int height;
   public final int depth;
   public byte[] blocks;
   private int[] lightDepths;
   private ArrayList<LevelListener> levelListeners = new ArrayList();
   private Random random = new Random();
   int unprocessed = 0;

   public Level(int w, int h, int d) {
      this.width = w;
      this.height = h;
      this.depth = d;
      this.blocks = new byte[w * h * d];
      this.lightDepths = new int[w * h];
      boolean mapLoaded = this.load();
      if (!mapLoaded) {
         this.blocks = (new LevelGen(w, h, d)).generateMap();
      }

      this.calcLightDepths(0, 0, w, h);
   }

   public boolean load() {
      try {
         DataInputStream dis = new DataInputStream(new GZIPInputStream(new FileInputStream(new File("level.dat"))));
         dis.readFully(this.blocks);
         this.calcLightDepths(0, 0, this.width, this.height);

         for(int i = 0; i < this.levelListeners.size(); ++i) {
            ((LevelListener)this.levelListeners.get(i)).allChanged();
         }

         dis.close();
         return true;
      } catch (Exception var3) {
         var3.printStackTrace();
         return false;
      }
   }

   public void save() {
      try {
         DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(new File("level.dat"))));
         dos.write(this.blocks);
         dos.close();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void calcLightDepths(int x0, int y0, int x1, int y1) {
      for(int x = x0; x < x0 + x1; ++x) {
         for(int z = y0; z < y0 + y1; ++z) {
            int oldDepth = this.lightDepths[x + z * this.width];

            int y;
            for(y = this.depth - 1; y > 0 && !this.isLightBlocker(x, y, z); --y) {
            }

            this.lightDepths[x + z * this.width] = y;
            if (oldDepth != y) {
               int yl0 = oldDepth < y ? oldDepth : y;
               int yl1 = oldDepth > y ? oldDepth : y;

               for(int i = 0; i < this.levelListeners.size(); ++i) {
                  ((LevelListener)this.levelListeners.get(i)).lightColumnChanged(x, z, yl0, yl1);
               }
            }
         }
      }

   }

   public void addListener(LevelListener levelListener) {
      this.levelListeners.add(levelListener);
   }

   public void removeListener(LevelListener levelListener) {
      this.levelListeners.remove(levelListener);
   }

   public boolean isLightBlocker(int x, int y, int z) {
      Tile tile = Tile.tiles[this.getTile(x, y, z)];
      return tile == null ? false : tile.blocksLight();
   }
   
   public void explode(Entity var1, float var2, float var3, float var4, float var5) {
	      int var6 = (int)(var2 - var5 - 1.0F);
	      int var7 = (int)(var2 + var5 + 1.0F);
	      int var8 = (int)(var3 - var5 - 1.0F);
	      int var9 = (int)(var3 + var5 + 1.0F);
	      int var10 = (int)(var4 - var5 - 1.0F);
	      int var11 = (int)(var4 + var5 + 1.0F);

	      int var13;
	      float var15;
	      float var16;
	      for(int var12 = var6; var12 < var7; ++var12) {
	         for(var13 = var9 - 1; var13 >= var8; --var13) {
	            for(int var14 = var10; var14 < var11; ++var14) {
	               var15 = (float)var12 + 0.5F - var2;
	               var16 = (float)var13 + 0.5F - var3;
	               float var17 = (float)var14 + 0.5F - var4;
	               int var20;
	               if(var12 >= 0 && var13 >= 0 && var14 >= 0 && var12 < this.width && var13 < this.depth && var14 < this.height && var15 * var15 + var16 * var16 + var17 * var17 < var5 * var5 && (var20 = this.getTile(var12, var13, var14)) > 0 && Tile.tiles[var20].canExplode()) {
//	                  Block.blocks[var20].dropItems(this, var12, var13, var14, 0.3F);
	                  this.setTile(var12, var13, var14, 0);
	                  Tile.tiles[var20].explode(this, var12, var13, var14);
	               }
	            }
	         }
	      }
	      }

   public ArrayList<AABB> getCubes(AABB aABB) {
      ArrayList<AABB> aABBs = new ArrayList();
      int x0 = (int)aABB.x0;
      int x1 = (int)(aABB.x1 + 1.0F);
      int y0 = (int)aABB.y0;
      int y1 = (int)(aABB.y1 + 1.0F);
      int z0 = (int)aABB.z0;
      int z1 = (int)(aABB.z1 + 1.0F);
      if (x0 < 0) {
         x0 = 0;
      }

      if (y0 < 0) {
         y0 = 0;
      }

      if (z0 < 0) {
         z0 = 0;
      }

      if (x1 > this.width) {
         x1 = this.width;
      }

      if (y1 > this.depth) {
         y1 = this.depth;
      }

      if (z1 > this.height) {
         z1 = this.height;
      }

      for(int x = x0; x < x1; ++x) {
         for(int y = y0; y < y1; ++y) {
            for(int z = z0; z < z1; ++z) {
               Tile tile = Tile.tiles[this.getTile(x, y, z)];
               if (tile != null) {
                  AABB aabb = tile.getAABB(x, y, z);
                  if (aabb != null) {
                     aABBs.add(aabb);
                  }
               }
            }
         }
      }

      return aABBs;
   }

   public boolean setTile(int x, int y, int z, int type) {
      if (x >= 0 && y >= 0 && z >= 0 && x < this.width && y < this.depth && z < this.height) {
         if (type == this.blocks[(y * this.height + z) * this.width + x]) {
            return false;
         } else {
            this.blocks[(y * this.height + z) * this.width + x] = (byte)type;
            this.calcLightDepths(x, z, 1, 1);

            for(int i = 0; i < this.levelListeners.size(); ++i) {
               ((LevelListener)this.levelListeners.get(i)).tileChanged(x, y, z);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean isLit(int x, int y, int z) {
      if (x >= 0 && y >= 0 && z >= 0 && x < this.width && y < this.depth && z < this.height) {
         return y >= this.lightDepths[x + z * this.width];
      } else {
         return true;
      }
   }

   public int getTile(int x, int y, int z) {
      return x >= 0 && y >= 0 && z >= 0 && x < this.width && y < this.depth && z < this.height ? this.blocks[(y * this.height + z) * this.width + x] : 0;
   }

   public boolean isSolidTile(int x, int y, int z) {
      Tile tile = Tile.tiles[this.getTile(x, y, z)];
      return tile == null ? false : tile.isSolid();
   }

   public void tick() {
      this.unprocessed += this.width * this.height * this.depth;
      int ticks = this.unprocessed / 400;
      this.unprocessed -= ticks * 400;

      for(int i = 0; i < ticks; ++i) {
         int x = this.random.nextInt(this.width);
         int y = this.random.nextInt(this.depth);
         int z = this.random.nextInt(this.height);
         Tile tile = Tile.tiles[this.getTile(x, y, z)];
         if (tile != null) {
            tile.tick(this, x, y, z, this.random);
         }
      }

   }

public void setTileNoUpdate(int x, int y, int z, int calmTileId) {
	// TODO Auto-generated method stub
	
}
}
