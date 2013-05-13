package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import mods.natura.common.PHNatura;
import mods.natura.worldgen.BloodTreeLargeGen;
import mods.natura.worldgen.BushTreeGen;
import mods.natura.worldgen.DarkwoodGen;
import mods.natura.worldgen.EucalyptusTreeGenShort;
import mods.natura.worldgen.RareTreeGen;
import mods.natura.worldgen.RedwoodTreeGen;
import mods.natura.worldgen.SakuraTreeGen;
import mods.natura.worldgen.WhiteTreeGen;
import mods.natura.worldgen.WillowGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OverworldSapling extends BlockSapling
{
    public Icon[] icons;
    public String[] textureNames = new String[] { "maple", "silverbell", "purpleheart", "tiger", "willow" };

    public OverworldSapling(int id)
    {
        super(id);
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setHardness(0.0F);
        this.setStepSound(Block.soundGrassFootstep);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_sapling");
        }
    }

    public boolean canPlaceBlockAt (World world, int x, int y, int z)
    {
        int blockID = world.getBlockId(x, y, z);
        if (blockID == 0 || blocksList[blockID].blockMaterial.isReplaceable())
            return true;
        //return canBlockStay(world, x, y, z);
        return false;
    }

    public boolean canThisPlantGrowOnThisBlockID (int id)
    {
        return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.slowSand.blockID || id == Block.netherrack.blockID || id == NContent.taintedSoil.blockID;
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
            int blockID = world.getBlockId(x, y - 1, z);
            Block soil = blocksList[blockID];
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
    public EnumPlantType getPlantType (World world, int x, int y, int z)
    {
        /*int meta = world.getBlockMetadata(x, y, z) % 8;
        if (meta <= 3)*/
        return EnumPlantType.Plains;
        /*else
        	return EnumPlantType.Nether;*/
    }

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
                growTree(world, x, y, z, random);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        return icons[meta % 8];
    }

    @Override
    public void markOrGrowMarked (World world, int x, int y, int z, Random random)
    {
        boneFertilize(world, x, y, z, random);
    }

    public boolean boneFertilize (World world, int x, int y, int z, Random random)
    {
        int meta = world.getBlockMetadata(x, y, z);

        if ((meta & 8) == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, meta | 8, 4);
        }
        else
        {
            this.growTree(world, x, y, z, random);
        }

        return true;
    }

    public void growTree (World world, int x, int y, int z, Random random)
    {
        int md = world.getBlockMetadata(x, y, z) % 8;
        world.setBlock(x, y, z, 0);
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
            world.setBlock(x, y, z, blockID, md + 8, 3);
    }

    public int damageDropped (int i)
    {
        return i % 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (int id, CreativeTabs tab, List list)
    {
        for (int iter = 0; iter < icons.length; iter++)
        {
            list.add(new ItemStack(id, 1, iter));
        }
    }
}
