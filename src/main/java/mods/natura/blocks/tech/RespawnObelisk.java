package mods.natura.blocks.tech;

import java.util.Random;

import mods.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RespawnObelisk extends Block
{
	public RespawnObelisk(Material material)
	{
		super(material);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (playerIn.isSneaking())
			return false;
		playerIn.setSpawnChunk(pos, false, worldIn.provider.getDimensionId());

		if (!worldIn.isRemote)
			playerIn.addChatMessage(playerIn.getDisplayName().appendText("Spawn point set!"));
		worldIn.setBlockMetadataWithNotify(x, y, z, 1, 3);
		worldIn.playSound(x + 0.5D, y + 0.5D, z + 0.5D, "portal.portal", 1.0F, Natura.random.nextFloat() * 0.4F + 0.8F, false);

		return true;
	}

	@Override
	public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player)
	{
		return world.getBlockMetadata(x, y, z) != 0;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta > 0)
			return 7;
		return this.getLightValue();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta > 0)
		{
			for (int i = 0; i < 2; i++)
			{
				world.spawnParticle("portal", x + random.nextFloat() * 3 - 1, y + random.nextFloat() * 2, z + random.nextFloat() * 3 - 1, 0, 0, 0);
				world.spawnParticle("witchMagic", x + random.nextFloat() * 3 - 1, y + random.nextFloat() * 2, z + random.nextFloat() * 3 - 1, 0, 0, 0);
			}
		}
	}
}
