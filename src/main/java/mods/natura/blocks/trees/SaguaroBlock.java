package mods.natura.blocks.trees;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import mods.natura.worldgen.SaguaroGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SaguaroBlock extends Block implements IPlantable
{
    public static PropertyInteger METADATA = PropertyInteger.create("Metadata", 0, 15);

    public SaguaroBlock()
    {
        super(Material.cactus);
        this.setCreativeTab(NaturaTab.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
        setStepSound(soundTypeCloth);
        this.setHardness(0.3f);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick (World world, BlockPos pos, IBlockState state, Random random)
    {
        int meta = ((Integer) state.getValue(METADATA)).intValue();
        if (meta == 0 && world.getWorldInfo().isRaining() && random.nextInt(20) == 0 && world.getBlockState(pos.up()).getBlock() == Blocks.air)
        {
            switch (random.nextInt(4))
            {
            case 0:
                if (world.isAirBlock(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ())))
                    world.setBlockState(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()), this.getStateFromMeta(5), 3);
                break;
            case 1:
                if (world.isAirBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1)))
                    world.setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1), this.getStateFromMeta(6), 3);
                break;
            case 2:
                if (world.isAirBlock(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ())))
                    world.setBlockState(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()), this.getStateFromMeta(3), 3);
                break;
            case 3:
                if (world.isAirBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1)))
                    world.setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1), this.getStateFromMeta(4), 3);
                break;
            }
        }
        else if (meta == 2 && random.nextInt(200) == 0)
        {
            SaguaroGen gen = new SaguaroGen(NContent.saguaro, 0, true);
            gen.generate(world, random, pos);
        }
        else if (meta == 1 && random.nextInt(200) == 0)
        {
            world.setBlockState(pos, state.withProperty(METADATA, Integer.valueOf(2)), 3);
        }

        //Fruit shouldn't do a thing
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox (World worldIn, BlockPos pos, IBlockState state)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int meta = this.getMetaFromState(state);
        if (meta == 0)
        {
            float offset = 0.125F;
            return new AxisAlignedBB(x + offset, y, z + offset, x + 1 - offset, y + 1 - offset, z + 1 - offset);
        }
        else if (meta == 1 || meta == 2)
        {
            float offset = 0.325F;
            return new AxisAlignedBB(x + offset, y, z + offset, x + 1 - offset, y + 1 - offset, z + 1 - offset);
        }
        else if (meta == 3)
        {
            float offset = 0.25F;
            return new AxisAlignedBB(x + 0.625f, y + 0.1875, z + offset, x + 1.125f, y + 0.75, z + 1 - offset);
        }
        else if (meta == 4)
        {
            float offset = 0.25F;
            return new AxisAlignedBB(x + offset, y + 0.1875, z + 0.625f, x + 1 - offset, y + 0.75, z + 1.125f);
        }
        else if (meta == 5)
        {
            float offset = 0.25F;
            return new AxisAlignedBB(x - 0.125f, y + 0.1875, z + offset, x + 0.375f, y + 0.75, z + 1 - offset);
        }
        else if (meta == 6)
        {
            float offset = 0.25F;
            return new AxisAlignedBB(x + offset, y + 0.1875, z - 0.125f, x + 1 - offset, y + 0.75, z + 0.375f);
        }
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox (World worldIn, BlockPos pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int meta = this.getMetaFromState(worldIn.getBlockState(pos));
        if (meta == 0)
        {
            float offset = 0.125F;
            float height = 0.125F;
            float base = 0F;
            if (worldIn.getBlockState(pos.up()).getBlock() == this)
                height = 0F;

            Block block = worldIn.getBlockState(pos.down()).getBlock();
            if (block != null && !block.isOpaqueCube())
                base = 0.125F;

            return new AxisAlignedBB(x + offset, y, z + offset, x + 1 - offset, y + 1 - height, z + 1 - offset);
        }
        else if (meta == 1 || meta == 2)
        {
            float offset = 0.325F;
            return new AxisAlignedBB(x + offset, y, z + offset, x + 1 - offset, y + 0.5, z + 1 - offset);
        }
        else if (meta == 3)
        {
            float offset = 0.25F;
            return new AxisAlignedBB(x + 0.625f, y + 0.1875, z + offset, x + 1.125f, y + 0.75, z + 1 - offset);
        }
        else if (meta == 4)
        {
            float offset = 0.25F;
            return new AxisAlignedBB(x + offset, y + 0.1875, z + 0.625f, x + 1 - offset, y + 0.75, z + 1.125f);
        }
        else if (meta == 5)
        {
            float offset = 0.25F;
            return new AxisAlignedBB(x - 0.125f, y + 0.1875, z + offset, x + 0.375f, y + 0.75, z + 1 - offset);
        }
        else if (meta == 6)
        {
            float offset = 0.25F;
            return new AxisAlignedBB(x + offset, y + 0.1875, z - 0.125f, x + 1 - offset, y + 0.75, z + 0.375f);
        }
        return null;
    }

    public static int func_72219_c (int par0)
    {
        return (par0 & 12) >> 2;
    }

    public static int getRotation (int meta)
    {
        return meta - 3;
    }

    @Override
    public Item getItemDropped (IBlockState state, Random random, int fortune)
    {
        if (this.getMetaFromState(state) == 0)
            return Item.getItemFromBlock(this);
        else
            return NContent.seedFood;
    }

    @Override
    public boolean isFullCube ()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube ()
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt (World world, BlockPos pos)
    {
        return super.canPlaceBlockAt(world, pos) && canBlockStay(world, pos);
    }

    @Override
    public void onNeighborBlockChange (World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!canBlockStay(worldIn, pos))
        {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    public boolean canBlockStay (World world, BlockPos pos)
    {
        Block blockID = world.getBlockState(pos.down()).getBlock();
        return blockID == this || blockID == Blocks.sand || blockID == null;

    }

    @Override
    public void onEntityCollidedWithBlock (World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!(entityIn instanceof EntityItem))
            entityIn.attackEntityFrom(DamageSource.cactus, 1);
    }

    public boolean canConnectSuguaroTo (IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == this && this.getMetaFromState(world.getBlockState(pos)) == 0;

    }

    @Override
    public EnumPlantType getPlantType (IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Desert;
    }

    @Override
    public IBlockState getPlant (net.minecraft.world.IBlockAccess world, BlockPos pos)
    {
        return getDefaultState();
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta (int meta)
    {
        return this.getDefaultState().withProperty(METADATA, Integer.valueOf(meta));
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer ()
    {
        return EnumWorldBlockLayer.CUTOUT;
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

    /*public void getSubBlocks (int par1, CreativeTabs par2CreativeTabs, List list)
    {
    	for (int i = 0; i < 7; i++)
    		list.add(new ItemStack(par1, 1, i));
    }*/
}
