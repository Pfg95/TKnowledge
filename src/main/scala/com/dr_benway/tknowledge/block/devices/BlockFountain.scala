package com.dr_benway.tknowledge.block.devices

import scala.language.postfixOps
import com.dr_benway.tknowledge.block.traits
import com.dr_benway.tknowledge.block.BaseBlock
import com.dr_benway.tknowledge.block.EnumProperty.EnumFountainType
import com.dr_benway.tknowledge.block.tile.BaseTE
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockState
import net.minecraft.block.state.IBlockState
import thaumcraft.api.wands.IWandable
import net.minecraftforge.fluids.FluidContainerRegistry
import com.dr_benway.tknowledge.block.TKBlocks
import com.dr_benway.tknowledge.block.tile.devices.fountain.TileFountainFluidHolder
import com.dr_benway.tknowledge.block.tile.devices.fountain.TileFountain
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraft.util.BlockPos
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle
import thaumcraft.common.lib.utils.InventoryUtils
import thaumcraft.common.lib.network.PacketHandler
import thaumcraft.api.aura.AuraHelper
import net.minecraft.tileentity.TileEntity
import net.minecraft.entity.player.EntityPlayer
import com.dr_benway.tknowledge.block.tile.devices.fountain.TileFountainEssentiaHolder
import com.dr_benway.tknowledge.block.tile.TileSlave
import thaumcraft.api.aspects.IEssentiaTransport
import com.dr_benway.tknowledge.block.EnumProperty
import net.minecraft.util.EnumFacing
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.world.IBlockAccess
import net.minecraft.item.ItemStack
import thaumcraft.api.blocks.BlocksTC
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.Random
import net.minecraft.item.Item
import net.minecraft.init.Items
import net.minecraftforge.fml.relauncher.Side
import net.minecraft.inventory.IInventory
import net.minecraft.util.ChatComponentTranslation
import thaumcraft.api.aspects.AspectList



class BlockFountain(uName: String, mat: Material = Material.rock, hardness: Float = 2.0f, resistance: Float = 2.0f) extends {
      final val TYPE = PropertyEnum.create("type", classOf[EnumProperty.EnumFountainType])
      final val NORTH = PropertyBool.create("north")
      final val SOUTH = PropertyBool.create("south")
      final val EAST = PropertyBool.create("east")
      final val WEST = PropertyBool.create("west")
} with BaseBlock(uName, mat, hardness, resistance) with traits.WithTE with IWandable {
    
    
    setDefaultState(this.getBlockState.getBaseState.withProperty(TYPE, EnumProperty.EnumFountainType.PILLAR_THAUMIUM).withProperty(NORTH, Boolean.box(false)).withProperty(SOUTH, Boolean.box(false)).withProperty(EAST, Boolean.box(false)).withProperty(WEST, Boolean.box(false)))
    
    override def getSpecialName(stack: ItemStack): String = stack.getItemDamage match {
      case 0 => "pillar_thaumium"
      case 1 => "pillar_middle"
      case 2 => "pillar_top"
      case 3 => "side_dioptra_thaumium"
      case 4 => "floor"
      case 5 => "ghost"
      case 6 => "wall"
      case 7 => "side_dioptra_brass"
      case 8 => "side_dioptra_void"
      case 9 => "pillar_cup"
      case _ => "unforeseen"
    }
    
    override def createBlockState(): BlockState = new BlockState(this, TYPE, NORTH, SOUTH, EAST, WEST)
    
    override def isOpaqueCube() = false
    override def isFullCube() = false
    override def isSideSolid(world: IBlockAccess, pos: BlockPos, side: EnumFacing) = ((getMetaFromState(world.getBlockState(pos)) == 3 || getMetaFromState(world.getBlockState(pos)) == 7 || getMetaFromState(world.getBlockState(pos)) == 8) && side == EnumFacing.UP)
    
    override def getStateFromMeta(meta: Int): IBlockState = {
      getDefaultState().withProperty(TYPE, meta match {
        case 0 => EnumProperty.EnumFountainType.PILLAR_THAUMIUM
        case 1 => EnumProperty.EnumFountainType.PILLAR_MIDDLE
        case 2 => EnumProperty.EnumFountainType.PILLAR_TOP
        case 3 => EnumProperty.EnumFountainType.SIDE_DIOPTRA_T
        case 4 => EnumProperty.EnumFountainType.FLOOR
        case 5 => EnumProperty.EnumFountainType.GHOST
        case 6 => EnumProperty.EnumFountainType.WALL
        case 7 => EnumProperty.EnumFountainType.SIDE_DIOPTRA_B
        case 8 => EnumProperty.EnumFountainType.SIDE_DIOPTRA_V
        case 9 => EnumProperty.EnumFountainType.PILLAR_CUP
        case 10=> EnumProperty.EnumFountainType.PILLAR_BRASS
        case 11=> EnumProperty.EnumFountainType.PILLAR_VOID
      })
    }
    
    override def getMetaFromState(state: IBlockState): Int = state.getValue(TYPE).asInstanceOf[EnumProperty.EnumFountainType].getID
    
    private def hasNextBlock(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos) = {
      Boolean.box(!(worldIn.getBlockState(pos).getBlock.isInstanceOf[BlockFountain]) && (getMetaFromState(state) == 6))
    }
    
    override def getActualState(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): IBlockState = {
      state.withProperty(NORTH, hasNextBlock(state, worldIn, pos.north)).withProperty(SOUTH, hasNextBlock(state, worldIn, pos.south)).withProperty(EAST, hasNextBlock(state, worldIn, pos.east)).withProperty(WEST, hasNextBlock(state, worldIn, pos.west))//.withProperty(HASTOP, hasTop(state, worldIn, pos))
    } 
    
    
    override def getCollisionBoundingBox(worldIn: World, pos: BlockPos, state: IBlockState) = {
      setBlockBoundsBasedOnState(worldIn, pos)
      super.getCollisionBoundingBox(worldIn, pos, state)
    } 
    
    
    override def setBlockBoundsBasedOnState(worldIn: IBlockAccess, pos: BlockPos) {
      getMetaFromState(worldIn.getBlockState(pos)) match {
        case 0 => this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F)
        case 1 => this.setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 1.0F, 0.85F)
        case 2 => this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F)
        case 4 => this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F)
        case 5 => if(worldIn.getBlockState(pos.north).getBlock == worldIn.getBlockState(pos.east).getBlock && worldIn.getBlockState(pos.east).getBlock != TKBlocks.fountain)
          this.setBlockBounds(0.0F, 0.1F, 0.5F, 0.5F, 0.65F, 1.0F) else
            if(worldIn.getBlockState(pos.east).getBlock == worldIn.getBlockState(pos.south).getBlock && worldIn.getBlockState(pos.east).getBlock != TKBlocks.fountain)
              this.setBlockBounds(0.0F, 0.1F, 0.0F, 0.5F, 0.65F, 0.5F) else
                if(worldIn.getBlockState(pos.south).getBlock == worldIn.getBlockState(pos.west).getBlock && worldIn.getBlockState(pos.west).getBlock != TKBlocks.fountain)
                  this.setBlockBounds(0.5F, 0.1F, 0.0F, 1.0F, 0.65F, 0.5F) else
                    if(worldIn.getBlockState(pos.west).getBlock == worldIn.getBlockState(pos.north).getBlock && worldIn.getBlockState(pos.north).getBlock != TKBlocks.fountain)
                      this.setBlockBounds(0.5F, 0.1F, 0.5F, 1.0F, 0.65F, 1.0F) else
                        if(worldIn.getBlockState(pos.north) == TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_CUP))
                          this.setBlockBounds(0.0F, 0.1F, 0.0F, 1.0F, 0.65F, 0.5F) else
                            if(worldIn.getBlockState(pos.east) == TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_CUP))
                              this.setBlockBounds(0.5F, 0.1F, 0.0F, 1.0F, 0.65F, 1.0F) else
                                if(worldIn.getBlockState(pos.south) == TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_CUP))
                                  this.setBlockBounds(0.0F, 0.1F, 0.5F, 1.0F, 0.65F, 1.0F) else
                                    if(worldIn.getBlockState(pos.west) == TKBlocks.fountain.getDefaultState.withProperty(TKBlocks.fountain.TYPE, EnumProperty.EnumFountainType.PILLAR_CUP))
                                      this.setBlockBounds(0.0F, 0.1F, 0.0F, 0.5F, 0.65F, 1.0F)
        case 9 => this.setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 1.0F, 0.85F)
        case _ => this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F)
      }
    }
    
    override def damageDropped(state: IBlockState): Int = {
      getMetaFromState(state) match {
        case 0 => 0
        case 1 => 0
        case 2 => 0
        case 3 => 0
        case 4 => 0
        case 5 => 1
        case 6 => 0
        case 7 => 4
        case 8 => 1
        case 9 => 0
        case 10=> 4
        case 11=> 1
        case _ => 0
      }
    }
    
    override def getItemDropped(state: IBlockState, rand: Random, fortune: Int) = {
      getMetaFromState(state) match {
        case 0 => Item.getItemFromBlock(BlocksTC.metal)
        case 1 => Item.getItemFromBlock(BlocksTC.translucent)
        case 2 => Item.getItemFromBlock(TKBlocks.misc)
        case 3 => Item.getItemFromBlock(BlocksTC.metal)
        case 4 => Item.getItemFromBlock(BlocksTC.slabStone)
        case 5 => Item.getItemFromBlock(TKBlocks.misc)
        case 6 => Item.getItemFromBlock(BlocksTC.stairsArcane)
        case 7 => Item.getItemFromBlock(BlocksTC.metal)
        case 8 => Item.getItemFromBlock(BlocksTC.metal)
        case 9 => Item.getItemFromBlock(BlocksTC.translucent)
        case 10=>Item.getItemFromBlock(BlocksTC.metal)
        case 11=>Item.getItemFromBlock(BlocksTC.metal)
        case _ => null
      }
    }
    
    override def getLightValue(world: IBlockAccess, pos: BlockPos) = {
    
      val te = if(world.getTileEntity(pos) != null && world.getTileEntity(pos).isInstanceOf[TileFountain])
        world.getTileEntity(pos).asInstanceOf[TileFountain] else null
        if(te != null && te.tank.getFluid != null) te.tank.getFluid.getFluid.getLuminosity else super.getLightValue(world, pos)
      
    }
    
    override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float) = {
       
      getMetaFromState(state) match {
      case 2 => {
        if(world.isRemote) true
        
          val te = world.getTileEntity(pos)
          if(te != null && te.isInstanceOf[TileFountain]) {
            val cont = te.asInstanceOf[TileFountain]
            if(cont.getStackInSlot(0) == null && player.inventory.getCurrentItem != null) {
              var i = player.getCurrentEquippedItem.copy()
              i.stackSize = 1
              cont.setInventorySlotContents(0, i)
              player.getCurrentEquippedItem.stackSize -= 1
              if (player.getCurrentEquippedItem.stackSize == 0) {
                player.setCurrentItemOrArmor(0, null)
              }
              player.inventory.markDirty()
              world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.6F)
              if(cont.locked == false) {
                cont.refreshRecipe(player)
                cont.markReallyDirty()
              }
              true
            } else
              if(cont.getStackInSlot(0) != null) {
                InventoryUtils.dropItemsAtEntity(world, pos, player)
                cont.setInventorySlotContents(0, null)
                world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F)
                if(cont.locked == false) {
                  cont.refreshRecipe(player)
                  cont.markReallyDirty()
                }
                true
              } else true
          } else true 
  
      }   
      case 6 => { //God knows why case 4 | 6 isn't working...
      if(world.isRemote) true  
      
      
      val master = (for {
      x <- -2 to 2
      z <- -2 to 2
      } yield world.getTileEntity(pos.add(x, 3, z)) ) collectFirst { case f: TileFountain => f } orNull
      
        if(!player.isSneaking()) {
          val fs = FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem)
          
          if(fs != null) {
            if((master.tank.getFluidAmount < master.tank.getCapacity) && (master.tank.getFluid == null || master.tank.getFluid.isFluidEqual(fs))) {
              master.tank.fill(FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem()), true)
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
              if(player.inventory.getCurrentItem != null && player.inventory.getCurrentItem.getItem == Items.bucket && master.tank.getFluidAmount >= 1000) {
                val container = FluidContainerRegistry.fillFluidContainer(master.tank.getFluid, player.inventory.getCurrentItem)
                player.inventory.decrStackSize(player.inventory.currentItem, 1)
                player.inventory.addItemStackToInventory(container)
                player.inventoryContainer.detectAndSendChanges()
                world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "game.neutral.swim", 0.33F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F)
                master.tank.drain(1000, true)
              }
          if(master.locked == false)
            master.refreshRecipe(player)
          master.markReallyDirty()
          true
           }
        else super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ)
      }
      
      case 4 => {
      if(world.isRemote) true  
      
      
      val master = (for {
      x <- -1 to 1
      z <- -1 to 1
      } yield world.getTileEntity(pos.add(x, 3, z)) ) collectFirst { case f: TileFountain => f } orNull
      
        if(!player.isSneaking()) {
          val fs = FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem)
          
          if(fs != null) {
            if((master.tank.getFluidAmount < master.tank.getCapacity) && (master.tank.getFluid == null || master.tank.getFluid.isFluidEqual(fs))) {
              master.tank.fill(FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem()), true)
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
              if(player.inventory.getCurrentItem != null && player.inventory.getCurrentItem.getItem == Items.bucket && master.tank.getFluidAmount >= 1000) {
                val container = FluidContainerRegistry.fillFluidContainer(master.tank.getFluid, player.inventory.getCurrentItem)
                player.inventory.decrStackSize(player.inventory.currentItem, 1)
                player.inventory.addItemStackToInventory(container)
                player.inventoryContainer.detectAndSendChanges()
                world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "game.neutral.swim", 0.33F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F)
                master.tank.drain(1000, true)
              }
          if(master.locked == false)
            master.refreshRecipe(player)
          master.markReallyDirty()
          true
           }
        else super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ)
      }
      
      case _ => super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ)
      
        
      }
    }
    
    
    @SideOnly(Side.CLIENT)
    override def getSubBlocks(item: Item, tab: CreativeTabs, list: java.util.List[ItemStack]) {
      val i = EnumProperty.EnumFountainType.values().length
      for(i <- 0 until i) list.add(new ItemStack(item, 1, i))
    } 
    
    
    override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = meta match {
      case 0 => new TileSlave("fountain", 3)
      case 1 => new TileFountainEssentiaHolder()
      case 2 => new TileFountain()
      case 6 => new TileFountainFluidHolder()
      case 10 => new TileSlave("fountain", 3)
      case 11 => new TileSlave("fountain", 3)
      case _ => null
    }
    /*
    override def createTileEntity(w: World, s: IBlockState) = s match {
      case getDefaultState.withProperty(TYPE, EnumProperty.EnumFountainType.PILLAR_THAUMIUM) => new TileSlave("fountain", 3)
      case _ => null
    } */
    
    
    //Wand handling
    override def onWandRightClick(world: World, is: ItemStack, eplayer: EntityPlayer, pos: BlockPos, facing: EnumFacing) = {
    
    val master = (for {
      x <- -1 to 1
      z <- -1 to 1
      } yield world.getTileEntity(pos.add(x, 1, z)) ) collectFirst { case f: TileFountain => f } orNull
    
    eplayer.swingItem()
    if(!world.isRemote && !eplayer.isSneaking() && this.getMetaFromState(world.getBlockState(pos)) == 5) {
     
      if(master.locked == false && master.essentia.visSize() == 0) master.locked = true else
        if(master.locked == false && master.essentia.visSize() > 0) {
          eplayer.addChatMessage(new ChatComponentTranslation("tk.fountain.notempty"))
        } else master.locked = false
        if(master.locked == true)
          world.playSoundEffect(master.getPos.getX(), master.getPos.getY(), master.getPos.getZ(), "thaumcraft:craftstart", 0.5F, 1.0F) else
          {
            world.playSoundEffect(master.getPos.getX(), master.getPos.getY(), master.getPos.getZ(), "thaumcraft:craftfail", 1.0F, 1.0F)
            master.currentSuction = null
          }
        for(x <- -1 to 1; z <- -1 to 1) {
          PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(master.getPos.add(x, -1, z), 55537), new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), master.getPos.getX(), master.getPos.getY(), master.getPos.getZ(), 32.0D))
        }
        master.vis = new AspectList()
        master.markReallyDirty()
      
      
      true
    } else false
  }
  
  override def onUsingWandTick(is: ItemStack, eplayer: EntityPlayer, i: Int) {}
  override def onWandStoppedUsing(is: ItemStack, world: World, eplayer: EntityPlayer, i: Int) {}
    
    
  
}