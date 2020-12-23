package com.mojang.minecraft;

import com.mojang.minecraft.entity.Entity;
import com.mojang.minecraft.entity.Player;
import com.mojang.minecraft.entity.Zombie;
import com.mojang.minecraft.gui.Font;
import com.mojang.minecraft.gui.Gui;
import com.mojang.minecraft.gui.GuiConfirmation;
import com.mojang.minecraft.gui.GuiInventory;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.level.Chunk;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.LevelGen;
import com.mojang.minecraft.level.LevelRenderer;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.particle.ParticleEngine;
import com.mojang.minecraft.phys.AABB;
import com.mojang.minecraft.renderer.Frustum;
import com.mojang.minecraft.renderer.Tesselator;
import com.mojang.minecraft.renderer.Textures;
import java.awt.Canvas;
import java.awt.Component;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Minecraft implements Runnable {
   public static final String VERSION_STRING = "0.0.11a-enhanced";
   private boolean fullscreen = false;
   public int width;
   public int height;
   public AllowedTiles initInventory;
   private FloatBuffer fogColor0 = BufferUtils.createFloatBuffer(4);
   private FloatBuffer fogColor1 = BufferUtils.createFloatBuffer(4);
   private Timer timer = new Timer(20.0F);
   public static Level level;
   private LevelRenderer levelRenderer;
   private Player player;
   public static int paintTexture = 1;
   private ParticleEngine particleEngine;
   public static ArrayList<Entity> entities = new ArrayList();
   private Canvas parent;
   public boolean appletMode = false;
   public volatile boolean pause = false;
   private Cursor emptyCursor;
   private int yMouseAxis = 1;
   public static Textures textures;
   public Font font;
   private Screen screen;
   private int editMode = 0;
   private volatile boolean running = false;
   private String fpsString = "";
   private boolean mouseGrabbed = false;
   private IntBuffer viewportBuffer = BufferUtils.createIntBuffer(16);
   private IntBuffer selectBuffer = BufferUtils.createIntBuffer(2000);
   private HitResult hitResult = null;
   public Gui currentScreen = null;
   FloatBuffer lb = BufferUtils.createFloatBuffer(16);

   public Minecraft(Canvas parent, int width, int height, boolean fullscreen) {
      this.parent = parent;
      this.width = width;
      this.height = height;
      this.fullscreen = fullscreen;
      this.textures = new Textures();
   }

   public void init() throws LWJGLException, IOException {
      int col0 = 16710650;
      int col1 = 920330;
      float fr = 0.5F;
      float fg = 0.8F;
      float fb = 1.0F;
      this.fogColor0.put(new float[]{(float)(col0 >> 16 & 255) / 255.0F, (float)(col0 >> 8 & 255) / 255.0F, (float)(col0 & 255) / 255.0F, 1.0F});
      this.fogColor0.flip();
      this.fogColor1.put(new float[]{(float)(col1 >> 16 & 255) / 255.0F, (float)(col1 >> 8 & 255) / 255.0F, (float)(col1 & 255) / 255.0F, 1.0F});
      this.fogColor1.flip();
      if (this.parent != null) {
         Display.setParent(this.parent);
      } else if (this.fullscreen) {
         Display.setFullscreen(true);
         this.width = Display.getDisplayMode().getWidth();
         this.height = Display.getDisplayMode().getHeight();
      } else {
         Display.setDisplayMode(new DisplayMode(this.width, this.height));
      }

      Display.setTitle("Minecraft 0.0.11a-enhanced by Maxxx aka TheluckyMC");

      try {
         Display.create();
      } catch (LWJGLException var10) {
         var10.printStackTrace();

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var9) {
         }

         Display.create();
      }

      Keyboard.create();
      Mouse.create();
      this.checkGlError("Pre startup");
      GL11.glEnable(3553);
      GL11.glShadeModel(7425);
      GL11.glClearColor(fr, fg, fb, 0.0F);
      GL11.glClearDepth(1.0D);
      GL11.glEnable(2929);
      GL11.glDepthFunc(515);
      GL11.glEnable(3008);
      GL11.glAlphaFunc(516, 0.0F);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glMatrixMode(5888);
      this.checkGlError("Startup");
      this.level = new Level(256, 256, 256); // X Z Y format
      this.levelRenderer = new LevelRenderer(this.level, this.textures);
      this.player = new Player(this.level);
      this.particleEngine = new ParticleEngine(this.level, this.textures);
      this.font = new Font("/default.gif", this.textures);
      this.screen = new Screen();

      for(int i = 0; i < 10; ++i) {
         Zombie zombie = new Zombie(this.level, this.textures, 128.0F, 0.0F, 128.0F);
         zombie.resetPos();
         this.entities.add(zombie);
      }

      IntBuffer imgData = BufferUtils.createIntBuffer(256);
      imgData.clear().limit(256);
      if (this.appletMode) {
         try {
            this.emptyCursor = new Cursor(16, 16, 0, 0, 1, imgData, (IntBuffer)null);
         } catch (LWJGLException var8) {
            var8.printStackTrace();
         }
      } else {
         this.grabMouse();
      }

      this.checkGlError("Post startup");
   }

   private void checkGlError(String string) {
      int errorCode = GL11.glGetError();
      if (errorCode != 0) {
         String errorString = GLU.gluErrorString(errorCode);
         System.out.println("########## GL ERROR ##########");
         System.out.println("@ " + string);
         System.out.println(errorCode + ": " + errorString);
         System.exit(0);
      }

   }

   public void destroy() {
      try {
         this.level.save();
      } catch (Exception var2) {
      }

      Mouse.destroy();
      Keyboard.destroy();
      Display.destroy();
   }
   
   public void newLevel(){
	   	  this.setCurrentScreen(null);
	      this.level = new Level(256, 256, 256); // X Z Y format
	      this.level.blocks = (new LevelGen(this.level.width, this.level.height, this.level.depth)).generateMap();
	      this.level.calcLightDepths(0, 0, level.width, level.height);
	      this.entities = new ArrayList();
	      this.player = new Player(this.level);

	      

	      for(int i = 0; i < 10; ++i) {
	         Zombie zombie = new Zombie(this.level, this.textures, 128.0F, 0.0F, 128.0F);
	         zombie.resetPos();
	         this.entities.add(zombie);
	      }
	      this.levelRenderer = new LevelRenderer(this.level, this.textures);
   }

   public void run() {
      this.running = true;

      try {
         this.init();
      } catch (Exception var9) {
         JOptionPane.showMessageDialog((Component)null, var9.toString(), "Failed to start Minecraft", 0);
         return;
      }

      long lastTime = System.currentTimeMillis();
      int frames = 0;

      try {
         while(this.running) {
            if (this.pause) {
               Thread.sleep(100L);
            } else {
               if (this.parent == null && Display.isCloseRequested()) {
                  this.stop();
               }

               this.timer.advanceTime();

               for(int i = 0; i < this.timer.ticks; ++i) {
                  this.tick();
               }

               this.checkGlError("Pre render");
               this.render(this.timer.a);
               this.checkGlError("Post render");
               ++frames;

               while(System.currentTimeMillis() >= lastTime + 1000L) {
                  this.fpsString = frames + " fps, " + Chunk.updates + " chunk updates";
                  Chunk.updates = 0;
                  lastTime += 1000L;
                  frames = 0;
               }
            }
         }
      } catch (Exception var10) {
         var10.printStackTrace();
      } finally {
         this.destroy();
      }

   }

   public void stop() {
      this.running = false;
   }

   public void grabMouse() {
      if (!this.mouseGrabbed) {
         this.mouseGrabbed = true;
         if (this.appletMode) {
            try {
               Mouse.setNativeCursor(this.emptyCursor);
               Mouse.setCursorPosition(this.width / 2, this.height / 2);
            } catch (LWJGLException var2) {
               var2.printStackTrace();
            }
         } else {
            Mouse.setGrabbed(true);
         }

      }
   }
   
   public final void setCurrentScreen(Gui var1) {
//	      if(!(this.currentScreen instanceof ErrorScreen)) {
//
//	         }

//	         if(var1 == null && this.player.health <= 0) {
//	        	 if(this.networkManager == null){
//	            var1 = new GameOverScreen();
//	        	 }
//	         }

	         this.currentScreen = (Gui)var1;
	         if(var1 != null) {
	            if(this.mouseGrabbed) {
//	               this.player.releaseAllKeys();
	               this.mouseGrabbed = false;
//	               if(this.levelLoaded) {
//	                  try {
//	                     Mouse.setNativeCursor((Cursor)null);
//	                  } catch (LWJGLException var4) {
//	                     var4.printStackTrace();
//	                  }
//	               } else {
//	                  Mouse.setGrabbed(false);
//	               }
	            }

	            int var2 = 854;
	            int var3 = 480;
	            try
	            {
	            var2 = this.width * 240 / this.height;
	            var3 = this.height * 240 / this.height;
	            } catch(Exception e)
	            {
	            	System.out.println("Wrong window size!");
	            }
	            ((Gui)var1).open(this, var2, var3);
//	            this.online = false;
	         } else {
	            this.grabMouse();
	         }
	      }
	 

   public void releaseMouse() {
//      if (this.mouseGrabbed) {
         this.mouseGrabbed = false;
         if (this.appletMode) {
            try {
               Mouse.setNativeCursor((Cursor)null);
            } catch (LWJGLException var2) {
               var2.printStackTrace();
            }
         } else {
            Mouse.setGrabbed(false);
         }

      }
//   }

   private void handleMouseClick() {
      if (this.editMode == 0) {
         if (this.hitResult != null) {
            Tile oldTile = Tile.tiles[this.level.getTile(this.hitResult.x, this.hitResult.y, this.hitResult.z)];
            boolean changed = this.level.setTile(this.hitResult.x, this.hitResult.y, this.hitResult.z, 0);
            if (oldTile != null && changed) {
               oldTile.destroy(this.level, this.hitResult.x, this.hitResult.y, this.hitResult.z, this.particleEngine);
            }
         }
      } else if (this.hitResult != null) {
         int x = this.hitResult.x;
         int y = this.hitResult.y;
         int z = this.hitResult.z;
         if (this.hitResult.f == 0) {
            --y;
         }

         if (this.hitResult.f == 1) {
            ++y;
         }

         if (this.hitResult.f == 2) {
            --z;
         }

         if (this.hitResult.f == 3) {
            ++z;
         }

         if (this.hitResult.f == 4) {
            --x;
         }

         if (this.hitResult.f == 5) {
            ++x;
         }

         AABB aabb = Tile.tiles[this.paintTexture].getAABB(x, y, z);
         if (aabb == null || this.isFree(aabb)) {
            this.level.setTile(x, y, z, this.paintTexture);
         }
      }

   }

   public void tick() {
      while(Mouse.next()) {
         if (!this.mouseGrabbed && Mouse.getEventButtonState()) {
            this.grabMouse();
         } else {
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
               this.handleMouseClick();
            }

            if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
               this.editMode = (this.editMode + 1) % 2;
            }
         }
         
      }

      while(true) {
         do {
            if (!Keyboard.next()) {
               this.level.tick();
               this.particleEngine.tick();

               for(int i = 0; i < this.entities.size(); ++i) {
                  ((Entity)this.entities.get(i)).tick();
                  if (((Entity)this.entities.get(i)).removed) {
                     this.entities.remove(i--);
                  }
               }

               this.player.tick();
               return;
            }
            if(currentScreen != null){
                this.currentScreen.doInput();
                this.currentScreen.tick();
             }
         } while(!Keyboard.getEventKeyState());
//block select here
         if (Keyboard.getEventKey() == 1 && (this.appletMode || !this.fullscreen)) {
            this.releaseMouse();
         }
         if (Keyboard.getEventKey() == 28) {
             this.level.save();
          }


//         if (Keyboard.getEventKey() == 28) {
//            this.level.save();
//         }
//
//         if (Keyboard.getEventKey() == 2) {
//            this.paintTexture = 1;
//         }
//
//         if (Keyboard.getEventKey() == 3) {
//            this.paintTexture = 3;
//         }
//
//         if (Keyboard.getEventKey() == 4) {
//            this.paintTexture = 4;
//         }
//
//         if (Keyboard.getEventKey() == 5) {
//            this.paintTexture = 5;
//         }
//
//         if (Keyboard.getEventKey() == 6) {
//            this.paintTexture = 6;
//         }
//         
//         if (Keyboard.getEventKey() == 7) {
//             this.paintTexture = 8;
//          }
//         
//         if (Keyboard.getEventKey() == 8) {
//             this.paintTexture = 10;
//          }
//         
//         if (Keyboard.getEventKey() == 9) {
//             this.paintTexture = 12;
//          }
//         //start of keyboard blocks
//         if (Keyboard.getEventKey() == 44) {
//             this.paintTexture = 13;
//          }
//         
//         if (Keyboard.getEventKey() == 45) {
//             this.paintTexture = 14;
//          }
//         
//         if (Keyboard.getEventKey() == 46) {
//             this.paintTexture = 16;
//          }
//         
//         if (Keyboard.getEventKey() == 47) {
//             this.paintTexture = 17;
//          }
//         
         if (Keyboard.getEventKey() == 48) {
        	 this.setCurrentScreen(new GuiInventory());
          }
//         
         if (Keyboard.getEventKey() == 49) {
        	 this.setCurrentScreen(new GuiConfirmation());
          }
//         
//         if (Keyboard.getEventKey() == 50) {
//             this.paintTexture = 20;
//          }
//         if (Keyboard.getEventKey() == 35) {
//             this.paintTexture = 21;
//          }
//         
//         if (Keyboard.getEventKey() == 36) {
//             this.paintTexture = 22;
//          }
//         
//         if (Keyboard.getEventKey() == 33) {
//             this.paintTexture = 23;
//          }
//         
//         if (Keyboard.getEventKey() == 37) {
//             this.paintTexture = 24;
//          }
//         
//         if (Keyboard.getEventKey() == 38) {
//             this.paintTexture = 25;
//          }
//         
//         if (Keyboard.getEventKey() == 18) {
//             this.paintTexture = 26;
//          }
//         
//         if (Keyboard.getEventKey() == 20) {
//             this.paintTexture = 27;
//          }
//         
//         if (Keyboard.getEventKey() == 22) {
//             this.paintTexture = 28;
//          }
         
     
         
       
         if (Keyboard.getEventKey() == 21) {
            this.yMouseAxis *= -1;
         }

         if (Keyboard.getEventKey() == 34) {
            this.entities.add(new Zombie(this.level, this.textures, this.player.x, this.player.y, this.player.z));
         }
      }
   }

   private boolean isFree(AABB aabb) {
      if (this.player.bb.intersects(aabb)) {
         return false;
      } else {
         for(int i = 0; i < this.entities.size(); ++i) {
            if (((Entity)this.entities.get(i)).bb.intersects(aabb)) {
               return false;
            }
         }

         return true;
      }
   }

   private void moveCameraToPlayer(float a) {
      GL11.glTranslatef(0.0F, 0.0F, -0.3F);
      GL11.glRotatef(this.player.xRot, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(this.player.yRot, 0.0F, 1.0F, 0.0F);
      float x = this.player.xo + (this.player.x - this.player.xo) * a;
      float y = this.player.yo + (this.player.y - this.player.yo) * a;
      float z = this.player.zo + (this.player.z - this.player.zo) * a;
      GL11.glTranslatef(-x, -y, -z);
   }

   private void setupCamera(float a) {
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GLU.gluPerspective(70.0F, (float)this.width / (float)this.height, 0.05F, 1000.0F);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      this.moveCameraToPlayer(a);
   }

   private void setupPickCamera(float a, int x, int y) {
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      this.viewportBuffer.clear();
      GL11.glGetInteger(2978, this.viewportBuffer);
      this.viewportBuffer.flip();
      this.viewportBuffer.limit(16);
      GLU.gluPickMatrix((float)x, (float)y, 5.0F, 5.0F, this.viewportBuffer);
      GLU.gluPerspective(70.0F, (float)this.width / (float)this.height, 0.05F, 1000.0F);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      this.moveCameraToPlayer(a);
   }

   private void pick(float a) {
      this.selectBuffer.clear();
      GL11.glSelectBuffer(this.selectBuffer);
      GL11.glRenderMode(7170);
      this.setupPickCamera(a, this.width / 2, this.height / 2);
      this.levelRenderer.pick(this.player, Frustum.getFrustum());
      int hits = GL11.glRenderMode(7168);
      this.selectBuffer.flip();
      this.selectBuffer.limit(this.selectBuffer.capacity());
      long closest = 0L;
      int[] names = new int[10];
      int hitNameCount = 0;

      for(int i = 0; i < hits; ++i) {
         int nameCount = this.selectBuffer.get();
         long minZ = (long)this.selectBuffer.get();
         this.selectBuffer.get();
         int j;
         if (minZ >= closest && i != 0) {
            for(j = 0; j < nameCount; ++j) {
               this.selectBuffer.get();
            }
         } else {
            closest = minZ;
            hitNameCount = nameCount;

            for(j = 0; j < nameCount; ++j) {
               names[j] = this.selectBuffer.get();
            }
         }
      }

      if (hitNameCount > 0) {
         this.hitResult = new HitResult(names[0], names[1], names[2], names[3], names[4]);
      } else {
         this.hitResult = null;
      }

   }

   public void render(float a) {
      if (!Display.isActive()) {
         this.releaseMouse();
      }

      GL11.glViewport(0, 0, this.width, this.height);
      if (this.mouseGrabbed) {
         float xo = 0.0F;
         float yo = 0.0F;
         xo = (float)Mouse.getDX();
         yo = (float)Mouse.getDY();
         if (this.appletMode) {
            Display.processMessages();
            Mouse.poll();
            xo = (float)(Mouse.getX() - this.width / 2);
            yo = (float)(Mouse.getY() - this.height / 2);
            Mouse.setCursorPosition(this.width / 2, this.height / 2);
         }

         this.player.turn(xo, yo * (float)this.yMouseAxis);
      }

      this.checkGlError("Set viewport");
      this.pick(a);
      this.checkGlError("Picked");
      GL11.glClear(16640);
      this.setupCamera(a);
      this.checkGlError("Set up camera");
      GL11.glEnable(2884);
      Frustum frustum = Frustum.getFrustum();
      this.levelRenderer.updateDirtyChunks(this.player);
      this.checkGlError("Update chunks");
      this.setupFog(0);
      GL11.glEnable(2912);
      this.levelRenderer.render(this.player, 0);
      this.checkGlError("Rendered level");

      Entity zombie;
      int i;
      for(i = 0; i < this.entities.size(); ++i) {
         zombie = (Entity)this.entities.get(i);
         if (zombie.isLit() && frustum.isVisible(zombie.bb)) {
            ((Entity)this.entities.get(i)).render(a);
         }
      }

      this.checkGlError("Rendered entities");
      this.particleEngine.render(this.player, a, 0);
      this.checkGlError("Rendered particles");
      this.setupFog(1);
      this.levelRenderer.render(this.player, 1);

      for(i = 0; i < this.entities.size(); ++i) {
         zombie = (Entity)this.entities.get(i);
         if (!zombie.isLit() && frustum.isVisible(zombie.bb)) {
            ((Entity)this.entities.get(i)).render(a);
         }
      }

      this.particleEngine.render(this.player, a, 1);
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glDisable(2912);
      this.checkGlError("Rendered rest");
      if (this.hitResult != null) {
         GL11.glDisable(3008);
         this.levelRenderer.renderHit(this.hitResult, this.editMode, this.paintTexture);
         GL11.glEnable(3008);
      }

      this.checkGlError("Rendered hit");
      this.drawGui(a);
      this.checkGlError("Rendered gui");
      Display.update();
   }

   private void drawGui(float a) {
      int screenWidth = this.width * 240 / this.height;
      int screenHeight = this.height * 240 / this.height;
      GL11.glClear(256);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, (double)screenWidth, (double)screenHeight, 0.0D, 100.0D, 300.0D);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -200.0F);
      this.checkGlError("GUI: Init");
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(screenWidth - 16), 16.0F, 0.0F);
      Tesselator t = Tesselator.instance;
      GL11.glScalef(16.0F, 16.0F, 16.0F);
      GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-1.5F, 0.5F, -0.5F);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      int id = this.textures.loadTexture("/terrain.png", 9728);
      GL11.glBindTexture(3553, id);
      GL11.glEnable(3553);
      t.init();
      Tile.tiles[this.paintTexture].render(t, this.level, 0, -2, 0, 0);
      t.flush();
      GL11.glDisable(3553);
      GL11.glPopMatrix();
      this.checkGlError("GUI: Draw selected");
      this.font.drawShadow("0.0.11a-enhanced", 2, 2, 16777215);
      this.font.drawShadow(this.fpsString, 2, 12, 16777215);
      this.font.drawShadow("Build 6", 2, 22, 16777215);
      if(mouseGrabbed){
    	this.currentScreen = null;
      }
      if(currentScreen != null){
         int var82 = this.width * 240 / this.height;
         int var86 = this.height * 240 / this.height;
    	 this.currentScreen.render(Mouse.getX() * var82 / this.width, var86 - Mouse.getY() * var86 / this.height - 1);  
    	 if(currentScreen != null){
         this.currentScreen.doInput();
    	 }
//    	  System.out.println(Mouse.getX());
      }
      
//      this.screen.drawFadingBox(0, 0, this.width, this.height, 1610941696, -1607454624);
      this.checkGlError("GUI: Draw text");
      int wc = screenWidth / 2;
      int hc = screenHeight / 2;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      t.init();
      t.vertex((float)(wc + 1), (float)(hc - 4), 0.0F);
      t.vertex((float)(wc - 0), (float)(hc - 4), 0.0F);
      t.vertex((float)(wc - 0), (float)(hc + 5), 0.0F);
      t.vertex((float)(wc + 1), (float)(hc + 5), 0.0F);
      t.vertex((float)(wc + 5), (float)(hc - 0), 0.0F);
      t.vertex((float)(wc - 4), (float)(hc - 0), 0.0F);
      t.vertex((float)(wc - 4), (float)(hc + 1), 0.0F);
      t.vertex((float)(wc + 5), (float)(hc + 1), 0.0F);
      t.flush();
      this.checkGlError("GUI: Draw crosshair");
   }

   private void setupFog(int i) {
      if (i == 0) {
         GL11.glFogi(2917, 2048);
         GL11.glFogf(2914, 0.001F);
         GL11.glFog(2918, this.fogColor0);
         GL11.glDisable(2896);
      } else if (i == 1) {
         GL11.glFogi(2917, 2048);
         GL11.glFogf(2914, 0.01F);
         GL11.glFog(2918, this.fogColor1);
         GL11.glEnable(2896);
         GL11.glEnable(2903);
         float br = 0.6F;
         GL11.glLightModel(2899, this.getBuffer(br, br, br, 1.0F));
      }

   }

   private FloatBuffer getBuffer(float a, float b, float c, float d) {
      this.lb.clear();
      this.lb.put(a).put(b).put(c).put(d);
      this.lb.flip();
      return this.lb;
   }

   public static void checkError() {
      int e = GL11.glGetError();
      if (e != 0) {
         throw new IllegalStateException(GLU.gluErrorString(e));
      }
   }

   public static void main(String[] args) throws LWJGLException {
      Minecraft minecraft = new Minecraft((Canvas)null, 854, 480, false);
      (new Thread(minecraft)).start();
   }
}
