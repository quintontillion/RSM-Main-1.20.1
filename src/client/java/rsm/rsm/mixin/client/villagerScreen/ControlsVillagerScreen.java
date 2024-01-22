package rsm.rsm.mixin.client.villagerScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(MerchantScreen.class)
public abstract class ControlsVillagerScreen extends HandledScreen<MerchantScreenHandler> {

    @Shadow private int indexStartOffset;
    public boolean open = true;
    public ControlsVillagerScreen(MerchantScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    @Inject(at = @At(value = "TAIL"), method = "drawBackground")
    protected void init(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if (open) {
            System.out.println(((MerchantScreenHandler)this.handler).getRecipes().size());
            int l = (this.width - this.backgroundWidth) / 2;
            int j = (this.height - this.backgroundHeight) / 2;
            int k = j + 16 + 2;
            for (int i = 0; i < this.handler.getRecipes().size(); i++) {
                ItemStack item = ((MerchantScreenHandler) this.getScreenHandler()).getRecipes().get(i).getAdjustedFirstBuyItem();
                Text name = item.getName();
                int count = item.getCount();
                int amOT = 0;
                int rem = 0;
                boolean fe = false;
                for (ItemStack InvItem : MinecraftClient.getInstance().player.getInventory().main) {
                    if (InvItem.getName().equals(name)) {
                        fe = true;
                        amOT = (int) (InvItem.getCount()/count);
                        rem = (int) (InvItem.getCount() % count);
                    }
                }
                if (!fe) {
                    this.addDrawableChild(ButtonWidget.builder(Text.translatable("No trades available"), (button) -> {
                        return;
                    }).dimensions(0,20*i + k,l,20).build());
                } else {
                    int finalI1 = i;
                    this.addDrawableChild(ButtonWidget.builder(Text.translatable("Trades: " + amOT + " Remainder: " + rem), (button) -> {
                        //https://fabricmc.net/wiki/tutorial:interface_injection
                        // rememeber
                        //Optional<Merchant> merch = this.getScreenHandler().getMerchant();
                        int ie = 0;
                        while (!handler.getRecipes().get(finalI1).isDisabled()) {
                           // Mouse mouse = MinecraftClient.getInstance().mouse;
                           //int click = mouse.wasLeftButtonClicked() ? 0 : mouse.wasRightButtonClicked() ? 1 : mouse.wasMiddleButtonClicked() ? 2 : 3;
                            PlayerEntity player = MinecraftClient.getInstance().player;
                            // check if player doesn't have the first buy item slot
                            if ((!player.getInventory().contains(handler.getRecipes().get(finalI1).getAdjustedFirstBuyItem())) || handler.getRecipes().get(finalI1).getAdjustedFirstBuyItem() == null) {
                                break;
                            }
                            MerchantScreenHandler handler = this.handler;
                            Slot output = handler.getSlot(2);
                            Slot input = handler.getSlot(0);
                            Slot input2 = handler.getSlot(1);
                            // Populate the first slot
                            // If the item in the slot doesn't match the required, move it
                            MinecraftClient.getInstance().getNetworkHandler().sendPacket(new SelectMerchantTradeC2SPacket(finalI1));
                            MinecraftClient.getInstance().interactionManager.clickSlot(
                                    handler.syncId,
                                    output.id,
                                    0,
                                    SlotActionType.QUICK_MOVE,
                                    MinecraftClient.getInstance().player);
                            if (ie == 12) {
                                break;
                            }
                            ie++;
                        }
                    }).dimensions(0,20*i + k,l,20).build());
                }
            }
            open = false;
        }
    }


}
