package com.dr_benway.tknowledge.block.tile

import net.minecraft.tileentity.TileEntity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.Packet
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.NetworkManager
import net.minecraft.util.BlockPos
import net.minecraft.block.state.IBlockState
import net.minecraft.world.World
import net.minecraft.util.EnumFacing



class BaseTE extends TileEntity {
  
  
  def markReallyDirty() {
    worldObj.markBlockForUpdate(getPos)
    if(!worldObj.isRemote)
      markDirty()
  }
  
  
  override def readFromNBT(nbt: NBTTagCompound) {
    super.readFromNBT(nbt)
    readCustomNBT(nbt)
  }
  
  def readCustomNBT(nbt: NBTTagCompound) {}
  def writeCustomNBT(nbt: NBTTagCompound) {}
  
  override def writeToNBT(nbt: NBTTagCompound) {
    super.writeToNBT(nbt)
    writeCustomNBT(nbt)
  }
  
  override def getDescriptionPacket(): Packet[_] = {
    val nbt = new NBTTagCompound()
    writeCustomNBT(nbt)
    new S35PacketUpdateTileEntity(getPos(), 64537, nbt)
  }  
  
  override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity) {
    super.onDataPacket(net, pkt)
    readCustomNBT(pkt.getNbtCompound)
  }
  
  override def shouldRefresh(world: World, pos: BlockPos, oldState: IBlockState, newState: IBlockState): Boolean = {
    oldState.getBlock() != newState.getBlock()
  }
  
  def getFacing(): EnumFacing = {
    try {
      EnumFacing.getFront(getBlockMetadata() & 0x7)
    }
    catch {
      case e: Exception => EnumFacing.UP
    }
  }
  
  def redstone() = this.worldObj.isBlockPowered(getPos()) || (this.worldObj.isBlockIndirectlyGettingPowered(getPos()) > 0)
  def isMaster() = false
}