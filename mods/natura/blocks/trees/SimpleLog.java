package mods.natura.blocks.trees;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SimpleLog extends Planks
{
	public String[] textureNames = new String[] { "redwood_bark", "redwood_heart", "redwood_root" };
	public SimpleLog(int id) 
	{
		super(id);
		this.setCreativeTab(NaturaTab.tab);
	}
	
	public void registerIcons(IconRegister iconRegister)
    {
		this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:"+textureNames[i]);
        }
    }

	@Override
    public boolean canSustainLeaves(World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean isWood(World world, int x, int y, int z)
    {
        return true;
    }
    
    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return blockFlammability[blockID];
    }

    public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return blockFireSpreadSpeed[blockID];
    }
    
    @Override
	public Icon getIcon(int side, int meta)
    {		
		return icons[meta];
    }
    
    @SideOnly(Side.CLIENT)
	@Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for (int i = 0; i < 3; i++)
        par3List.add(new ItemStack(par1, 1, i));
    }
}
