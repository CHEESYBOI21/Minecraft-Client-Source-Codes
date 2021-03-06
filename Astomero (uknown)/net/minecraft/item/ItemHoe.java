package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;

public class ItemHoe extends Item
{
    protected ToolMaterial theToolMaterial;
    
    public ItemHoe(final ToolMaterial material) {
        this.theToolMaterial = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
            return false;
        }
        final IBlockState iblockstate = worldIn.getBlockState(pos);
        final Block block = iblockstate.getBlock();
        if (side != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getBlock().getMaterial() == Material.air) {
            if (block == Blocks.grass) {
                return this.useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
            }
            if (block == Blocks.dirt) {
                switch (iblockstate.getValue(BlockDirt.VARIANT)) {
                    case DIRT: {
                        return this.useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
                    }
                    case COARSE_DIRT: {
                        return this.useHoe(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                    }
                }
            }
        }
        return false;
    }
    
    protected boolean useHoe(final ItemStack stack, final EntityPlayer player, final World worldIn, final BlockPos target, final IBlockState newState) {
        worldIn.playSoundEffect(target.getX() + 0.5f, target.getY() + 0.5f, target.getZ() + 0.5f, newState.getBlock().stepSound.getStepSound(), (newState.getBlock().stepSound.getVolume() + 1.0f) / 2.0f, newState.getBlock().stepSound.getFrequency() * 0.8f);
        if (worldIn.isRemote) {
            return true;
        }
        worldIn.setBlockState(target, newState);
        stack.damageItem(1, player);
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    public String getMaterialName() {
        return this.theToolMaterial.toString();
    }
}
