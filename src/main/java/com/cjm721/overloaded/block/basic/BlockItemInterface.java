package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TileItemInterface;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.tile.ItemInterfaceRenderer;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public class BlockItemInterface extends ModBlock implements ITileEntityProvider, IProbeInfoAccessor {

    public BlockItemInterface() {
        super(Material.ROCK);
    }

    @Override
    public void baseInit() {
        setRegistryName("item_interface");
        setTranslationKey("item_interface");

        GameRegistry.registerTileEntity(TileItemInterface.class, MODID + ":item_interface");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), null));
        ClientRegistry.bindTileEntitySpecialRenderer(TileItemInterface.class, new ItemInterfaceRenderer());

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/block.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        ((TileItemInterface) worldIn.getTileEntity(pos)).breakBlock();
        super.breakBlock(worldIn, pos, state);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileItemInterface();
    }


    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos, EnumFacing side) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;

        TileEntity te = worldIn.getTileEntity(pos);

        if (!(te instanceof TileItemInterface))
            return true;

        TileItemInterface anInterface = (TileItemInterface) te;

        ItemStack stack = anInterface.getStoredItem();
        if (stack.isEmpty()) {
            ItemStack handStack = playerIn.getHeldItem(hand);

            if (handStack.isEmpty())
                return true;

            ItemStack returnedItem = anInterface.insertItem(0, handStack, false);
            playerIn.setHeldItem(hand, returnedItem);
        } else {
            if (!playerIn.getHeldItem(hand).isEmpty())
                return true;

            ItemStack toSpawn = anInterface.extractItem(0, 1, false);
            if (toSpawn.isEmpty())
                return true;

            ItemHandlerHelper.giveItemToPlayer(playerIn, toSpawn, playerIn.inventory.currentItem);
        }

        return true;
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te != null && te instanceof TileItemInterface) {
            ItemStack item = ((TileItemInterface) te).getStoredItem();
            if (!item.isEmpty()) {
                probeInfo.horizontal().item(item).itemLabel(item);
            }
        }
    }
}
