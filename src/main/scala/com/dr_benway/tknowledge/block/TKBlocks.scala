package com.dr_benway.tknowledge.block

import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraft.block.material.Material
import com.dr_benway.tknowledge.block
import com.dr_benway.tknowledge.block.basic._
import com.dr_benway.tknowledge.block.devices.BlockFountain
import com.dr_benway.tknowledge.block.devices.BlockTank

object TKBlocks {
  
  val fountain = new BlockFountain("fountain") with traits.Hidden
  val tank = new BlockTank("tank")
  val misc = new BlockMisc("misc")
  val slab = new BlockSlabTK("slab") 
  val slab_double = new BlockSlabTK("slab_double")
  val stairs_qs = new BlockStairsTK("stairs_qs", misc.getStateFromMeta(1))
  
  def addBlocks() {
    
    GameRegistry.registerBlock(fountain, classOf[ItemBaseBlock], "tk_fountain")
    GameRegistry.registerBlock(tank, classOf[ItemBaseBlock], "tk_tank")
    GameRegistry.registerBlock(misc, classOf[ItemBaseBlock], "tk_misc")
    GameRegistry.registerBlock(slab, classOf[ItemBlockSlabTK], "tk_slab")
    GameRegistry.registerBlock(slab_double, classOf[ItemBaseBlock], "tk_slab_double")
    GameRegistry.registerBlock(stairs_qs, "tk_stairs_qs")
    
  }
  
 
  
}