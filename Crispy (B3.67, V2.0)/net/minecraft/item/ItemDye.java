package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemDye extends Item
{
    public static final int[] dyeColors = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

    public ItemDye()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
        int var2 = stack.getMetadata();
        return super.getUnlocalizedName() + "." + EnumDyeColor.byDyeDamage(var2).getUnlocalizedName();
    }

    /**
     * Called when a Block is right-clicked with this Item
     *  
     * @param pos The block being right-clicked
     * @param side The side being right-clicked
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
        {
            return false;
        }
        else
        {
            EnumDyeColor var9 = EnumDyeColor.byDyeDamage(stack.getMetadata());

            if (var9 == EnumDyeColor.WHITE)
            {
                if (applyBonemeal(stack, worldIn, pos))
                {
                    if (!worldIn.isRemote)
                    {
                        worldIn.playAuxSFX(2005, pos, 0);
                    }

                    return true;
                }
            }
            else if (var9 == EnumDyeColor.BROWN)
            {
                IBlockState var10 = worldIn.getBlockState(pos);
                Block var11 = var10.getBlock();

                if (var11 == Blocks.log && var10.getValue(BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE)
                {
                    if (side == EnumFacing.DOWN)
                    {
                        return false;
                    }

                    if (side == EnumFacing.UP)
                    {
                        return false;
                    }

                    pos = pos.offset(side);

                    if (worldIn.isAirBlock(pos))
                    {
                        IBlockState var12 = Blocks.cocoa.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, 0, playerIn);
                        worldIn.setBlockState(pos, var12, 2);

                        if (!playerIn.capabilities.isCreativeMode)
                        {
                            --stack.stackSize;
                        }
                    }

                    return true;
                }
            }

            return false;
        }
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target)
    {
        IBlockState var3 = worldIn.getBlockState(target);

        if (var3.getBlock() instanceof IGrowable)
        {
            IGrowable var4 = (IGrowable)var3.getBlock();

            if (var4.canGrow(worldIn, target, var3, worldIn.isRemote))
            {
                if (!worldIn.isRemote)
                {
                    if (var4.canUseBonemeal(worldIn, worldIn.rand, target, var3))
                    {
                        var4.grow(worldIn, worldIn.rand, target, var3);
                    }

                    --stack.stackSize;
                }

                return true;
            }
        }

        return false;
    }

    public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount)
    {
        if (amount == 0)
        {
            amount = 15;
        }

        Block var3 = worldIn.getBlockState(pos).getBlock();

        if (var3.getMaterial() != Material.air)
        {
            var3.setBlockBoundsBasedOnState(worldIn, pos);

            for (int var4 = 0; var4 < amount; ++var4)
            {
                double var5 = itemRand.nextGaussian() * 0.02D;
                double var7 = itemRand.nextGaussian() * 0.02D;
                double var9 = itemRand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + itemRand.nextFloat()), (double)pos.getY() + (double)itemRand.nextFloat() * var3.getBlockBoundsMaxY(), (double)((float)pos.getZ() + itemRand.nextFloat()), var5, var7, var9, new int[0]);
            }
        }
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target)
    {
        if (target instanceof EntitySheep)
        {
            EntitySheep var4 = (EntitySheep)target;
            EnumDyeColor var5 = EnumDyeColor.byDyeDamage(stack.getMetadata());

            if (!var4.getSheared() && var4.getFleeceColor() != var5)
            {
                var4.setFleeceColor(var5);
                --stack.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     *  
     * @param subItems The List of sub-items. This is a List of ItemStacks.
     */
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
    {
        for (int var4 = 0; var4 < 16; ++var4)
        {
            subItems.add(new ItemStack(itemIn, 1, var4));
        }
    }
}
