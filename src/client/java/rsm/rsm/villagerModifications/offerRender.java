package rsm.rsm.villagerModifications;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.village.TradeOffer;

import java.nio.charset.MalformedInputException;
import java.util.Iterator;

public class offerRender {
    public int getWidth() {
        return width;
    }
    private int width;
    public void renderItem(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {

    }
    public offerRender(TradeOffer offer, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int x, int y) {
        Iterator iterator = Registries.ITEM.iterator();
        // get the model to be used with the rendering
        BakedModel backedModel = MinecraftClient.getInstance().getItemRenderer().getModel(offer.getAdjustedFirstBuyItem(), MinecraftClient.getInstance().world, null,0);
        // actually render the first model/item
        MinecraftClient.getInstance().getItemRenderer().renderItem(offer.getAdjustedFirstBuyItem(),
                ModelTransformationMode.FIXED, false, matrixStack, vertexConsumerProvider, 15728880, OverlayTexture.DEFAULT_UV,backedModel);

    }
    public offerRender(ItemStack offer, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int x, int y) {
        Iterator iterator = Registries.ITEM.iterator();
        // get the model to be used with the rendering
        BakedModel backedModel = MinecraftClient.getInstance().getItemRenderer().getModel(offer, MinecraftClient.getInstance().world, null,0);
        // actually render the first model/item
        MinecraftClient.getInstance().getItemRenderer().renderItem(offer,
                ModelTransformationMode.FIXED, false, matrixStack, vertexConsumerProvider, 15728880, OverlayTexture.DEFAULT_UV,backedModel);

    }
}
