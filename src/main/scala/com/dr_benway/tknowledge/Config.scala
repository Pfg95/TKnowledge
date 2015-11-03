package com.dr_benway.tknowledge

import java.io.File;
import net.minecraftforge.common.config.Configuration

class Config {
  
    def init(suggestedConfigurationFile: File) = {
      
      val conf = new Configuration(suggestedConfigurationFile)
      
      try {
        conf.load()
      }
      catch {
        case e: Exception => println("[Thaumaturgical Knowledge]: Failed to load configuration. Default values will be applied.")
      }
      finally {
        conf.save()
      }
      
    }
  
}