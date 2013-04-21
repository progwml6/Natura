package mods.natura.client;

import java.io.File;

import mods.natura.common.NCommonProxy;
import mods.natura.common.NaturaContent;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class NClientProxy extends NCommonProxy
{
	@Override
	public void registerRenderer ()
	{
		RenderingRegistry.registerBlockHandler(new BerryRender());
		RenderingRegistry.registerBlockHandler(new SaguaroRenderer());
		RenderingRegistry.registerBlockHandler(new CropRender());
        RenderingRegistry.registerBlockHandler(new OmniShapeRender());
		TickRegistry.registerTickHandler(new NCropsTickHandler(), Side.CLIENT);
	}

	@Override
	public void addNames ()
	{
		LanguageRegistry.instance().addStringLocalization("item.wheatBag.name", "en_US", "Wheat Seed Bag");
		LanguageRegistry.instance().addStringLocalization("item.barleyBag.name", "en_US", "Barley Seed Bag");
		LanguageRegistry.instance().addStringLocalization("item.potatoBag.name", "en_US", "Potato Bag");
		LanguageRegistry.instance().addStringLocalization("item.carrotBag.name", "en_US", "Carrot Bag");
		LanguageRegistry.instance().addStringLocalization("item.wartBag.name", "en_US", "Nether Wart Bag");
		LanguageRegistry.instance().addStringLocalization("item.cottonBag.name", "en_US", "Cotton Seed Bag");
		LanguageRegistry.instance().addStringLocalization("item.barley.seed.name", "en_US", "Barley Seeds");
		LanguageRegistry.instance().addStringLocalization("item.barley.plant.name", "en_US", "Barley");
		LanguageRegistry.instance().addStringLocalization("item.barley.flour.name", "en_US", "Barley Flour");
		LanguageRegistry.instance().addStringLocalization("item.wheat.flour.name", "en_US", "Wheat Flour");
		LanguageRegistry.instance().addStringLocalization("item.cotton.plant.name", "en_US", "Cotton");
		LanguageRegistry.instance().addStringLocalization("item.cotton.seed.name", "en_US", "Cotton Seeds");
		LanguageRegistry.instance().addStringLocalization("item.powder.sulfur.name", "en_US", "Sulfur");
		LanguageRegistry.instance().addStringLocalization("item.waterdrop.name", "en_US", "Cactus Juice");
		LanguageRegistry.instance().addStringLocalization("item.saguaro.fruit.name", "en_US", "Saguaro Fruit");

		LanguageRegistry.instance().addStringLocalization("item.berry.rasp.name", "en_US", "Raspberry");
		LanguageRegistry.instance().addStringLocalization("item.berry.blue.name", "en_US", "Blueberry");
		LanguageRegistry.instance().addStringLocalization("item.berry.black.name", "en_US", "Blackberry");
		LanguageRegistry.instance().addStringLocalization("item.berry.geo.name", "en_US", "Maloberry");
		LanguageRegistry.instance().addStringLocalization("item.berry.medley.name", "en_US", "Berry Medley");
		LanguageRegistry.instance().addStringLocalization("item.berry.blight.name", "en_US", "Blightberry");
		LanguageRegistry.instance().addStringLocalization("item.berry.dusk.name", "en_US", "Duskberry");
		LanguageRegistry.instance().addStringLocalization("item.berry.sky.name", "en_US", "Skyberry");
		LanguageRegistry.instance().addStringLocalization("item.berry.sting.name", "en_US", "Stingberry");
		
		LanguageRegistry.instance().addStringLocalization("block.raspberryBush.name", "en_US", "Raspberry Bush");
		LanguageRegistry.instance().addStringLocalization("block.blueberryBush.name", "en_US", "Blueberry Bush");
		LanguageRegistry.instance().addStringLocalization("block.blackberryBush.name", "en_US", "Blackberry Bush");
		LanguageRegistry.instance().addStringLocalization("block.geoberryBush.name", "en_US", "Maloberry Bush");
		
		LanguageRegistry.instance().addStringLocalization("block.bush.berry.blight.name", "en_US", "Blightberry Bush");
		LanguageRegistry.instance().addStringLocalization("block.bush.berry.dusk.name", "en_US", "Duskberry Bush");
		LanguageRegistry.instance().addStringLocalization("block.bush.berry.sky.name", "en_US", "Skyberry Bush");
		LanguageRegistry.instance().addStringLocalization("block.bush.berry.sting.name", "en_US", "Stingberry Bush");

		LanguageRegistry.instance().addStringLocalization("normalcloud.name", "en_US", "Cloud");
		LanguageRegistry.instance().addStringLocalization("darkcloud.name", "en_US", "Dark Cloud");
		LanguageRegistry.instance().addStringLocalization("ashcloud.name", "en_US", "Ash Cloud");
		LanguageRegistry.instance().addStringLocalization("sulfurcloud.name", "en_US", "Sulfur Cloud");

		LanguageRegistry.instance().addName(NaturaContent.bloodwood, "Bloodwood");
		LanguageRegistry.instance().addName(NaturaContent.saguaro, "Saguaro Cactus");

		LanguageRegistry.instance().addStringLocalization("redwoodNDoor.name", "en_US", "Redwood Door");
		LanguageRegistry.instance().addStringLocalization("eucalyptusNDoor.name", "en_US", "Eucalyptus Door");
		LanguageRegistry.instance().addStringLocalization("hopseedNDoor.name", "en_US", "Hopseed Door");
		LanguageRegistry.instance().addStringLocalization("sakuraNDoor.name", "en_US", "Sakura Door");
		LanguageRegistry.instance().addStringLocalization("ghostNDoor.name", "en_US", "Ghostwood Door");
		LanguageRegistry.instance().addStringLocalization("bloodNDoor.name", "en_US", "Bloodwood Door");
		LanguageRegistry.instance().addStringLocalization("redwoodBarkNDoor.name", "en_US", "Redwood Bark Door");

		LanguageRegistry.instance().addStringLocalization("barkRedwood.name", "en_US", "Redwood Bark");
		LanguageRegistry.instance().addStringLocalization("heartRedwood.name", "en_US", "Redwood");
		LanguageRegistry.instance().addStringLocalization("rootRedwood.name", "en_US", "Redwood Root");

		LanguageRegistry.instance().addStringLocalization("eucalyptusNPlanks.name", "en_US", "Eucalyptus Planks");
		LanguageRegistry.instance().addStringLocalization("sakuraNPlanks.name", "en_US", "Sakura Planks");
		LanguageRegistry.instance().addStringLocalization("ghostNPlanks.name", "en_US", "Ghostwood Planks");
		LanguageRegistry.instance().addStringLocalization("bushNPlanks.name", "en_US", "Hopseed Planks");
		LanguageRegistry.instance().addStringLocalization("redwoodNPlanks.name", "en_US", "Redwood Planks");
		LanguageRegistry.instance().addStringLocalization("bloodNPlanks.name", "en_US", "Bloodplanks");

		LanguageRegistry.instance().addStringLocalization("eucalyptusNSapling.name", "en_US", "Eucalyptus Sapling");
		LanguageRegistry.instance().addStringLocalization("sakuraNSapling.name", "en_US", "Sakura Sapling");
		LanguageRegistry.instance().addStringLocalization("ghostNSapling.name", "en_US", "Ghostwood Sapling");
		LanguageRegistry.instance().addStringLocalization("bushNSapling.name", "en_US", "Hopseed Sapling");
		LanguageRegistry.instance().addStringLocalization("redwoodNSapling.name", "en_US", "Redwood Sapling");
		LanguageRegistry.instance().addStringLocalization("bloodNSapling.name", "en_US", "Blood Sapling");

		LanguageRegistry.instance().addStringLocalization("eucalyptusNLeaves.name", "en_US", "Eucalyptus Leaves");
		LanguageRegistry.instance().addStringLocalization("sakuraNLeaves.name", "en_US", "Sakura Leaves");
		LanguageRegistry.instance().addStringLocalization("ghostNLeaves.name", "en_US", "White Leaves");
		LanguageRegistry.instance().addStringLocalization("bushNLeaves.name", "en_US", "Hopseed Leaves");
		LanguageRegistry.instance().addStringLocalization("redwoodNLeaves.name", "en_US", "Redwood Leaves");
		LanguageRegistry.instance().addStringLocalization("bloodNLeaves.name", "en_US", "Bloodleaves");

		LanguageRegistry.instance().addStringLocalization("eucalyptusLog.name", "en_US", "Eucalyptus Wood");
		LanguageRegistry.instance().addStringLocalization("sakuraLog.name", "en_US", "Sakura Wood");
		LanguageRegistry.instance().addStringLocalization("ghostLog.name", "en_US", "Ghostwood");
		LanguageRegistry.instance().addStringLocalization("hopseedLog.name", "en_US", "Hopseed Wood");

		LanguageRegistry.instance().addStringLocalization("redwoodBoat.name", "en_US", "Redwood Boat");
		LanguageRegistry.instance().addStringLocalization("bloodBoat.name", "en_US", "Bloodwood Boat");
		LanguageRegistry.instance().addStringLocalization("whiteBoat.name", "en_US", "White Boat");
		LanguageRegistry.instance().addStringLocalization("eucalyptusBoat.name", "en_US", "Eucalyptus Boat");
	}

	public File getMinecraftDir ()
	{
		return Minecraft.getMinecraftDir();
	}
}
