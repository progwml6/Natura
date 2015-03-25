package mods.natura.items.blocks;

import java.util.List;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NDoorItem extends Item
{
    public IIcon[] icons;
    public String[] textureNames = new String[] { "redwood", "eucalyptus", "hopseed", "sakura", "ghostwood", "bloodwood", "redwoodbark" };

    public NDoorItem()
    {
        super();
        maxStackSize = 64;
        setCreativeTab(NaturaTab.tab);
        setHasSubtypes(true);
    }

    public static final String unlocalizedNames[] = { "redwood", "eucalyptus", "hopseed", "sakura", "ghost", "blood", "redwoodBark" };

    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append(unlocalizedNames[itemstack.getItemDamage()]).append("NDoor").toString();
    }

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if (side != EnumFacing.UP)
        {
            return false;
        }
    	
    	IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos))
        {
            pos = pos.offset(side);
        }

        switch (stack.getItemDamage())
        {
        case 0:
            block = NContent.redwoodDoor;
            break;
        case 1:
            block = NContent.eucalyptusDoor;
            break;
        case 2:
            block = NContent.hopseedDoor;
            break;
        case 3:
            block = NContent.sakuraDoor;
            break;
        case 4:
            block = NContent.ghostDoor;
            break;
        case 5:
            block = NContent.bloodDoor;
            break;
        case 6:
            block = NContent.redwoodBarkDoor;
            break;
        default:
            block = Blocks.oak_door;
            break;
        }
        if (!playerIn.canPlayerEdit(pos, side, stack))
        {
            return false;
        }
        else if (!block.canPlaceBlockAt(worldIn, pos))
        {
            return false;
        }
        else
        {
        	placeDoor(worldIn, pos, EnumFacing.fromAngle((double)playerIn.rotationYaw), block);
            --stack.stackSize;
            return true;
        }
    }

    public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door)
    {
        BlockPos blockpos1 = pos.offset(facing.rotateY());
        BlockPos blockpos2 = pos.offset(facing.rotateYCCW());
        int i = (worldIn.getBlockState(blockpos2).getBlock().isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos2.up()).getBlock().isNormalCube() ? 1 : 0);
        int j = (worldIn.getBlockState(blockpos1).getBlock().isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).getBlock().isNormalCube() ? 1 : 0);
        boolean flag = worldIn.getBlockState(blockpos2).getBlock() == door || worldIn.getBlockState(blockpos2.up()).getBlock() == door;
        boolean flag1 = worldIn.getBlockState(blockpos1).getBlock() == door || worldIn.getBlockState(blockpos1.up()).getBlock() == door;
        boolean flag2 = false;

        if (flag && !flag1 || j > i)
        {
            flag2 = true;
        }

        BlockPos blockpos3 = pos.up();
        IBlockState iblockstate = door.getDefaultState().withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HINGE, flag2 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT);
        worldIn.setBlockState(pos, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
        worldIn.setBlockState(blockpos3, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
        worldIn.notifyNeighborsOfStateChange(pos, door);
        worldIn.notifyNeighborsOfStateChange(blockpos3, door);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_door_item");
        }
    }

    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < unlocalizedNames.length; i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.tree4"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.tree1"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.tree6"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.tree2"));
            break;
        case 4:
            list.add(StatCollector.translateToLocal("tooltip.tree3"));
            break;
        case 5:
            list.add(StatCollector.translateToLocal("tooltip.firedoor"));
            break;
        case 6:
            list.add(StatCollector.translateToLocal("tooltip.barkdoor"));
            break;
        }
    }
}
