package com.dr_benway.tknowledge.block.basic

import net.minecraft.block.material.Material
import com.dr_benway.tknowledge.block.BaseBlock
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.item.ItemStack
import net.minecraft.block.state.BlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraft.block.properties.PropertyEnum
import com.dr_benway.tknowledge.block.EnumProperty
import net.minecraft.util.BlockPos
import net.minecraft.world.IBlockAccess


 
class BlockMisc(uName: String, mat: Material = Material.iron, hardness: Float = 2.0f, resistance: Float = 2.0f) extends {
    final val TYPE = PropertyEnum.create("type", classOf[EnumProperty.EnumMiscType])
} with BaseBlock(uName, mat, hardness, resistance) {
  
  
  setDefaultState(this.blockState.getBaseState.withProperty(TYPE, EnumProperty.EnumMiscType.QS_CONS))
  
  override def getSpecialName(stack: ItemStack): String = stack.getItemDamage match {
    case 0 => "qs_cons"
    case 1 => "qs_block"
    case _ => "unforeseen"
  }
  
  override def isBeaconBase(worldObj: IBlockAccess, pos: BlockPos, beacon: BlockPos) = getMetaFromState(worldObj.getBlockState(pos)) == 1
  
  override def createBlockState(): BlockState = new BlockState(this, TYPE)
  
  override def getStateFromMeta(meta: Int): IBlockState = {
    getDefaultState().withProperty(TYPE, meta match {
      case 0 => EnumProperty.EnumMiscType.QS_CONS
      case 1 => EnumProperty.EnumMiscType.QS_BLOCK
      
    })
  }
  
  override def getMetaFromState(state: IBlockState): Int = {
      val t = state.getValue(TYPE).asInstanceOf[EnumProperty.EnumMiscType]
      t.getID
    }
  
  
  
  @SideOnly(Side.CLIENT)
   override def getSubBlocks(item: Item, tab: CreativeTabs, list: java.util.List[ItemStack]) {
    val i = EnumProperty.EnumMiscType.values().length
    for(n <- 0 until i) list.add(new ItemStack(item, 1, n))
  } 
  
  
}