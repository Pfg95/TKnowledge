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
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.BlockPos
import net.minecraft.util.ITickable

//PaleoCrafter <3
class TileFountainFluidHolder extends BaseTE with IFluidHandler {
  
  private var masterPos: Option[BlockPos] = None

  private def master: Option[TileFountain] = {
    masterPos = masterPos.orElse(
      (for {
        x <- -2 to 2
        z <- -2 to 2
      } yield worldObj.getTileEntity(getPos.add(x, 3, z))) collectFirst {
        case f: TileFountain => f
        } map { _.getPos subtract pos })
    masterPos map (pos add _) map worldObj.getTileEntity collect { case f: TileFountain => f }
  }

  override def writeToNBT(nbt: NBTTagCompound): Unit = {
    super.writeToNBT(nbt)
    masterPos foreach(p => nbt.setLong("MasterPosition", p.toLong))
  }

  override def readFromNBT(nbt: NBTTagCompound): Unit = {
    super.readFromNBT(nbt)
    masterPos = if (nbt.hasKey("MasterPosition")) Some(BlockPos.fromLong(nbt.getLong("MasterPosition"))) else None
  }

  override def fill(from: EnumFacing, resource: FluidStack, doFill: Boolean) =
    master map(_.fill(from, resource, doFill)) getOrElse(0)

  override def drain(from: EnumFacing, resource: FluidStack, doDrain: Boolean) =
    master map(_.drain(from, resource, doDrain)) orNull

  override def drain(from: EnumFacing, maxDrain: Int, doDrain: Boolean) =
    master map(_.drain(from, maxDrain, doDrain)) orNull

  override def canFill(from: EnumFacing, fluid: Fluid) =
    master exists(_.canFill(from, fluid))

  override def canDrain(from: EnumFacing, fluid: Fluid) =
    master exists(_.canDrain(from, fluid))

  override def getTankInfo(from: EnumFacing) =
    master map(_.getTankInfo(from)) orNull
    
    
}