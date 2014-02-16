package mods.natura.blocks.crops;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class HerbBlock extends CropBlock
{
    protected HerbBlock()
    {
        super();
        this.setTickRandomly(true);
        float var3 = 0.5F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
        this.setCreativeTab((CreativeTabs) null);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
	public void updateTick (World world, int x, int y, int z, Random random)
    {
        this.checkAndDropBlock(world, x, y, z);

        int light = world.getBlockLightValue(x, y, z);
        if (light >= 8)
        {
            int meta = world.getBlockMetadata(x, y, z);

            if (meta % 4 != 3)
            {
                float grow = this.getGrowthRate(world, x, y, z, meta, light);

                if (random.nextInt((int) (50.0F / grow) + 1) == 0)
                {
                    meta++;
                    world.setBlockMetadataWithNotify(x, y, z, meta, 2);
                }
            }
        }
    }

    /**
     * Apply bonemeal to the crops.
     */
    public void fertilize (World world, int x, int y, int z)
    {
    }

    @Override
	boolean requiresSun (int meta)
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
	public int getRenderType ()
    {
        return 1;
    }

    @Override
	protected Item getCropItem (int meta)
    {
        return NContent.plantItem;
    }

    @Override
	protected Item getSeedItem (int meta)
    {
        return NContent.seeds;
    }

    @Override
	public int damageDropped (int meta)
    {
        return 0;
    }

    @Override
    public int getPlantMetadata (IBlockAccess world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public EnumPlantType getPlantType (IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Cave;
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
	public boolean canBlockStay (World par1World, int x, int y, int z)
    {
        Block soil = par1World.getBlock(x, y - 1, z);
        return soil != null && soil.getMaterial() == Material.rock;
    }
}
