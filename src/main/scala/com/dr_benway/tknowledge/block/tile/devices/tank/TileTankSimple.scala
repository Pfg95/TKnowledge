package com.dr_benway.tknowledge.block.tile.devices.tank

import com.dr_benway.tknowledge.block.tile.BaseTE
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fluids.IFluidHandler
import net.minecraftforge.fluids.FluidContainerRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTankInfo
import net.minecraft.util.EnumFacing
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidTank



class TileTankSimple extends BaseTE with IFluidHandler {
  
  
  
  
  override def update() {
    
    
    
    
    
  }
  
  
  //Tank
  val tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 32)
   
  override def fill(from: EnumFacing, resource: FluidStack, doFill: Boolean) = {
    val df = this.tank.fill(resource, doFill)
    if(df > 0 && doFill) markReallyDirty()
    df
  }
  
  override def drain(from: EnumFacing, resource: FluidStack, doDrain: Boolean) = {
    if(resource == null || resource.isFluidEqual(this.tank.getFluid)) null else 
      this.tank.drain(resource.amount, doDrain)
  }
  
  override def drain(from: EnumFacing, maxDrain: Int, doDrain: Boolean) = {
    val fs = this.tank.drain(maxDrain, doDrain)
    if((fs != null) && (doDrain)) markReallyDirty()
    fs
  }
  
  override def canFill(from: EnumFacing, fluid: Fluid) = false
  override def canDrain(from: EnumFacing, fluid: Fluid) = false
  override def getTankInfo(from: EnumFacing): Array[FluidTankInfo] = Array[FluidTankInfo](this.tank.getInfo)
  
  
  
  
  
  override def writeCustomNBT(nbt: NBTTagCompound) {
    
  }
  
  override def readCustomNBT(nbt: NBTTagCompound) {
   
  }
  
  override def writeToNBT(nbt: NBTTagCompound) {
    super.writeToNBT(nbt)
    
  }
  
  override def readFromNBT(nbt: NBTTagCompound) {
    super.readFromNBT(nbt)
    
  }
  
}