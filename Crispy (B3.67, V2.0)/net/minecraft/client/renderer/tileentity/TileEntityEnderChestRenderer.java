package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation ENDER_CHEST_TEXTURE = new ResourceLocation("textures/entity/chest/ender.png");
    private ModelChest field_147521_c = new ModelChest();

    public void renderTileEntityEnderChest(TileEntityEnderChest enderChest, double p_180540_2_, double p_180540_4_, double p_180540_6_, float p_180540_8_, int p_180540_9_)
    {
        int var10 = 0;

        if (enderChest.hasWorldObj())
        {
            var10 = enderChest.getBlockMetadata();
        }

        if (p_180540_9_ >= 0)
        {
            this.bindTexture(DESTROY_STAGES[p_180540_9_]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            this.bindTexture(ENDER_CHEST_TEXTURE);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate((float)p_180540_2_, (float)p_180540_4_ + 1.0F, (float)p_180540_6_ + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        short var11 = 0;

        if (var10 == 2)
        {
            var11 = 180;
        }

        if (var10 == 3)
        {
            var11 = 0;
        }

        if (var10 == 4)
        {
            var11 = 90;
        }

        if (var10 == 5)
        {
            var11 = -90;
        }

        GlStateManager.rotate((float)var11, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        float var12 = enderChest.prevLidAngle + (enderChest.lidAngle - enderChest.prevLidAngle) * p_180540_8_;
        var12 = 1.0F - var12;
        var12 = 1.0F - var12 * var12 * var12;
        this.field_147521_c.chestLid.rotateAngleX = -(var12 * (float)Math.PI / 2.0F);
        this.field_147521_c.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (p_180540_9_ >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        this.renderTileEntityEnderChest((TileEntityEnderChest)te, x, y, z, partialTicks, destroyStage);
    }
}
