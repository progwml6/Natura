package mods.natura.blocks.trees;

import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NDoor extends BlockDoor
{
    private int meta;
    public IIcon[] icons;
    public IIcon[] iconsReverse;
    public final String doorName;

    public NDoor(Material material, int md, String doorName)
    {
        super(material);
        this.doorName = doorName;
        float f = 0.5F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        meta = md;
        this.setHardness(3F);
        this.setStepSound(Block.soundTypeWood);
        this.disableStats();
    }

    @Override
    public Item getItemDropped (int i, Random random, int j)
    {
        return (i & 8) != 0 ? Item.getItemFromBlock(Blocks.air) : NContent.doorItem;
    }

    @Override
    public int damageDropped (int par1)
    {
        return meta;
    }

    @Override
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[2];

        this.icons[0] = iconRegister.registerIcon("natura:" + doorName + "_door_bottom");
        this.icons[1] = iconRegister.registerIcon("natura:" + doorName + "_door_top");
        
        this.iconsReverse = new IIcon[this.icons.length];
        
        for (int i = 0; i < this.icons.length; i++)
        {
            this.iconsReverse[i] = new IconFlipped(this.icons[i], true, false);
        }
    }

    @SideOnly(Side.CLIENT)
    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    @Override
    public IIcon getIcon (IBlockAccess blockAccess, int x, int y, int z, int side)
    {
        if (side != 1 && side != 0)
        {
            int i1 = this.func_150012_g(blockAccess, x, y, z);
            int j1 = i1 & 3;
            boolean flag = (i1 & 4) != 0;
            boolean flag1 = false;
            boolean flag2 = (i1 & 8) != 0;
            
            if (flag)
            {
                if (j1 == 0 && side == 2)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && side == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && side == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && side == 4)
                {
                    flag1 = !flag1;
                }
            }
            else
            {
                if (j1 == 0 && side == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && side == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && side == 4)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && side == 2)
                {
                    flag1 = !flag1;
                }
    
                if ((i1 & 16) != 0)
                {
                    flag1 = !flag1;
                }
            }

            return flag1 ? this.iconsReverse[(flag2 ? 1 : 0)] : this.icons[(flag2 ? 1 : 0)];
        }
        else
        {
            return this.icons[0];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int par1, int par2)
    {
        return this.icons[0];
    }

    @Override
    public ItemStack getPickBlock (MovingObjectPosition target, World world, int x, int y, int z)
    {
        return new ItemStack(NContent.doorItem, 1, meta);
    }

    @Override
    public int getRenderType ()
    {
        return 7;
    }
}
