package com.dr_benway.tknowledge.client.render.block

import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraft.client.resources.model.ModelResourceLocation
import com.dr_benway.tknowledge.Knowledge
import com.dr_benway.tknowledge.block.TKBlocks
import net.minecraft.block.Block
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraft.client.resources.model.ModelBakery

object BlockRenderRegister {
  
  def registerBlockRenderer() {
    
    reg(TKBlocks.diocan)
    reg(TKBlocks.cazzo, 0, "tk_cazzo1")
    reg(TKBlocks.cazzo, 1, "tk_cazzo2")
    
  }
  
  def preInit(e: FMLPreInitializationEvent) {
    ModelBakery.addVariantName(Item.getItemFromBlock(TKBlocks.cazzo), "tknowledge:tk_cazzo1", "tknowledge:tk_cazzo2")
    
  }
  
  def reg(block: Block, meta: Byte = 0, file: String = null) {
    
    file match {
      case null => Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Knowledge.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"))
      case _: String => Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Knowledge.MODID + ":" + file, "inventory"))
    } 
  }
}