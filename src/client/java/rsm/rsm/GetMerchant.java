package rsm.rsm;

import net.minecraft.village.Merchant;

import java.util.Optional;

public interface GetMerchant {
    default Optional<Merchant> getMerchant() {
        return Optional.empty();
    }
}
