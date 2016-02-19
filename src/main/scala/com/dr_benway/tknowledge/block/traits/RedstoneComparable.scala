package com.dr_benway.tknowledge.block.traits

import net.minecraft.block.Block
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import net.minecraft.inventory.IInventory
import net.minecraftforge.fluids.IFluidHandler
import net.minecraft.util.EnumFacing
import net.minecraft.inventory.Container


trait RedstoneComparable extends Block {
  
  override def hasComparatorInputOverride = true
  
  override def getComparatorInputOverride(world: World, pos: BlockPos) =
    world.getTileEntity(pos) match {
      case fh: IFluidHandler => calcTankSignal(world, pos, fh)
      case i: IInventory => Container.calcRedstone(i)
      case _ => 0
    }
  
  def calcTankSignal(world: World, pos: BlockPos, fh: IFluidHandler) = {
    fh.getTankInfo(EnumFacing.UP).foldLeft(0)(_ + _.fluid.amount) / fh.getTankInfo(EnumFacing.UP).foldLeft(0)(_ + _.capacity) * 15
  }
  
}