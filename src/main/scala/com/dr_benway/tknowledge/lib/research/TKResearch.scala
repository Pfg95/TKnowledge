package com.dr_benway.tknowledge.lib.research

import scala.collection.JavaConversions._
import com.dr_benway.tknowledge.Knowledge
import com.dr_benway.tknowledge.block.TKBlocks
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import thaumcraft.api.aspects.Aspect
import thaumcraft.api.aspects.AspectList
import thaumcraft.api.crafting.CrucibleRecipe
import thaumcraft.api.research._
import net.minecraft.init.Items
import net.minecraft.init.Blocks
import thaumcraft.api.crafting.ShapedArcaneRecipe
import com.dr_benway.tknowledge.TKConfig
import scala.collection.mutable.ArrayBuffer

object TKResearch {
  
  private var researchAspects: AspectList = _
  private var pages: Array[ResearchPage] = _
  
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
  if(TKConfig.allowDupe) {
    researchAspects = new AspectList().add(Aspect.CRYSTAL,1).add(Aspect.DESIRE,1).add(Aspect.CRAFT,1)
    
    pages = Array(new ResearchPage("tk.research_page.DUPE.1"), new ResearchPage(getMap.apply("DUPE_COBBLE").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_END_STONE").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_EYES").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_BONE").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_REDSTONE").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_LAPIS").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_AMBER").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("DUPE_QUICKSILVER").asInstanceOf[CrucibleRecipe]))
    getTKResearchItem("DUPE", "KNOWLEDGE", researchAspects, -13, 2, 1, new ItemStack(Items.dye, 1, 4)).setParents("COPY_ALCHEMICALDUPLICATION").setPages(pages:_*).registerResearchItem()
  }
  
  if(TKConfig.allowEnt) {
    researchAspects = new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.WATER, 1).add(Aspect.CRAFT, 1)
    val elem = Array.newBuilder[ResearchPage]
    elem += (new ResearchPage("tk.research_page.ENT.1"))
    if(TKConfig.allowEnt_Sand) elem += (new ResearchPage(getMap.apply("ENT_SAND").asInstanceOf[CrucibleRecipe]))
    elem += new ResearchPage(getMap.apply("ENT_GRAVEL").asInstanceOf[CrucibleRecipe]) += new ResearchPage(getMap.apply("ENT_FLINT").asInstanceOf[CrucibleRecipe])
    getTKResearchItem("ENT", "KNOWLEDGE", researchAspects, -12, 0, 1, new ItemStack(Blocks.sand, 1)).setParents("COPY_ENTROPICPROCESSING").setPages(elem.result:_*).registerResearchItem()
  }
  if(TKConfig.allowMan) {
    researchAspects = new AspectList().add(Aspect.LIFE, 1).add(Aspect.EXCHANGE, 1).add(Aspect.CRAFT, 1)
    val elem = Array.newBuilder[ResearchPage]
    elem += new ResearchPage("tk.research_page.MAN.1") += new ResearchPage(getMap.apply("MAN_NETHERRACK").asInstanceOf[CrucibleRecipe]) += new ResearchPage(getMap.apply("MAN_SOUL_SAND").asInstanceOf[CrucibleRecipe])
    if(TKConfig.allowMan_BlazeRod) elem += new ResearchPage(getMap.apply("MAN_BLAZE_ROD").asInstanceOf[CrucibleRecipe])
    if(TKConfig.allowMan_EnderPearl) elem += new ResearchPage(getMap.apply("MAN_ENDER_PEARL").asInstanceOf[CrucibleRecipe])
    if(TKConfig.allowMan_GhastTear) elem += new ResearchPage(getMap.apply("MAN_GHAST_TEAR").asInstanceOf[CrucibleRecipe])
    getTKResearchItem("MAN", "KNOWLEDGE", researchAspects, -13, -2, 1, new ItemStack(Items.ender_pearl, 1)).setParents("COPY_ALCHEMICALMANUFACTURE").setPages(elem.result:_*).registerResearchItem()
  }
  researchAspects = new AspectList().add(Aspect.LIFE, 3)
  pages = Array[ResearchPage](new ResearchPage("tk.research_page.REC_QS.1"), new ResearchPage(getMap.apply("REC_QS_QS_BLOCK").asInstanceOf[CrucibleRecipe]), new ResearchPage(getMap.apply("QS_SLAB").asInstanceOf[IRecipe]), new ResearchPage(getMap.apply("QS_STAIRS").asInstanceOf[IRecipe]), new ResearchPage("tk.research_page.REC_QS.2").setRequisite("THAUMATORIUM"), new ResearchPage(getMap.apply("QS_CONS").asInstanceOf[ShapedArcaneRecipe]).setRequisite("THAUMATORIUM"))
  getTKResearchItem("REC_QS", "KNOWLEDGE", researchAspects, -11, -4, 1, new ItemStack(TKBlocks.misc, 1, 1)).setPages(pages:_*).setParents("METALLURGY").setSecondary().registerResearchItem()
  researchAspects = new AspectList().add(Aspect.WATER, 1).add(Aspect.CRAFT, 1).add(Aspect.FIRE, 1).add(Aspect.ENERGY, 1)
  pages = Array[ResearchPage](new ResearchPage("tk.research_page.FOUNTAIN.1"), new ResearchPage("tk.research_page.FOUNTAIN.thaumium"), new ResearchPage("tk.research_page.FOUNTAIN.2"), new ResearchPage("tk.research_page.FOUNTAIN.brass"), new ResearchPage("tk.research_page.FOUNTAIN.3").setRequisite("VOIDMETAL"), new ResearchPage("tk.research_page.FOUNTAIN.void").setRequisite("VOIDMETAL"), new ResearchPage("tk.research_page.FOUNTAIN.4").setRequisite("CRYSTALFARMER"), new ResearchPage("tk.research_page.FOUNTAIN.5").setRequisite("CRYSTALFARMER"), new ResearchPage("tk.research_page.FOUNTAIN.water"), new ResearchPage("tk.research_page.FOUNTAIN.purifying").setRequisite("LIQUIDDEATH").setRequisite("BATHSALTS"))
  getTKResearchItem("FOUNTAIN", "KNOWLEDGE", researchAspects, -8, -2, 2, new ResourceLocation("tknowledge", "textures/blocks/fountain/thaumium.png")).setPages(pages:_*).setParents("REC_QS", "THAUMATORIUM").registerResearchItem()
  researchAspects = new AspectList().add(Aspect.WATER, 1).add(Aspect.METAL, 1).add(Aspect.VOID, 1)
  pages = Array[ResearchPage](new ResearchPage("tk.research_page.TANK_SIMPLE.1"), new ResearchPage(getMap.apply("TANK_SIMPLE").asInstanceOf[ShapedArcaneRecipe]))
  //getTKResearchItem("TANK_SIMPLE", "KNOWLEDGE", researchAspects, -6, 0, 1, new ItemStack(TKBlocks.tank, 1, 0)).setPages(pages:_*).setParents("FOUNTAIN").registerResearchItem()
  
  
  
  
  
  
  }
  
  
  private def getMap = TKRecipes.recipes
  
  private def getTKResearchItem(tag: String, cat: String, researchAspects: AspectList, x: Int, y: Int, complex: Int, icon: Any): TKResearchItem = {
    icon match {
      case i: ItemStack => new TKResearchItem(tag, cat, researchAspects, x, y, complex, i)
      case r: ResourceLocation => new TKResearchItem(tag, cat, researchAspects, x, y, complex, r)
    }
  }
  
  private def getTCResearchItem(original: String, ocat: String, x: Int, y: Int, icon: Any): TKTCResearchItem = {
    icon match {
      case i: ItemStack => new TKTCResearchItem(s"COPY_$original", "KNOWLEDGE", original, ocat, x, y, i)
      case r: ResourceLocation => new TKTCResearchItem(s"COPY_$original", "KNOWLEDGE", original, ocat, x, y, r)
    }
  }
  
}