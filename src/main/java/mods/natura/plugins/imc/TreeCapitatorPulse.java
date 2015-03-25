package mods.natura.plugins.imc;

import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameData;

@Pulse(id = "Natura TreeCapitator Compatibility", modsRequired = TreeCapitatorPulse.modId)
public class TreeCapitatorPulse
{

    public static final String modId = "Treecapitator";

    @Handler
    public void init (FMLInitializationEvent evt)
    {
        NBTTagCompound tpModCfg = new NBTTagCompound();
        tpModCfg.setString("modID", "Natura");
        tpModCfg.setString("axeIDList", String.format("%s; %s; %s; %s; %s", getUniqueName(NContent.ghostwoodAxe), getUniqueName(NContent.bloodwoodAxe), getUniqueName(NContent.darkwoodAxe),
                getUniqueName(NContent.fusewoodAxe), getUniqueName(NContent.netherquartzAxe)));
        tpModCfg.setBoolean("useShiftedItemID", false);

        NBTTagList treeList = new NBTTagList();

        // amaranth
        NBTTagCompound treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "amaranth");
        treeDef.setString("logs", String.format("%s,2; %s,6; %s,10", getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree)));
        treeDef.setString("leaves", String.format("%s,2; %s,10", getUniqueName(NContent.rareLeaves), getUniqueName(NContent.rareLeaves)));
        treeList.appendTag(treeDef);
        // bloodwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "bloodwood");
        treeDef.setString("logs", String.format("%s", getUniqueName(NContent.bloodwood)));
        treeDef.setString("leaves", String.format("%s,2; %s,10", getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor)));
        treeList.appendTag(treeDef);
        // darkwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "darkwood");
        treeDef.setString("logs", String.format("%s,0; %s,4; %s,8", getUniqueName(NContent.darkTree), getUniqueName(NContent.darkTree), getUniqueName(NContent.darkTree)));
        treeDef.setString("leaves", String.format("%s", getUniqueName(NContent.darkLeaves)));
        treeList.appendTag(treeDef);
        // eucalyptus
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "eucalyptus");
        treeDef.setString("logs", String.format("%s,0; %s,4; %s,8", getUniqueName(NContent.tree), getUniqueName(NContent.tree), getUniqueName(NContent.tree)));
        treeDef.setString("leaves", String.format("%s,1; %s,9", getUniqueName(NContent.floraLeaves), getUniqueName(NContent.floraLeaves)));
        treeList.appendTag(treeDef);
        // ghostwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "ghostwood");
        treeDef.setString("logs", String.format("%s,2; %s,6; %s,10", getUniqueName(NContent.tree), getUniqueName(NContent.tree), getUniqueName(NContent.tree)));
        treeDef.setString("leaves", String.format("%s,1; %s,9", getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor)));
        treeList.appendTag(treeDef);
        // hopseed
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "hopseed");
        treeDef.setString("logs", String.format("%s,3; %s,7; %s,11", getUniqueName(NContent.tree), getUniqueName(NContent.tree), getUniqueName(NContent.tree)));
        treeDef.setString("leaves", String.format("%s,2; %s,10", getUniqueName(NContent.floraLeaves), getUniqueName(NContent.floraLeaves)));
        treeList.appendTag(treeDef);
        // maple
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "maple");
        treeDef.setString("logs", String.format("%s,0; %s,4; %s,8", getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree)));
        treeDef.setString("leaves", String.format("%s,0; %s,8", getUniqueName(NContent.rareLeaves), getUniqueName(NContent.rareLeaves)));
        treeList.appendTag(treeDef);
        // redwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "redwood");
        treeDef.setString("logs", String.format("%s", getUniqueName(NContent.redwood)));
        treeDef.setString("leaves", String.format("%s,0; %s,8", getUniqueName(NContent.floraLeaves), getUniqueName(NContent.floraLeaves)));
        treeList.appendTag(treeDef);
        // sakura
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "sakura");
        treeDef.setString("logs", String.format("%s,1; %s,5; %s,9", getUniqueName(NContent.tree), getUniqueName(NContent.tree), getUniqueName(NContent.tree)));
        treeDef.setString("leaves", String.format("%s,0; %s,8", getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor)));
        treeList.appendTag(treeDef);
        // siverbell
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "siverbell");
        treeDef.setString("logs", String.format("%s,1; %s,5; %s,9", getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree)));
        treeDef.setString("leaves", String.format("%s,1; %s,9", getUniqueName(NContent.rareLeaves), getUniqueName(NContent.rareLeaves)));
        treeList.appendTag(treeDef);
        // tigerwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "tigerwood");
        treeDef.setString("logs", String.format("%s,3; %s,7; %s,11", getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree)));
        treeDef.setString("leaves", String.format("%s,3; %s,11", getUniqueName(NContent.rareLeaves), getUniqueName(NContent.rareLeaves)));
        treeList.appendTag(treeDef);
        // willow
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "willow");
        treeDef.setString("logs", String.format("%s", getUniqueName(NContent.willow)));
        treeDef.setString("leaves", String.format("%s,3; %s,7; %s,11; %s,15", getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor),
                getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor)));
        treeDef.setInteger("maxHorLeafBreakDist", 5);
        treeList.appendTag(treeDef);

        tpModCfg.setTag("trees", treeList);

        FMLInterModComms.sendMessage(modId, "ThirdPartyModConfig", tpModCfg);

    }

    public static String getUniqueName (Block block)
    {
        return GameData.getBlockRegistry().getNameForObject(block).toString();
    }

    public static String getUniqueName (Item item)
    {
        return GameData.getItemRegistry().getNameForObject(item).toString();
    }
}
