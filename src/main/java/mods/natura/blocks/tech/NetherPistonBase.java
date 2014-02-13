package mods.natura.blocks.tech;

import java.util.List;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherPistonBase extends BlockPistonBase
{
    @SideOnly(Side.CLIENT)
    private Icon iIcon;
    @SideOnly(Side.CLIENT)
    private Icon bIcon;
    @SideOnly(Side.CLIENT)
    private Icon tIcon;
    private boolean sticky;

    public NetherPistonBase(int id, boolean sticky)
    {
        super(id, sticky);
        this.sticky = sticky;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getPistonExtensionTexture ()
    {
        return this.tIcon;
    }

    /*public int getRenderType ()
    {
        return PistonRender.model;
    }*/

    @SideOnly(Side.CLIENT)
    public static Icon getBaseIcon (String par0Str)
    {
        return par0Str == "piston_side" ? NContent.piston.blockIcon : (par0Str == "piston_top_normal" ? NContent.piston.tIcon : (par0Str == "piston_top_sticky" ? NContent.piston.tIcon
                : (par0Str == "piston_inner" ? NContent.piston.iIcon : null)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("natura:piston_side");
        this.tIcon = par1IconRegister.registerIcon(this.sticky ? "natura:piston_top_sticky" : "natura:piston_top_normal");
        this.iIcon = par1IconRegister.registerIcon("natura:piston_inner");
        this.bIcon = par1IconRegister.registerIcon("natura:piston_bottom");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        int k = getOrientation(meta);
        return k > 5 ? this.tIcon
                : (side == k ? (!isExtended(meta) && this.minX <= 0.0D && this.minY <= 0.0D && this.minZ <= 0.0D && this.maxX >= 1.0D && this.maxY >= 1.0D && this.maxZ >= 1.0D ? this.tIcon
                        : this.iIcon) : (side == Facing.oppositeSide[k] ? this.bIcon : this.blockIcon));
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube ()
    {
        return false;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated (World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return false;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy (World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int l = determineOrientation(par1World, par2, par3, par4, par5EntityLivingBase);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);

        if (!par1World.isRemote)
        {
            this.updatePistonState(par1World, par2, par3, par4);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange (World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote)
        {
            this.updatePistonState(par1World, par2, par3, par4);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded (World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote && par1World.getBlockTileEntity(par2, par3, par4) == null)
        {
            this.updatePistonState(par1World, par2, par3, par4);
        }
    }

    /**
     * handles attempts to extend or retract the piston.
     */
    private void updatePistonState (World par1World, int par2, int par3, int par4)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        int i1 = getOrientation(l);

        if (i1 != 7)
        {
            boolean flag = this.isIndirectlyPowered(par1World, par2, par3, par4, i1);

            if (flag && !isExtended(l))
            {
                if (canExtend(par1World, par2, par3, par4, i1))
                {
                    par1World.addBlockEvent(par2, par3, par4, this.blockID, 0, i1);
                }
            }
            else if (!flag && isExtended(l))
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, i1, 2);
                par1World.addBlockEvent(par2, par3, par4, this.blockID, 1, i1);
            }
        }
    }

    /**
     * checks the block to that side to see if it is indirectly powered.
     */
    private boolean isIndirectlyPowered (World par1World, int par2, int par3, int par4, int par5)
    {
        return par5 != 0 && par1World.getIndirectPowerOutput(par2, par3 - 1, par4, 0) ? true : (par5 != 1 && par1World.getIndirectPowerOutput(par2, par3 + 1, par4, 1) ? true : (par5 != 2
                && par1World.getIndirectPowerOutput(par2, par3, par4 - 1, 2) ? true : (par5 != 3 && par1World.getIndirectPowerOutput(par2, par3, par4 + 1, 3) ? true : (par5 != 5
                && par1World.getIndirectPowerOutput(par2 + 1, par3, par4, 5) ? true : (par5 != 4 && par1World.getIndirectPowerOutput(par2 - 1, par3, par4, 4) ? true : (par1World
                .getIndirectPowerOutput(par2, par3, par4, 0) ? true : (par1World.getIndirectPowerOutput(par2, par3 + 2, par4, 1) ? true : (par1World
                .getIndirectPowerOutput(par2, par3 + 1, par4 - 1, 2) ? true : (par1World.getIndirectPowerOutput(par2, par3 + 1, par4 + 1, 3) ? true : (par1World.getIndirectPowerOutput(par2 - 1,
                par3 + 1, par4, 4) ? true : par1World.getIndirectPowerOutput(par2 + 1, par3 + 1, par4, 5)))))))))));
    }

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
     * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
     */
    public boolean onBlockEventReceived (World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isRemote)
        {
            boolean flag = this.isIndirectlyPowered(par1World, par2, par3, par4, par6);

            if (flag && par5 == 1)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, par6 | 8, 2);
                return false;
            }

            if (!flag && par5 == 0)
            {
                return false;
            }
        }

        if (par5 == 0)
        {
            if (!this.tryExtend(par1World, par2, par3, par4, par6))
            {
                return false;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, par6 | 8, 2);
            par1World.playSoundEffect((double) par2 + 0.5D, (double) par3 + 0.5D, (double) par4 + 0.5D, "tile.piston.out", 0.5F, par1World.rand.nextFloat() * 0.25F + 0.6F);
        }
        else if (par5 == 1)
        {
            TileEntity tileentity = par1World.getBlockTileEntity(par2 + Facing.offsetsXForSide[par6], par3 + Facing.offsetsYForSide[par6], par4 + Facing.offsetsZForSide[par6]);

            if (tileentity instanceof TileEntityPiston)
            {
                ((TileEntityPiston) tileentity).clearPistonTileEntity();
            }

            par1World.setBlock(par2, par3, par4, Block.pistonMoving.blockID, par6, 3);
            par1World.setBlockTileEntity(par2, par3, par4, BlockPistonMoving.getTileEntity(this.blockID, par6, par6, false, true));

            if (this.sticky)
            {
                int j1 = par2 + Facing.offsetsXForSide[par6] * 2;
                int k1 = par3 + Facing.offsetsYForSide[par6] * 2;
                int l1 = par4 + Facing.offsetsZForSide[par6] * 2;
                int i2 = par1World.getBlockId(j1, k1, l1);
                int j2 = par1World.getBlockMetadata(j1, k1, l1);
                boolean flag1 = false;

                if (i2 == Block.pistonMoving.blockID)
                {
                    TileEntity tileentity1 = par1World.getBlockTileEntity(j1, k1, l1);

                    if (tileentity1 instanceof TileEntityPiston)
                    {
                        TileEntityPiston tileentitypiston = (TileEntityPiston) tileentity1;

                        if (tileentitypiston.getPistonOrientation() == par6 && tileentitypiston.isExtending())
                        {
                            tileentitypiston.clearPistonTileEntity();
                            i2 = tileentitypiston.getStoredBlockID();
                            j2 = tileentitypiston.getBlockMetadata();
                            flag1 = true;
                        }
                    }
                }

                if (!flag1 && i2 > 0 && canPushBlock(i2, par1World, j1, k1, l1, false)
                        && (Block.blocksList[i2].getMobilityFlag() == 0 || i2 == NContent.piston.blockID || i2 == NContent.pistonSticky.blockID))
                {
                    par2 += Facing.offsetsXForSide[par6];
                    par3 += Facing.offsetsYForSide[par6];
                    par4 += Facing.offsetsZForSide[par6];
                    par1World.setBlock(par2, par3, par4, Block.pistonMoving.blockID, j2, 3);
                    par1World.setBlockTileEntity(par2, par3, par4, BlockPistonMoving.getTileEntity(i2, j2, par6, false, false));
                    par1World.setBlockToAir(j1, k1, l1);
                }
                else if (!flag1)
                {
                    par1World.setBlockToAir(par2 + Facing.offsetsXForSide[par6], par3 + Facing.offsetsYForSide[par6], par4 + Facing.offsetsZForSide[par6]);
                }
            }
            else
            {
                par1World.setBlockToAir(par2 + Facing.offsetsXForSide[par6], par3 + Facing.offsetsYForSide[par6], par4 + Facing.offsetsZForSide[par6]);
            }

            par1World.playSoundEffect((double) par2 + 0.5D, (double) par3 + 0.5D, (double) par4 + 0.5D, "tile.piston.in", 0.5F, par1World.rand.nextFloat() * 0.15F + 0.6F);
        }

        return true;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        if (isExtended(l))
        {
            float f = 0.25F;

            switch (getOrientation(l))
            {
            case 0:
                this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;
            case 1:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                break;
            case 2:
                this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                break;
            case 3:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                break;
            case 4:
                this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;
            case 5:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
            }
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender ()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList (World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool (World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock ()
    {
        return false;
    }

    /**
     * returns an int which describes the direction the piston faces
     */
    public static int getOrientation (int par0)
    {
        return par0 & 7;
    }

    /**
     * Determine if the metadata is related to something powered.
     */
    public static boolean isExtended (int par0)
    {
        return (par0 & 8) != 0;
    }

    /**
     * gets the way this piston should face for that entity that placed it.
     */
    public static int determineOrientation (World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityLivingBase)
    {
        if (MathHelper.abs((float) par4EntityLivingBase.posX - (float) par1) < 2.0F && MathHelper.abs((float) par4EntityLivingBase.posZ - (float) par3) < 2.0F)
        {
            double d0 = par4EntityLivingBase.posY + 1.82D - (double) par4EntityLivingBase.yOffset;

            if (d0 - (double) par2 > 2.0D)
            {
                return 1;
            }

            if ((double) par2 - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double) (par4EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }

    /**
     * returns true if the piston can push the specified block
     */
    private static boolean canPushBlock (int par0, World par1World, int par2, int par3, int par4, boolean par5)
    {
        if (par0 == Block.obsidian.blockID)
        {
            return false;
        }
        else
        {
            if (par0 != NContent.piston.blockID && par0 != NContent.pistonSticky.blockID)
            {
                if (Block.blocksList[par0].getBlockHardness(par1World, par2, par3, par4) == -1.0F)
                {
                    return false;
                }

                if (Block.blocksList[par0].getMobilityFlag() == 2)
                {
                    return false;
                }

                if (Block.blocksList[par0].getMobilityFlag() == 1)
                {
                    if (!par5)
                    {
                        return false;
                    }

                    return true;
                }
            }
            else if (isExtended(par1World.getBlockMetadata(par2, par3, par4)))
            {
                return false;
            }

            return !par1World.blockHasTileEntity(par2, par3, par4);
        }
    }

    /**
     * checks to see if this piston could push the blocks in front of it.
     */
    private static boolean canExtend (World par0World, int par1, int par2, int par3, int par4)
    {
        int i1 = par1 + Facing.offsetsXForSide[par4];
        int j1 = par2 + Facing.offsetsYForSide[par4];
        int k1 = par3 + Facing.offsetsZForSide[par4];
        int l1 = 0;

        while (true)
        {
            if (l1 < 13)
            {
                if (j1 <= 0 || j1 >= par0World.getHeight() - 1)
                {
                    return false;
                }

                int i2 = par0World.getBlockId(i1, j1, k1);

                if (!par0World.isAirBlock(i1, j1, k1))
                {
                    if (!canPushBlock(i2, par0World, i1, j1, k1, true))
                    {
                        return false;
                    }

                    if (Block.blocksList[i2].getMobilityFlag() != 1)
                    {
                        if (l1 == 12)
                        {
                            return false;
                        }

                        i1 += Facing.offsetsXForSide[par4];
                        j1 += Facing.offsetsYForSide[par4];
                        k1 += Facing.offsetsZForSide[par4];
                        ++l1;
                        continue;
                    }
                }
            }

            return true;
        }
    }

    /**
     * attempts to extend the piston. returns false if impossible.
     */
    private boolean tryExtend (World par1World, int par2, int par3, int par4, int par5)
    {
        int i1 = par2 + Facing.offsetsXForSide[par5];
        int j1 = par3 + Facing.offsetsYForSide[par5];
        int k1 = par4 + Facing.offsetsZForSide[par5];
        int l1 = 0;

        while (true)
        {
            int i2;

            if (l1 < 13)
            {
                if (j1 <= 0 || j1 >= par1World.getHeight() - 1)
                {
                    return false;
                }

                i2 = par1World.getBlockId(i1, j1, k1);

                if (!par1World.isAirBlock(i1, j1, k1))
                {
                    if (!canPushBlock(i2, par1World, i1, j1, k1, true))
                    {
                        return false;
                    }

                    if (Block.blocksList[i2].getMobilityFlag() != 1)
                    {
                        if (l1 == 12)
                        {
                            return false;
                        }

                        i1 += Facing.offsetsXForSide[par5];
                        j1 += Facing.offsetsYForSide[par5];
                        k1 += Facing.offsetsZForSide[par5];
                        ++l1;
                        continue;
                    }

                    //With our change to how snowballs are dropped this needs to disallow to mimic vanilla behavior.
                    float chance = (Block.blocksList[i2] instanceof BlockSnow ? -1.0f : 1.0f);
                    Block.blocksList[i2].dropBlockAsItemWithChance(par1World, i1, j1, k1, par1World.getBlockMetadata(i1, j1, k1), chance, 0);
                    par1World.setBlockToAir(i1, j1, k1);
                }
            }

            l1 = i1;
            i2 = j1;
            int j2 = k1;
            int k2 = 0;
            int[] aint;
            int l2;
            int i3;
            int j3;

            for (aint = new int[13]; i1 != par2 || j1 != par3 || k1 != par4; k1 = j3)
            {
                l2 = i1 - Facing.offsetsXForSide[par5];
                i3 = j1 - Facing.offsetsYForSide[par5];
                j3 = k1 - Facing.offsetsZForSide[par5];
                int k3 = par1World.getBlockId(l2, i3, j3);
                int l3 = par1World.getBlockMetadata(l2, i3, j3);

                if (k3 == this.blockID && l2 == par2 && i3 == par3 && j3 == par4)
                {
                    par1World.setBlock(i1, j1, k1, Block.pistonMoving.blockID, par5 | (this.sticky ? 8 : 0), 4);
                    par1World.setBlockTileEntity(i1, j1, k1, BlockPistonMoving.getTileEntity(NContent.pistonExtension.blockID, par5 | (this.sticky ? 8 : 0), par5, true, false));
                }
                else
                {
                    par1World.setBlock(i1, j1, k1, Block.pistonMoving.blockID, l3, 4);
                    par1World.setBlockTileEntity(i1, j1, k1, BlockPistonMoving.getTileEntity(k3, l3, par5, true, false));
                }

                aint[k2++] = k3;
                i1 = l2;
                j1 = i3;
            }

            i1 = l1;
            j1 = i2;
            k1 = j2;

            for (k2 = 0; i1 != par2 || j1 != par3 || k1 != par4; k1 = j3)
            {
                l2 = i1 - Facing.offsetsXForSide[par5];
                i3 = j1 - Facing.offsetsYForSide[par5];
                j3 = k1 - Facing.offsetsZForSide[par5];
                par1World.notifyBlocksOfNeighborChange(l2, i3, j3, aint[k2++]);
                i1 = l2;
                j1 = i3;
            }

            return true;
        }
    }
}
