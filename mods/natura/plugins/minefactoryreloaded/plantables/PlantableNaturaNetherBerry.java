package mods.natura.plugins.minefactoryreloaded.plantables;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class PlantableNaturaNetherBerry extends PlantableStandard {

	public PlantableNaturaNetherBerry(int sourceId, int plantedBlockId)
	{
		super(sourceId, plantedBlockId);
	}

	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack) {
		Block groundBlock = Block.blocksList[world.getBlockId(x, y - 1, z)];
		
		return  (groundBlock != null && (groundBlock.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (IPlantable) NContent.netherBerryBush) || groundBlock == Block.netherrack) && world.isAirBlock(x, y, z));
	}

	@Override
	public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack) {
		return stack.getItemDamage() % 4;
	}
	
}
