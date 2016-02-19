package com.dr_benway.tknowledge.block.devices

import scala.language.postfixOps
import com.dr_benway.tknowledge.block.traits
import com.dr_benway.tknowledge.block.BaseBlock
import net.minecraft.block.material.Material
import com.dr_benway.tknowledge.block.EnumProperty
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.item.ItemStack
import net.minecraft.block.state.BlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import com.dr_benway.tknowledge.block.tile.devices.tank.TileTankSimple
import net.minecraft.util.BlockPos
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraftforge.fluids.FluidContainerRegistry
import net.minecraft.init.Items
import net.minecraftforge.fluids.IFluidHandler

class BlockTank(uName: String, mat: Material = Material.iron, hardness: Float = 2.0f, resistance: Float = 2.0f) extends {
     
  final val TYPE = PropertyEnum.create("type", classOf[EnumProperty.EnumTankType])
  
} with BaseBlock(uName, mat, hardness, resistance) with traits.WithTE with traits.RedstoneComparable {
  
  
  setDefaultState(this.getBlockState.getBaseState.withProperty(TYPE, EnumProperty.EnumTankType.SIMPLE))
  
  override def getSpecialName(stack: ItemStack): String = stack.getItemDamage match {
      case 0 => "simple"
      case _ => "unforeseen"
  }
  
  override def createBlockState(): BlockState = new BlockState(this, TYPE)
  override def isOpaqueCube() = false
  override def isFullCube() = true
  
  override def getStateFromMeta(meta: Int): IBlockState = {
      getDefaultState().withProperty(TYPE, meta match {
        case 0 => EnumProperty.EnumTankType.SIMPLE
      })
  }
  
  override def getMetaFromState(state: IBlockState): Int = state.getValue(TYPE).asInstanceOf[EnumProperty.EnumTankType].getID
  override def damageDropped(state: IBlockState): Int = getMetaFromState(state)
  
  
  override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float) = {
    
    getMetaFromState(state) match {
      case 0 => {
        if(!world.isRemote && world.getTileEntity(pos) != null) {
          val te = world.getTileEntity(pos)
            te match {
              case ts: TileTankSimple => {
                if(!player.isSneaking()) {
                  val fs = FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem)
                  if(fs != null) {
                    if((ts.tank.getFluidAmount < ts.tank.getCapacity) && (ts.tank.getFluid == null || ts.tank.getFluid.isFluidEqual(fs))) {
                      ts.tank.fill(FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem()), true)
                      var emptyContainer: ItemStack = null
                      val fcs = FluidContainerRegistry.getRegisteredFluidContainerData()
                      for(fcd <- fcs) {
                      if (fcd.filledContainer.isItemEqual(player.inventory.getCurrentItem()))
                        emptyContainer = fcd.emptyContainer.copy()
                      }
                      player.inventory.decrStackSize(player.inventory.currentItem, 1)
                      if ((emptyContainer != null) && (!player.inventory.addItemStackToInventory(emptyContainer))) {
                        player.dropPlayerItemWithRandomChoice(emptyContainer, false)
                      }
                    player.inventoryContainer.detectAndSendChanges()
                    world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "game.neutral.swim", 0.33F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F)
                   }
                 } else
                   if(player.inventory.getCurrentItem != null && player.inventory.getCurrentItem.getItem == Items.bucket && ts.tank.getFluidAmount >= 1000) {
                      val container = FluidContainerRegistry.fillFluidContainer(ts.tank.getFluid, player.inventory.getCurrentItem)
                      player.inventory.decrStackSize(player.inventory.currentItem, 1)
                      player.inventory.addItemStackToInventory(container)
                      player.inventoryContainer.detectAndSendChanges()
                      world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "game.neutral.swim", 0.33F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F)
                      ts.tank.drain(1000, true)
                   } 
                }
                true
              }
              case _ => true
            }
        } else true
      }
      case _ => false
    }
  }
  
  
  
  @SideOnly(Side.CLIENT)
  override def getSubBlocks(item: Item, tab: CreativeTabs, list: java.util.List[ItemStack]) {
    //list.add(new ItemStack(item, 1, 0))
  }
  
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = meta match {
      case 0 => new TileTankSimple()
      case _ => null
    }
  
}