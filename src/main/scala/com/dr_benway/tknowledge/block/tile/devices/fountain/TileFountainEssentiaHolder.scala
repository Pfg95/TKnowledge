package com.dr_benway.tknowledge.block.tile.devices.fountain

import com.dr_benway.tknowledge.block.tile.BaseTE
import thaumcraft.api.aspects.IEssentiaTransport
import thaumcraft.api.aspects.IAspectContainer
import thaumcraft.api.aspects.Aspect
import thaumcraft.api.aspects.AspectList
import net.minecraft.util.EnumFacing



class TileFountainEssentiaHolder extends BaseTE with IAspectContainer with IEssentiaTransport {
  
  
  private var master: TileFountain = null
  
  override def update() {
    
    val te = this.getWorld.getTileEntity(this.pos.add(0, 2, 0))
    if(master == null && te != null && te.isInstanceOf[TileFountain]) {
      master = te.asInstanceOf[TileFountain] 
    } 
    
   
  }
  
  
  
  
  //Essentia handling
  
  override def getAspects = if(master != null) master.essentia else null
  override def setAspects(aspects: AspectList) { if(master != null) master.setAspects(aspects) }
  override def doesContainerAccept(par: Aspect) = true
  override def addToContainer(aspect: Aspect, i: Int) = if(master != null) master.addToContainer(aspect, i) else i
  override def takeFromContainer(aspect: Aspect, i: Int) = if(master != null) master.takeFromContainer(aspect, i) else false
  override def doesContainerContainAmount(aspect: Aspect, am: Int) = if(master != null) master.doesContainerContainAmount(aspect, am) else false
  override def containerContains(aspect: Aspect) = if(master != null) master.containerContains(aspect) else 0
  override def takeFromContainer(al: AspectList) = false   //Deprecated
  override def doesContainerContain(al: AspectList)= false //Deprecated
  
  
  override def isConnectable(face: EnumFacing) = true
  override def canInputFrom(face: EnumFacing) = true
  override def canOutputTo(face: EnumFacing) = true
  override def setSuction(aspect: Aspect, am: Int) = master.currentSuction = aspect
  override def getSuctionType(loc: EnumFacing) = master.currentSuction
  override def getSuctionAmount(loc: EnumFacing) = if(master.currentSuction != null) 128 else 0
  override def getEssentiaType(loc: EnumFacing) = null
  override def getEssentiaAmount(loc: EnumFacing) = 0
  override def takeEssentia(aspect: Aspect, am: Int, face: EnumFacing) = if(master.takeFromContainer(aspect, am)) am else 0
  override def addEssentia(aspect: Aspect, am: Int, face: EnumFacing) = am - master.addToContainer(aspect, am)
  override def getMinimumSuction() = 0
  
  
  
}