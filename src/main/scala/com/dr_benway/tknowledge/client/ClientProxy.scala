package com.dr_benway.tknowledge.client

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import com.dr_benway.tknowledge.CommonProxy
import com.dr_benway.tknowledge.client.render.RenderRegister
import net.minecraftforge.fml.client.registry.ClientRegistry
import com.dr_benway.tknowledge.block.tile.devices.fountain.TileFountain
import com.dr_benway.tknowledge.client.render.tile.TileFountainRenderer
import com.dr_benway.tknowledge.client.render.tile.TileTankSimpleRenderer
import com.dr_benway.tknowledge.block.tile.devices.tank.TileTankSimple

class ClientProxy extends CommonProxy {
  
  
      
      override def preInit(e: FMLPreInitializationEvent) {
        super.preInit(e)
        registerRenderInfo()
        RenderRegister.preInit(e)   
      }
  
      
      override def init(e: FMLInitializationEvent) {
        super.init(e) 
      }
      
      
      override def postInit(e: FMLPostInitializationEvent) {
        super.postInit(e)
        
      }
      
      private def registerRenderInfo() {
        ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileFountain], TileFountainRenderer)
        ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileTankSimple], TileTankSimpleRenderer)
      }
  
}