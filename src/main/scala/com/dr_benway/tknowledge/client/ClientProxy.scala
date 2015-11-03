package com.dr_benway.tknowledge.client

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import com.dr_benway.tknowledge.CommonProxy
import com.dr_benway.tknowledge.client.render.item.ItemRenderRegister
import com.dr_benway.tknowledge.client.render.block.BlockRenderRegister

class ClientProxy extends CommonProxy {
  
  
      
      override def preInit(e: FMLPreInitializationEvent) {
        super.preInit(e)
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
  
}