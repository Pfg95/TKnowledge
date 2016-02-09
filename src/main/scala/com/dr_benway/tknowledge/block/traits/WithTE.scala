package com.dr_benway.tknowledge.block.traits

import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraft.util.BlockPos
import net.minecraft.block.state.IBlockState
import com.dr_benway.tknowledge.block.tile.BaseTE
import thaumcraft.common.lib.utils.InventoryUtils
import thaumcraft.api.aura.AuraHelper
import thaumcraft.api.aspects.IEssentiaTransport
import net.minecraft.util.EnumFacing
import net.minecraft.inventory.IInventory
 
trait WithTE extends Block with ITileEntityProvider {
  
  
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = null
  
  override def breakBlock(worldIn: World, pos: BlockPos, state: IBlockState) {
    
    val tile = worldIn.getTileEntity(pos).asInstanceOf[BaseTE]
      if(tile != null && tile.isInstanceOf[IInventory]) InventoryUtils.dropItems(worldIn, pos)
      if((tile != null && tile.isInstanceOf[IEssentiaTransport]) && !worldIn.isRemote) {
        val ess = tile.asInstanceOf[IEssentiaTransport].getEssentiaAmount(EnumFacing.UP)
        if (ess > 0) AuraHelper.pollute(worldIn, pos, ess, true)
      }
    worldIn.removeTileEntity(pos)
    super.breakBlock(worldIn, pos, state)
  }

  override def onBlockEventReceived(worldIn: World, pos: BlockPos, state: IBlockState, eventID: Int, eventPar: Int): Boolean = {
    super.onBlockEventReceived(worldIn, pos, state, eventID, eventPar)
    val te: TileEntity = worldIn.getTileEntity(pos)
    if (te == null) false else te.receiveClientEvent(eventID, eventPar)
  }
  
}