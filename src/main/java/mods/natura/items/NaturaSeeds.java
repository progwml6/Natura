package mods.natura.items;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NaturaSeeds extends ItemSeeds
{
    public String[] textureNames = new String[] { "barley", "cotton" };

    public Block blockType;

    public NaturaSeeds(Block cropID, Block soilID)
    {
        super(cropID, soilID);
        blockType = cropID;
        this.setCreativeTab(NaturaTab.tab);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < textureNames.length; i++)
            list.add(new ItemStack(id, 1, i));
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
        else if (worldIn.getBlockState(pos).getBlock().canSustainPlant(worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up()))
        {
            worldIn.setBlockState(pos.up(), this.blockType.getDefaultState());
            --stack.stackSize;
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String getUnlocalizedName (ItemStack stack)
    {
        int arr = MathHelper.clamp_int(stack.getItemDamage(), 0, textureNames.length);
        return "item." + textureNames[arr] + ".seed";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.barley"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.cotton"));
            break;
        }
    }
}
