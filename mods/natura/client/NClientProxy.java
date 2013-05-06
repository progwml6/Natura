package mods.natura.client;

import java.io.File;

import mods.natura.common.NCommonProxy;
import mods.natura.common.NContent;
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
        LanguageRegistry.instance().addStringLocalization("item.boneBag.name", "en_US", "Bonemeal Bag");
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
		
        LanguageRegistry.instance().addStringLocalization("block.log.maple.name", "en_US", "Maple Wood");
        LanguageRegistry.instance().addStringLocalization("block.log.silverbell.name", "en_US", "Silverbell Wood");
        LanguageRegistry.instance().addStringLocalization("block.log.purpleheart.name", "en_US", "Amaranth Wood");
        LanguageRegistry.instance().addStringLocalization("block.log.tiger.name", "en_US", "Tiger Wood");
        LanguageRegistry.instance().addStringLocalization("block.log.willow.name", "en_US", "Willow Wood");
        
        LanguageRegistry.instance().addStringLocalization("block.log.darkwood.name", "en_US", "Darkwood");
        LanguageRegistry.instance().addStringLocalization("block.log.fusewood.name", "en_US", "Fusewood");
        
        LanguageRegistry.instance().addStringLocalization("block.leaves.darkwood.name", "en_US", "Darkwood Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.darkwood.flowering.name", "en_US", "Darkwood Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.darkwood.fruit.name", "en_US", "Darkwood Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.fusewood.name", "en_US", "Fusewood Leaves");
        
        LanguageRegistry.instance().addStringLocalization("item.food.nether.potashapple.name", "en_US", "Potash Apple");
		
        LanguageRegistry.instance().addStringLocalization("block.leaves.maple.name", "en_US", "Maple Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.silverbell.name", "en_US", "Silverbell Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.purpleheart.name", "en_US", "Amaranth Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.tiger.name", "en_US", "Tiger Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.willow.name", "en_US", "Willow Leaves");

		LanguageRegistry.instance().addStringLocalization("normalcloud.name", "en_US", "Cloud");
		LanguageRegistry.instance().addStringLocalization("darkcloud.name", "en_US", "Dark Cloud");
		LanguageRegistry.instance().addStringLocalization("ashcloud.name", "en_US", "Ash Cloud");
		LanguageRegistry.instance().addStringLocalization("sulfurcloud.name", "en_US", "Sulfur Cloud");

		LanguageRegistry.instance().addName(NContent.bloodwood, "Bloodwood");
		LanguageRegistry.instance().addName(NContent.saguaro, "Saguaro Cactus");

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

		LanguageRegistry.instance().addStringLocalization("block.sapling.eucalyptus.name", "en_US", "Eucalyptus Sapling");
		LanguageRegistry.instance().addStringLocalization("block.sapling.sakura.name", "en_US", "Sakura Sapling");
		LanguageRegistry.instance().addStringLocalization("block.sapling.ghost.name", "en_US", "Ghostwood Sapling");
		LanguageRegistry.instance().addStringLocalization("block.sapling.bush.name", "en_US", "Hopseed Sapling");
		LanguageRegistry.instance().addStringLocalization("block.sapling.redwood.name", "en_US", "Redwood Sapling");
		LanguageRegistry.instance().addStringLocalization("block.sapling.blood.name", "en_US", "Blood Sapling");
        LanguageRegistry.instance().addStringLocalization("block.sapling.darkwood.name", "en_US", "Darkwood Sapling");
        LanguageRegistry.instance().addStringLocalization("block.sapling.fusewood.name", "en_US", "Fusewood Sapling");
        LanguageRegistry.instance().addStringLocalization("block.sapling.willow.name", "en_US", "Willow Sapling");
        LanguageRegistry.instance().addStringLocalization("block.sapling.tiger.name", "en_US", "Tigerwood Sapling");
        LanguageRegistry.instance().addStringLocalization("block.sapling.maple.name", "en_US", "Maple Sapling");
        LanguageRegistry.instance().addStringLocalization("block.sapling.purpleheart.name", "en_US", "Amaranth Sapling");
        LanguageRegistry.instance().addStringLocalization("block.sapling.silverbell.name", "en_US", "Silverbell Sapling");
        
        LanguageRegistry.instance().addStringLocalization("block.glowshroom.green.name", "en_US", "Green Glowshroom");
        LanguageRegistry.instance().addStringLocalization("block.glowshroom.purple.name", "en_US", "Purple Glowshroom");

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
		LanguageRegistry.addName(NContent.taintedSoil, "Tainted Soil");
        LanguageRegistry.addName(NContent.heatSand, "Heat Sand");
        LanguageRegistry.addName(NContent.potashApple, "Potash Apple");
	}

	public File getMinecraftDir ()
	{
		return Minecraft.getMinecraftDir();
	}
}
