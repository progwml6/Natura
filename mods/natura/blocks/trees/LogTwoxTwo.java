package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/*
 * This class is for a single tree with a 2x2 base and inside textures 
 */

public class LogTwoxTwo extends BlockLog
{
	public Icon[] icons;
	public String[] textureNames = new String[] {"bark", "heart_small", "upper_left", "upper_right", "side_left", "side_right", "lower_left", "lower_right" };
	public LogTwoxTwo(int id, float hardness)
	{
		super(id);
		this.setHardness(hardness);
        this.setStepSound(Block.soundMetalFootstep);
		this.setCreativeTab(NaturaTab.tab);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons (IconRegister iconRegister)
	{
		this.icons = new Icon[textureNames.length];

		for (int i = 0; i < this.icons.length; i++)
		{
			this.icons[i] = iconRegister.registerIcon("natura:bloodwood_" + textureNames[i]);
		}
	}
	
	public int idDropped(int par1, Random par2Random, int par3)
    {
        return NContent.bloodwood.blockID;
    }

	public int damageDropped (int meta)
	{
		if (meta < 12)
			return 0;
		else if (meta == 15)
			return 15;
		return 12;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public Icon getIcon (int side, int meta)
	{
		if (meta == 15) //Fullbark
			return icons[0];

		//TODO: Rotating single log for 12, 13, 14

		// Upward facing
		if (meta == 0)
		{
			switch (side)
			{
			case 0:
				return icons[2]; //Bottom
			case 1:
				return icons[2]; //Top
			case 2:
				return icons[0];
			case 3:
				return icons[4];
			case 4:
				return icons[0];
			case 5:
				return icons[5];
			}
		}

		if (meta == 1)
		{
			switch (side)
			{
			case 0:
				return icons[3]; //Bottom
			case 1:
				return icons[3]; //Top
			case 2:
				return icons[0];
			case 3:
				return icons[5];
			case 4:
				return icons[4];
			case 5:
				return icons[0];
			}
		}

		if (meta == 2)
		{
			switch (side)
			{
			case 0:
				return icons[6]; //Bottom
			case 1:
				return icons[6]; //Top
			case 2:
				return icons[5];
			case 3:
				return icons[0];
			case 4:
				return icons[0];
			case 5:
				return icons[4];
			}
		}

		if (meta == 3)
		{
			switch (side)
			{
			case 0:
				return icons[7]; //Bottom
			case 1:
				return icons[7]; //Top
			case 2:
				return icons[5];
			case 3:
				return icons[0];
			case 4:
				return icons[4];
			case 5:
				return icons[0];
			}
		}

		// East/West
		if (meta == 4)
		{
			switch (side)
			{
			case 0:
				return icons[4];
			case 1:
				return icons[0];
			case 2:
				return icons[5];
			case 3:
				return icons[0];
			case 4:
				return icons[3];
			case 5:
				return icons[2];
			}
		}

		if (meta == 5)
		{
			switch (side)
			{
			case 0:
				return icons[5];
			case 1:
				return icons[0];
			case 2:
				return icons[0];
			case 3:
				return icons[4];
			case 4:
				return icons[2];
			case 5:
				return icons[3];
			}
		}

		if (meta == 6)
		{
			switch (side)
			{
			case 0:
				return icons[0]; //Bottom
			case 1:
				return icons[5]; //Top
			case 2:
				return icons[4];
			case 3:
				return icons[0];
			case 4:
				return icons[7];
			case 5:
				return icons[6];
			}
		}

		if (meta == 7)
		{
			switch (side)
			{
			case 0:
				return icons[0]; //Bottom
			case 1:
				return icons[4]; //Top
			case 2:
				return icons[0];
			case 3:
				return icons[5];
			case 4:
				return icons[6];
			case 5:
				return icons[7];
			}
		}

		//North/south
		if (meta == 8)
		{
			switch (side)
			{
			case 0:
				return icons[4];
			case 1:
				return icons[0];
			case 2:
				return icons[3];
			case 3:
				return icons[2];
			case 4:
				return icons[0];
			case 5:
				return icons[5];
			}
		}

		if (meta == 9)
		{
			switch (side)
			{
			case 0:
				return icons[5];
			case 1:
				return icons[0];
			case 2:
				return icons[2];
			case 3:
				return icons[3];
			case 4:
				return icons[4];
			case 5:
				return icons[0];
			}
		}

		if (meta == 10)
		{
			switch (side)
			{
			case 0:
				return icons[0]; //Bottom
			case 1:
				return icons[4]; //Top
			case 2:
				return icons[7];
			case 3:
				return icons[6];
			case 4:
				return icons[0];
			case 5:
				return icons[4];
			}
		}

		if (meta == 11)
		{
			switch (side)
			{
			case 0:
				return icons[0]; //Bottom
			case 1:
				return icons[5]; //Top
			case 2:
				return icons[6];
			case 3:
				return icons[7];
			case 4:
				return icons[5];
			case 5:
				return icons[0];
			}
		}

		return icons[0];
	}

	/* Combined stair and log placement */
	@Override
	public int onBlockPlaced (World par1World, int blockX, int blockY, int blockZ, int side, float clickX, float clickY, float clickZ, int metadata)
	{
	    if (metadata >= 12)
	        return metadata;

		int meta = metadata & 3;
		byte add = 0;

		switch (side)
		{
		case 0:
		case 1:
			add = 0;
			if (clickX > 0.5f)
				add += 1;
			if (clickZ > 0.5f)
				add += 2;
			break;
		case 2:
		case 3:
			add = 8;
			if (clickX > 0.5f)
				add += 1;
			if (clickY < 0.5f)
				add += 2;
			break;
		case 4:
		case 5:
			add = 4;
			if (clickZ < 0.5f)
				add += 1;
			if (clickY < 0.5f)
				add += 2;
		}

		return meta | add;
	}

	@SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        //par3List.add(new ItemStack(par1, 1, 12));
        par3List.add(new ItemStack(par1, 1, 15));
    }
}
