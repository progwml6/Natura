package mods.natura.blocks.trees;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import mods.natura.client.SaguaroRenderer;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import mods.natura.worldgen.SaguaroGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class SaguaroBlock extends Block implements IPlantable
{
	public SaguaroBlock(int id)
	{
		super(id, Material.cactus);
		this.setCreativeTab(NaturaTab.tab);
		setStepSound(soundClothFootstep);
		this.setHardness(0.3f);
        this.setTickRandomly(true);
	}

	@Override
	public void updateTick (World world, int x, int y, int z, Random random)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0 && world.getWorldInfo().isRaining() && random.nextInt(20) == 0 && world.getBlockId(x, y+1, z) == 0)
		{
			switch(random.nextInt(4))
			{
			case 0:
				if (world.getBlockId(x+1, y, z) == 0)
					world.setBlock(x+1, y, z, this.blockID, 5, 3);
				break;
			case 1:
				if (world.getBlockId(x, y, z+1) == 0)
					world.setBlock(x, y, z+1, this.blockID, 6, 3);
				break;
			case 2:
				if (world.getBlockId(x-1, y, z) == 0)
					world.setBlock(x-1, y, z, this.blockID, 3, 3);
				break;
			case 3:
				if (world.getBlockId(x, y, z-1) == 0)
					world.setBlock(x, y, z-1, this.blockID, 4, 3);
				break;
			}
		}
		else if (meta == 2 && random.nextInt(200) == 0)
		{
			SaguaroGen gen = new SaguaroGen(NContent.saguaro.blockID, 0, true);
			gen.generate(world, random, x, y, z);
		}
		else if (meta == 1 && random.nextInt(200) == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 3);
		}
		
		//Fruit shouldn't do a thing
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool (World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0)
		{
			float offset = 0.125F;
			return AxisAlignedBB.getBoundingBox((float) x + offset, y, (float) z + offset, (float) (x + 1) - offset, (float) (y + 1) - offset, (float) (z + 1) - offset);
		}
		else if (meta == 1 || meta == 2)
		{
			float offset = 0.325F;
			return AxisAlignedBB.getBoundingBox((float) x + offset, y, (float) z + offset, (float) (x + 1) - offset, (float) (y + 1) - offset, (float) (z + 1) - offset);
		}
		else if (meta == 3)
		{
			float offset = 0.25F;
			return AxisAlignedBB.getBoundingBox((float) x + 0.625f, y+0.1875, (float) z + offset, x + 1.125f, y + 0.75, (float) (z + 1) - offset);
		}
		else if (meta == 4)
		{
			float offset = 0.25F;
			return AxisAlignedBB.getBoundingBox((float) x + offset, y+0.1875, (float) z + 0.625f, (float) (x + 1) - offset, y + 0.75, (float) z + 1.125f);
		}
		else if (meta == 5)
		{
			float offset = 0.25F;
			return AxisAlignedBB.getBoundingBox((float) x - 0.125f, y+0.1875, (float) z + offset, (float) x + 0.375f, y + 0.75, (float) (z + 1) - offset);
		}
		else if (meta == 6)
		{
			float offset = 0.25F;
			return AxisAlignedBB.getBoundingBox((float) x + offset, y+0.1875, (float) z - 0.125f, (float) (x + 1) - offset, y + 0.75, (float) z + 0.375f);
		}
		return null;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool (World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0)
		{
			float offset = 0.125F;
			float height = 0.125F;
			float base = 0F;
			if (world.getBlockId(x, y + 1, z) == this.blockID)
				height = 0F;

			Block block = Block.blocksList[world.getBlockId(x, y - 1, z)];
			if (block != null && !block.isOpaqueCube())
				base = 0.125F;

			return AxisAlignedBB.getBoundingBox((float) x + offset, y, (float) z + offset, (float) (x + 1) - offset, (float) (y + 1) - height, (float) (z + 1) - offset);
		}
		else if (meta == 1 || meta == 2)
		{
			float offset = 0.325F;
			return AxisAlignedBB.getBoundingBox((float) x + offset, y, (float) z + offset, (float) (x + 1) - offset, y + 0.5, (float) (z + 1) - offset);
		}
		else if (meta == 3)
		{
			float offset = 0.25F;
			return AxisAlignedBB.getBoundingBox((float) x + 0.625f, y+0.1875, (float) z + offset, x + 1.125f, y + 0.75, (float) (z + 1) - offset);
		}
		else if (meta == 4)
		{
			float offset = 0.25F;
			return AxisAlignedBB.getBoundingBox((float) x + offset, y+0.1875, (float) z + 0.625f, (float) (x + 1) - offset, y + 0.75, (float) z + 1.125f);
		}
		else if (meta == 5)
		{
			float offset = 0.25F;
			return AxisAlignedBB.getBoundingBox((float) x - 0.125f, y+0.1875, (float) z + offset, (float) x + 0.375f, y + 0.75, (float) (z + 1) - offset);
		}
		else if (meta == 6)
		{
			float offset = 0.25F;
			return AxisAlignedBB.getBoundingBox((float) x + offset, y+0.1875, (float) z - 0.125f, (float) (x + 1) - offset, y + 0.75, (float) z + 0.375f);
		}
		return null;
	}

	public int getRenderType ()
	{
		return SaguaroRenderer.model;
	}

	public static int func_72219_c (int par0)
	{
		return (par0 & 12) >> 2;
	}
	
	public static int getRotation(int meta)
    {
        return meta - 3;
    }
	
	public int idDropped (int meta, Random random, int fortune)
	{
		if (meta == 0)
			return this.blockID;
		else
			return NContent.seedFood.itemID;
	}

	public Icon[] icons;
	public String[] textureNames = new String[] { "saguaro_bottom", "saguaro_top", "saguaro_side", "saguaro_fruit" };

	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons (IconRegister iconRegister)
	{
		this.icons = new Icon[textureNames.length];

		for (int i = 0; i < this.icons.length; ++i)
		{
			this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i]);
		}
	}

    @Override
    @SideOnly(Side.CLIENT)
	public Icon getIcon (int side, int meta)
	{
		if (meta == 0)
		{
			if (side < 2)
				return icons[side];
			else
				return icons[2];
		}
		else if (meta == 1 || meta == 2)
		{
			return icons[1];
		}
		else
		{
			return icons[3];
		}
	}

	public boolean renderAsNormalBlock ()
	{
		return false;
	}

	public boolean isOpaqueCube ()
	{
		return false;
	}

	public boolean canPlaceBlockAt (World world, int i, int j, int k)
	{
		if (!super.canPlaceBlockAt(world, i, j, k))
		{
			return false;
		}
		else
		{
			return canBlockStay(world, i, j, k);
		}
	}

	public void onNeighborBlockChange (World world, int i, int j, int k, int l)
	{
		if (!canBlockStay(world, i, j, k))
		{
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	public boolean canBlockStay (World world, int x, int y, int z)
	{
		int blockID = world.getBlockId(x, y - 1, z);
		return blockID == this.blockID || blockID == Block.sand.blockID || blockID == 0;

	}

	public void onEntityCollidedWithBlock (World world, int i, int j, int k, Entity entity)
	{
		if (!(entity instanceof EntityItem))
			entity.attackEntityFrom(DamageSource.cactus, 1);
	}

	public boolean canConnectSuguaroTo (IBlockAccess world, int x, int y, int z)
	{
		if (world.getBlockId(x, y, z) == blockID && world.getBlockMetadata(x, y, z) == 0)
			return true;

		return false;
	}

	@Override
	public EnumPlantType getPlantType (World world, int x, int y, int z)
	{
		return EnumPlantType.Desert;
	}

	@Override
	public int getPlantID (World world, int x, int y, int z)
	{
		return this.blockID;
	}

	@Override
	public int getPlantMetadata (World world, int x, int y, int z)
	{
		return 1;
	}

	/*public void getSubBlocks (int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for (int i = 0; i < 7; i++)
			list.add(new ItemStack(par1, 1, i));
	}*/
}
