package com.dr_benway.tknowledge.block.tile

import net.minecraft.util.EnumFacing
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import com.dr_benway.tknowledge.util.MultiBlockHelper



class TileSlave(clazz: String, n: Int = 1, f: EnumFacing = EnumFacing.UP) extends BaseTE {
  
  def hasMaster(): Boolean = {
    val te = f match {
      case EnumFacing.UP =>    this.worldObj.getTileEntity(this.getPos.up(n))
      case EnumFacing.DOWN =>  this.worldObj.getTileEntity(this.getPos.down(n))
      case EnumFacing.SOUTH => this.worldObj.getTileEntity(this.getPos.south(n))
      case EnumFacing.WEST =>  this.worldObj.getTileEntity(this.getPos.west(n))
      case EnumFacing.NORTH => this.worldObj.getTileEntity(this.getPos.north(n))
      case EnumFacing.EAST =>  this.worldObj.getTileEntity(this.getPos.east(n))
    }
    te.isInstanceOf[BaseTE] && te.asInstanceOf[BaseTE].isMaster()
  }
  
  var tick = 0
  
  override def update() {
    tick += 1
    if(tick >= 40) tick = 0
    
    if(!worldObj.isRemote) {
      if(tick % 20 == 0 && !hasMaster) clazz match {
        case "fountain" => MultiBlockHelper.Fountain.destroy(this.worldObj, pos.add(0, n, 0))
        case _ =>
      }
    }
    
  }
  
  override def writeToNBT(tag: NBTTagCompound) {
    super.writeToNBT(tag)
  }
  
  override def readFromNBT(tag: NBTTagCompound) {
    super.readFromNBT(tag)
  }
  
  
  
  
  
}