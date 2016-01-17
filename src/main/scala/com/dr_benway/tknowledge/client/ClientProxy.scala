package com.dr_benway.tknowledge.client

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import com.dr_benway.tknowledge.CommonProxy
import com.dr_benway.tknowledge.client.render.item.ItemRenderRegister
import com.dr_benway.tknowledge.client.render.block.BlockRenderRegister
import net.minecraftforge.fml.client.registry.ClientRegistry
import com.dr_benway.tknowledge.block.tile.devices.TileFountain
import com.dr_benway.tknowledge.client.render.tile.TileFountainRenderer

class ClientProxy extends CommonProxy {
  
  
      
      override def preInit(e: FMLPreInitializationEvent) {
        super.preInit(e)
        registerRenderInfo()
        BlockRenderRegister.preInit(e)
        ItemRenderRegister.preInit(e)
        
      }
  
      
      override def init(e: FMLInitializationEvent) {
        super.init(e)
        ItemRenderRegister.registerItemRenderer()
        BlockRenderRegister.registerBlockRenderer()
        
      }
      
      
      override def postInit(e: FMLPostInitializationEvent) {
        super.postInit(e)
        
      }
      
      private def registerRenderInfo() {
        ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileFountain], TileFountainRenderer)
      }
  
}