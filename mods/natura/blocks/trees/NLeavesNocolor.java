package mods.natura.blocks.trees;

import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NLeavesNocolor extends NLeaves
{
	protected String[] textureNames = new String[] { "sakura", "ghostwood", "bloodwood" };
	public NLeavesNocolor(int id) 
	{
		super(id);
		this.setCreativeTab(NaturaTab.tab);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons (IconRegister iconRegister)
	{
		this.fastIcons = new Icon[textureNames.length];
		this.fancyIcons = new Icon[textureNames.length];

		for (int i = 0; i < this.fastIcons.length; i++)
		{
			this.fastIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i]+"_leaves_fast");
			this.fancyIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i]+"_leaves_fancy");
		}
	}
	
	@SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return 16777215;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {
        return 16777215;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return 16777215;
    }

    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 == 0 ? blockFlammability[blockID] : 0;
    }

    public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 == 0 ? blockFireSpreadSpeed[blockID] : 0;
    }
    
    public int damageDropped(int par1)
    {
        return (par1 & 3)+3;
    }
}
