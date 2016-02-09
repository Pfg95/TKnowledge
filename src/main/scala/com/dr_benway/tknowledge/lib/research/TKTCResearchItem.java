package com.dr_benway.tknowledge.lib.research;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class TKTCResearchItem extends ResearchItem {
	
	public ResearchItem original;

	public TKTCResearchItem(String key, String category, String originalResearch, String originalCategory, int displayX, int displayY, ResourceLocation icon)
	{
		super(key,category,new AspectList(),displayX,displayY,1,icon);
		this.original = ((ResearchCategoryList)ResearchCategories.researchCategories.get(originalCategory)).research.get(originalResearch);
		this.setupOriginal();
	}

	public TKTCResearchItem(String key, String category, String originalResearch, String originalCategory, int displayX, int displayY, ItemStack icon)
	{
		super(key,category,new AspectList(),displayX,displayY,1,icon);
		this.original = ((ResearchCategoryList)ResearchCategories.researchCategories.get(originalCategory)).research.get(originalResearch);
		this.setupOriginal();
	}

	private void setupOriginal()
	{
		if(this.original.siblings == null)
			this.original.setSiblings(new String[] { this.key });
		else
		{
			String[] newSiblings = new String[this.original.siblings.length+1];
			System.arraycopy(this.original.siblings, 0, newSiblings, 0, this.original.siblings.length);
			newSiblings[this.original.siblings.length] = this.key;
			this.original.setSiblings(newSiblings);
		}
		
	}

	@Override
	public String getName()
	{
		return this.original.getName();
	}

	@Override
	public String getText()
	{
		return this.original.getText();
	}

	@Override
	public boolean isStub()
	{
		return true;
	}

	@Override
	public boolean isHidden()
	{
		return false;
	}

	@Override
	public int getComplexity()
	{
		return 1;
	}

	@Override
	public ResearchPage[] getPages()
	{
		return this.original.getPages();
	}

}
