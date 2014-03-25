package mods.natura.plugins.fmp;

import mods.natura.Natura;
import mods.natura.common.NContent;
import mods.natura.plugins.fmp.register.RegisterWithFMP;

public class ForgeMultiPart implements ICompatPlugin
{
    @Override
    public String getModId() {
        return "ForgeMultipart";
    }

    @Override
    public void preInit() {

    }

    @Override
    public void init()
    {
        try
        {
            Natura.logger.fine("[FMP] Registering Natura decorative blocks with FMP.");
            RegisterWithFMP.registerBlock(NContent.bloodwood);
            RegisterWithFMP.registerBlock(NContent.willow);
            RegisterWithFMP.registerBlock(NContent.planks, 0, 12);
            RegisterWithFMP.registerBlock(NContent.tree, 0, 3);
            RegisterWithFMP.registerBlock(NContent.rareTree, 0, 3);
            RegisterWithFMP.registerBlock(NContent.rareLeaves, 0, 3);
            RegisterWithFMP.registerBlock(NContent.darkLeaves, 0, 3);
            RegisterWithFMP.registerBlock(NContent.redwood, 0, 2);
            RegisterWithFMP.registerBlock(NContent.floraLeaves, 0, 2);
            RegisterWithFMP.registerBlock(NContent.floraLeavesNoColor, 0, 2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void postInit() {

    }

}
