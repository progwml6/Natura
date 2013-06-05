package mods.natura;

import java.util.Random;

import mods.natura.common.NProxyCommon;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import mods.natura.common.PHNatura;
import mods.natura.dimension.NetheriteWorldProvider;
import mods.natura.gui.NGuiHandler;
import mods.natura.worldgen.BaseCloudWorldgen;
import mods.natura.worldgen.BaseCropWorldgen;
import mods.natura.worldgen.BaseTreeWorldgen;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "Natura", name = "Natura", version = "1.5.1_2.1.RC1")
@NetworkMod(serverSideRequired = false, clientSideRequired = true)
public class Natura
{
	/* Proxies for sides, used for graphics processing */
	@SidedProxy(clientSide = "mods.natura.client.NProxyClient", serverSide = "mods.natura.common.NProxyCommon")
	public static NProxyCommon proxy;

	/* Instance of this mod, used for grabbing prototype fields */
	@Instance("Natura")
	public static Natura instance;
	public static Material cloud = new CloudMaterial();

	@PreInit
	public void preInit (FMLPreInitializationEvent evt)
	{
		MinecraftForge.EVENT_BUS.register(this);

		PHNatura.initProps();
		content = new NContent();
		content.preInit();
		content.addOredictSupport();
		content.postIntermodCommunication();
	}

	@Init
	public void init (FMLInitializationEvent evt)
	{
		GameRegistry.registerWorldGenerator(new BaseCropWorldgen());
		GameRegistry.registerWorldGenerator(new BaseCloudWorldgen());
		GameRegistry.registerWorldGenerator(new BaseTreeWorldgen());
		NaturaTab.init(content.wheatBag.itemID);
		proxy.registerRenderer();
		proxy.addNames();
		NetworkRegistry.instance().registerGuiHandler(instance, new NGuiHandler());

		content.intermodCommunication();

		if (PHNatura.overrideNether)
		{
			DimensionManager.unregisterProviderType(-1);
			DimensionManager.registerProviderType(-1, NetheriteWorldProvider.class, true);
		}
	}

	@PostInit
	public void postInit (FMLPostInitializationEvent evt)
	{
		content.createEntities();
		content.modIntegration();
	}

	@ForgeSubscribe
	public void bonemealEvent (BonemealEvent event)
	{
		if (!event.world.isRemote)
		{
			if (event.ID == content.crops.blockID)
			{
				if (content.crops.boneFertilize(event.world, event.X, event.Y, event.Z, event.world.rand))
					event.setResult(Event.Result.ALLOW);
			}
			if (event.ID == content.floraSapling.blockID)
			{
				if (content.floraSapling.boneFertilize(event.world, event.X, event.Y, event.Z, event.world.rand))
					event.setResult(Event.Result.ALLOW);
			}
			if (event.ID == content.rareSapling.blockID)
			{
				if (content.rareSapling.boneFertilize(event.world, event.X, event.Y, event.Z, event.world.rand))
					event.setResult(Event.Result.ALLOW);
			}
			if (event.ID == content.glowshroom.blockID)
			{
				if (content.glowshroom.fertilizeMushroom(event.world, event.X, event.Y, event.Z, event.world.rand))
					event.setResult(Event.Result.ALLOW);
			}
			if (event.ID == content.berryBush.blockID)
			{
				if (content.berryBush.boneFertilize(event.world, event.X, event.Y, event.Z, event.world.rand))
					event.setResult(Event.Result.ALLOW);
			}
			if (event.ID == content.netherBerryBush.blockID)
			{
				if (content.netherBerryBush.boneFertilize(event.world, event.X, event.Y, event.Z, event.world.rand))
					event.setResult(Event.Result.ALLOW);
			}
		}
	}

	@ForgeSubscribe
	public void interactEvent (EntityInteractEvent event)
	{
		//if (event.target == null)
		if (event.target instanceof EntityCow || event.target instanceof EntitySheep)
		{
			ItemStack equipped = event.entityPlayer.getCurrentEquippedItem();
			EntityAnimal creature = (EntityAnimal) event.target;
			if (equipped != null && equipped.itemID == NContent.plantItem.itemID && equipped.getItemDamage() == 0 && creature.getGrowingAge() == 0 && creature.inLove <= 0)
			{
				EntityPlayer player = event.entityPlayer;
				if (!player.capabilities.isCreativeMode)
				{
					--equipped.stackSize;

					if (equipped.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
					}
				}

				creature.inLove = 600;
				creature.setTarget(null);

				for (int i = 0; i < 7; ++i)
				{
					double d0 = random.nextGaussian() * 0.02D;
					double d1 = random.nextGaussian() * 0.02D;
					double d2 = random.nextGaussian() * 0.02D;
					creature.worldObj.spawnParticle("heart", creature.posX + (double) (random.nextFloat() * creature.width * 2.0F) - (double) creature.width,
							creature.posY + 0.5D + (double) (random.nextFloat() * creature.height), creature.posZ + (double) (random.nextFloat() * creature.width * 2.0F) - (double) creature.width,
							d0, d1, d2);
				}
			}
		}
	}

	@ForgeSubscribe
	public void spawnEvent (LivingSpawnEvent event)
	{
		if (event.entityLiving instanceof EntityCow || event.entityLiving instanceof EntitySheep)
		{
			event.entityLiving.tasks.addTask(3, new EntityAITempt((EntityCreature) event.entityLiving, 0.25F, NContent.plantItem.itemID, false));
		}

		if (event.entityLiving instanceof EntityChicken)
		{
			event.entityLiving.tasks.addTask(3, new EntityAITempt((EntityCreature) event.entityLiving, 0.25F, NContent.seeds.itemID, false));
		}
	}

	NContent content;
	public static Random random = new Random();
}
