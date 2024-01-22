package rsm.rsm.mixin.client.villagerScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
abstract public class injectHandledScreens extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
    public injectHandledScreens(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method="fillRecipes", at=@At("TAIL"))
    public void init(CallbackInfo ci) {
        for (TradeOffer offer : this.getOffers()) {
            System.out.println(offer.getOriginalFirstBuyItem());
        }
        this.rsm_template_1_20_2$setTradeOffers(this.getOffers());
    }
}
