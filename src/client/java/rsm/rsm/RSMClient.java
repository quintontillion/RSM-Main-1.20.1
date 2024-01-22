package rsm.rsm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.encryption.ClientPlayerSession;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.QueryEntityNbtC2SPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.village.Merchant;
import net.minecraft.village.SimpleMerchant;
import net.minecraft.village.TradeOffer;
import rsm.rsm.entities.villagerOverheadEntity;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class RSMClient implements ClientModInitializer {
	Boolean fakeGuiOn = false;
	ArrayList<String> EntityChangeIds = new ArrayList<>();

	@Override
	public void onInitializeClient() {
		ClientEntityEvents.ENTITY_LOAD.register(((entity, world) -> {
			if (entity instanceof VillagerEntity && world.isClient) {
				EntityChangeIds.add(entity.getUuidAsString());
				MinecraftClient.getInstance().rsm_template_1_20_2$setIds(EntityChangeIds);
				((VillagerEntity)entity).rsm_template_1_20_2$setTradeOffers(((VillagerEntity)entity).getOffers());
				PlayerInteractEntityC2SPacket packet = PlayerInteractEntityC2SPacket.interact(entity,false, Hand.MAIN_HAND);
				MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
				fakeGuiOn = true;
			}
		}));

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (false) {
				if (client.currentScreen instanceof MerchantScreen) {
					EntityChangeIds.get(EntityChangeIds.size()-1);
					System.out.println("balls");
					MerchantScreen screen = (MerchantScreen) client.currentScreen;
					System.out.println(screen.getScreenHandler().syncId);
					Merchant merch = screen.getScreenHandler().getMerchant().get();
					System.out.println(merch);
					SimpleMerchant merchant = (SimpleMerchant) merch;
					if (merch instanceof VillagerEntity) {
						for (TradeOffer offer : merch.getOffers()) {
							System.out.println(offer.getOriginalFirstBuyItem());
						}
						((VillagerEntity)merch).rsm_template_1_20_2$setTradeOffers(merch.getOffers());
						((VillagerEntity)merch).rsm_template_1_20_2$getRealTradeOffers().get();
					}
					screen.close();
					fakeGuiOn = false;
				}
			}
		});
	/*EntityTrackingEvents.START_TRACKING.register((trackedEntity, player) -> {
			if (trackedEntity instanceof MerchantEntity) {
				if (!((MerchantEntity)trackedEntity).getOffers().isEmpty()) {
					System.out.println("Fuck you");
					for (TradeOffer offer : ((MerchantEntity)trackedEntity).getOffers()) {
						System.out.println(offer.getOriginalFirstBuyItem());
					}
					try {
						((MerchantEntity)trackedEntity).rsm_template_1_20_2$setTradeOffers(((MerchantEntity) trackedEntity).getOffers());
						((MerchantEntity)trackedEntity).rsm_template_1_20_2$getRealTradeOffers().get();
						if (((MerchantEntity)trackedEntity).rsm_template_1_20_2$getRealTradeOffers().isPresent()) {
							System.out.println("Cum");
						} else {
							System.out.println("Balls");
						}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});*/
	}
}