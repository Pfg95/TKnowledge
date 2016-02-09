package com.dr_benway.tknowledge.api;

import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.fluids.Fluid;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.research.ResearchHelper;

public class FountainRecipe {
	
	private FluidStack result;
	private Object cat;
	private AspectList aspects;
	private AspectList vis_aspects;
	private String research;
	private String key;
	private float pollutingChance;
	
	
	public FountainRecipe(String research1, String key1, FluidStack output, Object input, AspectList itags, AspectList aura, float danger) {
		
		this.cat = input;
		if(input instanceof String)
			this.cat = OreDictionary.getOres((String)input);
		this.research = research1;
		this.key = key1;
		this.result = output;
		this.aspects = itags;
		this.vis_aspects = aura;
		if(danger >= 0.0 && danger <= 1.0) {
			this.pollutingChance = danger;
		} else if(danger >= 1.0) {
				this.pollutingChance = 1.0F;
			} else this.pollutingChance = 0.0F;
				
		
	}
	
	
	public boolean matches(ItemStack in, Fluid out, EntityPlayer player) {
		if(out == null || in == null) return false;
		if(this.cat instanceof ItemStack && OreDictionary.itemMatches((ItemStack)cat, in, false)) {	
			return (this.result.getFluid() == out && ResearchHelper.isResearchComplete(player.getName(), this.research));
		} else { return false; }
	}
	
	
	
	public FluidStack getResult() {
		return this.result;
	}
	
	public Object getCatalyst() {
		return this.cat;
	}
	
	public String getResearch() {
		return this.research;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public AspectList getEssentia() {
		return this.aspects;
	}
	
	public AspectList getVis() {
		return this.vis_aspects;
	}
	
	public float getPollutingChance() {
		return this.pollutingChance;
	}

}
