package com.dr_benway.tknowledge.item

import net.minecraft.item.ItemStack
import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs



class ItemResource(uName: String = "wat", hidden: Boolean = false) extends BaseItem(uName, hidden) {
  
  setHasSubtypes(true)
  
  override def getUnlocalizedName(stack: ItemStack): String = {
     super.getUnlocalizedName(stack) + "." + (stack.getItemDamage() match {
       case 0 => "tab"
       case _ => "unforseen"
     })
  }
  
  
  
  override def getSubItems(item: Item, tab: CreativeTabs, list: java.util.List[_]) {
    val i = 1
    for(i <- 0 until i) list.asInstanceOf[java.util.List[ItemStack]].add(new ItemStack(item, 1, i))
  }
}