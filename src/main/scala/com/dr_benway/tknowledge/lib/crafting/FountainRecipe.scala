package com.dr_benway.tknowledge.lib.crafting

import net.minecraft.item.ItemStack
import thaumcraft.api.aspects.AspectList
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.oredict.OreDictionary
import thaumcraft.api.aspects.Aspect
import net.minecraftforge.fluids.Fluid
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagString
import net.minecraft.entity.player.EntityPlayer
import thaumcraft.api.research.ResearchHelper



class FountainRecipe(research1: String, key1: String, output: FluidStack, input: Any, itags: AspectList, aura: AspectList, danger: Float) {
  
  
  val cat = input match {
    case s: String => OreDictionary.getOres(input.asInstanceOf[String])
    case null => null
    case _ => input.asInstanceOf[ItemStack]
  }
  
  val research = research1
  val key = key1
  val aspects = itags
  val result = output
  val vis_aspects = aura
  val pollutingChance = if(danger >= 0.0F && danger <= 1.0F) danger else if(danger > 1.0F) 1.0F else 0.0F //yeah there is a smarter way
  
  def matches(in: ItemStack, out: Fluid, player: EntityPlayer): Boolean = {
    if(out == null) false
    if( (cat.isInstanceOf[ItemStack]) && (OreDictionary.itemMatches(cat.asInstanceOf[ItemStack], in, false)) ||
      ((cat.isInstanceOf[Seq[_]]) && (cat.asInstanceOf[Seq[_]].size > 0)) ) {
      result.isInstanceOf[FluidStack] && result.getFluid == out && ResearchHelper.isResearchComplete(player.getName, this.research)
    } else false
  }
  
  
  
}