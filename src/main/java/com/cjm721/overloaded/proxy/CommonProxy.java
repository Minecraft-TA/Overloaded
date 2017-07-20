package com.cjm721.overloaded.proxy;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.item.functional.armor.ArmorEventHandler;
import com.cjm721.overloaded.item.functional.armor.MultiArmorCapabilityProvider;
import com.cjm721.overloaded.network.handler.KeyBindPressedHandler;
import com.cjm721.overloaded.network.handler.MultiToolLeftClickHandler;
import com.cjm721.overloaded.network.handler.MultiToolRightClickHandler;
import com.cjm721.overloaded.network.handler.NoClipUpdateHandler;
import com.cjm721.overloaded.network.packets.KeyBindPressedMessage;
import com.cjm721.overloaded.network.packets.MultiToolLeftClickMessage;
import com.cjm721.overloaded.network.packets.MultiToolRightClickMessage;
import com.cjm721.overloaded.network.packets.NoClipStatusMessage;
import com.cjm721.overloaded.util.CapabilityHyperEnergy;
import com.cjm721.overloaded.util.CapabilityHyperFluid;
import com.cjm721.overloaded.util.CapabilityHyperItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber
public class CommonProxy {

    public SimpleNetworkWrapper networkWrapper;

    public static List<Block> blocksToRegister = new LinkedList<>();
    public static List<Item> itemToRegister = new LinkedList<>();


    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init();
        ModItems.init();

        CapabilityHyperItem.register();
        CapabilityHyperEnergy.register();
        CapabilityHyperFluid.register();
        MultiArmorCapabilityProvider.register();
    }

    public void init(FMLInitializationEvent event) {
        networkWrapper = new SimpleNetworkWrapper("overloaded");
        networkWrapper.registerMessage(MultiToolLeftClickHandler.class, MultiToolLeftClickMessage.class, 0, Side.SERVER);
        networkWrapper.registerMessage(MultiToolRightClickHandler.class, MultiToolRightClickMessage.class, 1, Side.SERVER);
        networkWrapper.registerMessage(KeyBindPressedHandler.class, KeyBindPressedMessage.class, 2, Side.SERVER);
        networkWrapper.registerMessage(NoClipUpdateHandler.class, NoClipStatusMessage.class, 3, Side.CLIENT);

        MinecraftForge.EVENT_BUS.register(ModItems.itemMultiTool);
        MinecraftForge.EVENT_BUS.register(new ArmorEventHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for(Block block: blocksToRegister)
            event.getRegistry().register(block);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for(Item item: itemToRegister)
            event.getRegistry().register(item);
    }

}
