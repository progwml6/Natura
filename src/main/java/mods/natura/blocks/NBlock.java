package mods.natura.blocks;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NBlock extends Block
{
    public String[] textureNames;
    public static PropertyInteger METADATA = PropertyInteger.create("Metadata", 0, 15);

    public NBlock(Material material, float hardness, String[] tex)
    {
        super(material);
        setHardness(hardness);
        this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
        this.setCreativeTab(NaturaTab.tab);
        textureNames = tex;
    }

    @Override
    public int damageDropped (IBlockState meta)
    {
        return this.getMetaFromState(meta);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta (int meta)
    {
        return this.getDefaultState().withProperty(METADATA, Integer.valueOf(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState (IBlockState state)
    {
        return ((Integer) state.getValue(METADATA)).intValue();
    }

    protected BlockState createBlockState ()
    {
        return new BlockState(this, new IProperty[] { METADATA });
    }

    @Override
    public void getSubBlocks (Item id, CreativeTabs tab, List list)
    {
        for (int iter = 0; iter < textureNames.length; iter++)
        {
            list.add(new ItemStack(id, 1, iter));
        }
    }
}
