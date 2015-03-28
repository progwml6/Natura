package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetherBerryBushItem extends MultiItemBlock
{

    public static final String blockType[] = { "blight", "dusk", "sky", "sting", "blight", "dusk", "sky", "sting", "blight", "dusk", "sky", "sting", "blight", "dusk", "sky", "sting" };

    public NetherBerryBushItem(Block block)
    {
        super(block, "block.bush.berry", blockType);
        //setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int meta)
    {
        return meta % 4;
    }

    /* Place bushes on dirt, grass, or other bushes only */
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (side != EnumFacing.UP)
            return false;

        else if (playerIn.canPlayerEdit(pos, side, stack) && playerIn.canPlayerEdit(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), side, stack))
        {
            IBlockState iBlockState = worldIn.getBlockState(pos);
            Block block = iBlockState.getBlock();

            if (block != null && (block.canSustainPlant(worldIn, pos, EnumFacing.UP, NContent.netherBerryBush) || block == Blocks.netherrack) && worldIn.isAirBlock(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())))
            {
                worldIn.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), NContent.netherBerryBush.getStateFromMeta(stack.getItemDamage() % 4), 3);
                if (!playerIn.capabilities.isCreativeMode)
                    stack.stackSize--;
                if (!worldIn.isRemote)
                    worldIn.playAuxSFX(2001, pos, Block.getIdFromBlock(NContent.netherBerryBush));
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    /* Block name in inventory */
    /*   @Override
       public String getUnlocalizedName (ItemStack itemstack)
       {
           return (new StringBuilder()).append("block.bush.berry.").append(blockType[itemstack.getItemDamage()]).toString();
       }*/

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.netherberrybush1"));
        switch (stack.getItemDamage() % 4)
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.netherberrybush2"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.netherberrybush3"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.netherberrybush4"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.netherberrybush5"));
            break;
        }
    }
}
