package com.dr_benway.tknowledge.block.devices

import com.dr_benway.tknowledge.block.BaseBlock
import com.dr_benway.tknowledge.block.traits
import net.minecraft.block.properties.PropertyEnum
import com.dr_benway.tknowledge.block.EnumProperty
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import net.minecraft.block.state.BlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.util.BlockPos
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.world.World
import net.minecraft.creativetab.CreativeTabs
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraft.tileentity.TileEntity
import com.dr_benway.tknowledge.block.tile.devices.TileShelve
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.entity.EntityLivingBase

class BlockShelve(uName: String, mat: Material = Material.wood, hardness: Float = 2.0f, resistance: Float = 2.0f) extends {
  final val FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL)
  final val TYPE = PropertyEnum.create("type", classOf[EnumProperty.EnumShelveType])
  
} with BaseBlock(uName, mat, hardness, resistance) with traits.WithTE {
  
  setDefaultState(this.getBlockState.getBaseState.withProperty(TYPE, EnumProperty.EnumShelveType.GREATWOOD).withProperty(FACING, EnumFacing.NORTH))
  
  override def getSpecialName(stack: ItemStack): String = stack.getItemDamage match {
      case 0 => "greatwood"
      case 1 => "silverwood"
      case _ => "unforeseen"
  }
  
  override def createBlockState(): BlockState = new BlockState(this, TYPE, FACING)
  override def isOpaqueCube() = false
  override def isFullCube() = false
  
  
  override def getStateFromMeta(meta: Int): IBlockState = {
      getDefaultState().withProperty(TYPE, meta match {
        case 0 => EnumProperty.EnumShelveType.GREATWOOD
        case 1 => EnumProperty.EnumShelveType.SILVERWOOD
      }).withProperty(FACING, EnumFacing.getFront((meta >> 1) & 1))
  }
  
  override def getMetaFromState(state: IBlockState): Int = state.getValue(TYPE).asInstanceOf[EnumProperty.EnumShelveType].getID |
    (state.getValue(FACING).getIndex << 1)
  
  override def damageDropped(state: IBlockState): Int = getMetaFromState(state)
  
  
  override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float) = {
    getMetaFromState(state) match {
      case 0 => false
      case 1 => false
      case _ => false
    }
  }
  
  override def onBlockPlaced(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase) = {
    this.getDefaultState.withProperty(FACING, placer.getHorizontalFacing.getOpposite)
  } 
  
  
  @SideOnly(Side.CLIENT)
  override def getSubBlocks(item: Item, tab: CreativeTabs, list: java.util.List[ItemStack]) {
    for(i <- 0 until EnumProperty.EnumShelveType.values().length) list.add(new ItemStack(item, 1, i))
  }
  
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileShelve()
  
  
}