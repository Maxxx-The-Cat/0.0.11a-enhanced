package com.mojang.minecraft.level.tile;

import java.util.Random;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.entity.PrimedTnt;
import com.mojang.minecraft.entity.Zombie;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.particle.ParticleEngine;

public class TntTile extends Tile {
   protected TntTile(int id, int tex) {
      super(id, tex);
      Random random = new Random();
   }
   
   public final void explode(Level var1, int var2, int var3, int var4) {
         PrimedTnt var5;
         Minecraft.entities.add(new PrimedTnt(var1, (float)var2 + 0.5F, (float)var3 + 0.5F, (float)var4 + 0.5F, false));
         
	   }

   
   
   protected final int getTexture(int var1) {
	      return var1 == 0?this.tex + 2:(var1 == 1?this.tex + 1:this.tex);
	   }
   
   public void destroy(Level level, int x, int y, int z, ParticleEngine particleEngine) {
	   Minecraft.entities.add(new PrimedTnt(level, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, false));
   }
}
