package com.dr_benway.tknowledge.lib.crafting

import net.minecraft.item.ItemStack
import thaumcraft.api.aspects.AspectList
import tknowledge.TKnowledgeAPI
import thaumcraft.api.research.ResearchHelper
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import thaumcraft.api.aspects.Aspect
import net.minecraft.entity.player.EntityPlayer



object TKCraftingManager {
  
  object Fountain {
    
    val defaultRecipe = new FountainRecipe("FOUNTAIN", "_WATER", new FluidStack(FluidRegistry.WATER, 4000), null, new AspectList(), new AspectList().add(Aspect.WATER, 1), 0.0F)
  
    def findMatchingRecipe(input: Any, out: Fluid, player: EntityPlayer): FountainRecipe = {
      var index = -1
      if(input != null) {
        for(x <- 0 until TKnowledgeAPI.getRecipes.size) {
          if(TKnowledgeAPI.getRecipes.get(x).isInstanceOf[FountainRecipe]) {
            val recipe = TKnowledgeAPI.getRecipes.get(x).asInstanceOf[FountainRecipe]
            if(input.isInstanceOf[ItemStack] && recipe.matches(input.asInstanceOf[ItemStack], out, player)) {
              index = x
            } 
          }
        }
        if(index > -1) TKnowledgeAPI.getRecipes.get(index).asInstanceOf[FountainRecipe] else defaultRecipe
      } else defaultRecipe
    }
    
    def retrieveRecipe(key: String) = {
      var index = -1
      if(key != null) {
        for(x <- 0 until TKnowledgeAPI.getRecipes.size) {
          if(TKnowledgeAPI.getRecipes.get(x).isInstanceOf[FountainRecipe] && TKnowledgeAPI.getRecipes.get(x).asInstanceOf[FountainRecipe].key == key) {
            index = x
          }
        }
        if(index > -1) TKnowledgeAPI.getRecipes.get(index).asInstanceOf[FountainRecipe] else defaultRecipe
      } else defaultRecipe
    }
    
    
    
  }
    
    
    
  
}