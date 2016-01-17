package com.dr_benway.tknowledge

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.SidedProxy
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import com.dr_benway.tknowledge.item.TKItems
import com.dr_benway.tknowledge.block.TKBlocks
import com.dr_benway.tknowledge.block.tile.TKTEs
import com.dr_benway.tknowledge.lib.research.TKResearch
import com.dr_benway.tknowledge.lib.research.TKRecipes
import com.dr_benway.tknowledge.compat.Compat
import com.dr_benway.tknowledge.lib.TKWandManager




@Mod(modid = Knowledge.MODID, name = Knowledge.NAME, version = Knowledge.VERSION, dependencies = "required-after:Thaumcraft", modLanguage = "scala")
object Knowledge {
      
      final val MODID = "tknowledge"
      final val NAME = "Thaumaturgical Knowledge"
      final val VERSION = "0.2.0"
      final val INGOT_FLUID_VALUE = 144
      final val NUGGET_FLUID_VALUE = INGOT_FLUID_VALUE / 9
      final val BLOCK_FLUID_VALUE = INGOT_FLUID_VALUE * 9
      final val log = LogManager.getLogger("TKNOWLEDGE")
      val tab = new CreativeTabs("knowledge") {
        override def getTabIconItem = TKItems.resource
        override def getIconItemDamage = 0
      }
      
  
      
      @SidedProxy(serverSide = "com.dr_benway.tknowledge.CommonProxy", clientSide = "com.dr_benway.tknowledge.client.ClientProxy")
      var proxy: CommonProxy = _
      
      
      @EventHandler
      def preInit(e: FMLPreInitializationEvent) {
        TKConfig.preInit(e.getSuggestedConfigurationFile) //ain't any
        TKBlocks.addBlocks()
        TKItems.addItems()
        TKTEs.addTEs()
        proxy.preInit(e)
      }
  
      @EventHandler
      def init(e: FMLInitializationEvent) {
        proxy.init(e)
      }
      
      @EventHandler
      def postInit(e: FMLPostInitializationEvent) {
        TKWandManager.regWandTriggers()
        TKRecipes.addRecipes()
        TKResearch.addResearch()
        Compat.addCompat() //ain't any
        proxy.postInit(e)
      }
  
  
  
  
}