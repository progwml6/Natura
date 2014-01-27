package mods.natura;

import java.util.Random;
import java.util.logging.Logger;

import mods.natura.common.*;
import mods.natura.dimension.NetheriteWorldProvider;
import mods.natura.gui.NGuiHandler;
import mods.natura.plugins.PluginController;
import mods.natura.worldgen.BaseCloudWorldgen;
import mods.natura.worldgen.BaseCropWorldgen;
import mods.natura.worldgen.BaseTreeWorldgen;
import mods.natura.worldgen.retro.TickHandlerWorld;
import mods.natura.worldgen.retro.WorldHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "Natura", name = "Natura", version = "2.1.14")
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

    public static Logger logger = Logger.getLogger("Natura");

    @EventHandler
    public void preInit (FMLPreInitializationEvent evt)
    {
        MinecraftForge.EVENT_BUS.register(this);
        logger.setParent(FMLCommonHandler.instance().getFMLLogger());

        PluginController.getController().registerBuiltins();

        PHNatura.initProps(evt.getModConfigurationDirectory());
        content = new NContent();
        content.preInit();
        content.addOredictSupport();
        content.postIntermodCommunication();

        PluginController.getController().preInit();
    }

    public static BaseCropWorldgen crops;
    public static BaseCloudWorldgen clouds;
    public static BaseTreeWorldgen trees;

    @EventHandler
    public void init (FMLInitializationEvent evt)
    {
        GameRegistry.registerWorldGenerator(crops = new BaseCropWorldgen());
        GameRegistry.registerWorldGenerator(clouds = new BaseCloudWorldgen());
        GameRegistry.registerWorldGenerator(trees = new BaseTreeWorldgen());
        NaturaTab.init(content.wheatBag.itemID);
        proxy.registerRenderer();
        proxy.addNames();
        NetworkRegistry.instance().registerGuiHandler(instance, new NGuiHandler());

        content.intermodCommunication();
        GameRegistry.registerFuelHandler(content);

        if (PHNatura.overrideNether)
        {
            DimensionManager.unregisterProviderType(-1);
            DimensionManager.registerProviderType(-1, NetheriteWorldProvider.class, true);
        }
        
        MinecraftForge.EVENT_BUS.register(new WorldHandler());
        
        if(retrogen)
        {
        	TickRegistry.registerTickHandler(new TickHandlerWorld(), Side.SERVER);
        }
        OreDictionary.registerOre("cropVine", new ItemStack(NContent.thornVines));
        random.setSeed(2 ^ 16 + 2 ^ 8 + (4 * 3 * 271));

        PluginController.getController().init();
    }

    @EventHandler
    public void postInit (FMLPostInitializationEvent evt)
    {
        content.createEntities();
        content.modIntegration();

        PluginController.getController().postInit();
    }

    @ForgeSubscribe
    public void bonemealEvent (BonemealEvent event)
    {
        if (!event.world.isRemote)
        {
            /*if (event.ID == content.crops.blockID)
            {
            	if (content.crops.boneFertilize(event.world, event.X, event.Y, event.Z, event.world.rand))
            		event.setResult(Event.Result.ALLOW);
            }*/
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
    public void spawnEvent (EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityCow || event.entity instanceof EntitySheep)
        {
            ((EntityLiving) event.entity).tasks.addTask(3, new EntityAITempt((EntityCreature) event.entity, 0.25F, NContent.plantItem.itemID, false));
        }

        if (event.entity instanceof EntityChicken)
        {
            ((EntityLiving) event.entity).tasks.addTask(3, new EntityAITempt((EntityCreature) event.entity, 0.25F, NContent.seeds.itemID, false));
        }
    }

    public static boolean retrogen;

    /*// RG removed until 1.7 needs a better implementation
    @ForgeSubscribe
    public void chunkDataLoad (ChunkDataEvent.Load event)
    {
        if (!event.getData().getBoolean("Natura.Retrogen") && retrogen)
        {
            Chunk chunk = event.getChunk();
            World world = chunk.worldObj;
            int chunkoffset = 1;
            //force adjacent chunks to load
            ChunkCoordIntPair coords = chunk.getChunkCoordIntPair();
            Chunk chunk01 = world.getChunkFromChunkCoords(coords.chunkXPos, coords.chunkZPos + chunkoffset);
            Chunk chunk10 = world.getChunkFromChunkCoords(coords.chunkXPos + chunkoffset, coords.chunkZPos);
            Chunk chunk11 = world.getChunkFromChunkCoords(coords.chunkXPos + chunkoffset, coords.chunkZPos + chunkoffset);
            crops.generate(random, chunk.xPosition, chunk.zPosition, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
            clouds.generate(random, chunk.xPosition, chunk.zPosition, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
            trees.retrogen = true;
            trees.generate(random, chunk.xPosition, chunk.zPosition, world, world.provider.createChunkGenerator(), world.provider.createChunkGenerator());
            trees.retrogen = false;
        }
    }*/
    
    @ForgeSubscribe
    public void chunkDataSave (ChunkDataEvent.Save event)
    {
        event.getData().setBoolean("Natura.Retrogen", true);
    }
    

    NContent content;
    public static Random random = new Random();
}
