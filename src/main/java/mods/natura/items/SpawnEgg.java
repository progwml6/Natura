package mods.natura.items;

import java.util.List;

import mods.natura.entity.BabyHeatscarSpider;
import mods.natura.entity.HeatscarSpider;
import mods.natura.entity.ImpEntity;
import mods.natura.entity.NitroCreeper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpawnEgg extends Item
{
	int[] primaryColor = { 0xF29735, 0xE64D10, 0xF73E6C, 0xE64D10 };

	int[] secondaryColor = { 0x2E1F10, 0x57B1BD, 0x9B5004, 0x57B1BD };

	String[] mobNames = { "Natura.Imp", "Natura.FlameSpider", "Natura.NitroCreeper", "Natura.FlameSpiderBaby" };

	public SpawnEgg()
	{
		super();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String s1 = mobNames[par1ItemStack.getItemDamage()];

		if (s1 != null)
		{
			s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
		}

		return s;
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list)
	{
		for (int i = 0; i < mobNames.length; i++)
			list.add(new ItemStack(id, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int damage = stack.getItemDamage();
		return pass == 0 ? primaryColor[damage] : secondaryColor[damage];
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			activateSpawnEgg(stack, worldIn, pos, side);
			if (!playerIn.capabilities.isCreativeMode)
			{
				--stack.stackSize;
			}
		}
		return true;
	}

	public static EntityLiving activateSpawnEgg(ItemStack stack, World world, BlockPos pos, EnumFacing side)//double posX, double posY, double posZ, int par7)
	{
		Block i1 = world.getBlockState(pos).getBlock();//((int) posX, (int) posY, (int) posZ);
		pos = pos.offset(side);
		double d0 = 0.0D;

		if (side == EnumFacing.UP && i1 != null && i1.getRenderType() == 11)
		{
			d0 = 0.5D;
		}

		int damage = stack.getItemDamage();
		EntityLiving entity = null;
		switch (damage)
		{
		case 0:
			entity = new ImpEntity(world);
			spawnEntity(pos, entity, world);
			break;
		case 1:
			entity = new HeatscarSpider(world);
			spawnEntity(pos, entity, world);
			break;
		case 2:
			entity = new NitroCreeper(world);
			spawnEntity(pos, entity, world);
			break;
		case 3:
			entity = new BabyHeatscarSpider(world);
			spawnEntity(pos, entity, world);
			break;
		}
		return entity;
	}

	public static void spawnEntity(BlockPos pos, EntityLiving entity, World world)
	{
		if (!world.isRemote)
		{
			entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
			world.spawnEntityInWorld(entity);
		}
	}
}
