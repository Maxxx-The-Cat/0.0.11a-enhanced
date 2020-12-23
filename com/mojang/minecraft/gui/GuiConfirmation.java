package com.mojang.minecraft.gui;

import com.mojang.minecraft.AllowedTiles;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Gui;
import com.mojang.minecraft.level.tile.Bush;
import com.mojang.minecraft.level.tile.CrossTile;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.renderer.Tesselator;
import com.mojang.minecraft.renderer.Textures;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class GuiConfirmation extends Gui {

   public GuiConfirmation() {
      this.grabsMouse = false;
   }

   
   

   
   public final void render(int var1, int var2) {
	  drawFadingBox(0, 0, this.width, this.height, 1610941696, -1607454624);
      drawCenteredString(this.fontRenderer, "Are you sure you want to generate a new level? You'll lose all unsaved progress.", this.width / 2, 100, 16777215);
      drawCenteredString(this.fontRenderer, "Press ESC to cancel, press space to generate a new level", this.width / 2, 120, 16777215);
      if (Keyboard.getEventKey() == 57) {
    	  this.minecraft.newLevel();
      }

   }
   }
   

   


   

