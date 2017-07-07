package com.cjm721.overloaded.item;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.crafting.ItemEnergyCore;
import com.cjm721.overloaded.item.crafting.ItemFluidCore;
import com.cjm721.overloaded.item.crafting.ItemItemCore;
import com.cjm721.overloaded.item.functional.ItemAmountSelector;
import com.cjm721.overloaded.item.functional.ItemEnergyShield;
import com.cjm721.overloaded.item.functional.ItemLinkingCard;
import com.cjm721.overloaded.item.functional.ItemMultiTool;
import com.cjm721.overloaded.item.functional.armor.ItemMultiBoots;
import com.cjm721.overloaded.item.functional.armor.ItemMultiChestplate;
import com.cjm721.overloaded.item.functional.armor.ItemMultiHelmet;
import com.cjm721.overloaded.item.functional.armor.ItemMultiLeggings;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class ModItems {
    public static ModItem linkingCard;
    public static ItemMultiTool itemMultiTool;

    public static ItemEnergyShield energyShield;
    public static ItemAmountSelector amountSelector;

    public static ModItem energyCore;
    public static ModItem fluidCore;
    public static ModItem itemCore;

    public static ItemMultiHelmet customHelmet;
    public static ItemMultiChestplate customChestplate;
    public static ItemMultiLeggings customLeggins;
    public static ItemMultiBoots customBoots;

    private static List<IModRegistrable> registerList = new LinkedList<>();

    public static void addToSecondaryInit(IModRegistrable item) {
        registerList.add(item);
    }

    public static void init() {
        linkingCard = new ItemLinkingCard();
        itemMultiTool = new ItemMultiTool();

        energyCore = new ItemEnergyCore();
        fluidCore = new ItemFluidCore();
        itemCore = new ItemItemCore();

        customHelmet = new ItemMultiHelmet();
        customChestplate = new ItemMultiChestplate();
        customLeggins = new ItemMultiLeggings();
        customBoots = new ItemMultiBoots();

        if(OverloadedConfig.developmentConfig.wipStuff) {
//            energyShield = new ItemEnergyShield();
//            amountSelector = new ItemAmountSelector();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for(IModRegistrable item: registerList)
            item.registerModel();
    }
}
