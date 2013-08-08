package mods.natura.client;

import java.io.IOException;

import mods.natura.client.entity.FlameSpiderRender;
import mods.natura.client.entity.FusewoodArrowRender;
import mods.natura.client.entity.ImpModel;
import mods.natura.client.entity.ImpRender;
import mods.natura.client.entity.NitroCreeperRender;
import mods.natura.common.NContent;
import mods.natura.common.NProxyCommon;
import mods.natura.entity.FlameSpider;
import mods.natura.entity.FlameSpiderBaby;
import mods.natura.entity.FusewoodArrow;
import mods.natura.entity.ImpEntity;
import mods.natura.entity.NitroCreeper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class NProxyClient extends NProxyCommon
{
    @Override
    public void registerRenderer ()
    {
        RenderingRegistry.registerBlockHandler(new BerryRender());
        RenderingRegistry.registerBlockHandler(new SaguaroRenderer());
        RenderingRegistry.registerBlockHandler(new CropRender());
        RenderingRegistry.registerBlockHandler(new FenceRender());
        TickRegistry.registerTickHandler(new NCropsTickHandler(), Side.CLIENT);

        RenderingRegistry.registerEntityRenderingHandler(ImpEntity.class, new ImpRender(new ImpModel(), 0f));
        RenderingRegistry.registerEntityRenderingHandler(FlameSpider.class, new FlameSpiderRender());
        RenderingRegistry.registerEntityRenderingHandler(NitroCreeper.class, new NitroCreeperRender());
        RenderingRegistry.registerEntityRenderingHandler(FusewoodArrow.class, new FusewoodArrowRender());
        RenderingRegistry.registerEntityRenderingHandler(FlameSpiderBaby.class, new FlameSpiderRender());

        Minecraft mc = Minecraft.getMinecraft();
        try
        {
            GrassColorizerAlternate.setBlueGrassBiomeColorizer(TextureUtil.func_110986_a(mc.func_110442_L(), bluegrass));
            GrassColorizerAlternate.setOrangeGrassBiomeColorizer(TextureUtil.func_110986_a(mc.func_110442_L(), bluegrass));
        }
        catch (IOException ioexception)
        {
            ;
        }
    }

    private static final ResourceLocation bluegrass = new ResourceLocation("assets/natura/textures/misc/bluegrasscolor.png");
    private static final ResourceLocation orangegrass = new ResourceLocation("assets/natura/textures/misc/orangegrasscolor.png");

    @Override
    public void addNames ()
    {
        super.addNames();
        LanguageRegistry.instance().addStringLocalization("item.natura.spawnegg.name", "en_US", "Spawn ");

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
        LanguageRegistry.instance().addStringLocalization("item.fletching.ghostwood.name", "en_US", "Ghostwood Fletching");
        LanguageRegistry.instance().addStringLocalization("item.leather.imp.name", "en_US", "Imp Leather");
        LanguageRegistry.instance().addStringLocalization("item.string.flame.name", "en_US", "Flamestring");
        LanguageRegistry.instance().addStringLocalization("item.dye.blue.name", "en_US", "Blue Dye");

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

        LanguageRegistry.instance().addStringLocalization("item.bowl.mushroom.name", "en_US", "Mushroom Stew");
        LanguageRegistry.instance().addStringLocalization("item.bowl.glowshroom.name", "en_US", "Glowshroom Stew");

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
        LanguageRegistry.instance().addName(NContent.bluebells, "Bluebells");

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
        LanguageRegistry.instance().addStringLocalization("bloodNPlanks.name", "en_US", "Bloodwood Planks");
        LanguageRegistry.instance().addStringLocalization("redwoodNPlanks.name", "en_US", "Redwood Planks");
        LanguageRegistry.instance().addStringLocalization("mapleNPlanks.name", "en_US", "Maple Planks");
        LanguageRegistry.instance().addStringLocalization("silverbellNPlanks.name", "en_US", "Silverbell Planks");
        LanguageRegistry.instance().addStringLocalization("tigerNPlanks.name", "en_US", "Tigerwood Planks");
        LanguageRegistry.instance().addStringLocalization("purpleheartNPlanks.name", "en_US", "Amaranth Planks");
        LanguageRegistry.instance().addStringLocalization("willowNPlanks.name", "en_US", "Willow Planks");
        LanguageRegistry.instance().addStringLocalization("darkwoodNPlanks.name", "en_US", "Darkwood Planks");
        LanguageRegistry.instance().addStringLocalization("fusewoodNPlanks.name", "en_US", "Fusewood Planks");

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
        
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.eucalyptus.name", "en_US", "Eucalyptus Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.sakura.name", "en_US", "Sakura Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.ghostwood.name", "en_US", "Ghostwood Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.hopseed.name", "en_US", "Hopseed Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.redwood.name", "en_US", "Redwood Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.bloodwood.name", "en_US", "Blood Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.darkwood.name", "en_US", "Darkwood Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.fusewood.name", "en_US", "Fusewood Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.willow.name", "en_US", "Willow Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.tiger.name", "en_US", "Tigerwood Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.maple.name", "en_US", "Maple Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.amaranth.name", "en_US", "Amaranth Pressure Plate");
        LanguageRegistry.instance().addStringLocalization("tile.pressureplate.silverbell.name", "en_US", "Silverbell Pressure Plate");
        
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.eucalyptus.name", "en_US", "Eucalyptus Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.sakura.name", "en_US", "Sakura Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.ghostwood.name", "en_US", "Ghostwood Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.hopseed.name", "en_US", "Hopseed Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.redwood.name", "en_US", "Redwood Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.bloodwood.name", "en_US", "Blood Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.darkwood.name", "en_US", "Darkwood Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.fusewood.name", "en_US", "Fusewood Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.willow.name", "en_US", "Willow Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.tiger.name", "en_US", "Tigerwood Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.maple.name", "en_US", "Maple Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.amaranth.name", "en_US", "Amaranth Fence Gate");
        LanguageRegistry.instance().addStringLocalization("tile.fenceGate.silverbell.name", "en_US", "Silverbell Fence Gate");
        
        LanguageRegistry.instance().addStringLocalization("tile.stair.eucalyptus.name", "en_US", "Eucalyptus Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.sakura.name", "en_US", "Sakura Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.ghostwood.name", "en_US", "Ghostwood Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.hopseed.name", "en_US", "Hopseed Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.redwood.name", "en_US", "Redwood Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.bloodwood.name", "en_US", "Blood Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.darkwood.name", "en_US", "Darkwood Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.fusewood.name", "en_US", "Fusewood Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.willow.name", "en_US", "Willow Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.tiger.name", "en_US", "Tigerwood Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.maple.name", "en_US", "Maple Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.amaranth.name", "en_US", "Amaranth Stairs");
        LanguageRegistry.instance().addStringLocalization("tile.stair.silverbell.name", "en_US", "Silverbell Stairs");
        
        LanguageRegistry.instance().addStringLocalization("tile.button.eucalyptus.name", "en_US", "Eucalyptus Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.sakura.name", "en_US", "Sakura Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.ghostwood.name", "en_US", "Ghostwood Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.hopseed.name", "en_US", "Hopseed Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.redwood.name", "en_US", "Redwood Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.bloodwood.name", "en_US", "Blood Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.darkwood.name", "en_US", "Darkwood Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.fusewood.name", "en_US", "Fusewood Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.willow.name", "en_US", "Willow Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.tiger.name", "en_US", "Tigerwood Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.maple.name", "en_US", "Maple Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.amaranth.name", "en_US", "Amaranth Button");
        LanguageRegistry.instance().addStringLocalization("tile.button.silverbell.name", "en_US", "Silverbell Button");
        
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.eucalyptus.name", "en_US", "Eucalyptus Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.sakura.name", "en_US", "Sakura Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.ghostwood.name", "en_US", "Ghostwood Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.hopseed.name", "en_US", "Hopseed Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.redwood.name", "en_US", "Redwood Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.bloodwood.name", "en_US", "Blood Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.darkwood.name", "en_US", "Darkwood Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.fusewood.name", "en_US", "Fusewood Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.willow.name", "en_US", "Willow Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.tiger.name", "en_US", "Tigerwood Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.maple.name", "en_US", "Maple Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.amaranth.name", "en_US", "Amaranth Trapdoor");
        LanguageRegistry.instance().addStringLocalization("tile.trapdoor.silverbell.name", "en_US", "Silverbell Trapdoor");

        LanguageRegistry.instance().addStringLocalization("block.glowshroom.green.name", "en_US", "Green Glowshroom");
        LanguageRegistry.instance().addStringLocalization("block.glowshroom.purple.name", "en_US", "Purple Glowshroom");
        LanguageRegistry.instance().addStringLocalization("block.glowshroom.blue.name", "en_US", "Blue Glowshroom");

        LanguageRegistry.instance().addStringLocalization("eucalyptusNLeaves.name", "en_US", "Eucalyptus Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.sakura.name", "en_US", "Sakura Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.ghost.name", "en_US", "Ghostwood Leaves");
        LanguageRegistry.instance().addStringLocalization("bushNLeaves.name", "en_US", "Hopseed Leaves");
        LanguageRegistry.instance().addStringLocalization("redwoodNLeaves.name", "en_US", "Redwood Leaves");
        LanguageRegistry.instance().addStringLocalization("block.leaves.blood.name", "en_US", "Bloodleaves");

        LanguageRegistry.instance().addStringLocalization("eucalyptusLog.name", "en_US", "Eucalyptus Wood");
        LanguageRegistry.instance().addStringLocalization("sakuraLog.name", "en_US", "Sakura Wood");
        LanguageRegistry.instance().addStringLocalization("ghostLog.name", "en_US", "Ghostwood");
        LanguageRegistry.instance().addStringLocalization("hopseedLog.name", "en_US", "Hopseed Wood");

        LanguageRegistry.instance().addStringLocalization("redwoodBoat.name", "en_US", "Redwood Boat");
        LanguageRegistry.instance().addStringLocalization("bloodBoat.name", "en_US", "Bloodwood Boat");
        LanguageRegistry.instance().addStringLocalization("whiteBoat.name", "en_US", "White Boat");
        LanguageRegistry.instance().addStringLocalization("eucalyptusBoat.name", "en_US", "Eucalyptus Boat");

        LanguageRegistry.instance().addStringLocalization("item.impmeat.raw.name", "en_US", "Raw Imphide");
        LanguageRegistry.instance().addStringLocalization("item.impmeat.cooked.name", "en_US", "Cooked Imphide");

        LanguageRegistry.instance().addStringLocalization("block.soil.grass.name", "en_US", "Topiary Grass");
        LanguageRegistry.instance().addStringLocalization("block.soil.bluegrass.name", "en_US", "Bluegrass");
        LanguageRegistry.instance().addStringLocalization("block.soil.autumngrass.name", "en_US", "Autumnal Grass");
        LanguageRegistry.instance().addStringLocalization("block.soil.grass.slab.name", "en_US", "Topiary Grass Slab");
        LanguageRegistry.instance().addStringLocalization("block.soil.bluegrass.slab.name", "en_US", "Bluegrass Slab");
        LanguageRegistry.instance().addStringLocalization("block.soil.autumngrass.slab.name", "en_US", "Autumnal Grass Slab");

        LanguageRegistry.addName(NContent.taintedSoil, "Tainted Soil");
        LanguageRegistry.addName(NContent.heatSand, "Heat Sand");
        LanguageRegistry.addName(NContent.thornVines, "Thornvines");
        LanguageRegistry.addName(NContent.potashApple, "Potash Apple");
        LanguageRegistry.addName(NContent.stickItem, "Stick");

        LanguageRegistry.addName(NContent.ghostwoodSword, "Ghostwood Sword");
        LanguageRegistry.addName(NContent.ghostwoodPickaxe, "Ghostwood Pickaxe");
        LanguageRegistry.addName(NContent.ghostwoodShovel, "Ghostwood Shovel");
        LanguageRegistry.addName(NContent.ghostwoodAxe, "Ghostwood Hatchet");
        LanguageRegistry.addName(NContent.ghostwoodKama, "Ghostwood Kama");
        LanguageRegistry.addName(NContent.ghostwoodBow, "Ghostwood Bow");

        LanguageRegistry.addName(NContent.bloodwoodSword, "Bloodwood Sword");
        LanguageRegistry.addName(NContent.bloodwoodPickaxe, "Bloodwood Pickaxe");
        LanguageRegistry.addName(NContent.bloodwoodShovel, "Bloodwood Shovel");
        LanguageRegistry.addName(NContent.bloodwoodAxe, "Bloodwood Hatchet");
        LanguageRegistry.addName(NContent.bloodwoodKama, "Bloodwood Kama");
        LanguageRegistry.addName(NContent.bloodwoodBow, "Bloodwood Bow");

        LanguageRegistry.addName(NContent.darkwoodSword, "Darkwood Sword");
        LanguageRegistry.addName(NContent.darkwoodPickaxe, "Darkwood Pickaxe");
        LanguageRegistry.addName(NContent.darkwoodShovel, "Darkwood Shovel");
        LanguageRegistry.addName(NContent.darkwoodAxe, "Darkwood Hatchet");
        LanguageRegistry.addName(NContent.darkwoodKama, "Darkwood Kama");
        LanguageRegistry.addName(NContent.darkwoodBow, "Darkwood Bow");

        LanguageRegistry.addName(NContent.fusewoodSword, "Fusewood Sword");
        LanguageRegistry.addName(NContent.fusewoodPickaxe, "Fusewood Pickaxe");
        LanguageRegistry.addName(NContent.fusewoodShovel, "Fusewood Shovel");
        LanguageRegistry.addName(NContent.fusewoodAxe, "Fusewood Hatchet");
        LanguageRegistry.addName(NContent.fusewoodKama, "Fusewood Kama");
        LanguageRegistry.addName(NContent.fusewoodBow, "Fusewood Bow");

        LanguageRegistry.addName(NContent.netherquartzSword, "Quartz Sword");
        LanguageRegistry.addName(NContent.netherquartzPickaxe, "Quartz Pickaxe");
        LanguageRegistry.addName(NContent.netherquartzShovel, "Quartz Shovel");
        LanguageRegistry.addName(NContent.netherquartzAxe, "Quartz Hatchet");
        LanguageRegistry.addName(NContent.netherquartzKama, "Quartz Kama");

        LanguageRegistry.addName(NContent.impHelmet, "Impskin Helmet");
        LanguageRegistry.addName(NContent.impJerkin, "Impskin Jerkin");
        LanguageRegistry.addName(NContent.impLeggings, "Impskin Leggings");
        LanguageRegistry.addName(NContent.impBoots, "Impskin Boots");
    }

    public static void renderStandardInvBlock (RenderBlocks renderblocks, Block block, int meta)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    /*public File getMinecraftDir ()
    {
        return Minecraft.getMinecraftDir();
    }*/
}
