package com.ngb.projectzulu.common.dungeon.packets;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import com.ngb.projectzulu.common.core.PZPacket;
import com.ngb.projectzulu.common.core.PZPacketBase;
import com.ngb.projectzulu.common.core.ProjectZuluLog;

/**
 * Packet uses exposes data writing/reading via ByteArrayOutputStream/DataInputStream
 * 
 * This allows using Minecraft methods to write traditional Minecraft objects, such as NBT data
 */
public abstract class PacketDataStream extends PZPacketBase {

    @Override
    public void toBytes(ByteBuf buffer) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(byteArray);
        try {
            writeData(data);
        } catch (Exception e) {
            // TODO: log exception
        }
        byte[] bytes = byteArray.toByteArray();
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
    }

    protected abstract void writeData(DataOutputStream buffer) throws IOException;

    @Override
    public void fromBytes(ByteBuf buffer) {
        int byteLength = buffer.readInt();
        byte[] dataBytes = new byte[byteLength];
        buffer.readBytes(dataBytes);

        ByteArrayInputStream byteArray = new ByteArrayInputStream(dataBytes);
        DataInputStream data = new DataInputStream(byteArray);
        try {
            readData(data);
        } catch (Exception e) {
            // TODO: log exception
        }
    }

    protected abstract void readData(DataInputStream buffer) throws IOException;

    /**
     * Reads a compressed NBTTagCompound from the InputStream
     */
    protected static NBTTagCompound readNBTTagCompound(DataInputStream par0DataInputStream) throws IOException {
     	short length = par0DataInputStream.readShort();
    	if (length < 0)
    		return null;
    	else {
    		byte[] compressed = new byte[length];
    		par0DataInputStream.readFully(compressed);
    	return CompressedStreamTools.readCompressed(new ByteArrayInputStream(compressed));
    	}
    }

    /**
     * Writes a compressed NBTTagCompound to the OutputStream
     */
    protected static void writeNBTTagCompound(NBTTagCompound par0NBTTagCompound, DataOutputStream par1DataOutputStream)
            throws IOException {
        if (par0NBTTagCompound == null) {
            par1DataOutputStream.writeShort(-1);
        } else {
            byte[] var2 = CompressedStreamTools.compress(par0NBTTagCompound);
            par1DataOutputStream.writeShort((short) var2.length);
            par1DataOutputStream.write(var2);
        }
    }
}
