package com.dr_benway.tknowledge.block.traits

import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraft.util.BlockPos
import net.minecraft.block.state.IBlockState
 
trait WithTE extends Block with ITileEntityProvider {
  
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = null
  
  
  override def breakBlock(worldIn: World, pos: BlockPos, state: IBlockState) {
    super.breakBlock(worldIn, pos, state)
    worldIn.removeTileEntity(pos)
  }

  override def onBlockEventReceived(worldIn: World, pos: BlockPos, state: IBlockState, eventID: Int, eventPar: Int): Boolean = {
    super.onBlockEventReceived(worldIn, pos, state, eventID, eventPar)
    val te: TileEntity = worldIn.getTileEntity(pos)
    if (te == null) false else te.receiveClientEvent(eventID, eventPar)
  }
  
}