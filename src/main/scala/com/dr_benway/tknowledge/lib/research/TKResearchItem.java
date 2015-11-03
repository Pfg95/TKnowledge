package com.dr_benway.tknowledge.lib.research;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;


public class TKResearchItem extends ResearchItem {
	
	public TKResearchItem(String key, String category, AspectList tags, int x, int y, int level, ResourceLocation icon)
	{
		super(key,category,tags,x,y,level,icon);
	}

	public TKResearchItem(String key, String category, AspectList tags, int x, int y, int level, ItemStack icon)
	{
		super(key,category,tags,x,y,level,icon);
	}
	
	
	
}
