package com.dr_benway.tknowledge.item

import net.minecraft.item.Item
import com.dr_benway.tknowledge.Knowledge

class BaseItem(uName: String = "wat", hidden: Boolean = false) extends Item {
  
  setUnlocalizedName("tk_" + uName)
  if (!hidden) setCreativeTab(Knowledge.tab)
  
  
  
}