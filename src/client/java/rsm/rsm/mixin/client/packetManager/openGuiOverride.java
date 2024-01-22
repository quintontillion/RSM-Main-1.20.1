package rsm.rsm.mixin.client.packetManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Optional;

@Mixin(ClientPlayNetworkHandler.class)
public class openGuiOverride {
    @Inject(method="onOpenScreen",at=@At("HEAD"))
    public void onOpenScreen(OpenScreenS2CPacket packet, CallbackInfo ci) {
        // If all the ids are empty there is no point to execute the rest of the code
        if (!(MinecraftClient.getInstance().rsm_template_1_20_2$getIds().isEmpty())) {
            // getting all the values for the ids, injected inheritance via getAndSet
            ArrayList<String> ids = MinecraftClient.getInstance().rsm_template_1_20_2$getIds().get();
            // make sure the world is in existence
            if (MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().world.isClient) {
                // iterate through each entity, if the UUID's match up then we have got the right entity (theoretically :) )
                for (Entity entity : MinecraftClient.getInstance().world.getEntities()) {
                    // get the topmost item, I know I could have used stacks but who cares at this point
                    if (entity.getUuidAsString().equals(ids.get(0))) {
                        // we know that if the UUID matches up it has to be a villager entity
                        // set the trade offers of the villager to the packeted trade offers in the merchant type
                        // we need to extract it first

                        MerchantScreenHandler screenHandler = (MerchantScreenHandler)packet.getScreenHandlerType().create(packet.getSyncId(),MinecraftClient.getInstance().player.getInventory());
                        System.out.println("Balls");
                        screenHandler.getMerchant().get().getOffers();
                        for (TradeOffer offer : screenHandler.getRecipes()) {
                            System.out.println(offer.getOriginalFirstBuyItem().getItem());
                        }
                        ((VillagerEntity)entity).rsm_template_1_20_2$setTradeOffers(screenHandler.getRecipes());
                        ArrayList<String> balls = ids;
                        balls.remove(0);
                        MinecraftClient.getInstance().rsm_template_1_20_2$setIds(balls);
                        // we have successfully done it
                        // now we dont actually want to look at the menu so we send a packet stating that and we exit the method
                        CloseHandledScreenC2SPacket packet1 = new CloseHandledScreenC2SPacket(packet.getSyncId());
                        MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet1);
                        break;
                    }
                }

            }

        }
    }
}
