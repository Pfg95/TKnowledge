package com.dr_benway.tknowledge.block;


import net.minecraft.util.IStringSerializable;

public class EnumProperty {
	
	public enum EnumType implements IStringSerializable {
	    WHITE(0, "white"),
	    BLACK(1, "black");

	    private int ID;
	    private String name;
	    
	    private EnumType(int ID, String name) {
	        this.ID = ID;
	        this.name = name;
	    }
	    
	    @Override
	    public String getName() {
	        return name;
	    }

	    public int getID() {
	        return ID;
	    }
	    
	    @Override
	    public String toString() {
	        return getName();
	    }
	}
	
	
	
	
	
	

}
