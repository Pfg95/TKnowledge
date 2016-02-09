package com.dr_benway.tknowledge.block

import net.minecraft.item.ItemBlock
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import com.dr_benway.tknowledge.block.basic.BlockSlabTK



class ItemBaseBlock(block: Block) extends ItemBlock(block) {
  
      if(!block.isInstanceOf[Block]) throw new IllegalArgumentException("The given block is not an instance of Block!")
      
      setMaxDamage(0)
      setHasSubtypes(true)
      
      
      override def getMetadata(meta: Int) = meta
      
      override def getUnlocalizedName(stack: ItemStack): String = {
        this.block match {
          case bb: BaseBlock => super.getUnlocalizedName(stack) + "." + this.block.asInstanceOf[BaseBlock].getSpecialName(stack)
          case bs: BlockSlabTK => super.getUnlocalizedName(stack) + "." + this.block.asInstanceOf[BlockSlabTK].getSpecialName(stack)
          case _ => super.getUnlocalizedName(stack) + "." + "ADD_MATCH_FOR_IT_FFS"
        }
      } 
  
}