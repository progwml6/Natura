package mods.natura.blocks.trees;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Planks extends Block
{
	public Icon[] icons;
	public String[] textureNames = new String[] { "redwood", "eucalyptus", "hopseed", "sakura", "ghostwood", "bloodwood" };
	public Planks(int id) 
	{
		super(id, Material.wood);
		setBurnProperties(this.blockID, 5, 20);
		this.setHardness(2.0f);
		this.setCreativeTab(NaturaTab.tab);
		this.setStepSound(Block.soundWoodFootstep);
	}
	
	@Override
	public Icon getIcon(int side, int meta)
    {
		if (meta == 0)
			return icons[1];
		if (meta == 1)
			return icons[3];
		if (meta == 2)
			return icons[4];
		if (meta == 3)
			return icons[0];
		if (meta == 4)
			return icons[5];
		if (meta == 5)
			return icons[2];
		
		return icons[meta];
    }
	
	public void registerIcons(IconRegister iconRegister)
    {
		this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:"+textureNames[i]+"_planks");
        }
    }
	
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
		if (metadata == 1 || metadata == 4)
			return 0;
        return blockFlammability[blockID];
    }

    public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
    	if (metadata == 1 || metadata == 4)
			return 0;
        return blockFireSpreadSpeed[blockID];
    }
    
    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for (int i = 0; i < 6; i++)
        par3List.add(new ItemStack(par1, 1, i));
    }
}
