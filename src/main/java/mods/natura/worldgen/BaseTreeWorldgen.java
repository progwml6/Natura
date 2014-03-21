package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.common.IWorldGenerator;

public class BaseTreeWorldgen implements IWorldGenerator
{
    public BaseTreeWorldgen()
    {
        genRedwood = new RedwoodTreeGen(false, NContent.redwood);
        genBlood = new BloodTreeLargeGen(3, 2);
        bush = new BushTreeGen(false, 2, 3, 2);
        pinkSakura = new SakuraTreeGen(false, 1, 0);
        whiteSakura = new WhiteTreeGen(false, 2, 1);
        eucalyptusShort = new EucalyptusTreeGenShort(0, 1);
        saguaro = new SaguaroGen(NContent.saguaro, 0, false);
        darkwood = new DarkwoodGen(false, 3, 0);
        fusewood = new FusewoodGen(false, 3, 1);

        silverbell = new RareTreeGen(false, 4, 2, 1, 1);
        purpleheart = new RareTreeGen(false, 9, 8, 2, 2);
        tiger = new RareTreeGen(false, 6, 4, 3, 3);
        maple = new RareTreeGen(false, 4, 2, 0, 0);
        willow = new WillowGen(false);

        blueGreenGlowshroom = new GlowshroomGenBlueGreen(false);
        purpleGlowshroom = new GlowshroomGenPurple(false);
    }

    RedwoodTreeGen genRedwood;
    BloodTreeLargeGen genBlood;
    BushTreeGen bush;
    SakuraTreeGen pinkSakura;
    WhiteTreeGen whiteSakura;
    EucalyptusTreeGenShort eucalyptusShort;
    DarkwoodGen darkwood;
    FusewoodGen fusewood;

    SaguaroGen saguaro;

    GlowshroomGenBlueGreen blueGreenGlowshroom;
    GlowshroomGenPurple purpleGlowshroom;

    RareTreeGen maple;
    RareTreeGen silverbell;
    RareTreeGen purpleheart;
    RareTreeGen tiger;
    WillowGen willow;

    public static boolean retrogen;

    @Override
    public void generate (Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int xSpawn, ySpawn, zSpawn;
        int xPos = chunkX * 16 + 8, zPos = chunkZ * 16 + 8;
        String biomeName = world.getWorldChunkManager().getBiomeGenAt(xPos, zPos).biomeName;
        
        if (biomeName == null) {
            return;
        }

        if (biomeName == "Forest" || biomeName == "AutumnWoods" || biomeName == "BirchForest" || biomeName == "PineForest" || biomeName == "Rainforest" || biomeName == "TemperateRainforest"
                || biomeName == "Woodlands")
        {
            if (PHNatura.generateSakura && random.nextInt(PHNatura.sakuraSpawnRarity * 5) == 0)
            {
                for (int iter = 0; iter < 3; iter++)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = random.nextInt(PHNatura.sakuraSpawnRange) + PHNatura.seaLevel;
                    zSpawn = zPos + random.nextInt(16);
                    pinkSakura.generate(world, random, xSpawn, ySpawn, zSpawn);
                }
            }
            if (PHNatura.generateSmallEucalyptus && random.nextInt(PHNatura.eucalyptusShortSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(PHNatura.eucalyptusShortSpawnRange) + PHNatura.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                eucalyptusShort.generate(world, random, xSpawn, ySpawn, zSpawn);
            }
        }
        if (biomeName == "Plains" || biomeName == "Meadow")
        {
            if (!retrogen && PHNatura.generateRedwood && random.nextInt(PHNatura.redwoodSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                zSpawn = zPos + random.nextInt(16);
                genRedwood.generate(world, random, xSpawn, PHNatura.seaLevel + 16, zSpawn);
            }
            if (PHNatura.generateSmallEucalyptus && random.nextInt((int) (PHNatura.eucalyptusShortSpawnRarity * 1.5)) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(32) + PHNatura.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                eucalyptusShort.generate(world, random, xSpawn, ySpawn, zSpawn);
            }
        }
        if (biomeName == "Extreme Hills" || biomeName == "Extreme Hills Edge" || biomeName == "ForestedHills" || biomeName == "GreenHills")
        {
            if (PHNatura.generateBush && random.nextInt(PHNatura.bushSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16) + 8;
                ySpawn = random.nextInt(PHNatura.bushSpawnRange) + PHNatura.seaLevel;
                zSpawn = zPos + random.nextInt(16) + 8;
                bush.generate(world, random, xSpawn, ySpawn, zSpawn);
            }
            if (PHNatura.generateSmallEucalyptus && random.nextInt(PHNatura.eucalyptusShortSpawnRarity) < 10)
            {
                xSpawn = xPos + random.nextInt(16) + 8;
                ySpawn = random.nextInt(PHNatura.eucalyptusShortSpawnRange) + PHNatura.seaLevel;
                zSpawn = zPos + random.nextInt(16) + 8;
                eucalyptusShort.generate(world, random, xSpawn, ySpawn, zSpawn);
            }
        }
        if (biomeName == "River")
        {
            if (PHNatura.generateSakura && random.nextInt((PHNatura.sakuraSpawnRarity)) == 0)
            {
                for (int iter = 0; iter < 3; iter++)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = random.nextInt(PHNatura.sakuraSpawnRange) + PHNatura.seaLevel;
                    zSpawn = zPos + random.nextInt(16);
                    pinkSakura.generate(world, random, xSpawn, ySpawn, zSpawn);
                }
            }
            if (PHNatura.generateWillow && random.nextInt(PHNatura.willowRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                zSpawn = zPos + random.nextInt(16);
                willow.generate(world, random, xSpawn, PHNatura.seaLevel + 16, zSpawn);

            }
        }

        if (biomeName == "Desert" || biomeName == "DesertHills")
        {
            if (PHNatura.generateSaguaro && random.nextInt((PHNatura.saguaroSpawnRarity)) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = random.nextInt(PHNatura.seaLevel) + 16;
                zSpawn = zPos + random.nextInt(16);
                saguaro.generate(world, random, xSpawn, ySpawn, zSpawn);
            }
        }

        if (biomeName == "Jungle" || biomeName == "JungleHills" || biomeName == "Extreme Jungle")
        {
            if (PHNatura.generatePurpleheart)// && random.nextInt((int) PHNatura.purpleheartRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                zSpawn = zPos + random.nextInt(16);
                purpleheart.generate(world, random, xSpawn, PHNatura.seaLevel + 48, zSpawn);
            }
        }
        if (biomeName == "Forest" || biomeName == "Woodlands" || biomeName == "AutumnWoods")
        {
            if (PHNatura.generateMaple && random.nextInt(PHNatura.mapleRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                zSpawn = zPos + random.nextInt(16);
                maple.generate(world, random, xSpawn, PHNatura.seaLevel + 48, zSpawn);
            }

            if (PHNatura.generateSilverbell && random.nextInt(PHNatura.silverbellRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                zSpawn = zPos + random.nextInt(16);
                silverbell.generate(world, random, xSpawn, PHNatura.seaLevel + 48, zSpawn);
            }
        }
        if (biomeName == "Forest" || biomeName == "Rainforest" || biomeName == "TemperateRainforest")
        {
            if (PHNatura.generateTiger && random.nextInt(PHNatura.tigerRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                zSpawn = zPos + random.nextInt(16);
                tiger.generate(world, random, xSpawn, PHNatura.seaLevel + 48, zSpawn);
            }
        }
        if (biomeName == "Swampland" || biomeName == "ForestHills")
        {
            if (PHNatura.generateWillow && random.nextInt(PHNatura.willowRarity) == 0)
            {
                for (int i = 0; i < 3; i++)
                {
                    xSpawn = xPos + random.nextInt(16);
                    zSpawn = zPos + random.nextInt(16);
                    willow.generate(world, random, xSpawn, PHNatura.seaLevel + 16, zSpawn);
                }
            }
        }

        //Nether trees
        if (biomeName.equals("Hell") || biomeName.equals("Boneyard") || biomeName.equals("Phantasmagoric Inferno") || biomeName.equals("Corrupted Sands") || biomeName.equals("Corrupted Sands"))
        {
            if (PHNatura.generateBloodwood && random.nextInt(PHNatura.bloodSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = 72;
                zSpawn = zPos + random.nextInt(16);
                genBlood.generate(world, random, xSpawn, ySpawn, zSpawn);
            }
            if (PHNatura.generateGhost && random.nextInt(PHNatura.ghostSpawnRarity) == 0)
            {
                for (int iter = 0; iter < 3; iter++)
                {
                    ySpawn = random.nextInt(80) + 16;
                    xSpawn = xPos + random.nextInt(16);
                    zSpawn = zPos + random.nextInt(16);
                    whiteSakura.generate(world, random, xSpawn, ySpawn, zSpawn);
                }
            }

            if (PHNatura.generateDarkwood && random.nextInt(PHNatura.darkSpawnRarity) == 0)
            {
                ySpawn = random.nextInt(64) + 32;
                xSpawn = xPos + random.nextInt(16);
                zSpawn = zPos + random.nextInt(16);
                darkwood.generate(world, random, xSpawn, ySpawn, zSpawn);
            }
            if (PHNatura.generateFusewood && random.nextInt(PHNatura.fuseSpawnRarity) == 0)
            {
                ySpawn = random.nextInt(64) + 32;
                xSpawn = xPos + random.nextInt(16);
                zSpawn = zPos + random.nextInt(16);
                fusewood.generate(world, random, xSpawn, ySpawn, zSpawn);
            }
            if (PHNatura.generateThornvines && random.nextInt(PHNatura.thornSpawnRarity) == 0)
            {
                ySpawn = 108;
                for (int i = 0; i < 20; i++)
                {
                    int vineMeta = random.nextInt(16);
                    xSpawn = xPos + random.nextInt(16);
                    zSpawn = zPos + random.nextInt(16);
                    int size = random.nextInt(25) + 1;
                    int height = ySpawn - (random.nextInt(size) + random.nextInt(size) + random.nextInt(size));
                    for (int yHeight = ySpawn; yHeight > height; yHeight--)
                    {
                        if (world.getBlock(xSpawn, yHeight, zSpawn) == Blocks.air)
                            world.setBlock(xSpawn, yHeight, zSpawn, NContent.thornVines, vineMeta, 2);
                    }
                }
            }
            if (random.nextInt(35) == 0)
            {
                for (int i = 0; i < 5; i++)
                {
                    WorldGenerator obj = random.nextInt(3) == 0 ? purpleGlowshroom : blueGreenGlowshroom;
                    xSpawn = xPos + random.nextInt(24) - 4;
                    zSpawn = zPos + random.nextInt(24) - 4;
                    ySpawn = findGround(world, xSpawn, random.nextInt(64) + 32, zSpawn);
                    if (ySpawn != -1)
                    {
                        obj.generate(world, random, xSpawn, ySpawn, zSpawn);
                    }
                }
            }
        }
    }

    int findGround (World world, int x, int y, int z)
    {
        boolean foundGround = false;
        int height = y;
        do
        {
            height--;
            Block underID = world.getBlock(x, height, z);
            if (underID == Blocks.netherrack || underID == Blocks.soul_sand || underID == NContent.taintedSoil || height < 0)
                foundGround = true;
        } while (!foundGround);
        return height + 1;
    }
}
