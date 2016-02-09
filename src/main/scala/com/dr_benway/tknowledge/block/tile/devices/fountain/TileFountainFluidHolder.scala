package com.dr_benway.tknowledge.block.tile.devices.fountain

import com.dr_benway.tknowledge.block.tile.BaseTE
import scala.language.postfixOps
import net.minecraftforge.fluids.IFluidHandler
import net.minecraftforge.fluids.FluidContainerRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTankInfo
import net.minecraft.util.EnumFacing
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidTank
import net.minecraft.tileentity.TileEntity
import com.dr_benway.tknowledge.block.TKBlocks
import com.dr_benway.tknowledge.block.EnumProperty


class TileFountainFluidHolder extends BaseTE with IFluidHandler {
  
  var master: TileFountain = null
  
  override def update() {
   
    if(master == null) {
      master = (for {
      x <- -2 to 2
      z <- -2 to 2
      if(this.getWorld.getTileEntity(this.pos.add(x, 3, z)).isInstanceOf[TileFountain])
      } yield this.getWorld.getTileEntity(this.pos.add(x, 3, z))) headOption match {
        case Some(x: TileFountain) => x
        case _ => null
      }
    }
    
    
  
    
    
  }
  
  
  
  
  //Fluid handling
  
  override def fill(from: EnumFacing, resource: FluidStack, doFill: Boolean) = if(master != null) master.fill(from, resource, doFill) else 0
  override def drain(from: EnumFacing, resource: FluidStack, doDrain: Boolean) = if(master != null) master.drain(from, resource, doDrain) else null
  override def drain(from: EnumFacing, maxDrain: Int, doDrain: Boolean) = if(master != null) master.drain(from, maxDrain, doDrain) else null
  override def canFill(from: EnumFacing, fluid: Fluid) = if(master != null) master.canFill(from, fluid) else false
  override def canDrain(from: EnumFacing, fluid: Fluid) = if(master != null) master.canDrain(from, fluid) else false
  override def getTankInfo(from: EnumFacing) = if(master != null) master.getTankInfo(from) else null
  
}