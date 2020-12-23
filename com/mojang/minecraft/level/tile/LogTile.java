package com.mojang.minecraft.level.tile;

public class LogTile extends Tile {
   protected LogTile(int id, int tex) {
      super(id, tex);
   }
   
   protected final int getTexture(int var1) {
	      return var1 == 1?42:(var1 == 0?42:26);
	   }
}
