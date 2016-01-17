package com.dr_benway.tknowledge.block.tile

import net.minecraftforge.fml.common.registry.GameRegistry
import com.dr_benway.tknowledge.block.tile.devices._
import com.dr_benway.tknowledge.Knowledge


object TKTEs {
  
  def addTEs() {
    
    GameRegistry.registerTileEntity(classOf[TileSlave], "tknowledge_TE_Slave")
    GameRegistry.registerTileEntity(classOf[TileFountain], "tknowledge_TE_Fountain")
    GameRegistry.registerTileEntity(classOf[TileFountainFluidHolder], "tknowledge_TE_Fountain_FluidHolder")
    GameRegistry.registerTileEntity(classOf[TileFountainEssentiaHolder], "tknowledge_TE_Fountain_EssentiaHolder")
  }
  
  
}