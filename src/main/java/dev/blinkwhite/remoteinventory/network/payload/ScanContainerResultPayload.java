package dev.blinkwhite.remoteinventory.network.payload;

//#if MC >= 12005
import dev.blinkwhite.remoteinventory.Reference;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.ArrayList;
import java.util.List;

public class ScanContainerResultPayload implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ScanContainerResultPayload> TYPE = new CustomPacketPayload.Type<>(
        //#if MC >= 12105
        net.minecraft.resources.Identifier.fromNamespaceAndPath(Reference.MOD_ID, "scan_container_result")
        //#elseif MC >= 12101
        //$$ net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "scan_container_result")
        //#else
        //$$ new net.minecraft.resources.ResourceLocation(Reference.MOD_ID, "scan_container_result")
        //#endif
    );

    public record SlotEntry(int slot, String itemId, int count) {}

    private final BlockPos pos;
    private final List<SlotEntry> entries;

    public ScanContainerResultPayload(BlockPos pos, List<SlotEntry> entries) {
        this.pos = pos;
        this.entries = entries;
    }

    public BlockPos getPos() { return pos; }
    public List<SlotEntry> getEntries() { return entries; }

    public static ScanContainerResultPayload decode(ByteBuf buf) {
        FriendlyByteBuf wrapped = (FriendlyByteBuf) buf;
        BlockPos pos = wrapped.readBlockPos();
        int size = wrapped.readVarInt();
        List<SlotEntry> entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int slot = wrapped.readVarInt();
            //#if MC >= 12105
            String itemId = wrapped.readIdentifier().toString();
            //#else
            //$$ String itemId = wrapped.readResourceLocation().toString();
            //#endif
            int count = wrapped.readVarInt();
            entries.add(new SlotEntry(slot, itemId, count));
        }
        return new ScanContainerResultPayload(pos, entries);
    }

    public void write(ByteBuf buf) {
        FriendlyByteBuf wrapped = (FriendlyByteBuf) buf;
        wrapped.writeBlockPos(pos);
        wrapped.writeVarInt(entries.size());
        for (SlotEntry e : entries) {
            wrapped.writeVarInt(e.slot());
            //#if MC >= 12105
            wrapped.writeIdentifier(net.minecraft.resources.Identifier.parse(e.itemId()));
            //#elseif MC >= 12101
            //$$ wrapped.writeResourceLocation(net.minecraft.resources.ResourceLocation.parse(e.itemId()));
            //#else
            //$$ wrapped.writeResourceLocation(new net.minecraft.resources.ResourceLocation(e.itemId()));
            //#endif
            wrapped.writeVarInt(e.count());
        }
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
//#else
//$$ import net.minecraft.core.BlockPos;
//$$
//$$ import java.util.List;
//$$
//$$ public class ScanContainerResultPayload {
//$$     public record SlotEntry(int slot, String itemId, int count) {}
//$$
//$$     private final BlockPos pos;
//$$     private final List<SlotEntry> entries;
//$$
//$$     public ScanContainerResultPayload(BlockPos pos, List<SlotEntry> entries) {
//$$         this.pos = pos;
//$$         this.entries = entries;
//$$     }
//$$
//$$     public BlockPos getPos() { return pos; }
//$$     public List<SlotEntry> getEntries() { return entries; }
//$$ }
//#endif
