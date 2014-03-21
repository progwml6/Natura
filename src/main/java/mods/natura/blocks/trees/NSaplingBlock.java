package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.Natura;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import mods.natura.worldgen.BloodTreeLargeGen;
import mods.natura.worldgen.BushTreeGen;
import mods.natura.worldgen.DarkwoodGen;
import mods.natura.worldgen.EucalyptusTreeGenShort;
import mods.natura.worldgen.FusewoodGen;
import mods.natura.worldgen.RedwoodTreeGen;
import mods.natura.worldgen.SakuraTreeGen;
import mods.natura.worldgen.WhiteTreeGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NSaplingBlock extends BlockSapling
{
    public IIcon[] icons;
    public String[] textureNames = new String[] { "redwood", "eucalyptus", "hopseed", "sakura", "ghostwood", "bloodwood", "darkwood", "fusewood" };

    public NSaplingBlock()
    {
        super();
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_sapling");
        }
    }

    @Override
    public boolean canPlaceBlockAt (World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block == null || block.isReplaceable(world, x, y, z))
        {
            Block lowerID = world.getBlock(x, y - 1, z);
            //return canThisPlantGrowOnThisBlockID(lowerID);
            if (!canThisPlantGrowOnThisBlock(lowerID))
            {
                Block upperID = world.getBlock(x, y + 1, z);
                return canThisPlantGrowOnThisBlock(upperID);
            }
            else
                return true;
        }
        return false;
    }

    public boolean canThisPlantGrowOnThisBlock (Block id)
    {
        return id == Blocks.grass || id == Blocks.dirt || id == Blocks.soul_sand || id == Blocks.netherrack || id == NContent.taintedSoil;
    }

    @Override
    public boolean canBlockStay (World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) % 8;
        switch (meta)
        {
        case 0:
        case 1:
        case 2:
        case 3:
            Block soil = world.getBlock(x, y - 1, z);
            return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && (soil != null && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this));
        case 4:
        case 6:
        case 7:
            Block netherSoil = world.getBlock(x, y - 1, z);
            return netherSoil != null && (netherSoil == Blocks.netherrack || netherSoil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this));
        case 5:
            Block nSoil = world.getBlock(x, y + 1, z);
            return nSoil != null && (nSoil == Blocks.netherrack || nSoil == Blocks.soul_sand || nSoil == NContent.taintedSoil);
        default:
            return true;
        }
    }

    @Override
    public EnumPlantType getPlantType (IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) % 8;
        if (meta <= 3)
            return EnumPlantType.Plains;
        else
            return EnumPlantType.Nether;
    }
    
    @Override
    public void updateTick (World world, int x, int y, int z, Random random)
    {
        if (world.isRemote)
        {
            return;
        }
        super.updateTick(world, x, y, z, random);
        int md = world.getBlockMetadata(x, y, z);
        if (md % 8 == 0)
        {
            if (world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextInt(120) == 0)
            {
                if ((md & 8) == 0)
                    world.setBlockMetadataWithNotify(x, y, z, md | 8, 4);

                else
                {
                    int numSaplings = 0;
                    for (int xPos = -3; xPos <= 3; xPos++)
                    {
                        for (int zPos = -3; zPos <= 3; zPos++)
                        {
                            int ecks = x + xPos, zee = z + zPos;
                            if (world.getBlock(x + xPos, y, z + zPos) == this && world.getBlockMetadata(x + xPos, y, z + zPos) % 8 == 0)
                            {
                                numSaplings++;
                            }
                        }
                    }

                    if (numSaplings >= 40)
                    {
                        for (int xPos = -4; xPos <= 4; xPos++)
                        {
                            for (int zPos = -4; zPos <= 4; zPos++)
                            {
                                int ecks = x + xPos, zee = z + zPos;
                                if (world.getBlock(ecks, y, zee) == this && world.getBlockMetadata(ecks, y, zee) % 8 == 0)
                                {
                                    world.setBlock(ecks, y, zee, Blocks.air, 0, 4);
                                }
                            }
                        }
                        func_149879_c(world, x, y, z, random);
                    }
                }
            }
        }
        else if (md % 8 <= 3)
        {
            if (random.nextInt(10) == 0 && world.getBlockLightValue(x, y + 1, z) >= 9)//&& random.nextInt(120) == 0)
            {
                if ((md & 8) == 0)
                    world.setBlockMetadataWithNotify(x, y, z, md | 8, 4);

                else
                    func_149879_c(world, x, y, z, random);
            }
        }
        else if (random.nextInt(10) == 0)
        {
            if ((md & 8) == 0)
                world.setBlockMetadataWithNotify(x, y, z, md | 8, 4);

            else
                func_149879_c(world, x, y, z, random);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return icons[meta % 8];
    }
    
    public void func_149879_c(World p_149879_1_, int p_149879_2_, int p_149879_3_, int p_149879_4_, Random p_149879_5_)
    {
        int l = p_149879_1_.getBlockMetadata(p_149879_2_, p_149879_3_, p_149879_4_);

        if ((l & 8) == 0)
        {
            p_149879_1_.setBlockMetadataWithNotify(p_149879_2_, p_149879_3_, p_149879_4_, l | 8, 4);
        }
        else
        {
            this.func_149878_d(p_149879_1_, p_149879_2_, p_149879_3_, p_149879_4_, p_149879_5_);
        }
    }
    
    public void func_149878_d(World world, int x, int y, int z, Random random)
    {
        int md = world.getBlockMetadata(x, y, z) % 8;
        world.setBlock(x, y, z, Blocks.air);
        WorldGenerator obj = null;

        if (md == 1)
            obj = new EucalyptusTreeGenShort(0, 1);

        else if (md == 2)
            obj = new BushTreeGen(true, 2, 3, 2);

        else if (md == 3)
            obj = new SakuraTreeGen(true, 1, 0);

        else if (md == 4)
            obj = new WhiteTreeGen(true, 2, 1);

        else if (md == 5)
            obj = new BloodTreeLargeGen(3, 2);

        else if (md == 6)
            obj = new DarkwoodGen(true, 3, 0);

        else if (md == 7)
            obj = new FusewoodGen(true, 3, 1);

        else
            obj = new RedwoodTreeGen(true, NContent.redwood);

        if (!(obj.generate(world, random, x, y, z)))
            world.setBlock(x, y, z, this, md + 8, 3);
    }

    @Override
    public int damageDropped (int i)
    {
        return i % 8;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 8; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
    
    public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
    {
        return true;
    }

    public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
    {
        return (double)p_149852_1_.rand.nextFloat() < 0.45D;
    }

    public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
    {
        this.func_149879_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
    }
}
