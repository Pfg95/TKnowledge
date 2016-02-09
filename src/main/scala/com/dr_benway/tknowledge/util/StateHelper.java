package com.dr_benway.tknowledge.util;

import net.minecraft.block.state.IBlockState;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.blocks.basic.BlockMetalTC;
import thaumcraft.common.blocks.basic.BlockStoneSlabTC;
import thaumcraft.common.blocks.basic.BlockTranslucent;

public final class StateHelper {      //Yes, I am shameless
	
	
	
	
	@SuppressWarnings("unchecked")
	private IBlockState thaumium = BlocksTC.metal.getDefaultState().withProperty(BlockMetalTC.VARIANT, BlockMetalTC.MetalType.THAUMIUM);
	@SuppressWarnings("unchecked")
	private IBlockState brass = BlocksTC.metal.getDefaultState().withProperty(BlockMetalTC.VARIANT, BlockMetalTC.MetalType.BRASS);
	@SuppressWarnings("unchecked")
	private IBlockState voidmetal = BlocksTC.metal.getDefaultState().withProperty(BlockMetalTC.VARIANT, BlockMetalTC.MetalType.VOID);
	@SuppressWarnings("unchecked")
	private IBlockState amber = BlocksTC.translucent.getDefaultState().withProperty(BlockTranslucent.VARIANT, BlockTranslucent.TransType.AMBER_BLOCK);
	@SuppressWarnings("unchecked")
	private IBlockState arcaneSlab = BlocksTC.slabStone.getDefaultState().withProperty(BlockStoneSlabTC.VARIANT, BlockStoneSlabTC.StoneType.ARCANE);
	
	
	public IBlockState getThaumium() {
		return thaumium;
	}
	
	public IBlockState getBrass() {
		return brass;
	}
	
	public IBlockState getVoid() {
		return voidmetal;
	}
	
	public IBlockState getAmber() {
		return amber;
	}
	
	public IBlockState getArcaneSlab() {
		return arcaneSlab;
	}

}

