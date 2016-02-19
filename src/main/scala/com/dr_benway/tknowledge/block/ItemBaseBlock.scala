package com.dr_benway.tknowledge.block

import net.minecraft.item.ItemBlock
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import com.dr_benway.tknowledge.block.basic.BlockSlabTK



class ItemBaseBlock(block: Block) extends ItemBlock(block) {
      
      setMaxDamage(0)
      setHasSubtypes(true)

      override def getMetadata(meta: Int) = meta
      
      override def getUnlocalizedName(stack: ItemStack): String = {
        this.block match {
          case bb: BaseBlock => super.getUnlocalizedName(stack) + "." + bb.getSpecialName(stack)
          case bs: BlockSlabTK => super.getUnlocalizedName(stack) + "." + bs.getSpecialName(stack)
          case _ => super.getUnlocalizedName(stack) + "." + "FORGOT_SOMETHING"
        }
      } 
  
}