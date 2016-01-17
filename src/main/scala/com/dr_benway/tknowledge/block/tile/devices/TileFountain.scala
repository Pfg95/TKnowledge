package com.dr_benway.tknowledge.block.tile.devices

import thaumcraft.api.aspects._
import com.dr_benway.tknowledge.block.tile.BaseTE
import com.dr_benway.tknowledge.util.MultiBlockHelper
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.AxisAlignedBB
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.fml.relauncher.Side
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.nbt.NBTTagList
import com.dr_benway.tknowledge.lib.crafting.FountainRecipe
import com.dr_benway.tknowledge.lib.crafting.TKCraftingManager
import net.minecraftforge.fluids.IFluidHandler
import net.minecraftforge.fluids.FluidTank
import net.minecraftforge.fluids.FluidContainerRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidTankInfo
import net.minecraftforge.fluids.FluidRegistry
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import thaumcraft.api.ThaumcraftApiHelper
import tknowledge.TKnowledgeAPI
import net.minecraft.client.Minecraft
import net.minecraft.world.IBlockAccess
import scala.util.control.Breaks._
import scala.util.Random
import thaumcraft.common.lib.aura.AuraHandler
import net.minecraftforge.oredict.OreDictionary
import com.dr_benway.tknowledge.block.TKBlocks
import thaumcraft.api.blocks.BlocksTC
import net.minecraft.block.state.IBlockState
import thaumcraft.common.blocks.world.ore.BlockCrystal
import net.minecraft.block.Block
import thaumcraft.api.aura.AuraHelper



class TileFountain extends BaseTE with ISidedInventory with IAspectContainer with IFluidHandler with IEssentiaTransport {
  
  override def isMaster() = true
  
  private var tick = 0
  private var enoughE = false
  var locked = false
  var ready = false
  var essentia = new AspectList()
  var vis = new AspectList()
  var currentSuction: Aspect = null
  var currentVis: Aspect = null
  var currentRecipe: FountainRecipe = TKCraftingManager.Fountain.defaultRecipe
  private var eCost = 1.0F
  private var vCost = 1.0F
  private var efficiency = 1.0F
  private var basePollution = 2.0F
  private var pollution = 1.0F
  private var baseSpeed = 2.0F
  private var speed = 1.0F // .5F makes the fountain faster and 1.5F makes it slower
  
  
  override def update() {
    tick += 1
    if(tick >= 1200) tick = 0
    height = calcTankHeight()
    visualHeight += ANIMATION_SPEED * Math.signum(height - visualHeight)
    
    if(!this.worldObj.isRemote) {
      if(baseSpeed == 2.0F) {
        baseSpeed = TKBlocks.fountain.getMetaFromState(this.getWorld.getBlockState(this.getPos.add(-2, -3, -2))) match {
          case 3 => 1.0F
          case 7 => 1.5F
          case 8 => 0.5F
        }
      }
      if(basePollution == 2.0F) {
        basePollution = TKBlocks.fountain.getMetaFromState(this.getWorld.getBlockState(this.getPos.add(-2, -3, -2))) match {
          case 3 => 0.5F
          case 7 => 0.2F
          case 8 => 0.8F
        }
      }
      
      if(tick % 20 == 0) MultiBlockHelper.Fountain.verify(this.worldObj, this.getPos) 
      
      if(tick % 10 == 0) {    
        if(locked == true && !this.redstone() && currentRecipe != null) {
          if(tick % 40 == 0) {
            getUpgrades()
            this.ready = false
            markReallyDirty()
          }
          enoughE = true
          craftCycle()
        }
      }
      
    }
  }
  
  private def craftCycle() {
    
    if(currentRecipe.aspects.visSize() > 0)
      drawEssentia()
    if(currentRecipe.vis_aspects.visSize() > 0 && enoughE == true)
      drawVis()
    if((enoughE == true || currentRecipe.aspects.visSize() <= 0) && this.vis.visSize() >= currentRecipe.vis_aspects.visSize())
      endRecipe()
    
  }
  
  
  private def drawEssentia() {
       
       currentSuction = null
       breakable {
          for(a <- currentRecipe.aspects.getAspectsSortedByAmount) {
          if(this.essentia.getAmount(a) < currentRecipe.aspects.getAmount(a) * eCost) {
            currentSuction = a
            break
            }
          }
      }
      
      for(dir <- EnumFacing.VALUES) {
        if(dir != EnumFacing.UP && dir != EnumFacing.DOWN && currentSuction != null) {
          val te = ThaumcraftApiHelper.getConnectableTile(worldObj, pos.add(0, -2, 0), dir)
          if(te != null) {
            val ic = te.asInstanceOf[IEssentiaTransport]
            val slave = worldObj.getTileEntity(pos.add(0, -2, 0)).asInstanceOf[TileFountainEssentiaHolder]
            if((ic.getEssentiaAmount(dir.getOpposite) > 0) && ic.getEssentiaType(dir.getOpposite) == this.currentSuction && (ic.getSuctionAmount(dir.getOpposite) < slave.getSuctionAmount(null)) && (slave.getSuctionAmount(null) >= ic.getMinimumSuction) && this.essentia.getAmount(currentSuction) < currentRecipe.aspects.getAmount(currentSuction) * eCost) {
              val ess = ic.takeEssentia(this.currentSuction, 1, dir.getOpposite)
              if(ess > 0) {
                this.addToContainer(this.currentSuction, ess)
              }
            }
          }
        }
      }
      
      breakable {
        for(a <- currentRecipe.aspects.getAspectsSortedByAmount) {
          if(this.essentia.getAmount(a) < currentRecipe.aspects.getAmount(a) * eCost) {
            enoughE = false
            break
        } 
      }
     }
  }
  
  private def drawVis() {
 
    for(a <- currentRecipe.vis_aspects.getAspectsSortedByAmount) {
    if(this.vis.getAmount(a) < currentRecipe.vis_aspects.getAmount(a) * vCost) {
      AuraHandler.drainAura(getWorld, getPos, a, 1)
      this.vis.add(a, 1)
      }
    }
  }
  
  private def endRecipe() {
    
    if(this.tank.getFluidAmount == 0 || (this.tank.getFluid.isFluidEqual(currentRecipe.result) && ( (currentRecipe.result.getFluid.getTemperature >= 1500 && this.tank.getCapacity - this.tank.getFluidAmount >= currentRecipe.result.amount * efficiency) || (currentRecipe.result.getFluid.getTemperature < 1500 && this.tank.getCapacity - this.tank.getFluidAmount >= currentRecipe.result.amount * (2 - efficiency)))) ) {
      
      if((this.getStackInSlot(0) != null && OreDictionary.itemMatches(currentRecipe.cat.asInstanceOf[ItemStack], this.getStackInSlot(0), false)) || (this.getStackInSlot(0) == null && currentRecipe.cat == null)) {
        
        if((currentRecipe.aspects.visSize() > 0 && tick % ((currentRecipe.aspects.visSize() + currentRecipe.vis_aspects.visSize()) * 3 * speed ).toInt == 0) || (currentRecipe.aspects.visSize() == 0 && tick % (20 * speed) == 0) ) {
        
        this.setInventorySlotContents(0, null)
        
        for(a <- currentRecipe.aspects.getAspectsSortedByAmount) {
          this.essentia.remove(a, (currentRecipe.aspects.getAmount(a) * eCost).toInt)
        }
      
        for(a <- currentRecipe.vis_aspects.getAspectsSortedByAmount) {
          this.vis.remove(a, (currentRecipe.vis_aspects.getAmount(a) * vCost).toInt)
        }
        
        this.ready = true
        
        this.worldObj.playSoundEffect(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "random.fizz", 0.25F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F)
        if(currentRecipe.result.getFluid.getTemperature >= 1500)
          this.tank.fill(new FluidStack(currentRecipe.result.getFluid, (currentRecipe.result.amount * efficiency).toInt), true) else
            this.tank.fill(new FluidStack(currentRecipe.result.getFluid, (currentRecipe.result.amount * (2 - efficiency)).toInt), true)  
        if((pollution * currentRecipe.pollutingChance * 100).toInt > Random.nextInt(101) - 1) AuraHelper.pollute(this.getWorld, getPos, 1, true)
        markReallyDirty()
        }
      
      }
    }
  }
  
  
  
  
  def getTankFluid() = { val f = this.tank.getFluid; if(f != null) f.getFluid else FluidRegistry.WATER }
  def refreshRecipe(player: EntityPlayer) {
    currentRecipe = TKCraftingManager.Fountain.findMatchingRecipe(this.inventory.apply(0), getTankFluid(), player)
    this.vis = new AspectList()
  }
  
  private def getUpgrades() {
    var speedPlus = 0.0F
    var efficiencyPlus = 0.0F
    var eCostPlus = 0.0F
    var vCostPlus = 0.0F
    var pollutionPlus = 0.0F
    
    def calc() {
      speed = baseSpeed + speedPlus
      efficiency = 1.0F + efficiencyPlus
      eCost = 1.0F + eCostPlus
      vCost = 1.0F + vCostPlus
      pollution = basePollution + pollutionPlus
    }
    
    (for {
      x <- -2 to 2
      z <- -2 to 2
      if((x == -2 || x == 2) && (z == -2 || z == 2))
    } this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getBlock match {
      
      case a: Block if a == BlocksTC.crystalAir => { speedPlus += (-.10F / 4) - .10F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE); vCostPlus += .10F + .10F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE) }
      case e: Block if e == BlocksTC.crystalEarth => { speedPlus += (.10F / 4) + .10F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE); vCostPlus += -.10F - .10F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE) }
      case w: Block if w == BlocksTC.crystalWater => { efficiencyPlus += (-.20F / 4) - .20F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE) }
      case f: Block if f == BlocksTC.crystalFire => { efficiencyPlus += (.20F / 4) + .20F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE) }
      case o: Block if o == BlocksTC.crystalOrder => { pollutionPlus += (-.45F / 4) -.45F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE); eCostPlus += .10F + .10F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE) }
      case e: Block if e == BlocksTC.crystalEntropy => { pollutionPlus += (.45F / 4) +.45F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE); eCostPlus += -.10F - .10F * this.getWorld.getBlockState(this.getPos.add(x, -2, z)).getValue(BlockCrystal.SIZE) }
      case _ => { }
    })
    
    calc()
  }
  
  override def writeCustomNBT(nbt: NBTTagCompound) {
    val nbttaglist = new NBTTagList()
    for(i <- 0 until this.inventory.length) {
      if(this.inventory(i) != null) {
        val nbt1 = new NBTTagCompound()
        nbt1.setByte("Slot", i.toByte)
        this.inventory(i).writeToNBT(nbt1)
        nbttaglist.appendTag(nbt1)
      }
    }
    nbt.setTag("Items", nbttaglist)
    nbt.setBoolean("locked", this.locked)
    nbt.setBoolean("ready", this.ready)
    this.essentia.writeToNBT(nbt)
    this.tank.writeToNBT(nbt)
    if(locked == true)
      nbt.setString("key", currentRecipe.key)
  }
  
  override def readCustomNBT(nbt: NBTTagCompound) {
   val nbttaglist = nbt.getTagList("Items", 10)
   this.inventory = new Array(getSizeInventory()): Array[ItemStack]
   for(i <- 0 until nbttaglist.tagCount()) {
     val nbt1 = nbttaglist.getCompoundTagAt(i)
     val b0 = nbt1.getByte("Slot")
     if((b0 >= 0) && (b0 < this.inventory.length)) {
       this.inventory(b0) = ItemStack.loadItemStackFromNBT(nbt1)
     }
   }
   this.locked = nbt.getBoolean("locked")
   this.ready = nbt.getBoolean("ready")
   this.essentia.readFromNBT(nbt)
   this.tank.readFromNBT(nbt)
   if(locked == true)
     currentRecipe = TKCraftingManager.Fountain.retrieveRecipe(nbt.getString("key")) 
  }
  
  
  
  override def writeToNBT(nbt: NBTTagCompound) {
    super.writeToNBT(nbt)
    nbt.setInteger("tick", tick)
    nbt.setFloat("baseSpeed", baseSpeed)
    nbt.setFloat("basePollution", basePollution)
    nbt.setFloat("speed", speed)
    nbt.setFloat("pollution", pollution)
    nbt.setFloat("efficiency", efficiency)
    nbt.setFloat("eCost", eCost)
    nbt.setFloat("vCost", vCost)
    this.vis.writeToNBT(nbt)
    if (hasCustomName()) {
      nbt.setString("CustomName", this.customName)
    }
  }
  
  override def readFromNBT(nbt: NBTTagCompound) {
    super.readFromNBT(nbt)
    this.tick = nbt.getInteger("tick")
    this.baseSpeed = nbt.getFloat("baseSpeed")
    this.basePollution = nbt.getFloat("basePollution")
    this.speed = nbt.getFloat("speed")
    this.pollution = nbt.getFloat("pollution")
    this.efficiency = nbt.getFloat("efficiency")
    this.eCost = nbt.getFloat("eCost")
    this.vCost = nbt.getFloat("vCost")
    this.vis.readFromNBT(nbt)
    if(nbt.hasKey("CustomName")) {
      this.customName = nbt.getString("CustomName")
    }
  }
  
  
  
  @SideOnly(Side.CLIENT)
  override def getRenderBoundingBox(): AxisAlignedBB = {
    AxisAlignedBB.fromBounds(getPos().getX() - 2.8D, getPos().getY() - 2.8D, getPos().getZ() - 2.8D, getPos().getX() + 2.8D, getPos().getY() + 3.1D, getPos().getZ() + 2.8D)
  } 
  
  
  //Essentia handling
  
  override def getAspects = this.essentia
  override def setAspects(aspects: AspectList) { this.essentia = aspects }
  override def doesContainerAccept(par: Aspect) = true
  override def addToContainer(aspect: Aspect, i: Int) = {
    val ce = this.currentRecipe.aspects.getAmount(aspect) - this.essentia.getAmount(aspect)
    if(this.currentRecipe == null || ce <= 0) i else {
      val add = Math.min(ce, i)
      this.essentia.add(aspect, add)
      markReallyDirty()
      i - add
    }
  }
  override def takeFromContainer(aspect: Aspect, i: Int) = {
    if(this.essentia.getAmount(aspect) >= i) {
      this.essentia.remove(aspect, i)
      markReallyDirty()
      true
    } else false
  }
  override def doesContainerContainAmount(aspect: Aspect, am: Int) = this.essentia.getAmount(aspect) >= am
  override def containerContains(aspect: Aspect) = this.essentia.getAmount(aspect)
  override def takeFromContainer(al: AspectList) = false   //Deprecated
  override def doesContainerContain(al: AspectList)= false //Deprecated
  
  
  override def isConnectable(face: EnumFacing) = false
  override def canInputFrom(face: EnumFacing) = false
  override def canOutputTo(face: EnumFacing) = false
  override def setSuction(aspect: Aspect, am: Int) = currentSuction = aspect
  override def getSuctionType(loc: EnumFacing) = currentSuction
  override def getSuctionAmount(loc: EnumFacing) = if(currentSuction != null) 128 else 0
  override def getEssentiaType(loc: EnumFacing) = null
  override def getEssentiaAmount(loc: EnumFacing) = 0
  override def takeEssentia(aspect: Aspect, am: Int, face: EnumFacing) = if(canOutputTo(face) && takeFromContainer(aspect, am)) am else 0
  override def addEssentia(aspect: Aspect, am: Int, face: EnumFacing) = if(canInputFrom(face)) am - addToContainer(aspect, am) else 0
  override def getMinimumSuction() = 0
  
  //Itemstack handling
  
  final private val slots = Array(0)
  private var inventory = new Array(1): Array[ItemStack]
  var customName: String = null
  
  override def getSizeInventory = 1
  override def getStackInSlot(index: Int) = this.inventory(index)
  override def getInventoryStackLimit = 1
  override def isUseableByPlayer(ep: EntityPlayer) = this.worldObj.getTileEntity(this.pos) == this
  override def openInventory(ep: EntityPlayer) {}
  override def closeInventory(ep: EntityPlayer) {}
  override def isItemValidForSlot(index: Int, stack: ItemStack) = true
  override def getSlotsForFace(side: EnumFacing) = slots
  override def canInsertItem(index: Int, stack: ItemStack, side: EnumFacing) = getStackInSlot(index) == null
  override def canExtractItem(index: Int, stack: ItemStack, side: EnumFacing) = true
  override def getField(id: Int) = 0
  override def setField(id: Int, value: Int) {}
  override def getFieldCount() = 0
  override def clear() {}
  override def hasCustomName() = (this.customName != null) && (this.customName.length > 0)
  override def getName() =  if(hasCustomName) customName else "container.tk_fountain"
  override def getDisplayName = if(hasCustomName) new ChatComponentText(getName()) else new ChatComponentTranslation(getName(), new Array(0): Array[Any])
  
  override def decrStackSize(index: Int, count: Int) = {
    if(this.inventory(index) != null) {
      if(!this.worldObj.isRemote) {
        this.worldObj.markBlockForUpdate(this.pos)
      }
      if(this.inventory(index).stackSize <= count) {
        val itemstack = this.inventory(index)
        this.inventory(index) = null
        markDirty()
        itemstack
      } else {
        val itemstack = this.inventory(index).splitStack(count)
        if(this.inventory(index).stackSize == 0) {
          this.inventory(index) = null
          markDirty()
          itemstack
        } else null
      }
    } else null
  }
  
  override def removeStackFromSlot(index: Int) = {
    if(this.inventory(index) != null) {
      val itemstack = this.inventory(index)
      this.inventory(index) = null
      itemstack
    } else null
  }
  
  override def setInventorySlotContents(index: Int, stack: ItemStack) {
    this.inventory(index) = stack
    if((stack != null) && (stack.stackSize > getInventoryStackLimit())) stack.stackSize = getInventoryStackLimit
    markReallyDirty()
  }
  
 //Fluid handling
  
  final val ANIMATION_SPEED = 1.0F
  final val MAX_TANK_HEIGHT = -2.30F
  final val MIN_TANK_HEIGHT = -2.76F
  final val MAX_CUP_HEIGHT = -.3F
  final val MIN_CUP_HEIGHT = -.8F
  
  
  val tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16)
  var height = MIN_TANK_HEIGHT
  var visualHeight = MIN_TANK_HEIGHT
   
  def calcTankHeight() = MIN_TANK_HEIGHT + ((MAX_TANK_HEIGHT - MIN_TANK_HEIGHT) / tank.getCapacity) * tank.getFluidAmount
  def calcCupHeight() = MIN_CUP_HEIGHT + ((MAX_CUP_HEIGHT - MIN_CUP_HEIGHT) / currentRecipe.aspects.visSize()) * this.essentia.visSize()
  
  override def fill(from: EnumFacing, resource: FluidStack, doFill: Boolean) = {
    val df = this.tank.fill(resource, doFill)
    if(df > 0 && doFill) markReallyDirty()
    df
  }
  override def drain(from: EnumFacing, resource: FluidStack, doDrain: Boolean) = {
    if(resource == null || resource.isFluidEqual(this.tank.getFluid)) null else 
      this.tank.drain(resource.amount, doDrain)
  }
  override def drain(from: EnumFacing, maxDrain: Int, doDrain: Boolean) = {
    val fs = this.tank.drain(maxDrain, doDrain)
    if((fs != null) && (doDrain)) markReallyDirty()
    fs
  }
  override def canFill(from: EnumFacing, fluid: Fluid) = false
  override def canDrain(from: EnumFacing, fluid: Fluid) = false
  override def getTankInfo(from: EnumFacing): Array[FluidTankInfo] = Array[FluidTankInfo](this.tank.getInfo)

}