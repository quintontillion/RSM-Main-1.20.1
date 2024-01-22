package rsm.rsm.mixin.client.packetManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.SetTradeOffersS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(ClientPlayNetworkHandler.class)
abstract public class printWhenEnding implements Packet<ClientPlayPacketListener> {
    @Inject(method="onOpenScreen",at=@At("TAIL"))
    public void onOpenScreen(OpenScreenS2CPacket packet, CallbackInfo ci) {
        System.out.println("Ending Screen Render!");
        //MinecraftClient.getInstance().currentScreen.close();
    }
    @Inject(method="onSetTradeOffers", at=@At("HEAD"))
    public void setTradeOffers(SetTradeOffersS2CPacket packet, CallbackInfo ci) {
        System.out.println("Recieved trade offers!");
    }
}