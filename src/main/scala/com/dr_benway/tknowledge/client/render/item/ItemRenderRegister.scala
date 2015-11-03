package com.dr_benway.tknowledge.client.render.item

import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraft.client.resources.model.ModelResourceLocation
import com.dr_benway.tknowledge.Knowledge
import com.dr_benway.tknowledge.item.TKItems
import net.minecraft.client.resources.model.ModelBakery
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

object ItemRenderRegister {
  
  
  def registerItemRenderer() {
    
    reg(TKItems.resource, 0, "tk_resource_tab")
    
  }
  
  def preInit(e: FMLPreInitializationEvent) {
    ModelBakery.addVariantName(TKItems.resource, "tknowledge:tk_resource_tab")
    
  }
  
  def reg(item: Item, meta: Byte = 0, file: String = null) {
    
    file match {
      case null => Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Knowledge.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"))
      case _: String => Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Knowledge.MODID + ":" + file, "inventory"))
    }
  }
  
}