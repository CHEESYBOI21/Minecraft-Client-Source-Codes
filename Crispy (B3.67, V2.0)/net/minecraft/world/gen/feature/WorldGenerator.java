package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenerator
{
    /**
     * Sets wither or not the generator should notify blocks of blocks it changes. When the world is first generated,
     * this is false, when saplings grow, this is true.
     */
    private final boolean doBlockNotify;

    public WorldGenerator()
    {
        this(false);
    }

    public WorldGenerator(boolean notify)
    {
        this.doBlockNotify = notify;
    }

    public abstract boolean generate(World var1, Random var2, BlockPos var3);

    public void func_175904_e() {}

    protected void setBlock(World worldIn, BlockPos p_175906_2_, Block p_175906_3_)
    {
        this.setBlock(worldIn, p_175906_2_, p_175906_3_, 0);
    }

    protected void setBlock(World worldIn, BlockPos p_175905_2_, Block p_175905_3_, int p_175905_4_)
    {
        this.setBlockAndNotifyAdequately(worldIn, p_175905_2_, p_175905_3_.getStateFromMeta(p_175905_4_));
    }

    protected void setBlockAndNotifyAdequately(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.doBlockNotify)
        {
            worldIn.setBlockState(pos, state, 3);
        }
        else
        {
            worldIn.setBlockState(pos, state, 2);
        }
    }
}
