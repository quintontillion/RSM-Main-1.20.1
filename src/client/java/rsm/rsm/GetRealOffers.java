package rsm.rsm;

import net.minecraft.village.TradeOfferList;

import java.util.Optional;

public interface GetRealOffers {
    Optional<TradeOfferList> realList = Optional.empty();
    default void rsm_template_1_20_2$setTradeOffers(TradeOfferList list) {

    }
    default Optional<TradeOfferList> rsm_template_1_20_2$getRealTradeOffers() {
        return Optional.empty();
    }
}
