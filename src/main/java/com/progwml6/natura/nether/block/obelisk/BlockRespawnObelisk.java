package com.progwml6.natura.nether.block.obelisk;

import java.util.Locale;
import java.util.Random;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.block.EnumBlock;

public class BlockRespawnObelisk extends EnumBlock<BlockRespawnObelisk.ObeliskType>
{
    public static PropertyEnum<ObeliskType> TYPE = PropertyEnum.create("type", ObeliskType.class);

    public BlockRespawnObelisk()
    {
        super(Material.WOOD, TYPE, ObeliskType.class);

        this.setCreativeTab(NaturaRegistry.tabWorld);
        this.setHardness(2.0f);
        this.setResistance(1000000F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (ObeliskType type : ObeliskType.values())
        {
            if (type == ObeliskType.ACTIVE)
            {
                continue;
            }
            list.add(new ItemStack(this, 1, type.getMeta()));
        }
    }

    @Override
    public BlockPos getBedSpawnPosition(IBlockState state, IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return pos.up();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        Random rand = new Random();
        rand.setSeed(2 ^ 16 + 2 ^ 8 + (4 * 3 * 271));

        if (playerIn.isSneaking())
        {
            return false;
        }

        playerIn.setSpawnChunk(pos, false, worldIn.provider.getDimension());
        playerIn.setSpawnDimension(worldIn.provider.getDimension());

        if (!worldIn.isRemote)
        {
            playerIn.sendMessage(playerIn.getDisplayName().appendText(": Spawn point set!"));
        }

        worldIn.setBlockState(pos, this.getDefaultState().withProperty(TYPE, ObeliskType.ACTIVE), 3);

        worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);

        return true;
    }

    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, Entity player)
    {
        return state.getValue(TYPE) == ObeliskType.ACTIVE;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if (state.getValue(TYPE) == ObeliskType.ACTIVE)
        {
            return 7;
        }

        return super.getLightValue(state, world, pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (stateIn.getValue(TYPE) == ObeliskType.ACTIVE)
        {
            for (int i = 0; i < 2; i++)
            {
                double x = pos.getX() + rand.nextFloat() * 3 - 1;
                double y = pos.getY() + rand.nextFloat() * 2;
                double z = pos.getZ() + rand.nextFloat() * 3 - 1;

                worldIn.spawnParticle(EnumParticleTypes.PORTAL, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
                worldIn.spawnParticle(EnumParticleTypes.SPELL_WITCH, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    public enum ObeliskType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        INACTIVE, ACTIVE;

        public final int meta;

        ObeliskType()
        {
            this.meta = this.ordinal();
        }

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta()
        {
            return this.meta;
        }
    }
}
