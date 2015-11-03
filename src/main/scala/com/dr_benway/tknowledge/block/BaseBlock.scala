package com.dr_benway.tknowledge.block

import scala.math._
import scala.util._
import scala.io._
import scala.collection._
import java.util.List
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import com.dr_benway.tknowledge.Knowledge
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.util.IStringSerializable
import net.minecraft.util.BlockPos
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World



 class BaseBlock(uName: String = "wat", mat: Material = Material.rock, hardness: Float = 2.0f, resistance: Float = 2.0f) extends Block(mat) { 
 
    
    setUnlocalizedName(("tk_" + uName))
    addToTab()
    setHardness(hardness)
    setResistance(resistance)
    
    
    def addToTab() { setCreativeTab(Knowledge.tab) }
    
    
    
    def getSpecialName(stack: ItemStack): String = "?"
    
    override def damageDropped(state: IBlockState): Int = getMetaFromState(state)
    
    override def getPickBlock(target: MovingObjectPosition, world: World, pos: BlockPos): ItemStack = {
      new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)))
    }
    
   
    
    def getStatename(state: IBlockState): String = {
      
      val uName: String = state.getBlock.getUnlocalizedName
      uName.substring(uName.indexOf(".") + 1)
    }
    
   
    
    
    
    
}