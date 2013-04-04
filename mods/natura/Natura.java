package mods.natura;

import java.util.Random;

import mods.natura.common.NCommonProxy;
import mods.natura.common.NaturaContent;
import mods.natura.common.NaturaTab;
import mods.natura.common.PHNatura;
import mods.natura.worldgen.BaseCloudWorldgen;
import mods.natura.worldgen.BaseCropWorldgen;
import mods.natura.worldgen.BaseTreeWorldgen;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "Natura", name = "Natura", version = "1.5.1_2.0.5")
public class Natura
{
	/* Proxies for sides, used for graphics processing */
	@SidedProxy(clientSide = "mods.natura.client.NClientProxy", serverSide = "mods.natura.common.NCommonProxy")
	public static NCommonProxy proxy;
	
	/* Instance of this mod, used for grabbing prototype fields */
	@Instance("Natura")
	public static Natura instance;
	public static Material cloud = new CloudMaterial();
	
	@PreInit
	public void preInit(FMLPreInitializationEvent evt)
	{
		MinecraftForge.EVENT_BUS.register(this);
		
		PHNatura.initProps();
		content = new NaturaContent();
		content.preInit();
	}
	
	@Init
	public void init(FMLInitializationEvent evt)
	{
		GameRegistry.registerWorldGenerator(new BaseCropWorldgen());
		GameRegistry.registerWorldGenerator(new BaseCloudWorldgen());
		GameRegistry.registerWorldGenerator(new BaseTreeWorldgen());
		NaturaTab.init(content.wheatBag.itemID);
		proxy.registerRenderer();
		proxy.addNames();
		
		//DimensionManager.unregisterProviderType(-1);
		//DimensionManager.registerProviderType(-1, NetheriteWorldProvider.class, true);
	}
	
	@ForgeSubscribe
	public void bonemealEvent(BonemealEvent evt)
	{
		if (evt.ID == content.crops.blockID)
		{
			if (content.crops.fertilize(evt.world, evt.X, evt.Y, evt.Z));
			evt.setResult(Event.Result.ALLOW);
		}
		if (evt.ID == content.floraSapling.blockID)
		{
			if (content.floraSapling.fertilize(evt.world, evt.X, evt.Y, evt.Z, evt.world.rand));
				evt.setResult(Event.Result.ALLOW);
		}
	}
	
	NaturaContent content;
	public static Random random = new Random();
}
