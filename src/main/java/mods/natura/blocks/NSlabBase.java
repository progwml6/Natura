package mods.natura.blocks;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NSlabBase extends Block
{
    public static PropertyInteger METADATA = PropertyInteger.create("Metadata", 0, 15);
    Block modelBlock;
    int startingMeta;
    int totalSize;

    public NSlabBase(Material material)
    {
        super(material);
        this.setCreativeTab(NaturaTab.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
    }

    public NSlabBase(Material material, Block model, int meta, int totalSize)
    {
        super(material);
        this.setCreativeTab(NaturaTab.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
        this.modelBlock = model;
        this.startingMeta = meta;
        this.totalSize = totalSize;
    }

    @Override
    public void addCollisionBoxesToList (World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
    {
        setBlockBoundsBasedOnState(worldIn, pos);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    }

    @Override
    public void setBlockBoundsForItemRender ()
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    public void setBlockBoundsBasedOnState (IBlockAccess world, BlockPos pos)
    {
        int meta = this.getMetaFromState(world.getBlockState(pos)) / 8;
        float minY = meta == 1 ? 0.5F : 0.0F;
        float maxY = meta == 1 ? 1.0F : 0.5F;
        setBlockBounds(0.0F, minY, 0F, 1.0F, maxY, 1.0F);
    }

    @Override
    public IBlockState onBlockPlaced (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (facing == EnumFacing.UP)
            return this.getStateFromMeta(meta);
        if (facing == EnumFacing.DOWN || hitY >= 0.5F)
            return this.getStateFromMeta(meta | 8);

        return this.getStateFromMeta(meta);
    }

    @Override
    public boolean isOpaqueCube ()
    {
        return false;
    }

    @Override
    public boolean isFullCube ()
    {
        return false;
    }

    @Override
    public void getSubBlocks (Item id, CreativeTabs tab, List list)
    {
        for (int iter = 0; iter < totalSize; iter++)
        {
            list.add(new ItemStack(id, 1, iter));
        }
    }

    @Override
    public int damageDropped (IBlockState meta)
    {
        return this.getMetaFromState(meta) % 8;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta (int meta)
    {
        return this.getDefaultState().withProperty(METADATA, Integer.valueOf(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState (IBlockState state)
    {
        return ((Integer) state.getValue(METADATA)).intValue();
    }

    protected BlockState createBlockState ()
    {
        return new BlockState(this, new IProperty[] { METADATA });
    }
}
