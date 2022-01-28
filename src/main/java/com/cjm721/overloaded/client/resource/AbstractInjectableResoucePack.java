package com.cjm721.overloaded.client.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public abstract class AbstractInjectableResoucePack implements IResourcePack {
    public final void inject() {
        List<IResourcePack> defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_110449_ao");
        defaultResourcePacks.add(this);

        Map<String, FallbackResourceManager> domainResourceManagers = ObfuscationReflectionHelper.getPrivateValue(SimpleReloadableResourceManager.class, (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager(), "field_110548_a");
        domainResourceManagers.get("overloaded").addResourcePack(this);
    }
}
