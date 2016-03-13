package com.dr_benway.tknowledge.lib

import thaumcraft.api.wands.IWandTriggerManager
import net.minecraft.util.BlockPos
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import thaumcraft.common.lib.research.ResearchManager
import thaumcraft.api.wands.IWand
import thaumcraft.api.aspects.Aspect
import thaumcraft.api.aspects.AspectList
import net.minecraft.block.Block
import thaumcraft.api.blocks.BlocksTC
import com.dr_benway.tknowledge.block.TKBlocks
import net.minecraft.init.Blocks
import scala.language.postfixOps
import com.dr_benway.tknowledge.block.EnumProperty
import thaumcraft.common.blocks.basic.BlockMetalTC
import net.minecraft.block.state.IBlockState
import net.minecraftforge.fml.common.network.NetworkRegistry
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle
import thaumcraft.common.lib.network.PacketHandler
import thaumcraft.common.blocks.basic.BlockTranslucent
import thaumcraft.common.blocks.basic.BlockStoneSlabTC
import net.minecraft.block.BlockStairs
import thaumcraft.common.blocks.basic.BlockStairsTC
import com.dr_benway.tknowledge.Knowledge
import thaumcraft.api.wands.WandTriggerRegistry
import com.dr_benway.tknowledge.util.MultiBlockHelper

object TKWandManager extends IWandTriggerManager {
  
  override def performTrigger(world: World, is: ItemStack, eplayer: EntityPlayer, pos: BlockPos, facing: EnumFacing, event: Int) = {
    
    event match {
      case 0 => if(ResearchManager.isResearchComplete(eplayer.getName, "FOUNTAIN"))
        createFountain(is, eplayer, world, pos, facing) else false
      case _ => false
    }
  }
  
  private def createFountain(is: ItemStack, player: EntityPlayer, world: World, pos: BlockPos, facing: EnumFacing) = {
    
     val wand = is.getItem().asInstanceOf[IWand]
     if(fitFountain(world, pos) && (wand.consumeAllVis(is, player, new AspectList().add(Aspect.FIRE, 150).add(Aspect.EARTH, 150).add(Aspect.ORDER, 150).add(Aspect.AIR, 150).add(Aspect.ENTROPY, 150).add(Aspect.WATER, 150), true, true))) {
       if(!world.isRemote) placeFountain(world, pos) else false
     } else false
  }
  
  private def fitFountain(world: World, pos: BlockPos) = {
    
    case class Point(x: Int, y: Int, z: Int) 
    
    val b0 = Blocks.air.getDefaultState
    val b1t = MultiBlockHelper.helper.getThaumium
    val b1b = MultiBlockHelper.helper.getBrass
    val b1v = MultiBlockHelper.helper.getVoid
    val b2n = BlocksTC.stairsArcane.getDefaultState.withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.OUTER_RIGHT)
    val b2s = BlocksTC.stairsArcane.getDefaultState.withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.OUTER_RIGHT)
    val b2w = BlocksTC.stairsArcane.getDefaultState.withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.OUTER_RIGHT)
    val b2e = BlocksTC.stairsArcane.getDefaultState.withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.OUTER_RIGHT)
    val b3 = MultiBlockHelper.helper.getArcaneSlab
    val b4 = MultiBlockHelper.helper.getAmber
    val b5 = TKBlocks.misc.getDefaultState.withProperty(TKBlocks.misc.TYPE, EnumProperty.EnumMiscType.QS_BLOCK) 

    val blueprint_t = List(
        Point(-2, -3, -2) -> b1t, Point(-1, -3, -2) -> b2n, Point(0, -3, -2) -> b2n, Point(1, -3, -2) -> b2n, Point(2, -3, -2) -> b1t,
        Point(-2, -3, -1) -> b2w, Point(-1, -3, -1) -> b3, Point(0, -3, -1) -> b3, Point(1, -3, -1) -> b3, Point(2, -3, -1) -> b2e,
        Point(-2, -3, 0)  -> b2w, Point(-1, -3, 0)  -> b3, Point(0, -3, 0)  -> b1t, Point(1, -3, 0)  -> b3, Point(2, -3, 0)  -> b2e,
        Point(-2, -3, 1)  -> b2w, Point(-1, -3, 1)  -> b3, Point(0, -3, 1)  -> b3, Point(1, -3, 1)  -> b3, Point(2, -3, 1)  -> b2e,
        Point(-2, -3, 2)  -> b1t, Point(-1, -3, 2)  -> b2s, Point(0, -3, 2)  -> b2s, Point(1, -3, 2)  -> b2s, Point(2, -3, 2)  -> b1t,
        
        Point(-2, -2, -2) -> b0, Point(-1, -2, -2) -> b0, Point(0, -2, -2) -> b0, Point(1, -2, -2) -> b0, Point(2, -2, -2) -> b0,
        Point(-2, -2, -1) -> b0, Point(-1, -2, -1) -> b0, Point(0, -2, -1) -> b0, Point(1, -2, -1) -> b0, Point(2, -2, -1) -> b0,
        Point(-2, -2, 0)  -> b0, Point(-1, -2, 0)  -> b0, Point(0, -2, 0)  -> b4, Point(1, -2, 0)  -> b0, Point(2, -2, 0)  -> b0,
        Point(-2, -2, 1)  -> b0, Point(-1, -2, 1)  -> b0, Point(0, -2, 1)  -> b0, Point(1, -2, 1)  -> b0, Point(2, -2, 1)  -> b0,
        Point(-2, -2, 2)  -> b0, Point(-1, -2, 2)  -> b0, Point(0, -2, 2)  -> b0, Point(1, -2, 2)  -> b0, Point(2, -2, 2)  -> b0,
        
        Point(-2, -1, -2) -> b0, Point(-1, -1, -2) -> b0, Point(0, -1, -2) -> b0, Point(1, -1, -2) -> b0, Point(2, -1, -2) -> b0,
        Point(-2, -1, -1) -> b0, Point(-1, -1, -1) -> b5, Point(0, -1, -1) -> b5, Point(1, -1, -1) -> b5, Point(2, -1, -1) -> b0,
        Point(-2, -1, 0)  -> b0, Point(-1, -1, 0)  -> b5, Point(0, -1, 0)  -> b5, Point(1, -1, 0)  -> b5, Point(2, -1, 0)  -> b0,
        Point(-2, -1, 1)  -> b0, Point(-1, -1, 1)  -> b5, Point(0, -1, 1)  -> b5, Point(1, -1, 1)  -> b5, Point(2, -1, 1)  -> b0,
        Point(-2, -1, 2)  -> b0, Point(-1, -1, 2)  -> b0, Point(0, -1, 2)  -> b0, Point(1, -1, 2)  -> b0, Point(2, -1, 2)  -> b0
    ) 
    
    val blueprint_b = blueprint_t map { x => if(x._2 == b1t) (x._1, b1b) else (x._1, x._2) }
    
    val blueprint_v = blueprint_t map { x => if(x._2 == b1t) (x._1, b1v) else (x._1, x._2) }
    
    val struct = (for {
      y <- -3 to -1 //east => x+, north => z-
      z <- -2 to 2
      x <- -2 to 2
    } yield Point(x, y, z) -> (world.getBlockState(pos.add(x, y, z)))) toList
    
   struct == blueprint_t || struct == blueprint_b || struct == blueprint_v
  }
  
  private def placeFountain(world: World, pos: BlockPos): Boolean = {
    
    
    setBlockSparkle(world, pos.add(0, 0, 0), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_TOP))
    setBlockSparkle(world, pos.add(0, -1, 0), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_CUP))
    setBlockSparkle(world, pos.add(0, -2, 0), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_MIDDLE))
    
    for(x <- -1 to 1) setBlockSparkle(world, pos.add(x, -3, -2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.WALL))
    for(x <- -1 to 1) setBlockSparkle(world, pos.add(x, -3, 2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.WALL))
    
    if(world.getBlockState(pos.add(-2, -3, -2)) == MultiBlockHelper.helper.getThaumium) {
      setBlockSparkle(world, pos.add(-2, -3, -2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_T))
        setBlockSparkle(world, pos.add(2, -3, -2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_T))
        setBlockSparkle(world, pos.add(-2, -3, 2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_T))
        setBlockSparkle(world, pos.add(2, -3, 2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_T))
        setBlockSparkle(world, pos.add(0, -3, 0), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_THAUMIUM))
    } else 
      if(world.getBlockState(pos.add(-2, -3, -2)) == MultiBlockHelper.helper.getBrass) {
      setBlockSparkle(world, pos.add(-2, -3, -2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_B))
        setBlockSparkle(world, pos.add(2, -3, -2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_B))
        setBlockSparkle(world, pos.add(-2, -3, 2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_B))
        setBlockSparkle(world, pos.add(2, -3, 2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_B))
        setBlockSparkle(world, pos.add(0, -3, 0), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_BRASS))
    } else
      if(world.getBlockState(pos.add(-2, -3, -2)) == MultiBlockHelper.helper.getVoid) {
      setBlockSparkle(world, pos.add(-2, -3, -2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_V))
        setBlockSparkle(world, pos.add(2, -3, -2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_V))
        setBlockSparkle(world, pos.add(-2, -3, 2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_V))
        setBlockSparkle(world, pos.add(2, -3, 2), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.SIDE_DIOPTRA_V))
        setBlockSparkle(world, pos.add(0, -3, 0), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_VOID))
    }
    
    for(x <- -1 to 1 if(x != 0)) setBlockSparkle(world, pos.add(x, -3, 0), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.FLOOR))
    for(x <- -1 to 1) setBlockSparkle(world, pos.add(x, -3, -1), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.FLOOR))
    for(x <- -1 to 1) setBlockSparkle(world, pos.add(x, -3, 1), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.FLOOR))
    
    for(z <- -1 to 1) setBlockSparkle(world, pos.add(-2, -3, z), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.WALL))
    for(z <- -1 to 1) setBlockSparkle(world, pos.add(2, -3, z), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.WALL))
    
    for(z <- -1 to 1 if (z != 0)) setBlockSparkle(world, pos.add(0, -1, z), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.GHOST))
    for(z <- -1 to 1) setBlockSparkle(world, pos.add(-1, -1, z), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.GHOST))
    for(z <- -1 to 1) setBlockSparkle(world, pos.add(1, -1, z), TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.GHOST))
    
    
    PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(pos, 55537), new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), pos.getX(), pos.getY(), pos.getZ(), 32.0D))
    world.playSoundEffect(pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D, "thaumcraft:wand", 1.0F, 1.0F)
    true
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  def regWandTriggers() {
    WandTriggerRegistry.registerWandBlockTrigger(TKWandManager, 0, TKBlocks.misc.getDefaultState, Knowledge.MODID)
  }
  
  private def setBlockSparkle(world: World, pos: BlockPos, state: IBlockState) {
    world.setBlockState(pos, state)
    PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(pos, 55537), new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), pos.getX(), pos.getY(), pos.getZ(), 32.0D))
  }
  
  
}