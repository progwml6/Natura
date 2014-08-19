package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import mods.natura.worldgen.RareTreeGen;
import mods.natura.worldgen.WillowGen;
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
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OverworldSapling extends BlockSapling
{
    public IIcon[] icons;
    public String[] textureNames = new String[] { "maple", "silverbell", "purpleheart", "tiger", "willow" };

    public OverworldSapling()
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
        Block blockID = world.getBlock(x, y, z);
        if (blockID == Blocks.air || blockID.getMaterial().isReplaceable())
            return true;
        //return canBlockStay(world, x, y, z);
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
            /*case 4:
            case 6:
                int belowID = world.getBlockId(x, y - 1, z);
                Block netherSoil = blocksList[belowID];
                return netherSoil != null && (netherSoil == Block.netherrack || netherSoil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this));
            case 5:
                int aboveID = world.getBlockId(x, y + 1, z);
                Block nSoil = blocksList[aboveID];
                return nSoil != null && (nSoil == Block.netherrack || nSoil == Block.slowSand || nSoil == NContent.taintedSoil);*/
        default:
            return true;
        }
    }

    @Override
    public EnumPlantType getPlantType (IBlockAccess world, int x, int y, int z)
    {
        /*int meta = world.getBlockMetadata(x, y, z) % 8;
        if (meta <= 3)*/
        return EnumPlantType.Plains;
        /*else
        	return EnumPlantType.Nether;*/
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
        if (world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextInt(120) == 0)
        {
            if ((md & 8) == 0)
                world.setBlockMetadataWithNotify(x, y, z, md | 8, 4);

            else
                func_149878_d(world, x, y, z, random);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return icons[meta % 8 % icons.length];
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

    public void func_149878_d (World world, int x, int y, int z, Random random)
    {
        int md = world.getBlockMetadata(x, y, z) % 8;
        world.setBlock(x, y, z, Blocks.air);
        WorldGenerator obj = null;

        if (md == 1)
            obj = new RareTreeGen(true, 4, 2, 1, 1);

        else if (md == 2)
            obj = new RareTreeGen(true, 9, 8, 2, 2);

        else if (md == 3)
            obj = new RareTreeGen(true, 6, 4, 3, 3);

        else if (md == 4)
            obj = new WillowGen(true);

        else
            obj = new RareTreeGen(true, 4, 2, 0, 0);

        if (!(obj.generate(world, random, x, y, z)))
            world.setBlock(x, y, z, this, md + 8, 3);
    }

    @Override
    public int damageDropped (int i)
    {
        return i % 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (Item id, CreativeTabs tab, List list)
    {
        for (int iter = 0; iter < icons.length; iter++)
        {
            list.add(new ItemStack(id, 1, iter));
        }
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
