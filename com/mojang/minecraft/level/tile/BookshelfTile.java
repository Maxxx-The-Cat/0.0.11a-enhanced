package com.mojang.minecraft.level.tile;

public class BookshelfTile extends Tile {
   protected BookshelfTile(int id, int tex) {
      super(id, tex);
   }
   
   protected final int getTexture(int var1) {
	      return var1 <= 1?4:this.tex;
	   }
}
