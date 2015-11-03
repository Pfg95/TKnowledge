package com.dr_benway.tknowledge.block

import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraft.block.material.Material
import com.dr_benway.tknowledge.util.traits.T_Hidden
import com.dr_benway.tknowledge.block.basic.BlockDummy

object TKBlocks {
  
  val diocan = new BaseBlock("diocan") with T_Hidden
  val cazzo = new BlockDummy("cazzo")
  
  def addBlocks() {
    
    GameRegistry.registerBlock(diocan, "tk_diocan")
    GameRegistry.registerBlock(cazzo, classOf[ItemBaseBlock], "tk_cazzo")
    
  }
  
 
  
}