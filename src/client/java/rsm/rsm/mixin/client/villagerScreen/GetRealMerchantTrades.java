package rsm.rsm.mixin.client.villagerScreen;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.Npc;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.village.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rsm.rsm.GetRealOffers;

import java.util.Optional;

@Mixin(VillagerEntity.class)
abstract public class GetRealMerchantTrades extends MerchantEntity implements InteractionObserver, VillagerDataContainer,GetRealOffers {
    @Shadow public abstract VillagerData getVillagerData();

    @Shadow protected abstract void fillRecipes();

    @Shadow @Final private static ImmutableList<SensorType<? extends Sensor<? super VillagerEntity>>> SENSORS;
    Optional<TradeOfferList> realList = Optional.empty();

    public GetRealMerchantTrades(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Optional<TradeOfferList> rsm_template_1_20_2$getRealTradeOffers() {
        if (realList.isEmpty()) {
            this.fillRecipes();
            this.rsm_template_1_20_2$setTradeOffers(this.getOffers());
        }
        return this.realList;
    }
    @Override
    public void rsm_template_1_20_2$setTradeOffers(TradeOfferList list) {
        this.realList = Optional.ofNullable(list);
    }
    @Inject(method="fillRecipes",at=@At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void init(CallbackInfo ci, VillagerData villagerData, Int2ObjectMap int2ObjectMap, TradeOffers.Factory[] factorys, TradeOfferList tradeOfferList) {
        System.out.println("vuajnchajbnd");
    }
    @Inject(method="readCustomDataFromNbt",at=@At("HEAD"))
    public void fartShit(NbtCompound nbt, CallbackInfo ci) {
        System.out.println("euihdiuahuiedhiajkehkd");
    }

}
