package com.cjm721.overloaded;

import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.handler.ConfigSyncHandler;
import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.util.UnsafeItemStack;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Overloaded.MODID, version = Overloaded.VERSION,
        acceptedMinecraftVersions = "[1.12,1.13)",
        useMetadata = true
)
public class Overloaded {

    @Mod.Instance(Overloaded.MODID)
    public static Overloaded instance;

    public static final String MODID = "overloaded";
    static final String VERSION = "0.0.59";

    private static final String PROXY_CLIENT = "com.cjm721.overloaded.proxy.ClientProxy";
    private static final String PROXY_SERVER = "com.cjm721.overloaded.proxy.ServerProxy";

    @SidedProxy(clientSide = Overloaded.PROXY_CLIENT, serverSide = Overloaded.PROXY_SERVER)
    public static CommonProxy proxy;

    public static File configFolder;
    public static Logger logger;

    public static OverloadedConfig cachedConfig;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Overloaded.logger = event.getModLog();
        configFolder = new File(event.getModConfigurationDirectory(), "overloaded/");
        configFolder.mkdir();
        ConfigSyncHandler.INSTANCE.updateConfig();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        System.out.println(UnsafeItemStack.create(Items.APPLE, 1));
    }
}
