package mods.natura.blocks.crops;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;

public class HerbBlock extends CropBlock
{
    protected HerbBlock(int id)
    {
        super(id);
        this.setTickRandomly(true);
        float var3 = 0.5F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
        this.setCreativeTab((CreativeTabs) null);
        this.setHardness(0.0F);
        this.setStepSound(soundGrassFootstep);
        this.disableStats();
        this.setCreativeTab(NaturaTab.tab);
    }

    public void updateTick (World world, int x, int y, int z, Random random)
    {
        this.checkFlowerChange(world, x, y, z);

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

    boolean requiresSun (int meta)
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType ()
    {
        return 1;
    }

    protected int getCropItem (int meta)
    {
        return NContent.plantItem.itemID;
    }

    protected int getSeedItem (int meta)
    {
        return NContent.seeds.itemID;
    }

    public int damageDropped (int meta)
    {
        return 0;
    }

    @Override
    public int getPlantMetadata (World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public EnumPlantType getPlantType (World world, int x, int y, int z)
    {
        return EnumPlantType.Cave;
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay (World par1World, int x, int y, int z)
    {
        Block soil = blocksList[par1World.getBlockId(x, y - 1, z)];
        return soil != null && soil.blockMaterial == Material.rock;
    }
}
