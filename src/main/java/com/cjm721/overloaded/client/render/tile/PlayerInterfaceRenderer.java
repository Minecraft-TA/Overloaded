package com.cjm721.overloaded.client.render.tile;

import com.cjm721.overloaded.block.tile.TilePlayerInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class PlayerInterfaceRenderer extends TileEntitySpecialRenderer<TilePlayerInterface> {

    @Override
    public void render(TilePlayerInterface te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderPlayer(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderPlayer(TilePlayerInterface te) {
        UUID uuid = te.getPlacer();

        if (uuid == null)
            return;

        EntityPlayer player = te.getWorld().getPlayerEntityByUUID(uuid);

        if (player == null) {
            renderItem(te.getStackCache());
            return;
        }

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();

        GlStateManager.translate(.5, .3, .5);
        GlStateManager.scale(.2f, .2f, .2f);
        long angle = (System.currentTimeMillis() / 10) % 360;
        GlStateManager.rotate(angle, 0, 1, 0);

        Minecraft.getMinecraft().getRenderManager().renderEntity(player, 0, 0, 0, 0, 1, false);

        GlStateManager.popMatrix();
    }

    private void renderItem(ItemStack stack) {
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();

        GlStateManager.translate(.5, .65, .5);
        GlStateManager.scale(.5f, .5f, .5f);
        long angle = (System.currentTimeMillis() / 10) % 360;
        GlStateManager.rotate(angle, 0, 1, 0);

        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

        GlStateManager.popMatrix();
    }
}
