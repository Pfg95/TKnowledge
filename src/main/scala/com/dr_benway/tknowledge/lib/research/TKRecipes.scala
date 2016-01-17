package com.dr_benway.tknowledge.lib.research

import com.dr_benway.tknowledge.block.TKBlocks
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.immutable.HashMap
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import thaumcraft.api.ThaumcraftApi
import thaumcraft.api.aspects.Aspect
import thaumcraft.api.aspects.AspectList
import thaumcraft.api.crafting._
import thaumcraft.api.items.ItemsTC
import thaumcraft.api.blocks.BlocksTC
import net.minecraftforge.fml.common.registry.GameRegistry
import tknowledge.TKnowledgeAPI
import net.minecraftforge.fluids.FluidStack
import com.dr_benway.tknowledge.lib.crafting.FountainRecipe
import net.minecraftforge.fluids.FluidRegistry
import thaumcraft.common.config.ConfigBlocks


object TKRecipes {
  
  var recipes = new HashMap[String, Any]()
  var aspects: AspectList = _
  
  def addRecipes() {
    
    //Crucible
    aspects = new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.EARTH, 1)
    addCrucibleRecipe("DUPE", "_COBBLE", new ItemStack(Blocks.cobblestone, 2), new ItemStack(Blocks.cobblestone, 1), aspects)
    aspects = new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.AIR, 6)
    addCrucibleRecipe("DUPE", "_END_STONE", new ItemStack(Blocks.end_stone, 2), new ItemStack(Blocks.end_stone, 1), aspects)
    aspects = new AspectList().add(Aspect.CRYSTAL,12).add(Aspect.SENSES,8);
    addCrucibleRecipe("DUPE", "_LAPIS", new ItemStack(Items.dye, 2, 4), new ItemStack(Items.dye, 1, 4), aspects)
    aspects = new AspectList().add(Aspect.MECHANISM, 4).add(Aspect.ENERGY, 2)
    addCrucibleRecipe("DUPE", "_REDSTONE", new ItemStack(Items.redstone, 2), new ItemStack(Items.redstone, 1), aspects)
    aspects = new AspectList().add(Aspect.DEATH,2).add(Aspect.MAN, 2)
    addCrucibleRecipe("DUPE","_BONE", new ItemStack(Items.bone,2,0), new ItemStack(Items.bone,1,0), aspects)
    aspects = new AspectList().add(Aspect.SENSES, 1).add(Aspect.BEAST, 2)
    addCrucibleRecipe("DUPE","_EYES", new ItemStack(Items.spider_eye,2,0), new ItemStack(Items.spider_eye,1,0), aspects)
    aspects = new AspectList().add(Aspect.TRAP,4).add(Aspect.CRYSTAL,6)
    addCrucibleRecipe("DUPE", "_AMBER", new ItemStack(ItemsTC.amber, 2), new ItemStack(ItemsTC.amber, 1), aspects)
    aspects = new AspectList().add(Aspect.METAL, 8).add(Aspect.EXCHANGE, 6).add(Aspect.MIND, 4)
    addCrucibleRecipe("DUPE", "_QUICKSILVER", new ItemStack(ItemsTC.quicksilver, 2), new ItemStack(ItemsTC.quicksilver, 1), aspects)
    
    aspects = new AspectList().add(Aspect.ENTROPY, 1)
    addCrucibleRecipe("ENT", "_SAND", new ItemStack(Blocks.sand, 1), new ItemStack(Blocks.cobblestone, 1), aspects)
    aspects = new AspectList().add(Aspect.TOOL, 2).add(Aspect.ENTROPY, 1)
    addCrucibleRecipe("ENT", "_FLINT", new ItemStack(Items.flint, 1), new ItemStack(Blocks.gravel, 1), aspects)
    aspects = new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.CRYSTAL, 2)
    addCrucibleRecipe("ENT", "_GRAVEL", new ItemStack(Blocks.gravel, 1), new ItemStack(Blocks.stone, 1), aspects)
    
    aspects = new AspectList().add(Aspect.SOUL, 1).add(Aspect.MIND, 2)
    addCrucibleRecipe("MAN", "_SOUL_SAND", new ItemStack(Blocks.soul_sand, 1), new ItemStack(Blocks.sand, 1), aspects)
    aspects = new AspectList().add(Aspect.FIRE, 1)
    addCrucibleRecipe("MAN", "_NETHERRACK", new ItemStack(Blocks.netherrack, 1), new ItemStack(Blocks.cobblestone, 1), aspects)
    aspects = new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.VOID, 8)
    addCrucibleRecipe("MAN", "_ENDER_PEARL", new ItemStack(Items.ender_pearl, 1), new ItemStack(Blocks.end_stone, 1), aspects)
    aspects = new AspectList().add(Aspect.FIRE, 8).add(Aspect.ENERGY, 4)
    addCrucibleRecipe("MAN", "_BLAZE_ROD", new ItemStack(Items.blaze_rod, 1), new ItemStack(Blocks.obsidian, 1), aspects)
    aspects = new AspectList().add(Aspect.SOUL, 8).add(Aspect.WATER, 4)
    addCrucibleRecipe("MAN", "_GHAST_TEAR", new ItemStack(Items.ghast_tear, 1), new ItemStack(Blocks.obsidian, 1), aspects)
    aspects = new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.COLD, 2)
    addCrucibleRecipe("QS_BLOCK", "", new ItemStack(TKBlocks.misc, 1, 1), new ItemStack(ItemsTC.quicksilver, 4), aspects)
    
    
    
    
    //Compound
    val wand = new ItemStack(ItemsTC.wand)
    
      
    
    //Infusion
    
    
    //Fountain
    aspects = new AspectList().add(Aspect.FIRE, 12)
    TKnowledgeAPI.registerFountainRecipe("FOUNTAIN", "_LAVA", new FluidStack(FluidRegistry.LAVA, 3000), new ItemStack(Blocks.netherrack, 1), aspects, new AspectList().add(Aspect.AIR, 1), .2F)
    aspects = new AspectList().add(Aspect.MIND, 8)
    TKnowledgeAPI.registerFountainRecipe("FOUNTAIN", "_PURIFYING_FLUID", new FluidStack(ConfigBlocks.FluidPure.instance, 3000), new ItemStack(ItemsTC.bathSalts, 1), aspects, new AspectList().add(Aspect.ORDER, 1), .3F)
    aspects = new AspectList().add(Aspect.FLUX, 8).add(Aspect.DEATH, 2)
    TKnowledgeAPI.registerFountainRecipe("FOUNTAIN", "_LIQUID_DEATH", new FluidStack(ConfigBlocks.FluidDeath.instance, 4000), new ItemStack(BlocksTC.taintBlock, 1, 3), aspects, new AspectList().add(Aspect.ENTROPY, 1), .4F)
    
    
    //Arcane
    aspects = new AspectList().add(Aspect.WATER, 250).add(Aspect.ORDER, 500)
    recipes += ("QS_CONS" -> ThaumcraftApi.addArcaneCraftingRecipe("QS_CONS", new ItemStack(TKBlocks.misc, 1, 0), aspects, Seq("QVQ", "TBT", "QVQ", Character.valueOf('Q'), new ItemStack(ItemsTC.quicksilver, 1), Character.valueOf('V'), new ItemStack(BlocksTC.tube, 1, 1), Character.valueOf('T'), new ItemStack(BlocksTC.tube, 1, 0), Character.valueOf('B'), new ItemStack(Items.bucket)):_*))
    
    
    //Vanilla
    recipes += ("QS_SLAB" -> GameRegistry.addShapedRecipe(new ItemStack(TKBlocks.slab, 6, 0), Seq("KKK", Character.valueOf('K'), new ItemStack(TKBlocks.misc, 1, 1)):_*))
    recipes += ("QS_STAIRS" -> GameRegistry.addShapedRecipe(new ItemStack(TKBlocks.stairs_qs, 4, 0), Seq("K  ", "KK ", "KKK", Character.valueOf('K'), new ItemStack(TKBlocks.misc, 1, 1)):_*))
    
    
    
  }
  
  
  
  
  
  private def addCrucibleRecipe(tag: String, tagAddon: String, result: ItemStack, cat: Any, aspects: AspectList) {
    
    val recipe = ThaumcraftApi.addCrucibleRecipe(tag, result, cat, aspects)
    if(recipe.isInstanceOf[CrucibleRecipe]) recipes += (tag+tagAddon -> recipe) else
      throw new IllegalArgumentException(s"$tag$tagAddon is not a valid recipe!")
  }
  
  private def addInfusionRecipe(tag: String, tagAddon: String, result: Any, inst: Int, aspects: AspectList, maincat: ItemStack, cats: AnyRef*) {
    
    val recipe = ThaumcraftApi.addInfusionCraftingRecipe(tag, result, inst, aspects, maincat, cats.toArray)
    if(recipe.isInstanceOf[InfusionRecipe]) recipes += (tag+tagAddon -> recipe) else
      throw new IllegalArgumentException(s"$tag$tagAddon is not a valid recipe!")
  }
  
  private def addCompoundRecipe(tag: String, tagAddon: String, sizeX: Int, sizeY: Int, sizeZ: Int, aspects: AspectList, recipe: ItemStack*) {
    
    val compoundRecipe = Seq(aspects, Integer.valueOf(sizeX), Integer.valueOf(sizeY), Integer.valueOf(sizeZ), recipe.asJava).asJava
    if(recipe.isInstanceOf[Seq[_]]) recipes += (tag+tagAddon -> compoundRecipe) else
      throw new IllegalArgumentException(s"$tag$tagAddon is not a valid recipe!")
  }
  
  
  
  
}