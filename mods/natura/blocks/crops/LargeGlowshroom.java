package mods.natura.blocks.crops;

import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LargeGlowshroom extends Block
{
    private final String mushroomType;
    @SideOnly(Side.CLIENT)
    private Icon iconSkin;
    @SideOnly(Side.CLIENT)
    private Icon iconStem;
    @SideOnly(Side.CLIENT)
    private Icon iconInside;

    public LargeGlowshroom(int par1, Material par2Material, String type)
    {
        super(par1, par2Material);
        mushroomType = type;
    }

    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon (int side, int meta)
    {
        return meta == 10 && side > 1 ? this.iconStem : (meta >= 1 && meta <= 9 && side == 1 ? this.iconSkin : (meta >= 1 && meta <= 3 && side == 2 ? this.iconSkin : (meta >= 7 && meta <= 9
                && side == 3 ? this.iconSkin : ((meta == 1 || meta == 4 || meta == 7) && side == 4 ? this.iconSkin : ((meta == 3 || meta == 6 || meta == 9)
                && side == 5 ? this.iconSkin : (meta == 14 ? this.iconSkin : (meta == 15 ? this.iconStem : this.iconInside)))))));
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped (Random par1Random)
    {
        int i = par1Random.nextInt(10) - 7;

        if (i < 0)
        {
            i = 0;
        }

        return i;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
        int blockID = iblockaccess.getBlockId(x, y, z);
        if (blockID == NContent.glowshroomBlue.blockID)
        {
            return false;
        }
        else
        {
            return super.shouldSideBeRendered(iblockaccess, x, y, z, side);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped (int par1, Random par2Random, int par3)
    {
        return NContent.glowshroom.blockID;
    }

    @SideOnly(Side.CLIENT)
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked (World par1World, int par2, int par3, int par4)
    {
        return NContent.glowshroom.blockID;
    }

    @SideOnly(Side.CLIENT)
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons (IconRegister iconregister)
    {
        this.iconSkin = iconregister.registerIcon("natura:mushroom_skin_" + mushroomType);
        this.iconInside = iconregister.registerIcon("natura:mushroom_inside_" + mushroomType);
        this.iconStem = iconregister.registerIcon("natura:mushroom_stem_" + mushroomType);
    }
}
