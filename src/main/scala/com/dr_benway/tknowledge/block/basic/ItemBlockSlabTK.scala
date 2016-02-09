package com.dr_benway.tknowledge.block.basic

import net.minecraft.block.Block
import net.minecraft.item.ItemSlab
import com.dr_benway.tknowledge.block.TKBlocks
import net.minecraft.block.BlockSlab
import net.minecraft.item.ItemStack


class ItemBlockSlabTK(b: Block) extends ItemSlab(b, TKBlocks.slab.asInstanceOf[BlockSlab], TKBlocks.slab_double.asInstanceOf[BlockSlab]) {
  
  override def getUnlocalizedName(stack: ItemStack): String = {
        this.block match {
          case bs: BlockSlabTK => super.getUnlocalizedName(stack) + "." + this.block.asInstanceOf[BlockSlabTK].getSpecialName(stack)
          case _ => super.getUnlocalizedName(stack) + "." + "ADD_MATCH_FOR_IT_FFS"
        }
      } 
  
}