package com.dr_benway.tknowledge.item

import net.minecraftforge.fml.common.registry.GameRegistry

object TKItems {
  
  val resource = new ItemResource("resource")
  
  def addItems() {
    
      GameRegistry.registerItem(resource, "tk_resource")
      
  }
}