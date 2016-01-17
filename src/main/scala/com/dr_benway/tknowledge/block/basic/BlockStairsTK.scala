package com.dr_benway.tknowledge.block.basic

import net.minecraft.block.BlockStairs
import net.minecraft.block.state.IBlockState
import com.dr_benway.tknowledge.Knowledge



class BlockStairsTK(uName: String = "wat", modelState: IBlockState) extends BlockStairs(modelState) {
  
  setLightOpacity(0)
  setUnlocalizedName(s"tk_$uName")
  setCreativeTab(Knowledge.tab)
  
}