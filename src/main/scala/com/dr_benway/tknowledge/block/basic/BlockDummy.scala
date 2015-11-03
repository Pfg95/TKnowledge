package com.dr_benway.tknowledge.block.basic

import com.dr_benway.tknowledge.block.BaseBlock
import com.dr_benway.tknowledge.block.EnumProperty
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.item.EnumDyeColor
import net.minecraft.util.IStringSerializable
import net.minecraft.block.state.BlockState
import net.minecraft.block.properties.IProperty
import com.dr_benway.tknowledge.util.traits.T_Hidden
import net.minecraft.block.state.IBlockState
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.fml.relauncher.Side


class BlockDummy(uName: String = "wat", mat: Material = Material.rock, hardness: Float = 2.0f, resistance: Float = 2.0f) extends {
    final val TYPE: PropertyEnum = PropertyEnum.create("type", classOf[EnumProperty.EnumType])
} with BaseBlock(uName, mat, hardness, resistance) {
  
  
  
  setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumProperty.EnumType.WHITE))
  
  
  override def getSpecialName(stack: ItemStack): String = stack.getItemDamage match {
    case 0 => "white"
    case 1 => "black"
    case _ => "unforseen"
  }
  
  override def createBlockState(): BlockState = new BlockState(this, TYPE)
  
  override def getStateFromMeta(meta: Int): IBlockState = {
    getDefaultState().withProperty(TYPE, meta match {
      case 0 => EnumProperty.EnumType.WHITE
      case 1 => EnumProperty.EnumType.BLACK
    })
  }
  
  override def getMetaFromState(state: IBlockState): Int = {
    val t = state.getValue(TYPE).asInstanceOf[EnumProperty.EnumType]
    t.getID
  }
  
  
  
  
  @SideOnly(Side.CLIENT)
   override def getSubBlocks(item: Item, tab: CreativeTabs, list: java.util.List[_]) {
    
    val i = EnumProperty.EnumType.values().length
    for(i <- 0 until i) list.asInstanceOf[java.util.List[ItemStack]].add(new ItemStack(item, 1, i))
    
  } 
  
}