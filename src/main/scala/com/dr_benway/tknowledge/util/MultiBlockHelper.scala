package com.dr_benway.tknowledge.util

import com.dr_benway.tknowledge.block.TKBlocks
import scala.language.postfixOps
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import net.minecraft.init.Blocks
import net.minecraft.block.Block
import thaumcraft.api.blocks.BlocksTC
import thaumcraft.common.blocks.basic._
import com.dr_benway.tknowledge.block.basic.BlockMisc
import com.dr_benway.tknowledge.block.EnumProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.block.BlockStairs
import net.minecraft.util.EnumFacing
import com.dr_benway.tknowledge.block.devices.BlockFountain
import com.dr_benway.tknowledge.block.tile.devices.fountain.TileFountain
import com.dr_benway.tknowledge.block.tile.devices.fountain.TileFountainEssentiaHolder
import net.minecraft.block.BlockDynamicLiquid
import net.minecraft.block.BlockLiquid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack



object MultiBlockHelper {
  
  case class Point(x: Int, y: Int, z: Int)
  val helper = new StateHelper
 
  object Fountain {
    
    
    val b0 = Blocks.air
    val b1 = TKBlocks.fountain
    val blueprint = List(
        Point(-2, -3, -2) -> b1, Point(-1, -3, -2) -> b1, Point(0, -3, -2) -> b1, Point(1, -3, -2) -> b1, Point(2, -3, -2) -> b1,
        Point(-2, -3, -1) -> b1, Point(-1, -3, -1) -> b1, Point(0, -3, -1) -> b1, Point(1, -3, -1) -> b1, Point(2, -3, -1) -> b1,
        Point(-2, -3, 0)  -> b1, Point(-1, -3, 0)  -> b1, Point(0, -3, 0)  -> b1, Point(1, -3, 0)  -> b1, Point(2, -3, 0)  -> b1,
        Point(-2, -3, 1)  -> b1, Point(-1, -3, 1)  -> b1, Point(0, -3, 1)  -> b1, Point(1, -3, 1)  -> b1, Point(2, -3, 1)  -> b1,
        Point(-2, -3, 2)  -> b1, Point(-1, -3, 2)  -> b1, Point(0, -3, 2)  -> b1, Point(1, -3, 2)  -> b1, Point(2, -3, 2)  -> b1,
       
        Point(-2, -2, -2) -> b0, Point(-1, -2, -2) -> b0, Point(0, -2, -2) -> b0, Point(1, -2, -2) -> b0, Point(2, -2, -2) -> b0,
        Point(-2, -2, -1) -> b0, Point(-1, -2, -1) -> b0, Point(0, -2, -1) -> b0, Point(1, -2, -1) -> b0, Point(2, -2, -1) -> b0,
        Point(-2, -2, 0)  -> b0, Point(-1, -2, 0)  -> b0, Point(0, -2, 0)  -> b1, Point(1, -2, 0)  -> b0, Point(2, -2, 0)  -> b0,
        Point(-2, -2, 1)  -> b0, Point(-1, -2, 1)  -> b0, Point(0, -2, 1)  -> b0, Point(1, -2, 1)  -> b0, Point(2, -2, 1)  -> b0,
        Point(-2, -2, 2)  -> b0, Point(-1, -2, 2)  -> b0, Point(0, -2, 2)  -> b0, Point(1, -2, 2)  -> b0, Point(2, -2, 2)  -> b0,
        
        Point(-2, -1, -2) -> b0, Point(-1, -1, -2) -> b0, Point(0, -1, -2) -> b0, Point(1, -1, -2) -> b0, Point(2, -1, -2) -> b0,
        Point(-2, -1, -1) -> b0, Point(-1, -1, -1) -> b1, Point(0, -1, -1) -> b1, Point(1, -1, -1) -> b1, Point(2, -1, -1) -> b0,
        Point(-2, -1, 0)  -> b0, Point(-1, -1, 0)  -> b1, Point(0, -1, 0)  -> b1, Point(1, -1, 0)  -> b1, Point(2, -1, 0)  -> b0,
        Point(-2, -1, 1)  -> b0, Point(-1, -1, 1)  -> b1, Point(0, -1, 1)  -> b1, Point(1, -1, 1)  -> b1, Point(2, -1, 1)  -> b0,
        Point(-2, -1, 2)  -> b0, Point(-1, -1, 2)  -> b0, Point(0, -1, 2)  -> b0, Point(1, -1, 2)  -> b0, Point(2, -1, 2)  -> b0
    )
    
    def verify(world: World, pos: BlockPos) {
      val struct = (for {
      y <- -3 to -1
      z <- -2 to 2
      x <- -2 to 2
    } yield Point(x, y, z) -> (world.getBlockState(pos.add(x, y, z)).getBlock)) toList
    
    if( struct.filter(_._2 == b1) != blueprint.filter(_._2 == b1) ) destroy(world, pos)
    }
    
    
    private def mapBlock(world: World, pos: BlockPos): IBlockState = {
      
      def neighbour(world: World, pos: BlockPos): Boolean = {
        val b = world.getBlockState(pos).getBlock
        b.isInstanceOf[BlockMetalTC] || b.isInstanceOf[BlockStairsTC] || b.isInstanceOf[BlockStoneSlabTC] || b.isInstanceOf[BlockFountain]
      }
      
      
      TKBlocks.fountain.getMetaFromState(world.getBlockState(pos)) match {
        case 0 => helper.getThaumium
        case 1 => helper.getAmber
        case 2 => TKBlocks.misc.getDefaultState.withProperty(TKBlocks.misc.TYPE, EnumProperty.EnumMiscType.QS_CONS)
        case 3 => helper.getThaumium
        case 4 => helper.getArcaneSlab
        case 5 => TKBlocks.misc.getDefaultState.withProperty(TKBlocks.misc.TYPE, EnumProperty.EnumMiscType.QS_BLOCK)
        case 6 =>
        if(!neighbour(world, pos.north))
          BlocksTC.stairsArcane.getDefaultState.withProperty(BlockStairs.FACING, EnumFacing.NORTH) else
            if(!neighbour(world, pos.south))
              BlocksTC.stairsArcane.getDefaultState.withProperty(BlockStairs.FACING, EnumFacing.SOUTH) else
                if(!neighbour(world, pos.west))
                  BlocksTC.stairsArcane.getDefaultState.withProperty(BlockStairs.FACING, EnumFacing.WEST) else
                    if(!neighbour(world, pos.east))
                      BlocksTC.stairsArcane.getDefaultState.withProperty(BlockStairs.FACING, EnumFacing.EAST) else
                        BlocksTC.stairsArcane.getDefaultState.withProperty(BlockStairs.FACING, EnumFacing.UP)
        case 7 => helper.getBrass
        case 8 => helper.getVoid
        case 9 => TKBlocks.misc.getDefaultState.withProperty(TKBlocks.misc.TYPE, EnumProperty.EnumMiscType.QS_BLOCK)
        case 10 => helper.getBrass
        case 11 => helper.getVoid
        case _ => null
      }
    }
    
    def destroy(world: World, pos: BlockPos) {
      /*
      val te = world.getTileEntity(pos) match {
        case f: TileFountain => f
        case _ => world.getTileEntity(pos.add(0, -2, 0)) match {
          case h: TileFountainEssentiaHolder => h
          case _ => null
        }
      }
      */
      var am = 0
      //var am = if(te != null) te match { case f: TileFountain => f.tank.getFluidAmount / 1000  case h: TileFountainEssentiaHolder => h.famount / 1000  case _ => 0 } else 0
      for {
      y <- -3 to 0
      z <- -2 to 2
      x <- -2 to 2
    } if(world.getBlockState(pos.add(x, y, z)).getBlock == TKBlocks.fountain) {
      world.setBlockState(pos.add(x, y, z), mapBlock(world, pos.add(x, y, z)))
      } else if(am >= 1) {
        //world.setBlockState(pos.add(x, y, z), te match { case f: TileFountain => f.tank.getFluid.getFluid.getBlock.getDefaultState  case h: TileFountainEssentiaHolder => h.fluid.getBlock.getDefaultState })
        am -= 1
      }
       
    }
    
    
    
    
  }
  
  
  
  
  
}