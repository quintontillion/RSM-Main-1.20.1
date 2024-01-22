package rsm.rsm.mixin.client.villagerScreen;

import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.village.Merchant;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import rsm.rsm.GetMerchant;

import java.util.Optional;

@Mixin(MerchantScreenHandler.class)
abstract public class getMerchantMixin extends ScreenHandler implements GetMerchant {

    @Shadow @Final private Merchant merchant;

    protected getMerchantMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public Optional<Merchant> getMerchant() {
        return Optional.of(this.merchant);
    }
}
