package mods.natura.blocks;

import mods.natura.Natura;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
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
        if (entity.motionY < 0.0D)
        {
            entity.motionY *= 0.005D;
        }
        entity.fallDistance = 0.0F;
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
    public int damageDropped(int meta)
    {
        return meta;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
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
}
