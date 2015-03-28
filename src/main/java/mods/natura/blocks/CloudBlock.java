package mods.natura.blocks;

import java.util.List;

import mods.natura.Natura;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CloudBlock extends Block
{
	public enum CloudVariant implements IStringSerializable
	{
		WHITE(0, "cloud_white"),
		GREY(1, "cloud_gray"),
		DARK(2, "cloud_dark"),
		SULFER(3, "cloud_sulfur");

		private static final CloudVariant[] metaLookup = new CloudVariant[CloudVariant.values().length];

		static
		{
			for (CloudVariant type : CloudVariant.values())
			{
				metaLookup[type.getMetadata()] = type;
			}
		}

		private int metadata;

		private String name;

		CloudVariant(int metadata, String name)
		{
			this.metadata = metadata;
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		public int getMetadata()
		{
			return this.metadata;
		}

		public static CloudVariant getTypeFromMetadata(int meta)
		{
			return CloudVariant.metaLookup[meta];
		}
	}

	public static final PropertyEnum CLOUD_TYPE = PropertyEnum.create("variant", CloudVariant.class);

	public CloudBlock()
	{
		super(Natura.cloud);
		this.setStepSound(soundTypeCloth);
		this.setHardness(0.3F);
		this.setUnlocalizedName("cloud");

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(CLOUD_TYPE, CloudVariant.WHITE));
		this.setCreativeTab(NaturaTab.tab);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (CloudVariant type : CloudVariant.values())
		{
			list.add(new ItemStack(itemIn, 1, type.getMetadata()));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn)
	{
		int meta = this.getMetaFromState(worldIn.getBlockState(pos));
		if (meta == 3 && entityIn instanceof EntityArrow && !worldIn.isRemote)
		{
			EntityArrow entityarrow = (EntityArrow) entityIn;

			if (entityarrow.isBurning())
			{
				this.explode(worldIn, pos, 1, entityarrow.shootingEntity instanceof EntityLiving ? (EntityLiving) entityarrow.shootingEntity : null);
				worldIn.setBlockToAir(pos);
				return;
			}
		}

		if (entityIn.motionY < 0.0D)
		{
			entityIn.motionY *= 0.005D;
		}
		entityIn.fallDistance = 0.0F;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		int meta = this.getMetaFromState(state);
		if (meta == 3 && playerIn.getCurrentEquippedItem() != null && playerIn.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
		{
			worldIn.setBlockToAir(pos);
			this.explode(worldIn, pos, 1, playerIn);
			return true;
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitX);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		/*int meta = world.getBlockMetadata(x, y, z);
		if (meta == 3)
		{
		    this.explode(world, x, y, z, 1, null);
		}*/
	}

	public void explode(World world, BlockPos pos, int size, EntityLivingBase living)
	{
		world.createExplosion(living, pos.getX(), pos.getY(), pos.getZ(), size, true);
	}

	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		Block block = worldIn.getBlockState(pos).getBlock();
		return block != NContent.cloud && super.shouldSideBeRendered(worldIn, pos, side);
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		Block block = worldIn.getBlockState(pos).getBlock();
		return block != NContent.cloud && super.isBlockSolid(worldIn, pos, side);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		if (worldIn.getBlockState(pos.down()).getBlock() == NContent.cloud)
		{
			return null;
		}
		else
		{
			return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 0.0625D, pos.getZ() + 1.0D);
		}
	}

	/* Explosions! */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(CLOUD_TYPE, CloudVariant.getTypeFromMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((CloudVariant) state.getValue(CLOUD_TYPE)).getMetadata();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { CLOUD_TYPE });
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((CloudVariant) state.getValue(CLOUD_TYPE)).getMetadata();
	}
}
