package com.dr_benway.tknowledge.util.traits

import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraft.util.BlockPos
import net.minecraft.block.state.IBlockState



trait T_HasTE extends Block with ITileEntityProvider {
  
  def getTileClass: Class[_ <: TileEntity] = null
  
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = getTileClass.newInstance()
  
  override def isOpaqueCube(): Boolean = false
  
  override def getRenderType(): Int = -1
  
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