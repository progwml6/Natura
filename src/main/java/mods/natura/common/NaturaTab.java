package mods.natura.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NaturaTab extends CreativeTabs
{
	public static NaturaTab tab = new NaturaTab("natura.plants");
	public static NaturaTab tabTrees = new NaturaTab("natura.trees");
	public static NaturaTab tabNether = new NaturaTab("natura.nether");
	private Item icon;
	private int damage;

	public NaturaTab(String name)
	{
		super(name);
	}
	
	public void setIcon(Item icon, int damage)
	{
		this.icon = icon;
		this.damage = damage;
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(icon, 0, damage);
	}

	@Override
	public Item getTabIconItem()
	{
		return icon;
	}
}
