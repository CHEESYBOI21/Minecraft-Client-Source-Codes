package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class StructureVillagePieces
{

    public static void registerVillagePieces()
    {
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House1.class, "ViBH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Field1.class, "ViDF");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Field2.class, "ViF");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Torch.class, "ViL");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Hall.class, "ViPH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House4Garden.class, "ViSH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.WoodHut.class, "ViSmH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Church.class, "ViST");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House2.class, "ViS");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Start.class, "ViStart");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Path.class, "ViSR");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House3.class, "ViTRH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Well.class, "ViW");
    }

    public static List getStructureVillageWeightedPieceList(Random random, int p_75084_1_)
    {
        ArrayList var2 = Lists.newArrayList();
        var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House4Garden.class, 4, MathHelper.getRandomIntegerInRange(random, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
        var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Church.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + p_75084_1_, 1 + p_75084_1_)));
        var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House1.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + p_75084_1_, 2 + p_75084_1_)));
        var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.WoodHut.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + p_75084_1_, 5 + p_75084_1_ * 3)));
        var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Hall.class, 15, MathHelper.getRandomIntegerInRange(random, 0 + p_75084_1_, 2 + p_75084_1_)));
        var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Field1.class, 3, MathHelper.getRandomIntegerInRange(random, 1 + p_75084_1_, 4 + p_75084_1_)));
        var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Field2.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
        var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House2.class, 15, MathHelper.getRandomIntegerInRange(random, 0, 1 + p_75084_1_)));
        var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House3.class, 8, MathHelper.getRandomIntegerInRange(random, 0 + p_75084_1_, 3 + p_75084_1_ * 2)));
        Iterator var3 = var2.iterator();

        while (var3.hasNext())
        {
            if (((StructureVillagePieces.PieceWeight)var3.next()).villagePiecesLimit == 0)
            {
                var3.remove();
            }
        }

        return var2;
    }

    private static int func_75079_a(List p_75079_0_)
    {
        boolean var1 = false;
        int var2 = 0;
        StructureVillagePieces.PieceWeight var4;

        for (Iterator var3 = p_75079_0_.iterator(); var3.hasNext(); var2 += var4.villagePieceWeight)
        {
            var4 = (StructureVillagePieces.PieceWeight)var3.next();

            if (var4.villagePiecesLimit > 0 && var4.villagePiecesSpawned < var4.villagePiecesLimit)
            {
                var1 = true;
            }
        }

        return var1 ? var2 : -1;
    }

    private static StructureVillagePieces.Village func_176065_a(StructureVillagePieces.Start start, StructureVillagePieces.PieceWeight weight, List p_176065_2_, Random rand, int p_176065_4_, int p_176065_5_, int p_176065_6_, EnumFacing facing, int p_176065_8_)
    {
        Class var9 = weight.villagePieceClass;
        Object var10 = null;

        if (var9 == StructureVillagePieces.House4Garden.class)
        {
            var10 = StructureVillagePieces.House4Garden.func_175858_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
        }
        else if (var9 == StructureVillagePieces.Church.class)
        {
            var10 = StructureVillagePieces.Church.func_175854_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
        }
        else if (var9 == StructureVillagePieces.House1.class)
        {
            var10 = StructureVillagePieces.House1.func_175850_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
        }
        else if (var9 == StructureVillagePieces.WoodHut.class)
        {
            var10 = StructureVillagePieces.WoodHut.func_175853_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
        }
        else if (var9 == StructureVillagePieces.Hall.class)
        {
            var10 = StructureVillagePieces.Hall.func_175857_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
        }
        else if (var9 == StructureVillagePieces.Field1.class)
        {
            var10 = StructureVillagePieces.Field1.func_175851_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
        }
        else if (var9 == StructureVillagePieces.Field2.class)
        {
            var10 = StructureVillagePieces.Field2.func_175852_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
        }
        else if (var9 == StructureVillagePieces.House2.class)
        {
            var10 = StructureVillagePieces.House2.func_175855_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
        }
        else if (var9 == StructureVillagePieces.House3.class)
        {
            var10 = StructureVillagePieces.House3.func_175849_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
        }

        return (StructureVillagePieces.Village)var10;
    }

    private static StructureVillagePieces.Village func_176067_c(StructureVillagePieces.Start start, List p_176067_1_, Random rand, int p_176067_3_, int p_176067_4_, int p_176067_5_, EnumFacing facing, int p_176067_7_)
    {
        int var8 = func_75079_a(start.structureVillageWeightedPieceList);

        if (var8 <= 0)
        {
            return null;
        }
        else
        {
            int var9 = 0;

            while (var9 < 5)
            {
                ++var9;
                int var10 = rand.nextInt(var8);
                Iterator var11 = start.structureVillageWeightedPieceList.iterator();

                while (var11.hasNext())
                {
                    StructureVillagePieces.PieceWeight var12 = (StructureVillagePieces.PieceWeight)var11.next();
                    var10 -= var12.villagePieceWeight;

                    if (var10 < 0)
                    {
                        if (!var12.canSpawnMoreVillagePiecesOfType(p_176067_7_) || var12 == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > 1)
                        {
                            break;
                        }

                        StructureVillagePieces.Village var13 = func_176065_a(start, var12, p_176067_1_, rand, p_176067_3_, p_176067_4_, p_176067_5_, facing, p_176067_7_);

                        if (var13 != null)
                        {
                            ++var12.villagePiecesSpawned;
                            start.structVillagePieceWeight = var12;

                            if (!var12.canSpawnMoreVillagePieces())
                            {
                                start.structureVillageWeightedPieceList.remove(var12);
                            }

                            return var13;
                        }
                    }
                }
            }

            StructureBoundingBox var14 = StructureVillagePieces.Torch.func_175856_a(start, p_176067_1_, rand, p_176067_3_, p_176067_4_, p_176067_5_, facing);

            if (var14 != null)
            {
                return new StructureVillagePieces.Torch(start, p_176067_7_, rand, var14, facing);
            }
            else
            {
                return null;
            }
        }
    }

    private static StructureComponent func_176066_d(StructureVillagePieces.Start start, List p_176066_1_, Random rand, int p_176066_3_, int p_176066_4_, int p_176066_5_, EnumFacing facing, int p_176066_7_)
    {
        if (p_176066_7_ > 50)
        {
            return null;
        }
        else if (Math.abs(p_176066_3_ - start.getBoundingBox().minX) <= 112 && Math.abs(p_176066_5_ - start.getBoundingBox().minZ) <= 112)
        {
            StructureVillagePieces.Village var8 = func_176067_c(start, p_176066_1_, rand, p_176066_3_, p_176066_4_, p_176066_5_, facing, p_176066_7_ + 1);

            if (var8 != null)
            {
                int var9 = (var8.boundingBox.minX + var8.boundingBox.maxX) / 2;
                int var10 = (var8.boundingBox.minZ + var8.boundingBox.maxZ) / 2;
                int var11 = var8.boundingBox.maxX - var8.boundingBox.minX;
                int var12 = var8.boundingBox.maxZ - var8.boundingBox.minZ;
                int var13 = var11 > var12 ? var11 : var12;

                if (start.getWorldChunkManager().areBiomesViable(var9, var10, var13 / 2 + 4, MapGenVillage.villageSpawnBiomes))
                {
                    p_176066_1_.add(var8);
                    start.field_74932_i.add(var8);
                    return var8;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    private static StructureComponent func_176069_e(StructureVillagePieces.Start start, List p_176069_1_, Random rand, int p_176069_3_, int p_176069_4_, int p_176069_5_, EnumFacing facing, int p_176069_7_)
    {
        if (p_176069_7_ > 3 + start.terrainType)
        {
            return null;
        }
        else if (Math.abs(p_176069_3_ - start.getBoundingBox().minX) <= 112 && Math.abs(p_176069_5_ - start.getBoundingBox().minZ) <= 112)
        {
            StructureBoundingBox var8 = StructureVillagePieces.Path.func_175848_a(start, p_176069_1_, rand, p_176069_3_, p_176069_4_, p_176069_5_, facing);

            if (var8 != null && var8.minY > 10)
            {
                StructureVillagePieces.Path var9 = new StructureVillagePieces.Path(start, p_176069_7_, rand, var8, facing);
                int var10 = (var9.boundingBox.minX + var9.boundingBox.maxX) / 2;
                int var11 = (var9.boundingBox.minZ + var9.boundingBox.maxZ) / 2;
                int var12 = var9.boundingBox.maxX - var9.boundingBox.minX;
                int var13 = var9.boundingBox.maxZ - var9.boundingBox.minZ;
                int var14 = var12 > var13 ? var12 : var13;

                if (start.getWorldChunkManager().areBiomesViable(var10, var11, var14 / 2 + 4, MapGenVillage.villageSpawnBiomes))
                {
                    p_176069_1_.add(var9);
                    start.field_74930_j.add(var9);
                    return var9;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    public static class Church extends StructureVillagePieces.Village
    {

        public Church() {}

        public Church(StructureVillagePieces.Start start, int p_i45564_2_, Random rand, StructureBoundingBox p_i45564_4_, EnumFacing facing)
        {
            super(start, p_i45564_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45564_4_;
        }

        public static StructureVillagePieces.Church func_175854_a(StructureVillagePieces.Start start, List p_175854_1_, Random rand, int p_175854_3_, int p_175854_4_, int p_175854_5_, EnumFacing facing, int p_175854_7_)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, facing);
            return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175854_1_, var8) == null ? new StructureVillagePieces.Church(start, p_175854_7_, rand, var8, facing) : null;
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 11, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 11, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 1, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 1, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 3, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 1)), 1, 2, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 0)), 3, 2, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 6, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 7, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 6, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 7, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 3, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 2, 4, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateY()), 1, 4, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateYCCW()), 3, 4, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 4, 5, structureBoundingBoxIn);
            int var4 = this.getMetadataWithOffset(Blocks.ladder, 4);
            int var5;

            for (var5 = 1; var5 <= 9; ++var5)
            {
                this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, var5, 3, structureBoundingBoxIn);
            }

            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
            this.placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));

            if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
            {
                this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
            }

            for (var5 = 0; var5 < 9; ++var5)
            {
                for (int var6 = 0; var6 < 5; ++var6)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var6, 12, var5, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), var6, -1, var5, structureBoundingBoxIn);
                }
            }

            this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
            return true;
        }

        protected int func_180779_c(int p_180779_1_, int p_180779_2_)
        {
            return 2;
        }
    }

    public static class Field1 extends StructureVillagePieces.Village
    {
        private Block cropTypeA;
        private Block cropTypeB;
        private Block cropTypeC;
        private Block cropTypeD;

        public Field1() {}

        public Field1(StructureVillagePieces.Start start, int p_i45570_2_, Random rand, StructureBoundingBox p_i45570_4_, EnumFacing facing)
        {
            super(start, p_i45570_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45570_4_;
            this.cropTypeA = this.func_151559_a(rand);
            this.cropTypeB = this.func_151559_a(rand);
            this.cropTypeC = this.func_151559_a(rand);
            this.cropTypeD = this.func_151559_a(rand);
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
            tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
            tagCompound.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
            tagCompound.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound)
        {
            super.readStructureFromNBT(tagCompound);
            this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
            this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
            this.cropTypeC = Block.getBlockById(tagCompound.getInteger("CC"));
            this.cropTypeD = Block.getBlockById(tagCompound.getInteger("CD"));
        }

        private Block func_151559_a(Random rand)
        {
            switch (rand.nextInt(5))
            {
                case 0:
                    return Blocks.carrots;

                case 1:
                    return Blocks.potatoes;

                default:
                    return Blocks.wheat;
            }
        }

        public static StructureVillagePieces.Field1 func_175851_a(StructureVillagePieces.Start start, List p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, EnumFacing facing, int p_175851_7_)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, facing);
            return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175851_1_, var8) == null ? new StructureVillagePieces.Field1(start, p_175851_7_, rand, var8, facing) : null;
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
            int var4;

            for (var4 = 1; var4 <= 7; ++var4)
            {
                this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 1, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 2, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 4, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 5, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 7, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 8, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 10, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 11, 1, var4, structureBoundingBoxIn);
            }

            for (var4 = 0; var4 < 9; ++var4)
            {
                for (int var5 = 0; var5 < 13; ++var5)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var5, 4, var4, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.dirt.getDefaultState(), var5, -1, var4, structureBoundingBoxIn);
                }
            }

            return true;
        }
    }

    public static class Field2 extends StructureVillagePieces.Village
    {
        private Block cropTypeA;
        private Block cropTypeB;

        public Field2() {}

        public Field2(StructureVillagePieces.Start start, int p_i45569_2_, Random rand, StructureBoundingBox p_i45569_4_, EnumFacing facing)
        {
            super(start, p_i45569_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45569_4_;
            this.cropTypeA = this.func_151560_a(rand);
            this.cropTypeB = this.func_151560_a(rand);
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
            tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound)
        {
            super.readStructureFromNBT(tagCompound);
            this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
            this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
        }

        private Block func_151560_a(Random rand)
        {
            switch (rand.nextInt(5))
            {
                case 0:
                    return Blocks.carrots;

                case 1:
                    return Blocks.potatoes;

                default:
                    return Blocks.wheat;
            }
        }

        public static StructureVillagePieces.Field2 func_175852_a(StructureVillagePieces.Start start, List p_175852_1_, Random rand, int p_175852_3_, int p_175852_4_, int p_175852_5_, EnumFacing facing, int p_175852_7_)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175852_3_, p_175852_4_, p_175852_5_, 0, 0, 0, 7, 4, 9, facing);
            return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175852_1_, var8) == null ? new StructureVillagePieces.Field2(start, p_175852_7_, rand, var8, facing) : null;
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 6, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 5, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
            int var4;

            for (var4 = 1; var4 <= 7; ++var4)
            {
                this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 1, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 2, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 4, 1, var4, structureBoundingBoxIn);
                this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 5, 1, var4, structureBoundingBoxIn);
            }

            for (var4 = 0; var4 < 9; ++var4)
            {
                for (int var5 = 0; var5 < 7; ++var5)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var5, 4, var4, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.dirt.getDefaultState(), var5, -1, var4, structureBoundingBoxIn);
                }
            }

            return true;
        }
    }

    public static class Hall extends StructureVillagePieces.Village
    {

        public Hall() {}

        public Hall(StructureVillagePieces.Start start, int p_i45567_2_, Random rand, StructureBoundingBox p_i45567_4_, EnumFacing facing)
        {
            super(start, p_i45567_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45567_4_;
        }

        public static StructureVillagePieces.Hall func_175857_a(StructureVillagePieces.Start start, List p_175857_1_, Random rand, int p_175857_3_, int p_175857_4_, int p_175857_5_, EnumFacing facing, int p_175857_7_)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175857_3_, p_175857_4_, p_175857_5_, 0, 0, 0, 9, 7, 11, facing);
            return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175857_1_, var8) == null ? new StructureVillagePieces.Hall(start, p_175857_7_, rand, var8, facing) : null;
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 8, 0, 10, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 0, 6, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 6, 8, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 10, 7, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
            int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
            int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
            int var6;
            int var7;

            for (var6 = -1; var6 <= 2; ++var6)
            {
                for (var7 = 0; var7 <= 8; ++var7)
                {
                    this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var4), var7, 4 + var6, var6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var5), var7, 4 + var6, 5 - var6, structureBoundingBoxIn);
                }
            }

            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 3, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
            this.placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));

            if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
            {
                this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
            }

            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 6, 3, 4, structureBoundingBoxIn);
            this.placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 6, 1, 5, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));

            for (var6 = 0; var6 < 5; ++var6)
            {
                for (var7 = 0; var7 < 9; ++var7)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var7, 7, var6, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), var7, -1, var6, structureBoundingBoxIn);
                }
            }

            this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
            return true;
        }

        protected int func_180779_c(int p_180779_1_, int p_180779_2_)
        {
            return p_180779_1_ == 0 ? 4 : super.func_180779_c(p_180779_1_, p_180779_2_);
        }
    }

    public static class House1 extends StructureVillagePieces.Village
    {

        public House1() {}

        public House1(StructureVillagePieces.Start start, int p_i45571_2_, Random rand, StructureBoundingBox p_i45571_4_, EnumFacing facing)
        {
            super(start, p_i45571_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45571_4_;
        }

        public static StructureVillagePieces.House1 func_175850_a(StructureVillagePieces.Start start, List p_175850_1_, Random rand, int p_175850_3_, int p_175850_4_, int p_175850_5_, EnumFacing facing, int p_175850_7_)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, facing);
            return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175850_1_, var8) == null ? new StructureVillagePieces.House1(start, p_175850_7_, rand, var8, facing) : null;
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
            int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
            int var6;
            int var7;

            for (var6 = -1; var6 <= 2; ++var6)
            {
                for (var7 = 0; var7 <= 8; ++var7)
                {
                    this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var4), var7, 6 + var6, var6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var5), var7, 6 + var6, 5 - var6, structureBoundingBoxIn);
                }
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 0)), 7, 1, 3, structureBoundingBoxIn);
            var6 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), 6, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), 5, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), 4, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), 3, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 4, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.crafting_table.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
            this.placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));

            if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
            {
                this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBoxIn);
            }

            for (var7 = 0; var7 < 6; ++var7)
            {
                for (int var8 = 0; var8 < 9; ++var8)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var8, 9, var7, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), var8, -1, var7, structureBoundingBoxIn);
                }
            }

            this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
            return true;
        }

        protected int func_180779_c(int p_180779_1_, int p_180779_2_)
        {
            return 1;
        }
    }

    public static class House2 extends StructureVillagePieces.Village
    {
        private static final List villageBlacksmithChestContents = Lists.newArrayList(new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)});
        private boolean hasMadeChest;

        public House2() {}

        public House2(StructureVillagePieces.Start start, int p_i45563_2_, Random rand, StructureBoundingBox p_i45563_4_, EnumFacing facing)
        {
            super(start, p_i45563_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45563_4_;
        }

        public static StructureVillagePieces.House2 func_175855_a(StructureVillagePieces.Start start, List p_175855_1_, Random rand, int p_175855_3_, int p_175855_4_, int p_175855_5_, EnumFacing facing, int p_175855_7_)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, facing);
            return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175855_1_, var8) == null ? new StructureVillagePieces.House2(start, p_175855_7_, rand, var8, facing) : null;
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Chest", this.hasMadeChest);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound)
        {
            super.readStructureFromNBT(tagCompound);
            this.hasMadeChest = tagCompound.getBoolean("Chest");
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 0, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 4, 6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, 1, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 3, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 5, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 6, 5, 3, 6, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 5, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 9, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 7, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 8, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 4, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.furnace.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.furnace.getDefaultState(), 6, 3, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 8, 1, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 4, structureBoundingBoxIn);

            if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5))))
            {
                this.hasMadeChest = true;
                this.generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 5, 1, 5, villageBlacksmithChestContents, 3 + randomIn.nextInt(6));
            }

            int var4;

            for (var4 = 6; var4 <= 8; ++var4)
            {
                if (this.getBlockStateFromPos(worldIn, var4, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(worldIn, var4, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
                {
                    this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), var4, 0, -1, structureBoundingBoxIn);
                }
            }

            for (var4 = 0; var4 < 7; ++var4)
            {
                for (int var5 = 0; var5 < 10; ++var5)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var5, 6, var4, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), var5, -1, var4, structureBoundingBoxIn);
                }
            }

            this.spawnVillagers(worldIn, structureBoundingBoxIn, 7, 1, 1, 1);
            return true;
        }

        protected int func_180779_c(int p_180779_1_, int p_180779_2_)
        {
            return 3;
        }
    }

    public static class House3 extends StructureVillagePieces.Village
    {

        public House3() {}

        public House3(StructureVillagePieces.Start start, int p_i45561_2_, Random rand, StructureBoundingBox p_i45561_4_, EnumFacing facing)
        {
            super(start, p_i45561_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45561_4_;
        }

        public static StructureVillagePieces.House3 func_175849_a(StructureVillagePieces.Start start, List p_175849_1_, Random rand, int p_175849_3_, int p_175849_4_, int p_175849_5_, EnumFacing facing, int p_175849_7_)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175849_3_, p_175849_4_, p_175849_5_, 0, 0, 0, 9, 7, 12, facing);
            return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175849_1_, var8) == null ? new StructureVillagePieces.House3(start, p_175849_7_, rand, var8, facing) : null;
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 4, structureBoundingBoxIn);
            int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
            int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
            int var6;
            int var7;

            for (var6 = -1; var6 <= 2; ++var6)
            {
                for (var7 = 0; var7 <= 8; ++var7)
                {
                    this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var4), var7, 4 + var6, var6, structureBoundingBoxIn);

                    if ((var6 > -1 || var7 <= 1) && (var6 > 0 || var7 <= 3) && (var6 > 1 || var7 <= 4 || var7 >= 6))
                    {
                        this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var5), var7, 4 + var6, 5 - var6, structureBoundingBoxIn);
                    }
                }
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            var6 = this.getMetadataWithOffset(Blocks.oak_stairs, 0);
            int var8;

            for (var7 = 4; var7 >= 1; --var7)
            {
                this.setBlockState(worldIn, Blocks.planks.getDefaultState(), var7, 2 + var7, 7 - var7, structureBoundingBoxIn);

                for (var8 = 8 - var7; var8 <= 10; ++var8)
                {
                    this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), var7, 2 + var7, var8, structureBoundingBoxIn);
                }
            }

            var7 = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 6, 6, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var7), 6, 6, 4, structureBoundingBoxIn);
            int var9;

            for (var8 = 6; var8 <= 8; ++var8)
            {
                for (var9 = 5; var9 <= 10; ++var9)
                {
                    this.setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(var7), var8, 12 - var8, var9, structureBoundingBoxIn);
                }
            }

            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 9, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 2, 9, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 4, 4, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 4, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 6, 4, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 5, 5, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
            this.placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);

            if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
            {
                this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
            }

            for (var8 = 0; var8 < 5; ++var8)
            {
                for (var9 = 0; var9 < 9; ++var9)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var9, 7, var8, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), var9, -1, var8, structureBoundingBoxIn);
                }
            }

            for (var8 = 5; var8 < 11; ++var8)
            {
                for (var9 = 2; var9 < 9; ++var9)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var9, 7, var8, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), var9, -1, var8, structureBoundingBoxIn);
                }
            }

            this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
            return true;
        }
    }

    public static class House4Garden extends StructureVillagePieces.Village
    {
        private boolean isRoofAccessible;

        public House4Garden() {}

        public House4Garden(StructureVillagePieces.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, EnumFacing facing)
        {
            super(start, p_i45566_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45566_4_;
            this.isRoofAccessible = rand.nextBoolean();
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Terrace", this.isRoofAccessible);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound)
        {
            super.readStructureFromNBT(tagCompound);
            this.isRoofAccessible = tagCompound.getBoolean("Terrace");
        }

        public static StructureVillagePieces.House4Garden func_175858_a(StructureVillagePieces.Start start, List p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, EnumFacing facing, int p_175858_7_)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, facing);
            return StructureComponent.findIntersecting(p_175858_1_, var8) != null ? null : new StructureVillagePieces.House4Garden(start, p_175858_7_, rand, var8, facing);
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 4, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 2, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 1, 0, structureBoundingBoxIn);

            if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
            {
                this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);

            if (this.isRoofAccessible)
            {
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 3, structureBoundingBoxIn);
            }

            int var4;

            if (this.isRoofAccessible)
            {
                var4 = this.getMetadataWithOffset(Blocks.ladder, 3);
                this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, 1, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, 2, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, 3, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, 4, 3, structureBoundingBoxIn);
            }

            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);

            for (var4 = 0; var4 < 5; ++var4)
            {
                for (int var5 = 0; var5 < 5; ++var5)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var5, 6, var4, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), var5, -1, var4, structureBoundingBoxIn);
                }
            }

            this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
            return true;
        }
    }

    public static class Path extends StructureVillagePieces.Road
    {
        private int length;

        public Path() {}

        public Path(StructureVillagePieces.Start start, int p_i45562_2_, Random rand, StructureBoundingBox p_i45562_4_, EnumFacing facing)
        {
            super(start, p_i45562_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45562_4_;
            this.length = Math.max(p_i45562_4_.getXSize(), p_i45562_4_.getZSize());
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setInteger("Length", this.length);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound)
        {
            super.readStructureFromNBT(tagCompound);
            this.length = tagCompound.getInteger("Length");
        }

        public void buildComponent(StructureComponent componentIn, List listIn, Random rand)
        {
            boolean var4 = false;
            int var5;
            StructureComponent var6;

            for (var5 = rand.nextInt(5); var5 < this.length - 8; var5 += 2 + rand.nextInt(5))
            {
                var6 = this.getNextComponentNN((StructureVillagePieces.Start)componentIn, listIn, rand, 0, var5);

                if (var6 != null)
                {
                    var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                    var4 = true;
                }
            }

            for (var5 = rand.nextInt(5); var5 < this.length - 8; var5 += 2 + rand.nextInt(5))
            {
                var6 = this.getNextComponentPP((StructureVillagePieces.Start)componentIn, listIn, rand, 0, var5);

                if (var6 != null)
                {
                    var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                    var4 = true;
                }
            }

            if (var4 && rand.nextInt(3) > 0 && this.coordBaseMode != null)
            {
                switch (StructureVillagePieces.SwitchEnumFacing.FACING_LOOKUP[this.coordBaseMode.ordinal()])
                {
                    case 1:
                        StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType());
                        break;

                    case 2:
                        StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, this.getComponentType());
                        break;

                    case 3:
                        StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                        break;

                    case 4:
                        StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                }
            }

            if (var4 && rand.nextInt(3) > 0 && this.coordBaseMode != null)
            {
                switch (StructureVillagePieces.SwitchEnumFacing.FACING_LOOKUP[this.coordBaseMode.ordinal()])
                {
                    case 1:
                        StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType());
                        break;

                    case 2:
                        StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, this.getComponentType());
                        break;

                    case 3:
                        StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                        break;

                    case 4:
                        StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                }
            }
        }

        public static StructureBoundingBox func_175848_a(StructureVillagePieces.Start start, List p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, EnumFacing facing)
        {
            for (int var7 = 7 * MathHelper.getRandomIntegerInRange(rand, 3, 5); var7 >= 7; var7 -= 7)
            {
                StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, var7, facing);

                if (StructureComponent.findIntersecting(p_175848_1_, var8) == null)
                {
                    return var8;
                }
            }

            return null;
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            IBlockState var4 = this.func_175847_a(Blocks.gravel.getDefaultState());
            IBlockState var5 = this.func_175847_a(Blocks.cobblestone.getDefaultState());

            for (int var6 = this.boundingBox.minX; var6 <= this.boundingBox.maxX; ++var6)
            {
                for (int var7 = this.boundingBox.minZ; var7 <= this.boundingBox.maxZ; ++var7)
                {
                    BlockPos var8 = new BlockPos(var6, 64, var7);

                    if (structureBoundingBoxIn.isVecInside(var8))
                    {
                        var8 = worldIn.getTopSolidOrLiquidBlock(var8).down();
                        worldIn.setBlockState(var8, var4, 2);
                        worldIn.setBlockState(var8.down(), var5, 2);
                    }
                }
            }

            return true;
        }
    }

    public static class PieceWeight
    {
        public Class villagePieceClass;
        public final int villagePieceWeight;
        public int villagePiecesSpawned;
        public int villagePiecesLimit;

        public PieceWeight(Class p_i2098_1_, int p_i2098_2_, int p_i2098_3_)
        {
            this.villagePieceClass = p_i2098_1_;
            this.villagePieceWeight = p_i2098_2_;
            this.villagePiecesLimit = p_i2098_3_;
        }

        public boolean canSpawnMoreVillagePiecesOfType(int p_75085_1_)
        {
            return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
        }

        public boolean canSpawnMoreVillagePieces()
        {
            return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
        }
    }

    public abstract static class Road extends StructureVillagePieces.Village
    {

        public Road() {}

        protected Road(StructureVillagePieces.Start start, int type)
        {
            super(start, type);
        }
    }

    public static class Start extends StructureVillagePieces.Well
    {
        public WorldChunkManager worldChunkMngr;
        public boolean inDesert;
        public int terrainType;
        public StructureVillagePieces.PieceWeight structVillagePieceWeight;
        public List structureVillageWeightedPieceList;
        public List field_74932_i = Lists.newArrayList();
        public List field_74930_j = Lists.newArrayList();

        public Start() {}

        public Start(WorldChunkManager chunkManagerIn, int p_i2104_2_, Random rand, int p_i2104_4_, int p_i2104_5_, List p_i2104_6_, int p_i2104_7_)
        {
            super((StructureVillagePieces.Start)null, 0, rand, p_i2104_4_, p_i2104_5_);
            this.worldChunkMngr = chunkManagerIn;
            this.structureVillageWeightedPieceList = p_i2104_6_;
            this.terrainType = p_i2104_7_;
            BiomeGenBase var8 = chunkManagerIn.getBiomeGenerator(new BlockPos(p_i2104_4_, 0, p_i2104_5_), BiomeGenBase.field_180279_ad);
            this.inDesert = var8 == BiomeGenBase.desert || var8 == BiomeGenBase.desertHills;
            this.func_175846_a(this.inDesert);
        }

        public WorldChunkManager getWorldChunkManager()
        {
            return this.worldChunkMngr;
        }
    }

    static final class SwitchEnumFacing
    {
        static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];

        static
        {
            try
            {
                FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }

    public static class Torch extends StructureVillagePieces.Village
    {

        public Torch() {}

        public Torch(StructureVillagePieces.Start start, int p_i45568_2_, Random rand, StructureBoundingBox p_i45568_4_, EnumFacing facing)
        {
            super(start, p_i45568_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45568_4_;
        }

        public static StructureBoundingBox func_175856_a(StructureVillagePieces.Start start, List p_175856_1_, Random rand, int p_175856_3_, int p_175856_4_, int p_175856_5_, EnumFacing facing)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_175856_3_, p_175856_4_, p_175856_5_, 0, 0, 0, 3, 4, 2, facing);
            return StructureComponent.findIntersecting(p_175856_1_, var7) != null ? null : var7;
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 3, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 0, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, structureBoundingBoxIn);
            boolean var4 = this.coordBaseMode == EnumFacing.EAST || this.coordBaseMode == EnumFacing.NORTH;
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateY()), var4 ? 2 : 0, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 1, 3, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateYCCW()), var4 ? 0 : 2, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 1, 3, -1, structureBoundingBoxIn);
            return true;
        }
    }

    abstract static class Village extends StructureComponent
    {
        protected int field_143015_k = -1;
        private int villagersSpawned;
        private boolean isDesertVillage;

        public Village() {}

        protected Village(StructureVillagePieces.Start start, int type)
        {
            super(type);

            if (start != null)
            {
                this.isDesertVillage = start.inDesert;
            }
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            tagCompound.setInteger("HPos", this.field_143015_k);
            tagCompound.setInteger("VCount", this.villagersSpawned);
            tagCompound.setBoolean("Desert", this.isDesertVillage);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound)
        {
            this.field_143015_k = tagCompound.getInteger("HPos");
            this.villagersSpawned = tagCompound.getInteger("VCount");
            this.isDesertVillage = tagCompound.getBoolean("Desert");
        }

        protected StructureComponent getNextComponentNN(StructureVillagePieces.Start start, List p_74891_2_, Random rand, int p_74891_4_, int p_74891_5_)
        {
            if (this.coordBaseMode != null)
            {
                switch (StructureVillagePieces.SwitchEnumFacing.FACING_LOOKUP[this.coordBaseMode.ordinal()])
                {
                    case 1:
                        return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, this.getComponentType());

                    case 2:
                        return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, this.getComponentType());

                    case 3:
                        return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());

                    case 4:
                        return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                }
            }

            return null;
        }

        protected StructureComponent getNextComponentPP(StructureVillagePieces.Start start, List p_74894_2_, Random rand, int p_74894_4_, int p_74894_5_)
        {
            if (this.coordBaseMode != null)
            {
                switch (StructureVillagePieces.SwitchEnumFacing.FACING_LOOKUP[this.coordBaseMode.ordinal()])
                {
                    case 1:
                        return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, this.getComponentType());

                    case 2:
                        return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, this.getComponentType());

                    case 3:
                        return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());

                    case 4:
                        return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                }
            }

            return null;
        }

        protected int getAverageGroundLevel(World worldIn, StructureBoundingBox p_74889_2_)
        {
            int var3 = 0;
            int var4 = 0;

            for (int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; ++var5)
            {
                for (int var6 = this.boundingBox.minX; var6 <= this.boundingBox.maxX; ++var6)
                {
                    BlockPos var7 = new BlockPos(var6, 64, var5);

                    if (p_74889_2_.isVecInside(var7))
                    {
                        var3 += Math.max(worldIn.getTopSolidOrLiquidBlock(var7).getY(), worldIn.provider.getAverageGroundLevel());
                        ++var4;
                    }
                }
            }

            if (var4 == 0)
            {
                return -1;
            }
            else
            {
                return var3 / var4;
            }
        }

        protected static boolean canVillageGoDeeper(StructureBoundingBox p_74895_0_)
        {
            return p_74895_0_ != null && p_74895_0_.minY > 10;
        }

        protected void spawnVillagers(World worldIn, StructureBoundingBox p_74893_2_, int p_74893_3_, int p_74893_4_, int p_74893_5_, int p_74893_6_)
        {
            if (this.villagersSpawned < p_74893_6_)
            {
                for (int var7 = this.villagersSpawned; var7 < p_74893_6_; ++var7)
                {
                    int var8 = this.getXWithOffset(p_74893_3_ + var7, p_74893_5_);
                    int var9 = this.getYWithOffset(p_74893_4_);
                    int var10 = this.getZWithOffset(p_74893_3_ + var7, p_74893_5_);

                    if (!p_74893_2_.isVecInside(new BlockPos(var8, var9, var10)))
                    {
                        break;
                    }

                    ++this.villagersSpawned;
                    EntityVillager var11 = new EntityVillager(worldIn);
                    var11.setLocationAndAngles((double)var8 + 0.5D, (double)var9, (double)var10 + 0.5D, 0.0F, 0.0F);
                    var11.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(var11)), (IEntityLivingData)null);
                    var11.setProfession(this.func_180779_c(var7, var11.getProfession()));
                    worldIn.spawnEntityInWorld(var11);
                }
            }
        }

        protected int func_180779_c(int p_180779_1_, int p_180779_2_)
        {
            return p_180779_2_;
        }

        protected IBlockState func_175847_a(IBlockState p_175847_1_)
        {
            if (this.isDesertVillage)
            {
                if (p_175847_1_.getBlock() == Blocks.log || p_175847_1_.getBlock() == Blocks.log2)
                {
                    return Blocks.sandstone.getDefaultState();
                }

                if (p_175847_1_.getBlock() == Blocks.cobblestone)
                {
                    return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
                }

                if (p_175847_1_.getBlock() == Blocks.planks)
                {
                    return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
                }

                if (p_175847_1_.getBlock() == Blocks.oak_stairs)
                {
                    return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, p_175847_1_.getValue(BlockStairs.FACING));
                }

                if (p_175847_1_.getBlock() == Blocks.stone_stairs)
                {
                    return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, p_175847_1_.getValue(BlockStairs.FACING));
                }

                if (p_175847_1_.getBlock() == Blocks.gravel)
                {
                    return Blocks.sandstone.getDefaultState();
                }
            }

            return p_175847_1_;
        }

        protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
        {
            IBlockState var7 = this.func_175847_a(blockstateIn);
            super.setBlockState(worldIn, var7, x, y, z, boundingboxIn);
        }

        protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly)
        {
            IBlockState var12 = this.func_175847_a(boundaryBlockState);
            IBlockState var13 = this.func_175847_a(insideBlockState);
            super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, var12, var13, existingOnly);
        }

        protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
        {
            IBlockState var7 = this.func_175847_a(blockstateIn);
            super.replaceAirAndLiquidDownwards(worldIn, var7, x, y, z, boundingboxIn);
        }

        protected void func_175846_a(boolean p_175846_1_)
        {
            this.isDesertVillage = p_175846_1_;
        }
    }

    public static class Well extends StructureVillagePieces.Village
    {

        public Well() {}

        public Well(StructureVillagePieces.Start start, int p_i2109_2_, Random rand, int p_i2109_4_, int p_i2109_5_)
        {
            super(start, p_i2109_2_);
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(rand);

            switch (StructureVillagePieces.SwitchEnumFacing.FACING_LOOKUP[this.coordBaseMode.ordinal()])
            {
                case 1:
                case 2:
                    this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
                    break;

                default:
                    this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
            }
        }

        public void buildComponent(StructureComponent componentIn, List listIn, Random rand)
        {
            StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, this.getComponentType());
            StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, this.getComponentType());
            StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
            StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 4, 12, 4, Blocks.cobblestone.getDefaultState(), Blocks.flowing_water.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 12, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 3, 12, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 12, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 3, 12, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 4, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 15, 1, 4, 15, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);

            for (int var4 = 0; var4 <= 5; ++var4)
            {
                for (int var5 = 0; var5 <= 5; ++var5)
                {
                    if (var5 == 0 || var5 == 5 || var4 == 0 || var4 == 5)
                    {
                        this.setBlockState(worldIn, Blocks.gravel.getDefaultState(), var5, 11, var4, structureBoundingBoxIn);
                        this.clearCurrentPositionBlocksUpwards(worldIn, var5, 12, var4, structureBoundingBoxIn);
                    }
                }
            }

            return true;
        }
    }

    public static class WoodHut extends StructureVillagePieces.Village
    {
        private boolean isTallHouse;
        private int tablePosition;

        public WoodHut() {}

        public WoodHut(StructureVillagePieces.Start start, int p_i45565_2_, Random rand, StructureBoundingBox p_i45565_4_, EnumFacing facing)
        {
            super(start, p_i45565_2_);
            this.coordBaseMode = facing;
            this.boundingBox = p_i45565_4_;
            this.isTallHouse = rand.nextBoolean();
            this.tablePosition = rand.nextInt(3);
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setInteger("T", this.tablePosition);
            tagCompound.setBoolean("C", this.isTallHouse);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound)
        {
            super.readStructureFromNBT(tagCompound);
            this.tablePosition = tagCompound.getInteger("T");
            this.isTallHouse = tagCompound.getBoolean("C");
        }

        public static StructureVillagePieces.WoodHut func_175853_a(StructureVillagePieces.Start start, List p_175853_1_, Random rand, int p_175853_3_, int p_175853_4_, int p_175853_5_, EnumFacing facing, int p_175853_7_)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_175853_3_, p_175853_4_, p_175853_5_, 0, 0, 0, 4, 6, 5, facing);
            return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175853_1_, var8) == null ? new StructureVillagePieces.WoodHut(start, p_175853_7_, rand, var8, facing) : null;
        }

        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.field_143015_k < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);

            if (this.isTallHouse)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            }
            else
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            }

            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 1, 4, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 4, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 1, 4, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 4, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 3, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 2, structureBoundingBoxIn);

            if (this.tablePosition > 0)
            {
                this.setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), this.tablePosition, 1, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), this.tablePosition, 2, 3, structureBoundingBoxIn);
            }

            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
            this.placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));

            if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
            {
                this.setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBoxIn);
            }

            for (int var4 = 0; var4 < 5; ++var4)
            {
                for (int var5 = 0; var5 < 4; ++var5)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, var5, 6, var4, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), var5, -1, var4, structureBoundingBoxIn);
                }
            }

            this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
            return true;
        }
    }
}
