package com.dr_benway.tknowledge.block.tile.devices.fountain

import thaumcraft.api.aspects.IEssentiaTransport
import com.dr_benway.tknowledge.block.tile.BaseTE
import thaumcraft.api.aspects.IAspectContainer
import net.minecraft.util.BlockPos
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import thaumcraft.api.aspects.AspectList
import thaumcraft.api.aspects.Aspect
import net.minecraft.util.ITickable


//PaleoCrafter <3
class TileFountainEssentiaHolder extends BaseTE with IAspectContainer with IEssentiaTransport {
  
    private var masterPos: Option[BlockPos] = None
  
    private def master: Option[TileFountain] = {
      masterPos = masterPos.orElse( Some(worldObj.getTileEntity(pos.add(0, 2, 0))) map { _.getPos subtract pos } )
      masterPos map (pos add _) map worldObj.getTileEntity collect { case f: TileFountain => f } 
    }
    
    
    override def writeToNBT(nbt: NBTTagCompound): Unit = {
      super.writeToNBT(nbt)
      masterPos foreach(p => nbt.setLong("MasterPosition", p.toLong))
    }

    override def readFromNBT(nbt: NBTTagCompound): Unit = {
      super.readFromNBT(nbt)
      masterPos = if (nbt.hasKey("MasterPosition")) Some(BlockPos.fromLong(nbt.getLong("MasterPosition"))) else None
    }
    
    
    
  override def getAspects =
    master map(_.essentia) orNull
    
  override def setAspects(aspects: AspectList) =
    master map(_.setAspects(aspects)) orNull
    
  override def doesContainerAccept(par: Aspect) = true
  
  override def addToContainer(aspect: Aspect, i: Int) =
    master map(_.addToContainer(aspect, i)) getOrElse(i)
    
  override def takeFromContainer(aspect: Aspect, i: Int) =
    master exists(_.takeFromContainer(aspect, i))
  
  override def doesContainerContainAmount(aspect: Aspect, am: Int) =
    master exists(_.doesContainerContainAmount(aspect, am))
  
  override def containerContains(aspect: Aspect) =
    master map(_.containerContains(aspect)) getOrElse(0)
    
  override def takeFromContainer(al: AspectList) = false   //Deprecated
  
  override def doesContainerContain(al: AspectList)= false //Deprecated
  
  
  override def isConnectable(face: EnumFacing) = true
  
  override def canInputFrom(face: EnumFacing) = true
  
  override def canOutputTo(face: EnumFacing) = true
  
  override def setSuction(aspect: Aspect, am: Int) =
    master map(_.currentSuction = aspect)
  
  override def getSuctionType(loc: EnumFacing) =
    master map(_.currentSuction) orNull
    
  override def getSuctionAmount(loc: EnumFacing) =
    if(master exists(_.currentSuction != null)) 128 else 0
    
  override def getEssentiaType(loc: EnumFacing) = null
  
  override def getEssentiaAmount(loc: EnumFacing) = 0
  
  override def takeEssentia(aspect: Aspect, am: Int, face: EnumFacing) =
    if(master exists(_.takeFromContainer(aspect, am))) am else 0
    
  override def addEssentia(aspect: Aspect, am: Int, face: EnumFacing) =
    am - master.map(_.addToContainer(aspect, am)).getOrElse(0)
    
  override def getMinimumSuction() = 0
    
    
}