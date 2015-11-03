package com.dr_benway.tknowledge.lib.research

import thaumcraft.api.research._
import net.minecraft.util.ResourceLocation
import com.dr_benway.tknowledge.Knowledge
import thaumcraft.api.aspects.AspectList
import net.minecraft.item.ItemStack
import net.minecraft.init.Items
import thaumcraft.api.crafting.CrucibleRecipe
import thaumcraft.api.ThaumcraftApi
import thaumcraft.api.aspects.Aspect
import net.minecraft.init.Blocks


object TKResearch {
  
  var researchAspects: AspectList = null
  var pages: Array[ResearchPage] = null
  
  def addResearch() {
    
   // x+ => right, y+ => down
  
  //TKnowledge's tab
  ResearchCategories.registerCategory("KNOWLEDGE", null, new ResourceLocation(Knowledge.MODID + ":textures/research/tab_icon.png"), new ResourceLocation("thaumcraft:textures/gui/gui_research_back_3.jpg"))
  
  //Vanilla researches
  getTCResearchItem("CRUCIBLE", "ALCHEMY", -16, 0, new ResourceLocation("thaumcraft", "textures/research/r_crucible.png")).registerResearchItem()
  getTCResearchItem("ALCHEMICALMANUFACTURE", "ALCHEMY", -15, -2, new ResourceLocation("thaumcraft", "textures/research/r_alchman.png")).registerResearchItem()
  getTCResearchItem("ALCHEMICALDUPLICATION", "ALCHEMY", -15, 2, new ResourceLocation("thaumcraft", "textures/research/r_alchmult.png")).registerResearchItem()
  getTCResearchItem("ENTROPICPROCESSING", "ALCHEMY", -14, 0, new ResourceLocation("thaumcraft", "textures/research/r_alchent.png")).registerResearchItem()
  
  //TKnowledge's researches
  researchAspects = new AspectList().add(Aspect.CRYSTAL,1).add(Aspect.DESIRE,1).add(Aspect.CRAFT,1)
  pages = Array(new ResearchPage("tk.research_page.DUPE.1"), new ResearchPage(getMap.apply("DUPE_COBBLE").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_END_STONE").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_EYES").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_BONE").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_REDSTONE").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_LAPIS").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_AMBER").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_QUICKSILVER").asInstanceOf[CrucibleRecipe]))
  getTKResearchItem("DUPE", "KNOWLEDGE", researchAspects, -13, 2, 1, new ItemStack(Items.dye, 1, 4)).setParents("COPY_ALCHEMICALDUPLICATION").setPages(pages:_*).registerResearchItem()
  researchAspects = new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.WATER, 1).add(Aspect.CRAFT, 1)
  pages = Array(new ResearchPage("tk.research_page.ENT.1"), new ResearchPage(getMap.apply("ENT_SAND").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("ENT_GRAVEL").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("ENT_FLINT").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("ENT_WOOL").asInstanceOf[CrucibleRecipe]))
  getTKResearchItem("ENT", "KNOWLEDGE", researchAspects, -12, 0, 1, new ItemStack(Blocks.sand, 1)).setParents("COPY_ENTROPICPROCESSING").setPages(pages:_*).registerResearchItem()
  researchAspects = new AspectList().add(Aspect.LIFE, 1).add(Aspect.EXCHANGE, 1).add(Aspect.CRAFT, 1)
  pages = Array(new ResearchPage("tk.research_page.MAN.1"), new ResearchPage(getMap.apply("MAN_NETHERRACK").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("MAN_SOUL_SAND").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("MAN_BLAZE_ROD").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("MAN_GHAST_TEAR").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("MAN_ENDER_PEARL").asInstanceOf[CrucibleRecipe]))
  getTKResearchItem("MAN", "KNOWLEDGE", researchAspects, -13, -2, 1, new ItemStack(Items.ender_pearl, 1)).setParents("COPY_ALCHEMICALMANUFACTURE").setPages(pages:_*).registerResearchItem()
  
  
  
  
  
  }
  
  
  private def getMap = TKRecipes.recipes
  
  private def getTKResearchItem(tag: String, cat: String, researchAspects: AspectList, x: Int, y: Int, complex: Int, icon: Any): TKResearchItem = {
    val item: TKResearchItem = icon match {
      case i: ItemStack => new TKResearchItem(tag, cat, researchAspects, x, y, complex, icon.asInstanceOf[ItemStack])
      case r: ResourceLocation => new TKResearchItem(tag, cat, researchAspects, x, y, complex, icon.asInstanceOf[ResourceLocation])
    } 
   item
  }
  
  private def getTCResearchItem(original: String, ocat: String, x: Int, y: Int, icon: Any): TKTCResearchItem = {
    val item: TKTCResearchItem = icon match {
      case i: ItemStack => new TKTCResearchItem("COPY_" + original, "KNOWLEDGE", original, ocat, x, y, icon.asInstanceOf[ItemStack])
      case r: ResourceLocation => new TKTCResearchItem("COPY_" + original, "KNOWLEDGE", original, ocat, x, y, icon.asInstanceOf[ResourceLocation])
    }
    item
  }
  
  
}