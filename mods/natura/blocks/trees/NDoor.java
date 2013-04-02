package mods.natura.blocks.trees;
import java.util.List;
import java.util.Random;

import mods.natura.common.NaturaContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NDoor extends BlockDoor
{
    private int meta;
    public Icon[] icons;
    public final String doorName;
	
	public NDoor(int id, Material material, int md, String doorName)
    {
        super(id, material);
        this.doorName = doorName;
        float f = 0.5F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        meta = md;
        this.setHardness(3F);
        this.setStepSound(Block.soundWoodFootstep);
        this.disableStats();
    }

    public int idDropped(int i, Random random, int j)
    {
    	return PHNatura.doorItemID;
    }
    
    public int damageDropped(int par1)
    {
        return meta;
    }
    
    public void registerIcons(IconRegister iconRegister)
    {
		this.icons = new Icon[2];

		this.icons[0] = iconRegister.registerIcon("natura:"+doorName+"_door_bottom");
		this.icons[1] = iconRegister.registerIcon("natura:"+doorName+"_door_top");
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 != 1 && par5 != 0)
        {
            int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
            int j1 = i1 & 3;
            boolean flag = (i1 & 4) != 0;
            boolean flag1 = false;
            boolean flag2 = (i1 & 8) != 0;

            if (flag)
            {
                if (j1 == 0 && par5 == 2)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && par5 == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && par5 == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && par5 == 4)
                {
                    flag1 = !flag1;
                }
            }
            else
            {
                if (j1 == 0 && par5 == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && par5 == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && par5 == 4)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && par5 == 2)
                {
                    flag1 = !flag1;
                }

                if ((i1 & 16) != 0)
                {
                    flag1 = !flag1;
                }
            }

            return this.icons[0 + (flag1 ? 1 : 0) + (flag2 ? 1 : 0)];
        }
        else
        {
            return this.icons[0];
        }
    }
    
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
    	return new ItemStack(NaturaContent.doorItem, 1, meta);
    }
    
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    }
}
