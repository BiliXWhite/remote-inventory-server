package dev.blinkwhite.remoteinventory.network.payload;

//#if MC >= 12005
import dev.blinkwhite.remoteinventory.Reference;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ScanContainerPayload implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ScanContainerPayload> TYPE = new CustomPacketPayload.Type<>(
        //#if MC >= 12105
        net.minecraft.resources.Identifier.fromNamespaceAndPath(Reference.MOD_ID, "scan_container")
        //#elseif MC >= 12101
        //$$ net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "scan_container")
        //#else
        //$$ new net.minecraft.resources.ResourceLocation(Reference.MOD_ID, "scan_container")
        //#endif
    );

    private final BlockPos pos;

    public ScanContainerPayload(BlockPos pos) {
        this.pos = pos;
    }

    public BlockPos getPos() { return pos; }

    public static ScanContainerPayload decode(ByteBuf buf) {
        FriendlyByteBuf wrapped = (FriendlyByteBuf) buf;
        return new ScanContainerPayload(wrapped.readBlockPos());
    }

    public void write(ByteBuf buf) {
        FriendlyByteBuf wrapped = (FriendlyByteBuf) buf;
        wrapped.writeBlockPos(pos);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
//#else
//$$ class ScanContainerPayload {}
//#endif
