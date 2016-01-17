package com.dr_benway.tknowledge.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * UpperCup - RoyalHuggableJelly
 * Created using Tabula 5.1.0
 */
public class ModelFountain extends ModelBase {
    public ModelRenderer Bottom;
    public ModelRenderer WallA;
    public ModelRenderer WallB;
    public ModelRenderer WallC;
    public ModelRenderer WallD;

    public ModelFountain() {
        this.textureWidth = 128;
        this.textureHeight = 48;
        this.WallA = new ModelRenderer(this, 0, 30);
        this.WallA.setRotationPoint(17.0F, 12.0F, -13.0F);
        this.WallA.addBox(0.0F, 0.0F, 0.0F, 30, 12, 4, 0.0F);
        this.setRotateAngle(WallA, 0.0F, -1.5707963267948966F, 0.0F);
        this.Bottom = new ModelRenderer(this, 0, 0);
        this.Bottom.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.Bottom.addBox(-13.0F, 0.0F, -13.0F, 26, 3, 26, 0.0F);
        this.WallB = new ModelRenderer(this, 0, 30);
        this.WallB.setRotationPoint(-17.0F, 12.0F, 13.0F);
        this.WallB.addBox(0.0F, 0.0F, 0.0F, 30, 12, 4, 0.0F);
        this.setRotateAngle(WallB, 0.0F, 1.5707963267948966F, 0.0F);
        this.WallD = new ModelRenderer(this, 0, 30);
        this.WallD.setRotationPoint(-13.0F, 12.0F, -17.0F);
        this.WallD.addBox(0.0F, 0.0F, 0.0F, 30, 12, 4, 0.0F);
        this.WallC = new ModelRenderer(this, 0, 30);
        this.WallC.setRotationPoint(13.0F, 12.0F, 17.0F);
        this.WallC.addBox(0.0F, 0.0F, 0.0F, 30, 12, 4, 0.0F);
        this.setRotateAngle(WallC, 0.0F, -3.141592653589793F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.WallA.render(f5);
        this.Bottom.render(f5);
        this.WallB.render(f5);
        this.WallD.render(f5);
        this.WallC.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
