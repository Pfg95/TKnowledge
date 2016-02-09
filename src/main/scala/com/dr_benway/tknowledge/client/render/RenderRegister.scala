package com.dr_benway.tknowledge.client.render

import net.minecraft.item.Item
import net.minecraft.client.resources.model.ModelResourceLocation
import com.dr_benway.tknowledge.Knowledge
import com.dr_benway.tknowledge.block.TKBlocks
import net.minecraft.block.Block
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.client.model.ModelLoader
import com.dr_benway.tknowledge.item.TKItems


object RenderRegister {
  
  def preInit(e: FMLPreInitializationEvent) {
    reg(TKItems.resource, 0, "tk_resource_tab")
    reg(TKBlocks.misc, 0, "tk_misc.qs_cons")
    reg(TKBlocks.misc, 1, "tk_misc.qs_block")
    reg(TKBlocks.slab, 0, "tk_slab.qs_block")
    reg(TKBlocks.slab_double, 0, "tk_misc.qs_block")
    reg(TKBlocks.stairs_qs, 0, "tk_stairs_qs")
    reg(TKBlocks.tank, 0, "tk_tank.simple")
  }
  
  private def reg(a: Any, meta: Byte = 0, file: String) {
    val item = a match { case i: Item => i  case b: Block => Item.getItemFromBlock(b) }
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Knowledge.MODID + ":" + file, "inventory"))
  }
}
