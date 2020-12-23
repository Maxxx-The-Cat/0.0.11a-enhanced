package com.mojang.minecraft;

import com.mojang.minecraft.level.tile.Tile;
import java.util.ArrayList;
import java.util.List;

public final class AllowedTiles {

   public static List allowedTiles;
   public static List TileNames;
//   public String username;
//   public String sessionId;
//   public String mppass;
//   public boolean haspaid;


   public AllowedTiles() {
//      this.username = var1;
//      this.sessionId = var2;
   }

   static {
      (allowedTiles = new ArrayList()).add(Tile.rock);
      allowedTiles.add(Tile.grass);
      allowedTiles.add(Tile.dirt);
      allowedTiles.add(Tile.stoneBrick);
      allowedTiles.add(Tile.wood);
      allowedTiles.add(Tile.bush);
      allowedTiles.add(Tile.water);
      allowedTiles.add(Tile.calmWater);
      allowedTiles.add(Tile.lava);
      allowedTiles.add(Tile.calmLava);
      allowedTiles.add(Tile.glass);
      allowedTiles.add(Tile.rglass);
      allowedTiles.add(Tile.yglass);
      allowedTiles.add(Tile.gglass);
      allowedTiles.add(Tile.bglass);
      allowedTiles.add(Tile.blglass);
      allowedTiles.add(Tile.wbricks);
      allowedTiles.add(Tile.rbricks);
      allowedTiles.add(Tile.bbricks);
      allowedTiles.add(Tile.gbricks);
      allowedTiles.add(Tile.ybricks);
      allowedTiles.add(Tile.blbricks);
      allowedTiles.add(Tile.book);
      allowedTiles.add(Tile.moss);
      allowedTiles.add(Tile.wwool);
      allowedTiles.add(Tile.rwool);
      allowedTiles.add(Tile.ywool);
      allowedTiles.add(Tile.gwool);
      allowedTiles.add(Tile.bwool);
      allowedTiles.add(Tile.blwool);
      allowedTiles.add(Tile.wplanks);
      allowedTiles.add(Tile.rplanks);
      allowedTiles.add(Tile.yplanks);
      allowedTiles.add(Tile.gplanks);
      allowedTiles.add(Tile.bplanks);
      allowedTiles.add(Tile.blplanks);
      allowedTiles.add(Tile.bark);
      allowedTiles.add(Tile.log);
      allowedTiles.add(Tile.leaf);
      allowedTiles.add(Tile.redflower);
      allowedTiles.add(Tile.yellowflower);
      allowedTiles.add(Tile.cyanflower);
      allowedTiles.add(Tile.bottle);
      allowedTiles.add(Tile.beam);
      allowedTiles.add(Tile.box);
      allowedTiles.add(Tile.tnt);
      allowedTiles.add(Tile.rdcobble);
      allowedTiles.add(Tile.preclassicgrass);
      allowedTiles.add(Tile.sponge);
      allowedTiles.add(Tile.classicglass);
      
      System.out.println(allowedTiles.size());
   }
   static {
	      (TileNames = new ArrayList()).add("Rock");
	      TileNames.add("Grass");
	      TileNames.add("Dirt");
	      TileNames.add("Stone brick");
	      TileNames.add("Wood");
	      TileNames.add("Bush");
	      TileNames.add("Flowing water");
	      TileNames.add("Stationary water");
	      TileNames.add("Flowing lava");
	      TileNames.add("Stationary lava");
	      TileNames.add("Glass");
	      TileNames.add("Red stained glass"); 
	      TileNames.add("Yellow stained glass");
	      TileNames.add("Green stained glass");
	      TileNames.add("Blue stained glass");
	      TileNames.add("Black stained glass");
	      TileNames.add("White bricks");
	      TileNames.add("Red bricks");
	      TileNames.add("Blue bricks");
	      TileNames.add("Green bricks");
	      TileNames.add("Yellow bricks");
	      TileNames.add("Black bricks"); 
	      TileNames.add("Bookshelf");
	      TileNames.add("Mossy rock");
	      TileNames.add("White cloth");
	      TileNames.add("Red cloth");
	      TileNames.add("Yellow cloth");
	      TileNames.add("Green cloth");
	      TileNames.add("Blue cloth");
	      TileNames.add("Black cloth");
	      TileNames.add("White Planks");
	      TileNames.add("Red Planks");
	      TileNames.add("Yellow Planks");
	      TileNames.add("Green Planks");
	      TileNames.add("Blue Planks");
	      TileNames.add("Black Planks");
	      TileNames.add("Wood bark");
	      TileNames.add("Wood log");
	      TileNames.add("Leaves");
	      TileNames.add("Rose");
	      TileNames.add("Dandelion");
	      TileNames.add("Cyan Flower");
	      TileNames.add("Water bottle");
	      TileNames.add("Wooden beam");
	      TileNames.add("Crate");
	      TileNames.add("TNT");
	      TileNames.add("RubyDung Cobblestone");
	      TileNames.add("Pre-Classic Grass");
	      TileNames.add("Sponge");
	      TileNames.add("Classic Glass");
	      
	   }
   
}