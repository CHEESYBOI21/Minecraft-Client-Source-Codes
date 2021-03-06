package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenForest extends WorldGenAbstractTree
{
    private boolean useExtraRandomHeight;

    public WorldGenForest(boolean p_i45449_1_, boolean p_i45449_2_)
    {
        super(p_i45449_1_);
        this.useExtraRandomHeight = p_i45449_2_;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int var4 = rand.nextInt(3) + 5;

        if (this.useExtraRandomHeight)
        {
            var4 += rand.nextInt(7);
        }

        boolean var5 = true;

        if (position.getY() >= 1 && position.getY() + var4 + 1 <= 256)
        {
            int var8;
            int var9;

            for (int var6 = position.getY(); var6 <= position.getY() + 1 + var4; ++var6)
            {
                byte var7 = 1;

                if (var6 == position.getY())
                {
                    var7 = 0;
                }

                if (var6 >= position.getY() + 1 + var4 - 2)
                {
                    var7 = 2;
                }

                for (var8 = position.getX() - var7; var8 <= position.getX() + var7 && var5; ++var8)
                {
                    for (var9 = position.getZ() - var7; var9 <= position.getZ() + var7 && var5; ++var9)
                    {
                        if (var6 >= 0 && var6 < 256)
                        {
                            if (!this.func_150523_a(worldIn.getBlockState(new BlockPos(var8, var6, var9)).getBlock()))
                            {
                                var5 = false;
                            }
                        }
                        else
                        {
                            var5 = false;
                        }
                    }
                }
            }

            if (!var5)
            {
                return false;
            }
            else
            {
                Block var16 = worldIn.getBlockState(position.down()).getBlock();

                if ((var16 == Blocks.grass || var16 == Blocks.dirt || var16 == Blocks.farmland) && position.getY() < 256 - var4 - 1)
                {
                    this.func_175921_a(worldIn, position.down());
                    int var17;

                    for (var17 = position.getY() - 3 + var4; var17 <= position.getY() + var4; ++var17)
                    {
                        var8 = var17 - (position.getY() + var4);
                        var9 = 1 - var8 / 2;

                        for (int var10 = position.getX() - var9; var10 <= position.getX() + var9; ++var10)
                        {
                            int var11 = var10 - position.getX();

                            for (int var12 = position.getZ() - var9; var12 <= position.getZ() + var9; ++var12)
                            {
                                int var13 = var12 - position.getZ();

                                if (Math.abs(var11) != var9 || Math.abs(var13) != var9 || rand.nextInt(2) != 0 && var8 != 0)
                                {
                                    BlockPos var14 = new BlockPos(var10, var17, var12);
                                    Block var15 = worldIn.getBlockState(var14).getBlock();

                                    if (var15.getMaterial() == Material.air || var15.getMaterial() == Material.leaves)
                                    {
                                        this.setBlock(worldIn, var14, Blocks.leaves, BlockPlanks.EnumType.BIRCH.getMetadata());
                                    }
                                }
                            }
                        }
                    }

                    for (var17 = 0; var17 < var4; ++var17)
                    {
                        Block var18 = worldIn.getBlockState(position.up(var17)).getBlock();

                        if (var18.getMaterial() == Material.air || var18.getMaterial() == Material.leaves)
                        {
                            this.setBlock(worldIn, position.up(var17), Blocks.log, BlockPlanks.EnumType.BIRCH.getMetadata());
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
