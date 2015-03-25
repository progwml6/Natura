package mods.natura.items;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BerryMedley extends ItemFood
{

	public String[] textureNames = new String[] { "medley" };

	public BerryMedley(int heal)
	{
		super(heal, 1.4F, false);
		setHasSubtypes(true);
		setMaxDamage(0);
		this.setCreativeTab(NaturaTab.tab);
		this.setAlwaysEdible();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
	{
		if (player.canEat(true) && player.getFoodStats().getSaturationLevel() < 18F)
		{
			player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}

		return par1ItemStack;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		ItemStack stack = super.onItemUseFinish(par1ItemStack, par2World, par3EntityPlayer);

		if (!par3EntityPlayer.capabilities.isCreativeMode)
		{
			if (par1ItemStack.stackSize <= 0)
			{
				return new ItemStack(Items.bowl);
			}

			par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.bowl));
		}

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return 32;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add(StatCollector.translateToLocal("tooltip.berrymedley"));
	}

	/* Name override */
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return (new StringBuilder()).append("item.berry.").append(textureNames[itemstack.getItemDamage()]).toString();
	}
}
