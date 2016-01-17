package com.dr_benway.tknowledge

import java.io.File;
import net.minecraftforge.common.config.Configuration

object TKConfig {
  
  
  
    def preInit(suggestedConfigurationFile: File) = {
      
      val conf = new Configuration(suggestedConfigurationFile)
      
      try {
        conf.load()
      }
      catch {
        case e: Exception => Knowledge.log.error("[TKNOWLEDGE]: Failed to load configuration. Default values will be applied.")
      }
      finally {
        if(conf != null) conf.save()
      }
      
    }
  
}