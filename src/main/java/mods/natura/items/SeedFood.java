package mods.natura.items;

import java.util.List;

import mods.natura.blocks.trees.SaguaroBlock;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SeedFood extends ItemSeedFood
{
    public Block crop;

    public SeedFood(int hunger, float saturation, Block cropID)
    {
        // TODO 1.7 check last param
        super(hunger, saturation, cropID, Blocks.farmland);
        crop = cropID;
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (side != EnumFacing.UP)
        {
            return false;
        }
        else if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
        {
            return false;
        }
        else if (worldIn.getBlockState(pos).getBlock().canSustainPlant(worldIn, pos, EnumFacing.UP, (IPlantable) NContent.saguaro) && worldIn.isAirBlock(pos.up()))
        {
            worldIn.setBlockState(pos.up(), this.crop.getDefaultState().withProperty(SaguaroBlock.METADATA, 1));
            --stack.stackSize;
            if (!worldIn.isRemote)
                worldIn.playAuxSFX(2001, pos, Block.getIdFromBlock(crop));
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.sagurofruit"));
    }
}
