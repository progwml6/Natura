package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.common.NaturaTab;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class NSaplingBlock extends BlockSapling
{
    public IIcon[] icons;
    public EnumSaplingType[] saplingTypes;
    private static final int growFlag = 8;

    public NSaplingBlock(EnumSaplingType[] saplingTypes)
    {
        super();
        this.saplingTypes = saplingTypes;
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
        this.setCreativeTab(NaturaTab.woodTab);
    }

    public EnumSaplingType getSaplingType(int meta) {
        int saplingOrdinal = meta & ~growFlag;
        if (saplingOrdinal >= saplingTypes.length)
            throw new RuntimeException("Unknown sapling type. Meta: " + meta);
        return saplingTypes[saplingOrdinal];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[saplingTypes.length];

        for (int i = 0; i < saplingTypes.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + saplingTypes[i] + "_sapling");
        }
    }

    @Override
    public boolean canBlockStay (World world, int x, int y, int z)
    {
        Block soil = world.getBlock(x, y - 1, z);
        if (soil == null)
            return false;
        boolean hasLight = world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z);
        return hasLight && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
    }

    @Override
    public EnumPlantType getPlantType (IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Plains;
    }

    @Override
    public void updateTick (World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
            this.checkAndDropBlock(world, x, y, z);

            int meta = world.getBlockMetadata(x, y, z);
            EnumSaplingType saplingType = getSaplingType(meta);
            if (random.nextInt(saplingType.growthProbability) == 0 && hasEnoughLightToGrow(saplingType, world, x, y, z))
                func_149879_c(world, x, y, z, random);
        }
    }

    protected boolean hasEnoughLightToGrow (EnumSaplingType saplingType, World world, int x, int y, int z)
    {
        return world.getBlockLightValue(x, y + 1, z) >= 9;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        meta &= ~growFlag;
        if (meta >= saplingTypes.length)
            return null;

        return icons[meta];
    }

    public abstract WorldGenerator getWorldGenerator(EnumSaplingType saplingType);

    @Override
    public void func_149878_d(World world, int x, int y, int z, Random random)
    {
        int meta = world.getBlockMetadata(x, y, z);
        EnumSaplingType saplingType = getSaplingType(meta);

        world.setBlock(x, y, z, Blocks.air);
        WorldGenerator obj = getWorldGenerator(saplingType);

        if (!(obj.generate(world, random, x, y, z)))
            world.setBlock(x, y, z, this, meta | growFlag, 3);
    }

    @Override
    public int damageDropped (int i)
    {
        return i & ~growFlag;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item item, CreativeTabs creativeTabs, List list)
    {
        for (int i = 0; i < saplingTypes.length; i++)
            list.add(new ItemStack(item, 1, i));
    }

}
