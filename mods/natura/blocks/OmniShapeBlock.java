package mods.natura.blocks;

import java.util.List;
import java.util.Random;

import mods.natura.client.OmniShapeRender;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OmniShapeBlock extends Block
{
    public static final int[][] field_72159_a = new int[][] { { 2, 6 }, { 3, 7 }, { 2, 3 }, { 6, 7 }, { 0, 4 }, { 1, 5 }, { 0, 1 }, { 4, 5 } };

    /** The block that is used as model for the stair. */
    public final Block modelBlock;
    public final int modelBlockMetadata;
    public boolean field_72156_cr = false;
    public int field_72160_cs = 0;

    public OmniShapeBlock(int id, Block block, int meta)
    {
        super(id, block.blockMaterial);
        this.modelBlock = block;
        this.modelBlockMetadata = meta;
        this.setHardness(block.blockHardness);
        this.setResistance(block.blockResistance / 3.0F);
        this.setStepSound(block.stepSound);
        //this.setLightOpacity(255);
        this.setCreativeTab(NaturaTab.tab);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if (meta < 8)
        {
            /*if (this.field_72156_cr)
            {
                this.setBlockBounds(0.5F * (float) (this.field_72160_cs % 2), 0.5F * (float) (this.field_72160_cs / 2 % 2), 0.5F * (float) (this.field_72160_cs / 4 % 2),
                        0.5F + 0.5F * (float) (this.field_72160_cs % 2), 0.5F + 0.5F * (float) (this.field_72160_cs / 2 % 2), 0.5F + 0.5F * (float) (this.field_72160_cs / 4 % 2));
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }*/
        }

        else if (meta < 14)
        {
            float offset = 0.5f;
            switch (meta)
            {
            case 8:
                setBlockBounds(0f, offset, 0f, 1f, 1f, 1f);
                break;
            case 9:
                setBlockBounds(0f, 0f, 0f, 1f, offset, 1f);
                break;
            case 10:
                setBlockBounds(0f, 0f, offset, 1f, 1f, 1f);
                break;
            case 11:
                setBlockBounds(0f, 0f, 0f, 1f, 1f, offset);
                break;
            case 12:
                setBlockBounds(offset, 0f, 0f, 1f, 1f, 1f);
                break;
            case 13:
                setBlockBounds(0f, 0f, 0f, offset, 1f, 1f);
                break;
            }
        }

        else if (meta == 14)
        {
            boolean flag = this.canConnectWallTo(par1IBlockAccess, par2, par3, par4 - 1);
            boolean flag1 = this.canConnectWallTo(par1IBlockAccess, par2, par3, par4 + 1);
            boolean flag2 = this.canConnectWallTo(par1IBlockAccess, par2 - 1, par3, par4);
            boolean flag3 = this.canConnectWallTo(par1IBlockAccess, par2 + 1, par3, par4);
            float f = 0.25F;
            float f1 = 0.75F;
            float f2 = 0.25F;
            float f3 = 0.75F;
            float f4 = 1.0F;

            if (flag)
            {
                f2 = 0.0F;
            }

            if (flag1)
            {
                f3 = 1.0F;
            }

            if (flag2)
            {
                f = 0.0F;
            }

            if (flag3)
            {
                f1 = 1.0F;
            }

            if (flag && flag1 && !flag2 && !flag3)
            {
                f4 = 0.8125F;
                f = 0.3125F;
                f1 = 0.6875F;
            }
            else if (!flag && !flag1 && flag2 && flag3)
            {
                f4 = 0.8125F;
                f2 = 0.3125F;
                f3 = 0.6875F;
            }

            this.setBlockBounds(f, 0.0F, f2, f1, f4, f3);
        }
    }

    public boolean canConnectWallTo (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getBlockId(par2, par3, par4);

        if (l != this.blockID && l != Block.fenceGate.blockID)
        {
            Block block = Block.blocksList[l];
            return block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock() ? block.blockMaterial != Material.pumpkin : false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public boolean isOpaqueCube ()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock ()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType ()
    {
        return OmniShapeRender.model;
    }

    public void func_82541_d (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        if ((l & 4) != 0)
        {
            this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    /**
     * Checks if supplied ID is one of a BlockStairs
     */
    public static boolean isBlockStairsID (int par0)
    {
        return par0 > 0 && Block.blocksList[par0] instanceof OmniShapeBlock;
    }

    public boolean func_82540_f (IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int i1 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return isBlockStairsID(i1) && par1IBlockAccess.getBlockMetadata(par2, par3, par4) == par5;
    }

    public boolean func_82542_g (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        int i1 = l & 3;
        float f = 0.5F;
        float f1 = 1.0F;

        if ((l & 4) != 0)
        {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 0.5F;
        boolean flag = true;
        int j1;
        int k1;
        int l1;

        if (i1 == 0)
        {
            f2 = 0.5F;
            f5 = 1.0F;
            j1 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
            k1 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);

            if (isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 3 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 + 1, l))
                {
                    f5 = 0.5F;
                    flag = false;
                }
                else if (l1 == 2 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 - 1, l))
                {
                    f4 = 0.5F;
                    flag = false;
                }
            }
        }
        else if (i1 == 1)
        {
            f3 = 0.5F;
            f5 = 1.0F;
            j1 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
            k1 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);

            if (isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 3 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 + 1, l))
                {
                    f5 = 0.5F;
                    flag = false;
                }
                else if (l1 == 2 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 - 1, l))
                {
                    f4 = 0.5F;
                    flag = false;
                }
            }
        }
        else if (i1 == 2)
        {
            f4 = 0.5F;
            f5 = 1.0F;
            j1 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
            k1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);

            if (isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 1 && !this.func_82540_f(par1IBlockAccess, par2 + 1, par3, par4, l))
                {
                    f3 = 0.5F;
                    flag = false;
                }
                else if (l1 == 0 && !this.func_82540_f(par1IBlockAccess, par2 - 1, par3, par4, l))
                {
                    f2 = 0.5F;
                    flag = false;
                }
            }
        }
        else if (i1 == 3)
        {
            j1 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
            k1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);

            if (isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 1 && !this.func_82540_f(par1IBlockAccess, par2 + 1, par3, par4, l))
                {
                    f3 = 0.5F;
                    flag = false;
                }
                else if (l1 == 0 && !this.func_82540_f(par1IBlockAccess, par2 - 1, par3, par4, l))
                {
                    f2 = 0.5F;
                    flag = false;
                }
            }
        }

        this.setBlockBounds(f2, f, f4, f3, f1, f5);
        return flag;
    }

    public boolean func_82544_h (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        int i1 = l & 3;
        float f = 0.5F;
        float f1 = 1.0F;

        if ((l & 4) != 0)
        {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = 0.5F;
        float f5 = 1.0F;
        boolean flag = false;
        int j1;
        int k1;
        int l1;

        if (i1 == 0)
        {
            j1 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
            k1 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);

            if (isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 3 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 - 1, l))
                {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    flag = true;
                }
                else if (l1 == 2 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 + 1, l))
                {
                    f4 = 0.5F;
                    f5 = 1.0F;
                    flag = true;
                }
            }
        }
        else if (i1 == 1)
        {
            j1 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
            k1 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);

            if (isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                f2 = 0.5F;
                f3 = 1.0F;
                l1 = k1 & 3;

                if (l1 == 3 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 - 1, l))
                {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    flag = true;
                }
                else if (l1 == 2 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 + 1, l))
                {
                    f4 = 0.5F;
                    f5 = 1.0F;
                    flag = true;
                }
            }
        }
        else if (i1 == 2)
        {
            j1 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
            k1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);

            if (isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                f4 = 0.0F;
                f5 = 0.5F;
                l1 = k1 & 3;

                if (l1 == 1 && !this.func_82540_f(par1IBlockAccess, par2 - 1, par3, par4, l))
                {
                    flag = true;
                }
                else if (l1 == 0 && !this.func_82540_f(par1IBlockAccess, par2 + 1, par3, par4, l))
                {
                    f2 = 0.5F;
                    f3 = 1.0F;
                    flag = true;
                }
            }
        }
        else if (i1 == 3)
        {
            j1 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
            k1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);

            if (isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 1 && !this.func_82540_f(par1IBlockAccess, par2 - 1, par3, par4, l))
                {
                    flag = true;
                }
                else if (l1 == 0 && !this.func_82540_f(par1IBlockAccess, par2 + 1, par3, par4, l))
                {
                    f2 = 0.5F;
                    f3 = 1.0F;
                    flag = true;
                }
            }
        }

        if (flag)
        {
            this.setBlockBounds(f2, f, f4, f3, f1, f5);
        }

        return flag;
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList (World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        /*int meta = world.getBlockMetadata(x, y, z);
        if (meta < 8)
        {
            this.func_82541_d(world, x, y, z);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
            boolean flag = this.func_82542_g(world, x, y, z);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

            if (flag && this.func_82544_h(world, x, y, z))
            {
                super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
            }

            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else*/
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked (World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        this.modelBlock.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
    }

    @SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick (World par1World, int par2, int par3, int par4, Random par5Random)
    {
        this.modelBlock.randomDisplayTick(par1World, par2, par3, par4, par5Random);
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer (World par1World, int par2, int par3, int par4, int par5)
    {
        this.modelBlock.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
    }

    //@SideOnly(Side.CLIENT)
    /**
     * Goes straight to getLightBrightnessForSkyBlocks for Blocks, does some fancy computing for Fluids
     */
    /*public int getMixedBrightnessForBlock (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.modelBlock.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
    }*/

    //@SideOnly(Side.CLIENT)
    /**
     * How bright to render this block based on the light its receiving. Args: iBlockAccess, x, y, z
     */
    /*public float getBlockBrightness (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.modelBlock.getBlockBrightness(par1IBlockAccess, par2, par3, par4);
    }*/

    /**
     * Returns how much this block can resist explosions from the passed in entity.
     */
    public float getExplosionResistance (Entity par1Entity)
    {
        return this.modelBlock.getExplosionResistance(par1Entity);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate (World par1World)
    {
        return this.modelBlock.tickRate(par1World);
    }

    /**
     * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
     */
    public void velocityToAddToEntity (World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3)
    {
        this.modelBlock.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3);
    }

    //@SideOnly(Side.CLIENT)
    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    /*public int getRenderBlockPass ()
    {
        return this.modelBlock.getRenderBlockPass();
    }*/

    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon (int side, int meta)
    {
        return this.modelBlock.getIcon(side, this.modelBlockMetadata);
    }

    //@SideOnly(Side.CLIENT)
    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool (World world, int x, int y, int z)
    {
        if (world.getBlockMetadata(x, y, z) < 8)
            return this.modelBlock.getSelectedBoundingBoxFromPool(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean isCollidable ()
    {
        return this.modelBlock.isCollidable();
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean canCollideCheck (int par1, boolean par2)
    {
        return this.modelBlock.canCollideCheck(par1, par2);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    /*public boolean canPlaceBlockAt (World par1World, int par2, int par3, int par4)
    {
        return this.modelBlock.canPlaceBlockAt(par1World, par2, par3, par4);
    }*/

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded (World par1World, int par2, int par3, int par4)
    {
        this.onNeighborBlockChange(par1World, par2, par3, par4, 0);
        this.modelBlock.onBlockAdded(par1World, par2, par3, par4);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    /*public void breakBlock (World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        this.modelBlock.breakBlock(par1World, par2, par3, par4, par5, par6);
    }*/

    /**
     * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
     */
    /*public void onEntityWalking (World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        this.modelBlock.onEntityWalking(par1World, par2, par3, par4, par5Entity);
    }*/

    /**
     * Ticks the block if it's been scheduled
     */
    /*public void updateTick (World par1World, int par2, int par3, int par4, Random par5Random)
    {
        this.modelBlock.updateTick(par1World, par2, par3, par4, par5Random);
    }*/

    /**
     * Called upon block activation (right click on the block.)
     */
    /*public boolean onBlockActivated (World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return this.modelBlock.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, 0, 0.0F, 0.0F, 0.0F);
    }*/

    /**
     * Called upon the block being destroyed by an explosion
     */
    /*public void onBlockDestroyedByExplosion (World par1World, int par2, int par3, int par4, Explosion par5Explosion)
    {
        this.modelBlock.onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
    }*/

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy (World world, int x, int y, int z, EntityLiving living, ItemStack stack)
    {
        int rotation = MathHelper.floor_double((double) (living.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 8)
        {
            meta = meta & 4;

            if (rotation == 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, 2 | meta, 2);
            }

            if (rotation == 1)
            {
                world.setBlockMetadataWithNotify(x, y, z, 1 | meta, 2);
            }

            if (rotation == 2)
            {
                world.setBlockMetadataWithNotify(x, y, z, 3 | meta, 2);
            }

            if (rotation == 3)
            {
                world.setBlockMetadataWithNotify(x, y, z, 0 | meta, 2);
            }
        }
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced (World par1World, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        if (meta < 8)
            return side != 0 && (side == 1 || (double) hitY <= 0.5D) ? meta : meta | 4;
        else if (meta < 14)
            return side + 8;
        else
            return meta;
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace (World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        MovingObjectPosition[] amovingobjectposition = new MovingObjectPosition[8];
        int l = par1World.getBlockMetadata(par2, par3, par4);
        int i1 = l & 3;
        boolean flag = (l & 4) == 4;
        int[] aint = field_72159_a[i1 + (flag ? 4 : 0)];
        this.field_72156_cr = true;
        int j1;
        int k1;
        int l1;

        for (int i2 = 0; i2 < 8; ++i2)
        {
            this.field_72160_cs = i2;
            int[] aint1 = aint;
            j1 = aint.length;

            for (k1 = 0; k1 < j1; ++k1)
            {
                l1 = aint1[k1];

                if (l1 == i2)
                {
                    ;
                }
            }

            amovingobjectposition[i2] = super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
        }

        int[] aint2 = aint;
        int j2 = aint.length;

        for (j1 = 0; j1 < j2; ++j1)
        {
            k1 = aint2[j1];
            amovingobjectposition[k1] = null;
        }

        MovingObjectPosition movingobjectposition = null;
        double d0 = 0.0D;
        MovingObjectPosition[] amovingobjectposition1 = amovingobjectposition;
        l1 = amovingobjectposition.length;

        for (int k2 = 0; k2 < l1; ++k2)
        {
            MovingObjectPosition movingobjectposition1 = amovingobjectposition1[k2];

            if (movingobjectposition1 != null)
            {
                double d1 = movingobjectposition1.hitVec.squareDistanceTo(par6Vec3);

                if (d1 > d0)
                {
                    movingobjectposition = movingobjectposition1;
                    d0 = d1;
                }
            }
        }

        return movingobjectposition;
    }

    @Override
    public boolean shouldSideBeRendered (IBlockAccess world, int x, int y, int z, int side)
    {
        //if (world.getBlockMetadata(x, y, z) == 0)
            return true;
        //return super.shouldSideBeRendered(world, x, y, z, side);
    }

    @SideOnly(Side.CLIENT)
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons (IconRegister par1IconRegister)
    {
    }

    public void getSubBlocks (int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 8));
        par3List.add(new ItemStack(par1, 1, 14));
    }
}
