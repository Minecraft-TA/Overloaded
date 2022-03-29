package com.cjm721.overloaded.block.tile;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class TilePlayerInterface extends TileEntity {

    private UUID placer;
    private ItemStack stackCache;

    @Override
    @Nonnull
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);

        return new SPacketUpdateTileEntity(getPos(), 1, tag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        if (compound.hasKey("Placer"))
            placer = UUID.fromString(compound.getString("Placer"));

        super.readFromNBT(compound);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        if (placer != null)
            compound.setString("Placer", placer.toString());

        return super.writeToNBT(compound);
    }

    public void setPlacer(@Nonnull EntityLivingBase placer) {
        if (placer instanceof EntityPlayer)
            this.placer = placer.getUniqueID();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (this.placer != null) {
            EntityPlayer player = this.getWorld().getPlayerEntityByUUID(this.placer);

            if (player != null) {
                return player.hasCapability(capability, facing);
            }
        }

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (this.placer != null) {
            EntityPlayer player = this.getWorld().getPlayerEntityByUUID(this.placer);

            if (player != null) {
                return player.getCapability(capability, facing);
            }
        }

        return super.getCapability(capability, facing);
    }

    public UUID getPlacer() {
        return placer;
    }

    public ItemStack getStackCache() {
        if (this.stackCache == null) {
            stackCache = new ItemStack(Items.SKULL, 1, 3);
            NBTTagCompound tag = new NBTTagCompound();
            GameProfile gameprofile = TileEntitySkull.updateGameProfile(new GameProfile((UUID) null, this.placer.toString()));
            tag.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
            stackCache.setTagCompound(tag);
        }
        return stackCache;
    }
}
