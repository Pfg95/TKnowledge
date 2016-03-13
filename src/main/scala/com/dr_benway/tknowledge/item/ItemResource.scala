package com.dr_benway.tknowledge.item

import net.minecraft.item.ItemStack
import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs



class ItemResource(uName: String = "wat", hidden: Boolean = false) extends BaseItem(uName, hidden) {
  
  setHasSubtypes(true)
  
  override def getUnlocalizedName(stack: ItemStack): String = {
     super.getUnlocalizedName(stack) + "." + (stack.getItemDamage() match {
       case 0 => "tab"
       case 1 => "qs_crystal"
       case _ => "unforseen"
     })
  }
  
  
  
  override def getSubItems(item: Item, tab: CreativeTabs, list: java.util.List[ItemStack]) {
    for(i <- 1 until 2) list.add(new ItemStack(item, 1, i))
  }
}