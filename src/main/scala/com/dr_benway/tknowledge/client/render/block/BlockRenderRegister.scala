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
    
    reg(TKBlocks.misc, 0, "tk_misc.qs_cons")
    reg(TKBlocks.misc, 1, "tk_misc.qs_block")
    reg(TKBlocks.slab, 0, "tk_slab.qs_block")
    reg(TKBlocks.slab_double, 0, "tk_misc.qs_block")
    reg(TKBlocks.stairs_qs, 0, "tk_stairs_qs")
    
  }
  
  def preInit(e: FMLPreInitializationEvent) {
    ModelBakery.addVariantName(Item.getItemFromBlock(TKBlocks.misc), "tknowledge:tk_misc.qs_cons", "tknowledge:tk_misc.qs_block")
    ModelBakery.addVariantName(Item.getItemFromBlock(TKBlocks.slab), "tknowledge:tk_slab.qs_block")
    ModelBakery.addVariantName(Item.getItemFromBlock(TKBlocks.stairs_qs), "tknowledge:tk_stairs_qs")
    
    
  }
  
  def reg(block: Block, meta: Byte = 0, file: String = null) {
    
    file match {
      case null => Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Knowledge.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"))
      case _: String => Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Knowledge.MODID + ":" + file, "inventory"))
    } 
  }
}