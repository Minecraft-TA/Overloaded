package com.cjm721.overloaded.common.item.functional.armor;

import com.cjm721.overloaded.client.render.entity.RenderMultiChestplate;
import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.util.IModRegistrable;
import com.cjm721.overloaded.common.util.IntEnergyWrapper;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemMultiChestplate extends ItemArmor implements IModRegistrable {

    public ItemMultiChestplate() {
        super(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.CHEST);
        setMaxDamage(-1);

        setRegistryName("multi_chestplate");
        setUnlocalizedName("multi_chestplate");
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.register(this);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);

        ModItems.addToSecondaryInit(this);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        return new RenderMultiChestplate();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.customChestplate) {
            //GameRegistry.addRecipe(new ItemStack(this), "GII", "IRI", "III", 'G', Items.GOLD_NUGGET, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE);
        }
    }

    @Override
    @Nonnull
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(@Nullable EntityEquipmentSlot equipmentSlot) {
        return super.getItemAttributeModifiers(equipmentSlot);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new IntEnergyWrapper(stack,nbt);
    }
}
