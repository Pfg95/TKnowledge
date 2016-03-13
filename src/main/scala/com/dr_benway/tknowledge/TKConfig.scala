package com.dr_benway.tknowledge

import java.io.File;
import net.minecraftforge.common.config.Configuration

object TKConfig {
  
    final private val CATEGORY_RECIPES = "Recipes / Balance"
    final var allowEnt = true
    final var allowMan = true
    final var allowDupe = true
    final var allowEnt_Sand = true
    final var allowMan_EnderPearl = true
    final var allowMan_BlazeRod = true
    final var allowMan_GhastTear = true
    
    def preInit(suggestedConfigurationFile: File) {
      
      val conf = new Configuration(suggestedConfigurationFile)
      conf.addCustomCategoryComment(CATEGORY_RECIPES, "Here you can disable various alchemy recipes.")
      
      try {
        conf.load()
        allowEnt = conf.get(CATEGORY_RECIPES, "Additional alchemical entropic processings", allowEnt, "Disable to remove all additional entropic recipes.").getBoolean(true)
        allowMan = conf.get(CATEGORY_RECIPES, "Additional alchemical manufacture", allowMan, "Disable to remove all additional manufacture recipes.").getBoolean(true)
        allowDupe = conf.get(CATEGORY_RECIPES, "Additional alchemical duplications", allowDupe, "Disable to remove all additional duplication recipes.").getBoolean(true)
        allowEnt_Sand = conf.get(CATEGORY_RECIPES, "Cobble to sand", allowEnt_Sand, "Disable to remove the cobble -> sand recipe.").getBoolean(true)
        allowMan_EnderPearl = conf.get(CATEGORY_RECIPES, "Endstone to ender pearl", allowMan_EnderPearl, "Disable to remove the endstone -> ender pearl recipe.").getBoolean(true)
        allowMan_BlazeRod = conf.get(CATEGORY_RECIPES, "Obsidian to blaze rod", allowMan_BlazeRod, "Disable to remove the obsidian -> blaze rod recipe.").getBoolean(true)
        allowMan_GhastTear = conf.get(CATEGORY_RECIPES, "Obsidian to ghast tear", allowMan_GhastTear, "Disable to remove the obsidian -> ghast tear recipe.").getBoolean(true)
        
        
        
        
        
        
        
        
        
      }
      catch {
        case e: Exception => Knowledge.log.error("[TKNOWLEDGE]: Failed to load configuration. Default values will be applied.")
      }
      finally {
        if(conf != null) conf.save()
      }
      
    }
  
}