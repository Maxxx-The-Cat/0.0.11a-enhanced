package com.mojang.minecraft.gui;

import com.mojang.minecraft.AllowedTiles;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Gui;
import com.mojang.minecraft.level.tile.Bush;
import com.mojang.minecraft.level.tile.CrossTile;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.renderer.Tesselator;
import com.mojang.minecraft.renderer.Textures;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class GuiInventory extends Gui {

   public GuiInventory() {
      this.grabsMouse = false;
   }

   private int getBlockOnScreen(int var1, int var2) {
	      for(int var3 = 0; var3 < AllowedTiles.allowedTiles.size(); ++var3) {
	         int var4 = this.width / 2 + var3 % 9 * 24 + -108 - 3;
	         int var5 = this.height / 2 + var3 / 9 * 24 + -60 + 3;
	         if(var1 >= var4 && var1 <= var4 + 24 && var2 >= var5 - 12 && var2 <= var5 + 12) {
	            return var3;
	         }
	      }
		

	      return -1;
	   }
   

   
   public final void render(int var1, int var2) {
      var1 = this.getBlockOnScreen(var1, var2);
      drawFadingBox(this.width / 2 - 120, 30, this.width / 2 + 120, 200, -1878719232, -1070583712);
      if(var1 >= 0) {
         var2 = this.width / 2 + var1 % 9 * 24 + -108;
         int var3 = this.height / 2 + var1 / 9 * 24 + -60;
         drawFadingBox(var2 - 3, var3 - 8, var2 + 23, var3 + 24 - 6, -1862270977, -1056964609);
      }

      drawCenteredString(this.fontRenderer, "Select tile", this.width / 2, 35, 16777215);
//      System.out.println(Mouse.getEventX() * this.width / this.minecraft.width);
//      System.out.println(Mouse.getEventY() * this.height / this.minecraft.height - 1);
      if(var1 != -1){
//      if(minecraft.networkManager == null || (minecraft.player.userType >= 100)){ 
      drawCenteredString(this.fontRenderer, (String)(AllowedTiles.TileNames.get(var1)), this.width / 2, 45, 16777215);
//      }else{
//      drawCenteredString(this.fontRenderer, (String)(SessionData.blockNames.get(var1)) + " (allowed: " + minecraft.isAllowedForNonAdmins(var1) + ")", this.width / 2, 45, 16777215);	 
//      }
      }
      Textures var7 = this.minecraft.textures;
      Tesselator var8 = Tesselator.instance;
      var2 = var7.loadTexture("/terrain.png", 9728);
      GL11.glBindTexture(3553, var2);
      for(var2 = 0; var2 < AllowedTiles.allowedTiles.size(); ++var2) {
          Tile var4 = (Tile)AllowedTiles.allowedTiles.get(var2);
          GL11.glPushMatrix();
          int var5 = this.width / 2 + var2 % 9 * 24 + -108;
          int var6 = this.height / 2 + var2 / 9 * 24 + -60;
          GL11.glTranslatef((float)var5, (float)var6, 0.0F);
          GL11.glScalef(10.0F, 10.0F, 10.0F);
          GL11.glTranslatef(1.0F, 0.5F, 8.0F);
          if(!(var4 instanceof Bush) && !(var4 instanceof CrossTile)){
             GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
             GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
          } else {
             GL11.glScalef(1.7F, 1.7F, 1.7F);
          }

          if(var1 == var2) {
             GL11.glScalef(1.6F, 1.6F, 1.6F);
          }

          GL11.glTranslatef(-1.5F, 0.5F, 0.5F);
          GL11.glScalef(-1.0F, -1.0F, -1.0F);
         GL11.glEnable(3553);
          GL11.glEnable(3042);
          GL11.glBlendFunc(770, 771);
          var8.init();
          if(!(var4 instanceof Bush) && !(var4 instanceof CrossTile)){
             var4.renderFullbright(var8);

          } else {
             var4.renderItem(var8);
          }

          var8.flush();
          GL11.glDisable(3042);
          GL11.glEnable(3553);
          GL11.glPopMatrix();
       }
   }
   



	   protected final void onMouseClick(int var1, int var2, int var3) {
	       if ((this.getBlockOnScreen(var1, var2) + 1) != -1 && (this.getBlockOnScreen(var1, var2) + 1) != 0){
	  	       Tile newBlock = (Tile)AllowedTiles.allowedTiles.get(this.getBlockOnScreen(var1, var2));
	  		   Minecraft.paintTexture = newBlock.id;
	           this.minecraft.setCurrentScreen((Gui)null);
	       } else {
	           this.minecraft.setCurrentScreen((Gui)null);
	       }
	   }
}

