package com.mojang.minecraft.level.tile;

public class NonSolidTile extends Tile {
   protected NonSolidTile(int id, int tex) {
      super(id, tex);
   }
   
   public boolean isSolid() {
	      return false;
     
   }
   
   public boolean blocksLight() {
	      return false;
	   }
}
