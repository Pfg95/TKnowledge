package tknowledge;



import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.aspects.AspectList;

import com.dr_benway.tknowledge.lib.crafting.FountainRecipe;

public class TKnowledgeAPI {

	private static ArrayList<Object> recipes = new ArrayList<Object>();
	
	
	public static List<Object> getRecipes() {
	    return recipes;
	}
	
	//Do NOT add a recipe with a null catalyst, nor with a null result, nor with a null or empty key / research!
	//key is the recipe's own name, research is the research you want the recipe to be linked to.
	//If you put an inexistent research as an argument, the recipe will never be available!
	//danger is the recipe's polluting chance. It must be between 0.0F and 1.0F, higher or lower numbers will be evened.
	public static FountainRecipe registerFountainRecipe(String research, String key, FluidStack result, Object catalyst, AspectList tags, AspectList aura, float danger) {
		if(research == null || research.isEmpty() || key == null || key.isEmpty() || catalyst == null || result == null) throw new IllegalArgumentException("[TKNOWLEDGE]:  " + research + key + " is not a valid recipe!");
		FountainRecipe recipe = new FountainRecipe(research, key, result, catalyst, tags, aura, danger);
		recipes.add(recipe);
		return recipe;
	}
	
	

}
