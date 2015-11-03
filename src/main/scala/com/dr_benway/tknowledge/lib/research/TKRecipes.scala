package com.dr_benway.tknowledge.lib.research

import scala.collection.immutable.HashMap
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import thaumcraft.api.ThaumcraftApi
import thaumcraft.api.aspects.Aspect
import thaumcraft.api.aspects.AspectList
import thaumcraft.api.crafting._
import thaumcraft.api.items.ItemsTC
import net.minecraft.init.Blocks
import net.minecraftforge.oredict.OreDictionary


object TKRecipes {
  
  var recipes = new HashMap[String,Any]()
  var alchemyAspects: AspectList = null
  var infusionAspects: AspectList = null
  
  def addRecipes() {
    
    //Crucible
    alchemyAspects = new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.EARTH, 1)
    addCrucibleRecipe("DUPE", "_COBBLE", new ItemStack(Blocks.cobblestone, 2), new ItemStack(Blocks.cobblestone, 1), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.AIR, 6)
    addCrucibleRecipe("DUPE", "_END_STONE", new ItemStack(Blocks.end_stone, 2), new ItemStack(Blocks.end_stone, 1), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.CRYSTAL,12).add(Aspect.SENSES,8);
    addCrucibleRecipe("DUPE", "_LAPIS", new ItemStack(Items.dye, 2, 4), new ItemStack(Items.dye, 1, 4), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.MECHANISM, 8).add(Aspect.ENERGY, 8)
    addCrucibleRecipe("DUPE", "_REDSTONE", new ItemStack(Items.redstone, 2), new ItemStack(Items.redstone, 1), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.DEATH,2).add(Aspect.MAN, 4)
    addCrucibleRecipe("DUPE","_BONE", new ItemStack(Items.bone,2,0), new ItemStack(Items.bone,1,0), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.SENSES, 4).add(Aspect.BEAST, 4)
    addCrucibleRecipe("DUPE","_EYES", new ItemStack(Items.spider_eye,2,0), new ItemStack(Items.spider_eye,1,0), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.TRAP,6).add(Aspect.CRYSTAL,6)
    addCrucibleRecipe("DUPE", "_AMBER", new ItemStack(ItemsTC.amber, 2), new ItemStack(ItemsTC.amber, 1), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.METAL, 8).add(Aspect.EXCHANGE, 6).add(Aspect.MIND, 4)
    addCrucibleRecipe("DUPE", "_QUICKSILVER", new ItemStack(ItemsTC.quicksilver, 2), new ItemStack(ItemsTC.quicksilver, 1), alchemyAspects)
    
    alchemyAspects = new AspectList().add(Aspect.ENTROPY, 4)
    addCrucibleRecipe("ENT", "_SAND", new ItemStack(Blocks.sand, 1), new ItemStack(Blocks.cobblestone, 1), alchemyAspects)
    addCrucibleRecipe("ENT", "_WOOL", new ItemStack(Items.string, 4), new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.TOOL, 6)
    addCrucibleRecipe("ENT", "_FLINT", new ItemStack(Items.flint, 1), new ItemStack(Blocks.gravel, 1), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.CRYSTAL, 2)
    addCrucibleRecipe("ENT", "_GRAVEL", new ItemStack(Blocks.gravel, 1), new ItemStack(Blocks.stone, 1), alchemyAspects)
    
   alchemyAspects = new AspectList().add(Aspect.SOUL, 8).add(Aspect.MIND, 4)
    addCrucibleRecipe("MAN", "_SOUL_SAND", new ItemStack(Blocks.soul_sand, 1), new ItemStack(Blocks.sand, 1), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.FIRE, 6)
    addCrucibleRecipe("MAN", "_NETHERRACK", new ItemStack(Blocks.netherrack, 1), new ItemStack(Blocks.cobblestone, 1), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.VOID, 8)
    addCrucibleRecipe("MAN", "_ENDER_PEARL", new ItemStack(Items.ender_pearl, 1), new ItemStack(Blocks.end_stone, 1), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.FIRE, 12)
    addCrucibleRecipe("MAN", "_BLAZE_ROD", new ItemStack(Items.blaze_rod, 1), new ItemStack(Blocks.obsidian, 1), alchemyAspects)
    alchemyAspects = new AspectList().add(Aspect.SOUL, 8).add(Aspect.WATER, 4).add(Aspect.DEATH, 4)
    addCrucibleRecipe("MAN", "_GHAST_TEAR", new ItemStack(Items.ghast_tear, 1), new ItemStack(Blocks.obsidian, 1), alchemyAspects)
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  }
  
  
  
  private def addArcaneRecipe(tag: String, tagAddon: String, result: ItemStack, aspects: AspectList, input : Any*) {
    
    val recipe: ShapedArcaneRecipe = ThaumcraftApi.addArcaneCraftingRecipe(tag, result, aspects, input)
    if(recipe.isInstanceOf[ShapedArcaneRecipe]) recipes += (tag+tagAddon -> recipe) else
      throw new IllegalArgumentException(tag+tagAddon + " is not a valid recipe!")
    
  }
  
  private def addShapelessArcaneRecipe(tag: String, tagAddon: String, result: ItemStack, aspects: AspectList, input : Any*) {
    
    val recipe: ShapelessArcaneRecipe = ThaumcraftApi.addShapelessArcaneCraftingRecipe(tag, result, aspects, input)
    if(recipe.isInstanceOf[ShapelessArcaneRecipe]) recipes += (tag+tagAddon -> recipe) else
      throw new IllegalArgumentException(tag+tagAddon + " is not a valid recipe!")
    
  }
  
  private def addCrucibleRecipe(tag: String, tagAddon: String, result: ItemStack, cat: Any, aspects: AspectList) {
    
    val recipe: CrucibleRecipe = ThaumcraftApi.addCrucibleRecipe(tag, result, cat, aspects)
    if(recipe.isInstanceOf[CrucibleRecipe]) recipes += (tag+tagAddon -> recipe) else
      throw new IllegalArgumentException(tag+tagAddon + " is not a valid recipe!")
  }
  
  private def addInfusionRecipe(tag: String, tagAddon: String, result: Any, inst: Int, aspects: AspectList, maincat: ItemStack, cats: AnyRef*) {
    
    val recipe: InfusionRecipe = ThaumcraftApi.addInfusionCraftingRecipe(tag, result, inst, aspects, maincat, cats.toArray)
    if(recipe.isInstanceOf[InfusionRecipe]) recipes += (tag+tagAddon -> recipe) else
      throw new IllegalArgumentException(tag+tagAddon + " is not a valid recipe!")
  }
  
}