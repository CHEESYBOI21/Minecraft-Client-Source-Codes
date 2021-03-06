package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;

public class BlockRedstoneLight extends Block
{
    private final boolean isOn;
    
    public BlockRedstoneLight(final boolean isOn) {
        super(Material.redstoneLight);
        this.isOn = isOn;
        if (isOn) {
            this.setLightLevel(1.0f);
        }
    }
    
    @Override
    public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.isRemote) {
            if (this.isOn && !worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, Blocks.redstone_lamp.getDefaultState(), 2);
            }
            else if (!this.isOn && worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.isRemote) {
            if (this.isOn && !worldIn.isBlockPowered(pos)) {
                worldIn.scheduleUpdate(pos, this, 4);
            }
            else if (!this.isOn && worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
            }
        }
    }
    
    @Override
    public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.isRemote && this.isOn && !worldIn.isBlockPowered(pos)) {
            worldIn.setBlockState(pos, Blocks.redstone_lamp.getDefaultState(), 2);
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
        return Item.getItemFromBlock(Blocks.redstone_lamp);
    }
    
    @Override
    public Item getItem(final World worldIn, final BlockPos pos) {
        return Item.getItemFromBlock(Blocks.redstone_lamp);
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState state) {
        return new ItemStack(Blocks.redstone_lamp);
    }
}
