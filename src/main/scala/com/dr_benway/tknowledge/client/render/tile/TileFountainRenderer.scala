package com.dr_benway.tknowledge.client.render.tile

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import com.dr_benway.tknowledge.block.tile.devices.TileFountain
import org.lwjgl.opengl.GL11
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.Minecraft
import com.dr_benway.tknowledge.client.model.ModelFountain
import net.minecraft.world.World
import net.minecraft.block.Block
import com.dr_benway.tknowledge.block.TKBlocks
import com.dr_benway.tknowledge.Knowledge
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraft.entity.item.EntityItem
import thaumcraft.common.config.ConfigBlocks
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.OpenGlHelper



object TileFountainRenderer extends TileEntitySpecialRenderer[TileFountain] {
  
  val tess = Tessellator.getInstance
  val wr = tess.getWorldRenderer
  val model = new ModelFountain()
  
  
  override def renderTileEntityAt(t: TileFountain, x: Double, y: Double, z: Double, f: Float, q: Int) {
    
    GlStateManager.pushMatrix()
    GlStateManager.translate(x, y, z)
    renderModel(t, t.getWorld, t.getPos.getX, t.getPos.getY, t.getPos.getZ, TKBlocks.fountain, f)
    renderItem(t, t.getWorld, t.getPos.getX, t.getPos.getY, t.getPos.getZ, f)
    if(t.essentia.visSize() > 0)
      renderFluidCup(t, t.getWorld, t.getPos.getX, t.getPos.getY, t.getPos.getZ, f)
    renderFluidTank(t, t.getWorld, t.getPos.getX, t.getPos.getY, t.getPos.getZ, f)
    if(t.ready == true || t.locked == false)
      renderFluidPouring(t, t.getWorld, t.getPos.getX, t.getPos.getY, t.getPos.getZ, f)
    GlStateManager.popMatrix()
    
  }
  
  
  private def renderModel(te: TileFountain, world: World, x: Int, y: Int, z: Int, b: Block, f: Float) {
    
    val f: Float = b.getLightValue(world, te.getPos)
    
    GlStateManager.pushMatrix()
    GlStateManager.rotate(180F, 0F, 0F, 1F)
    GlStateManager.translate(-0.5, -0.5, 0.5)
    Minecraft.getMinecraft.renderEngine.bindTexture(new ResourceLocation(Knowledge.MODID + ":textures/models/upper_cup.png"))
    model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F)
    GlStateManager.popMatrix()
  }
  
  private def renderItem(te: TileFountain, world: World, x: Int, y: Int, z: Int, f: Float) {
    
    if(te != null && te.getWorld != null && te.getStackInSlot(0) != null) {
      
      val ticks: Float = Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + f
      var eitem: EntityItem = null
      
      GlStateManager.pushMatrix()     
      GlStateManager.translate(0.5F, 0.4F, 0.5F)
      GlStateManager.scale(1.5F, 1.5F, 1.5F)
      GlStateManager.rotate(ticks % 360.0F, 0.0F, 1.0F, 0.0F)
      
      var is = te.getStackInSlot(0).copy()
      is.stackSize = 1
      eitem = new EntityItem(te.getWorld(), 0.0D, 0.0D, 0.0D, is)
      eitem.hoverStart = 0.0F
      val rendermanager = Minecraft.getMinecraft.getRenderManager()
      
      rendermanager.renderEntityWithPosYaw(eitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F)
      if (!Minecraft.isFancyGraphicsEnabled()) {
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F)
        rendermanager.renderEntityWithPosYaw(eitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F)
      }      
      GlStateManager.popMatrix()
    }
  }
  
  private def renderFluidCup(te: TileFountain, world: World, x: Int, y: Int, z: Int, f: Float) {
    
    val fluid = te.currentRecipe.result.getFluid
    val fff = te.currentRecipe.result.getFluid.getName
    val f_s = Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(fluid.getStill.toString)
    val f_f = Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(fluid.getFlowing.toString)
    val c = fluid.getColor
    val red: Float = (c&0xFF).toFloat/255
    val green: Float = ((c >> 8)&0xFF).toFloat/255
    val blue: Float = ((c >> 16)&0xFF).toFloat/255  //Why??? it works, that's why
    val alpha: Float = ((c >> 24)&0xFF).toFloat/255
    
    GlStateManager.pushMatrix()
    GlStateManager.enableBlend()
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GlStateManager.disableAlpha()
    GlStateManager.translate(-.4F, te.calcCupHeight(), -.4F)
    GlStateManager.scale(1.8F, 1.8F, 1.8F)
    
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(1, 0, 0).tex(f_s.getMaxU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 0, 0).tex(f_s.getMinU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 0, 1).tex(f_s.getMinU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 0, 1).tex(f_s.getMaxU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
    tess.draw()
    
    GlStateManager.popMatrix() 
  }
  
  private def renderFluidPouring(te: TileFountain, world: World, x: Int, y: Int, z: Int, f: Float) {
    
    val fluid = te.currentRecipe.result.getFluid
    val f_s = Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(fluid.getStill.toString)
    val f_f = Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(fluid.getFlowing.toString)
    val c = fluid.getColor
    val red: Float = (c&0xFF).toFloat/255
    val green: Float = ((c >> 8)&0xFF).toFloat/255
    val blue: Float = ((c >> 16)&0xFF).toFloat/255
    val a: Float = ((c >> 24)&0xFF).toFloat/255
    var alpha = a
    
    GlStateManager.pushMatrix()
    GlStateManager.enableBlend()
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GlStateManager.disableAlpha()
    
    //I heard you like magic numbers...
    
    if(te.locked == false) alpha /= 3 else alpha == a
    
    GlStateManager.pushMatrix()
    GlStateManager.rotate(0, 0, 1, 0)
    GlStateManager.translate(0, 0, 0)
    add()
    GlStateManager.popMatrix()
    
    GlStateManager.pushMatrix()
    GlStateManager.rotate(90, 0, 1, 0)
    GlStateManager.translate(-1, 0, 0)
    add()
    GlStateManager.popMatrix()
    
    GlStateManager.pushMatrix()
    GlStateManager.rotate(180, 0, 1, 0)
    GlStateManager.translate(-1, 0, -1)
    add()
    GlStateManager.popMatrix()
    
    GlStateManager.pushMatrix()
    GlStateManager.rotate(270, 0, 1, 0)
    GlStateManager.translate(0, 0, -1)
    add()
    GlStateManager.popMatrix()
    
    
    def add() {
    GlStateManager.pushMatrix()
    GlStateManager.translate(0.0F, -.5F, 1.58F)
    GlStateManager.scale(1.0F, 1.0F, .22F)
    GlStateManager.rotate(0, 0, 0, 0)
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(1, 0, 0).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 0, 0).tex(f_f.getInterpolatedU(0), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()       //Top
    wr.pos(0, 0, 1).tex(f_f.getInterpolatedU(0), f_f.getInterpolatedV(8 * .22)).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 0, 1).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(8 * .22)).color(red, green, blue, alpha).endVertex()
    tess.draw()
    GlStateManager.popMatrix() 
    
    GlStateManager.pushMatrix()
    GlStateManager.translate(0.0F, -.5F, 1.8F)
    GlStateManager.scale(1.0F, 1.0F, 1.0F)
    GlStateManager.rotate(180, 1, 0, 0)
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(1, 0, 0).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 0, 0).tex(f_f.getInterpolatedU(0), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex() //Outer 1
    wr.pos(0, 1, 0).tex(f_f.getInterpolatedU(0), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 1, 0).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    tess.draw()
    GlStateManager.popMatrix()
    
    GlStateManager.pushMatrix()
    GlStateManager.translate(0.0F, -1.5F, 1.8F)
    GlStateManager.scale(1.0F, 1.0F, 1.0F)
    GlStateManager.rotate(180, 1, 0, 0)
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(1, 0, 0).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 0, 0).tex(f_f.getInterpolatedU(0), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex() //Outer 2
    wr.pos(0, 1, 0).tex(f_f.getInterpolatedU(0), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 1, 0).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    tess.draw()
    GlStateManager.popMatrix()
      
    GlStateManager.pushMatrix()
    GlStateManager.translate(0.0F, -.5F, 1.58F)
    GlStateManager.scale(1.0F, 1.0F, 1.0F)
    GlStateManager.rotate(180, 1, 0, 0)
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(0, 0, 0).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 0, 0).tex(f_f.getInterpolatedU(0), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex() //Inner 1
    wr.pos(1, 1, 0).tex(f_f.getInterpolatedU(0), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 1, 0).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    tess.draw()
    GlStateManager.popMatrix()
    
    GlStateManager.pushMatrix()
    GlStateManager.translate(0.0F, -1.5F, 1.58F)
    GlStateManager.scale(1.0F, 1.0F, 1.0F)
    GlStateManager.rotate(180, 1, 0, 0)
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(0, 0, 0).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 0, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex() //Inner 2
    wr.pos(1, 1, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 1, 0).tex(f_f.getInterpolatedU(8), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    tess.draw()
    GlStateManager.popMatrix()
    
    GlStateManager.pushMatrix()
    GlStateManager.translate(0.0F, -.5F, 1.58F)
    GlStateManager.scale(1.0F, 1.0F, .22F)
    GlStateManager.rotate(180, 1, 0, 0)
    GlStateManager.rotate(90, 0, 1, 0)
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(1, 0, 0).tex(f_f.getInterpolatedU(8 * .22), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 0, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex() // Left 1
    wr.pos(0, 1, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 1, 0).tex(f_f.getInterpolatedU(8 * .22), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    tess.draw()
    GlStateManager.popMatrix()
    
    GlStateManager.pushMatrix()
    GlStateManager.translate(0.0F, -1.5F, 1.58F)
    GlStateManager.scale(1.0F, 1.0F, .22F)
    GlStateManager.rotate(180, 1, 0, 0)
    GlStateManager.rotate(90, 0, 1, 0)
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(1, 0, 0).tex(f_f.getInterpolatedU(8 * .22), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 0, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex() // Left 2
    wr.pos(0, 1, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 1, 0).tex(f_f.getInterpolatedU(8 * .22), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    tess.draw()
    GlStateManager.popMatrix()
    
    GlStateManager.pushMatrix()
    GlStateManager.translate(1.0F, -.5F, 1.8F)
    GlStateManager.scale(1.0F, 1.0F, .22F)
    GlStateManager.rotate(180, 1, 0, 0)
    GlStateManager.rotate(-90, 0, 1, 0)
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(1, 0, 0).tex(f_f.getInterpolatedU(8 * .22), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 0, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex() // Right 1
    wr.pos(0, 1, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 1, 0).tex(f_f.getInterpolatedU(8 * .22), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    tess.draw()
    GlStateManager.popMatrix()
    
    GlStateManager.pushMatrix()
    GlStateManager.translate(1.0F, -1.5F, 1.8F)
    GlStateManager.scale(1.0F, 1.0F, .22F)
    GlStateManager.rotate(180, 1, 0, 0)
    GlStateManager.rotate(-90, 0, 1, 0)
    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    this.bindTexture(TextureMap.locationBlocksTexture)
    wr.pos(1, 0, 0).tex(f_f.getInterpolatedU(8 * .22), f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex()
    wr.pos(0, 0, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(0)).color(red, green, blue, alpha).endVertex() // Right 2
    wr.pos(0, 1, 0).tex(f_f.getInterpolatedU(0),  f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    wr.pos(1, 1, 0).tex(f_f.getInterpolatedU(8 * .22), f_f.getInterpolatedV(8)).color(red, green, blue, alpha).endVertex()
    tess.draw()
    GlStateManager.popMatrix()    
    }
    
    
    
    
    
    
    GlStateManager.popMatrix()
  }
  
  private def renderFluidTank(te: TileFountain, world: World, x: Int, y: Int, z: Int, f: Float) {
    
    
    if(te.tank.getFluidAmount > 0) {
      val fluid = te.tank.getFluid.getFluid
      val f_s = Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(fluid.getStill.toString)
      val f_f = Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(fluid.getFlowing.toString)
      val c = fluid.getColor
      val red: Float = (c&0xFF).toFloat/255
      val green: Float = ((c >> 8)&0xFF).toFloat/255
      val blue: Float = ((c >> 16)&0xFF).toFloat/255
      val alpha: Float = ((c >> 24)&0xFF).toFloat/255
      
      
      
      GlStateManager.pushMatrix()
      GlStateManager.enableBlend()
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
      GlStateManager.disableAlpha()
      GlStateManager.translate(-1.85F, te.calcTankHeight(), 0.45F)
      GlStateManager.scale(2.3F, 2.3F, 2.3F)
      
      wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
      this.bindTexture(TextureMap.locationBlocksTexture)
      wr.pos(1, 0, 0).tex(f_s.getMaxU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
      wr.pos(0, 0, 0).tex(f_s.getMinU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
      wr.pos(0, 0, 1).tex(f_s.getMinU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
      wr.pos(1, 0, 1).tex(f_s.getMaxU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
      tess.draw()
      
      GlStateManager.popMatrix()
      
      GlStateManager.pushMatrix()
      GlStateManager.enableBlend()
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
      GlStateManager.disableAlpha()
      GlStateManager.translate(-1.85F, te.calcTankHeight(), -1.85F)
      GlStateManager.scale(2.3F, 2.3F, 2.3F)
      GlStateManager.color(red, green, blue, alpha)   
      wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
      this.bindTexture(TextureMap.locationBlocksTexture)
      wr.pos(1, 0, 0).tex(f_s.getMaxU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
      wr.pos(0, 0, 0).tex(f_s.getMinU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
      wr.pos(0, 0, 1).tex(f_s.getMinU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
      wr.pos(1, 0, 1).tex(f_s.getMaxU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
      tess.draw() 
      GlStateManager.popMatrix()
      
      GlStateManager.pushMatrix()
      GlStateManager.enableBlend()
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
      GlStateManager.disableAlpha()
      GlStateManager.translate(0.45F, te.calcTankHeight(), -1.85F)
      GlStateManager.scale(2.3F, 2.3F, 2.3F)
      GlStateManager.color(red, green, blue, alpha)   
      wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
      this.bindTexture(TextureMap.locationBlocksTexture)
      wr.pos(1, 0, 0).tex(f_s.getMaxU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
      wr.pos(0, 0, 0).tex(f_s.getMinU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
      wr.pos(0, 0, 1).tex(f_s.getMinU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
      wr.pos(1, 0, 1).tex(f_s.getMaxU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
      tess.draw() 
      GlStateManager.popMatrix()
      
      GlStateManager.pushMatrix()
      GlStateManager.enableBlend()
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
      GlStateManager.disableAlpha()
      GlStateManager.translate(0.45F, te.calcTankHeight(), 0.45F)
      GlStateManager.scale(2.3F, 2.3F, 2.3F)
      GlStateManager.color(red, green, blue, alpha)   
      wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
      this.bindTexture(TextureMap.locationBlocksTexture)
      wr.pos(1, 0, 0).tex(f_s.getMaxU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
      wr.pos(0, 0, 0).tex(f_s.getMinU, f_s.getMinV).color(red, green, blue, alpha).endVertex()
      wr.pos(0, 0, 1).tex(f_s.getMinU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
      wr.pos(1, 0, 1).tex(f_s.getMaxU, f_s.getMaxV).color(red, green, blue, alpha).endVertex()
      tess.draw()  
      GlStateManager.popMatrix() 
    }
  }
  
  
  
}