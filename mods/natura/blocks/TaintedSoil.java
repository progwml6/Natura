package mods.natura.blocks;

import static net.minecraftforge.common.ForgeDirection.UP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class TaintedSoil extends NBlock
{

    public TaintedSoil(int id)
    {
        super(id, Material.ground, 5f, new String[] { "tainted_soil", "tainted_farmland_dry", "tainted_farmland_heated" });
        this.setStepSound(Block.soundGravelFootstep);
    }

    public boolean isFertile (World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) == 2;
    }

    public boolean isGenMineableReplaceable (World world, int x, int y, int z, int target)
    {
        return blockID == target || target == Block.netherrack.blockID;
    }
    
    public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant)
    {
        EnumPlantType plantType = plant.getPlantType(world, x, y + 1, z);
        if (plantType == EnumPlantType.Nether)
            return true;
        return super.canSustainPlant(world, x, y, z, direction, plant);
    }
    
    public boolean isFireSource(World world, int x, int y, int z, int metadata, ForgeDirection side)
    {
        return true;
    }
}
