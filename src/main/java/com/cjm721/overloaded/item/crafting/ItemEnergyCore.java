package com.cjm721.overloaded.item.crafting;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.item.ModItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemEnergyCore extends ModItem {

    public ItemEnergyCore() {
        setMaxStackSize(64);
        setRegistryName("energy_core");
        setTranslationKey("energy_core");
        setCreativeTab(OverloadedCreativeTabs.TECH);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/items/energy_core.png"),
                Overloaded.cachedConfig.textureResolutions.itemResolution);
    }
}
