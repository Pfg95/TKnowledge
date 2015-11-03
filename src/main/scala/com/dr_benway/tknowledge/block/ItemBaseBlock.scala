package com.dr_benway.tknowledge.block

import net.minecraft.item.ItemBlock
import net.minecraft.block.Block
import net.minecraft.item.ItemStack



class ItemBaseBlock(block: Block) extends ItemBlock(block) {
  
      if(!block.isInstanceOf[BaseBlock]) throw new IllegalArgumentException("The given block is not an instance of BaseBlock!")
      
      setMaxDamage(0)
      setHasSubtypes(true)
      
      
      override def getMetadata(meta: Int) = meta
      
      override def getUnlocalizedName(stack: ItemStack): String = {
        super.getUnlocalizedName(stack) + "." + this.block.asInstanceOf[BaseBlock].getSpecialName(stack)
      }
  
  
}