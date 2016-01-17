package com.dr_benway.tknowledge.block.basic

import net.minecraft.block.BlockSlab
import net.minecraft.block.material.Material
import com.dr_benway.tknowledge.Knowledge
import com.dr_benway.tknowledge.block.BaseBlock
import net.minecraft.util.BlockPos
import net.minecraft.util.MovingObjectPosition
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.block.properties.PropertyEnum
import thaumcraft.common.Thaumcraft
import com.dr_benway.tknowledge.block.EnumProperty
import java.util.Random
import com.dr_benway.tknowledge.block.TKBlocks
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.fml.relauncher.Side
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.BlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.block.Block
import net.minecraft.world.IBlockAccess



class BlockSlabTK(uName: String = "wat", mat: Material = Material.iron, hardness: Float = 2.0f, resistance: Float = 2.0f) extends {
  final val VARIANT = PropertyEnum.create("variant", classOf[EnumProperty.EnumSlabType])
} with BlockSlab(mat) {
  
    setUnlocalizedName(s"tk_$uName")
    setHardness(hardness)
    setResistance(resistance)
    
    override def isDouble() = this == TKBlocks.slab_double
    
    
    
    var state: IBlockState = this.blockState.getBaseState()
    if (!isDouble())
    {
      state = state.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM)
      setCreativeTab(Knowledge.tab)
    }
    setDefaultState(this.blockState.getBaseState.withProperty(VARIANT, EnumProperty.EnumSlabType.QS_BLOCK))
    this.useNeighborBrightness = !isDouble()
    
    override def getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item = {
      Item.getItemFromBlock(TKBlocks.slab)
  }
  
  @SideOnly(Side.CLIENT)
  override def getItem(worldIn: World, pos: BlockPos): Item = {
   Item.getItemFromBlock(TKBlocks.slab)
  }
  
  override def getUnlocalizedName(meta: Int) = getUnlocalizedName()
  
  override def getVariant(stack: ItemStack) = (getStateFromMeta(stack.getItemDamage).getValue(VARIANT)).asInstanceOf[EnumProperty.EnumSlabType]
    
  override def getVariantProperty() = VARIANT
  
  override def getStateFromMeta(meta: Int): IBlockState = {
    val state: IBlockState = if(!isDouble) {
      getDefaultState().withProperty(VARIANT, EnumProperty.EnumSlabType.QS_BLOCK).withProperty(BlockSlab.HALF, if((meta & 0x8) == 0) BlockSlab.EnumBlockHalf.BOTTOM else BlockSlab.EnumBlockHalf.TOP)
      }
    else getDefaultState().withProperty(VARIANT, EnumProperty.EnumSlabType.QS_BLOCK)
    state
  }
  
  override def getMetaFromState(state: IBlockState): Int = {
    val b0: Byte = 0
    var i: Int = b0 | state.getValue(VARIANT).asInstanceOf[EnumProperty.EnumSlabType].ordinal()
    if((!isDouble()) && (state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP)) {i |= 0x8} else null
    i
  }
  
  override def createBlockState(): BlockState = {
    if (isDouble) new BlockState(this, VARIANT) else new BlockState(this, BlockSlab.HALF, VARIANT)
  }
  
  override def damageDropped(state: IBlockState): Int = getMetaFromState(state)
  
  def getSpecialName(stack: ItemStack): String = stack.getItemDamage match {
    case 0 => "qs_block"
    case _ => "unforseen"
  }
    
  @SideOnly(Side.CLIENT)
   override def getSubBlocks(item: Item, tab: CreativeTabs, list: java.util.List[ItemStack]) {
    val i = EnumProperty.EnumSlabType.values().length
    if(!isDouble()) for(n <- 0 until i) list.add(new ItemStack(item, 1, n))
  } 
  
  override def getPickBlock(target: MovingObjectPosition, world: World, pos: BlockPos): ItemStack = {
      new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)))
    }

    
  
}