package com.mojang.minecraft.gui;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Font;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.level.Level;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Gui extends Screen {

   protected Minecraft minecraft;
   protected int width;
   protected int height;
//   protected List buttons = new ArrayList();
   public boolean grabsMouse = false;
   protected Font fontRenderer;


   public void render(int var1, int var2) {
//      for(int var3 = 0; var3 < this.buttons.size(); ++var3) {
//         Button var4 = (Button)this.buttons.get(var3);
//         Minecraft var7 = this.minecraft;
//         Button var4 = var10000;
//         if(var4.visible) {
//            Font var8 = var7.font;
//            GL11.glBindTexture(3553, var7.textureManager.load("/gui/gui.png"));
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//            byte var9 = 1;
//            boolean var6 = var1 >= var4.x && var2 >= var4.y && var1 < var4.x + var4.width && var2 < var4.y + var4.height;
//            if(!var4.active) {
//               var9 = 0;
//            } else if(var6) {
//               var9 = 2;
//            }
//
//            var4.drawImage(var4.x, var4.y, 0, 46 + var9 * 20, var4.width / 2, var4.height);
//            var4.drawImage(var4.x + var4.width / 2, var4.y, 200 - var4.width / 2, 46 + var9 * 20, var4.width / 2, var4.height);
//            if(!var4.active) {
//               Button.drawCenteredString(var8, var4.text, var4.x + var4.width / 2, var4.y + (var4.height - 8) / 2, -6250336);
//            } else if(var6) {
//               Button.drawCenteredString(var8, var4.text, var4.x + var4.width / 2, var4.y + (var4.height - 8) / 2, 16777120);
//            } else {
//               Button.drawCenteredString(var8, var4.text, var4.x + var4.width / 2, var4.y + (var4.height - 8) / 2, 14737632);
//            }
//         }
//      }

   }

   protected void onKeyPress(char var1, int var2) {
      if(var2 == 1) {
         this.minecraft.setCurrentScreen((Gui)null);
         this.minecraft.grabMouse();
      }

   }

   protected void onMouseClick(int var1, int var2, int var3) {
//      if(var3 == 0) {
//         for(var3 = 0; var3 < this.buttons.size(); ++var3) {
//            Button var4;
//            Button var7;
//            if((var7 = var4 = (Button)this.buttons.get(var3)).active && var1 >= var7.x && var2 >= var7.y && var1 < var7.x + var7.width && var2 < var7.y + var7.height) {
//               this.onButtonClick(var4);
//               if(minecraft.level != null){
//               minecraft.level.playSound("random.click", minecraft.player.x, minecraft.player.y, minecraft.player.z, 1.0F, 1.0f);
//               }
//            }
//            }
//         }
      }

   

//   protected void onButtonClick(Button var1) {}

   public final void open(Minecraft var1, int var2, int var3) {
      this.minecraft = var1;
      this.fontRenderer = var1.font;
      this.width = var2;
      this.height = var3;
      this.onOpen();
   }

   public void onOpen() {
	   this.minecraft.releaseMouse();
   }

   public final void doInput() {
      while(Mouse.next()) {
         this.mouseEvent();
      }

      while(Keyboard.next()) {
         this.keyboardEvent();
      }

   }
   public static String getClipboardString()
   {
       try
       {
           java.awt.datatransfer.Transferable transferable = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
           if(transferable != null && transferable.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor))
           {
               String s = (String)transferable.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
               return s;
           }
       }
       catch(Exception exception) { }
       return null;
   }
   
   public void selectNextField()
   {
   }
   
   public final void mouseEvent() {
      if(Mouse.getEventButtonState()) {
         int var1 = Mouse.getEventX() * this.width / this.minecraft.width;
         int var2 = this.height - Mouse.getEventY() * this.height / this.minecraft.height - 1;
         this.onMouseClick(var1, var2, Mouse.getEventButton());
      }

   }

   public final void keyboardEvent() {
      if(Keyboard.getEventKeyState()) {
         this.onKeyPress(Keyboard.getEventCharacter(), Keyboard.getEventKey());
      }

   }
   
//   public final void setWorldAndResolution(final Minecraft d, final int integer2, final int integer3) {
//       this.minecraft = d;
//       this.fontRenderer = d.fontRenderer;
//       this.width = integer2;
//       this.height = integer3;
//       this.initGui();
//   }

   public void initGui() {
   }
   
   public void tick() {}

   public void onClose() {}
}
