package mods.natura.blocks;

import mods.natura.Natura;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CloudBlock extends NBlock
{
    public CloudBlock(int id)
    {
        super(id, Natura.cloud, 0.3F, new String[] { "cloud_white", "cloud_gray", "cloud_dark", "cloud_sulfur" });
        this.setStepSound(Block.soundClothFootstep);
        this.setUnlocalizedName("cloud");
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	if (meta == 3 && entity instanceof EntityArrow && !world.isRemote)
        {
            EntityArrow entityarrow = (EntityArrow)entity;

            if (entityarrow.isBurning())
            {
                this.explode(world, x, y, z, 1, entityarrow.shootingEntity instanceof EntityLiving ? (EntityLiving)entityarrow.shootingEntity : null);
                world.setBlockToAir(x, y, z);
                return;
            }
        }
    	
        if (entity.motionY < 0.0D)
        {
            entity.motionY *= 0.005D;
        }
        entity.fallDistance = 0.0F;
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
    	int meta = world.getBlockMetadata(x, y, z);
        if (meta == 3 && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == Item.flintAndSteel.itemID)
        {
            world.setBlockToAir(x, y, z);
            this.explode(world, x, y, z, 1, player);
            return true;
        }
        return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }
    
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion par5Explosion) 
    {
    	/*int meta = world.getBlockMetadata(x, y, z);
        if (meta == 3)
        {
            this.explode(world, x, y, z, 1, null);
        }*/
    }
    
    public void explode(World world, int x, int y, int z, int size, EntityLiving living)
    {
        world.createExplosion(living, x, y, z, size, true);
    }
    
    public boolean canDropFromExplosion(Explosion par1Explosion)
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
        if (blockID == NContent.cloud.blockID)
        {
            return false;
        }
        else
        {
            return super.shouldSideBeRendered(iblockaccess, x, y, z, side);
        }
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
    public int damageDropped(int meta)
    {
        return meta;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess iblockaccess, int x, int y, int z, int l)
    {
        int blockID = iblockaccess.getBlockId(x, y, z);
        if (blockID == NContent.cloud.blockID)
        {
            return false;
        }
        else
        {
            return super.isBlockSolid(iblockaccess, x, y, z, l);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        if (world.getBlockId(x, y - 1, z) == NContent.cloud.blockID)
        {
            return null;
        }
        else
        {
            return AxisAlignedBB.getBoundingBox(x, y, z, (double)x + 1.0D, (double)y + 0.0625D, (double)z + 1.0D);
        }
    }
    
    /* Explosions! */
}
