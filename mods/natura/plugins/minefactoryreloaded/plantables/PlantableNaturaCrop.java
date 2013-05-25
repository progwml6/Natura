package mods.natura.plugins.minefactoryreloaded.plantables;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class PlantableNaturaCrop extends PlantableStandard
{
	
	public PlantableNaturaCrop(int sourceId, int plantedBlockId)
	{
		super(sourceId, plantedBlockId);
	}
	
	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		if(stack.getItemDamage() == 0 || stack.getItemDamage() == 1)
		{
			int groundId = world.getBlockId(x, y - 1, z);
			if(!world.isAirBlock(x, y, z))
			{
				return false;
			}
			return (
					groundId == Block.dirt.blockID ||
					groundId == Block.grass.blockID ||
					groundId == Block.tilledField.blockID ||
					(Block.blocksList[_plantedBlockId] instanceof IPlantable && Block.blocksList[groundId] != null &&
					Block.blocksList[groundId].canSustainPlant(world, x, y, z, ForgeDirection.UP, ((IPlantable)Block.blocksList[_plantedBlockId]))));
		}
		return false;
	}
	
	@Override
	public void prePlant(World world, int x, int y, int z, ItemStack stack)
	{
		int groundId = world.getBlockId(x, y - 1, z);
		if(groundId == Block.dirt.blockID || groundId == Block.grass.blockID)
		{
			world.setBlock(x, y - 1, z, Block.tilledField.blockID);
		}
	}
	
	@Override
	public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
	{
		return stack.getItemDamage() == 0 ? 0 : 4;
	}
}
