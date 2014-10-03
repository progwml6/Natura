package mods.natura.plugins.minefactoryreloaded.plantables;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;

/*
 * Used for directly placing blocks (ie saplings) and items (ie sugarcane). Pass in source ID to constructor,
 * so one instance per source ID.
 */

public class PlantableStandard implements IFactoryPlantable
{
    protected Item _sourceId;
    protected Block _plantedBlockId;

    public PlantableStandard(Item sourceId, Block plantedBlockId)
    {
        this._sourceId = sourceId;
        this._plantedBlockId = plantedBlockId;
    }
    public boolean canBePlanted(ItemStack stack, boolean forFermenting) {
        return true;//TODO figure out what on earth this is supposed to be
    }
    @Override
    public boolean canBePlantedHere (World world, int x, int y, int z, ItemStack stack)
    {
        Block groundId = world.getBlock(x, y - 1, z);
        return world.isAirBlock(x, y, z) && ((_plantedBlockId.canPlaceBlockAt(world, x, y, z) && _plantedBlockId.canBlockStay(world, x, y, z)) || (
                _plantedBlockId instanceof IPlantable && groundId != null && groundId
                        .canSustainPlant(world, x, y, z, ForgeDirection.UP, ((IPlantable) _plantedBlockId))));
    }

    @Override
    public void prePlant (World world, int x, int y, int z, ItemStack stack)
    {
    }

    @Override
    public void postPlant (World world, int x, int y, int z, ItemStack stack)
    {
    }
    @Override
    public ReplacementBlock getPlantedBlock (World world, int x, int y, int z, ItemStack stack)
    {
        if (stack.getItem() != _sourceId)
        {
            return new ReplacementBlock(Blocks.air);
        }
        return new ReplacementBlock(_plantedBlockId).setMeta(getMeta(stack));

    }

    @Override
    public Item getSeed ()
    {
        return _sourceId;
    }
    public int getMeta (ItemStack i) {
        return i.getItemDamage();
    }
}
