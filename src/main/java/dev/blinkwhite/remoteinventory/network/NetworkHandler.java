package dev.blinkwhite.remoteinventory.network;

import dev.blinkwhite.remoteinventory.Reference;
import dev.blinkwhite.remoteinventory.network.handler.GetItemFromInventoryHandler;
import dev.blinkwhite.remoteinventory.network.handler.ScanContainerHandler;

//#if MC >= 12005
import dev.blinkwhite.remoteinventory.network.payload.GetItemFromInventoryPayload;
import dev.blinkwhite.remoteinventory.network.payload.GetItemResultPayload;
import dev.blinkwhite.remoteinventory.network.payload.ScanContainerPayload;
import dev.blinkwhite.remoteinventory.network.payload.ScanContainerResultPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.codec.StreamCodec;
//#endif

public class NetworkHandler {
    public static void registerReceivers() {
        Reference.LOGGER.info("Registering remote inventory packet receivers...");

        //#if MC >= 12005
        PayloadTypeRegistry.playC2S().register(GetItemFromInventoryPayload.TYPE,
                StreamCodec.ofMember(GetItemFromInventoryPayload::write, GetItemFromInventoryPayload::decode));
        PayloadTypeRegistry.playS2C().register(GetItemResultPayload.TYPE,
                StreamCodec.ofMember(GetItemResultPayload::write, GetItemResultPayload::decode));
        PayloadTypeRegistry.playC2S().register(ScanContainerPayload.TYPE,
                StreamCodec.ofMember(ScanContainerPayload::write, ScanContainerPayload::decode));
        PayloadTypeRegistry.playS2C().register(ScanContainerResultPayload.TYPE,
                StreamCodec.ofMember(ScanContainerResultPayload::write, ScanContainerResultPayload::decode));
        //#endif

        GetItemFromInventoryHandler.register();
        ScanContainerHandler.register();
    }
}
