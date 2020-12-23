package com.mojang.minecraft.entity;

import com.mojang.minecraft.entity.Entity;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.particle.Particle;
import com.mojang.minecraft.particle.ParticleEngine;
import com.mojang.minecraft.phys.MathsHelper;
import java.util.Random;
import org.lwjgl.opengl.GL11;

public class PrimedTnt extends Entity {

   public static final long serialVersionUID = 0L;
   private float xd;
   private float yd;
   private float zd;
   public int life = 0;
   private boolean defused;
   private boolean isLiquid;
   private ParticleEngine particleEngine = new ParticleEngine(level, Minecraft.textures);


   public PrimedTnt(Level var1, float var2, float var3, float var4, boolean isliquidvar) {
      super(var1);
      isLiquid = isliquidvar;
      this.setSize(0.98F, 0.98F);
      this.heightOffset = this.bbHeight / 2.0F;
      this.setPos(var2, var3, var4);
      float var5 = (float)(Math.random() * 3.1415927410125732D * 2.0D);
      this.xd = -MathsHelper.sin(var5 * 3.1415927F / 180.0F) * 0.02F;
      this.yd = 0.2F;
      this.zd = -MathsHelper.cos(var5 * 3.1415927F / 180.0F) * 0.02F;
//      this.makeStepSound = false;
      this.life = 40;
      this.xo = var2;
      this.yo = var3;
      this.zo = var4;
   }

//   public void hurt(Entity var1, int var2) {
//	  if(Minecraft.networkManager != null){return;} 
//      if(!this.removed) {
//         super.hurt(var1, var2);
//         if(var1 instanceof Player) {
//            this.remove();
//            this.level.addEntity(new Item(this.level, this.x, this.y, this.z, Block.TNT.id, 0, 0, 0));
//         }
//
//      }
//   }

   public boolean isPickable() {
      return !this.removed;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.yd -= 0.04F;
      this.move(this.xd, this.yd, this.zd);
      this.xd *= 0.98F;
      this.yd *= 0.98F;
      this.zd *= 0.98F;
      if(this.onGround) {
         this.xd *= 0.7F;
         this.zd *= 0.7F;
         this.yd *= -0.5F;
      }

      if(!this.defused) {
         if(this.life-- > 0) {
//            this.level.particleEngine.spawnParticle(new SmokeParticle(this.level, this.x, this.y + 0.6F, this.z));
         } else {
            this.remove();
            Random var1 = new Random();
            float var2 = 4.0F;
            this.level.explode((Entity)null, this.x, this.y, this.z, var2);
            int SD = 4;
            for(int xx = 0; xx < SD; ++xx) {
                for(int yy = 0; yy < SD; ++yy) {
                   for(int zz = 0; zz < SD; ++zz) {
                      float xp = (float)x + ((float)xx + 0.5F) / (float)SD;
                      float yp = (float)y + ((float)yy + 0.5F) / (float)SD;
                      float zp = (float)z + ((float)zz + 0.5F) / (float)SD;
                      particleEngine.add(new Particle(level, xp, yp, zp, xp - (float)x - 0.5F, yp - (float)y - 0.5F, zp - (float)z - 0.5F, 35));
                   }
                }
            }
         }
      }
             }



   public void render(float a) {
	  com.mojang.minecraft.renderer.Textures var1 = new com.mojang.minecraft.renderer.Textures();
      int var3 = var1.loadTexture("/terrain.png", 9728);
      GL11.glBindTexture(3553, var3);
//      float var4 = this.level.getEntityBrightness(level, (int)this.x, (int)this.y, (int)this.z);
      GL11.glPushMatrix();
      GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
      GL11.glTranslatef(this.xo + (this.x - this.xo) * a - 0.5F, this.yo + (this.y - this.yo) * a - 0.5F, this.zo + (this.z - this.zo) * a - 0.5F);
      GL11.glPushMatrix();
      com.mojang.minecraft.renderer.Tesselator var5 = com.mojang.minecraft.renderer.Tesselator.instance;
      Tile.tnt.renderPreview(var5);
    	  
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, (float)((this.life / 4 + 1) % 2) * 0.4F);
      if(this.life <= 16) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, (float)((this.life + 1) % 2) * 0.6F);
      }

      if(this.life <= 2) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
      }

      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 1);
      Tile.tnt.renderPreview(var5);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2896);
      GL11.glPopMatrix();
      GL11.glPopMatrix();
   }
}
