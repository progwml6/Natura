package mods.natura.plugins.minefactoryreloaded.plantables;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class PlantableNaturaCrop extends PlantableStandard
{

    public PlantableNaturaCrop(Item sourceId, Block plantedBlockId)
    {
        super(sourceId, plantedBlockId);
    }

    @Override
    public boolean canBePlantedHere (World world, int x, int y, int z, ItemStack stack)
    {
        if (stack.getItemDamage() == 0 || stack.getItemDamage() == 1)
        {
            Block groundId = world.getBlock(x, y - 1, z);
            if (!world.isAirBlock(x, y, z))
            {
                return false;
            }
            return (groundId == Blocks.dirt || groundId == Blocks.grass || groundId == Blocks.farmland || (_plantedBlockId instanceof IPlantable
                    && groundId != null && groundId.canSustainPlant(world, x, y, z, ForgeDirection.UP, ((IPlantable) _plantedBlockId))));
        }
        return false;
    }

    @Override
    public void prePlant (World world, int x, int y, int z, ItemStack stack)
    {
        Block groundId = world.getBlock(x, y - 1, z);
        if (groundId == Blocks.dirt || groundId == Blocks.grass)
        {
            world.setBlock(x, y - 1, z, Blocks.farmland);
        }
    }

    @Override
    public int getMeta (ItemStack stack)
    {
        return stack.getItemDamage() == 0 ? 0 : 4;
    }
}
