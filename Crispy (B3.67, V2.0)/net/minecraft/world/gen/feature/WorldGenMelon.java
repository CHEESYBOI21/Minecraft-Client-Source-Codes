package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenMelon extends WorldGenerator
{

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int var4 = 0; var4 < 64; ++var4)
        {
            BlockPos var5 = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (Blocks.melon_block.canPlaceBlockAt(worldIn, var5) && worldIn.getBlockState(var5.down()).getBlock() == Blocks.grass)
            {
                worldIn.setBlockState(var5, Blocks.melon_block.getDefaultState(), 2);
            }
        }

        return true;
    }
}
