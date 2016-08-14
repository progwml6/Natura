package mods.natura.blocks.trees;

import mods.natura.worldgen.RareTreeGen;
import mods.natura.worldgen.WillowGen;
import net.minecraft.world.gen.feature.WorldGenerator;

public class RareSaplingBlock extends NSaplingBlock {
    public RareSaplingBlock() {
        super(EnumSaplingType.rareSaplings);
    }

    @Override
    public WorldGenerator getWorldGenerator(EnumSaplingType saplingType) {
        switch (saplingType) {
            case MAPLE:
                return new RareTreeGen(true, 4, 2, 0, 0);
            case SILVERBELL:
                return new RareTreeGen(true, 4, 2, 1, 1);
            case PURPLEHEART:
                return new RareTreeGen(true, 9, 8, 2, 2);
            case TIGER:
                return new RareTreeGen(true, 6, 4, 3, 3);
            case WILLOW:
                return new WillowGen(true);
            default:
                throw new RuntimeException("Wrong sapling type: " + saplingType);
        }
    }
}
