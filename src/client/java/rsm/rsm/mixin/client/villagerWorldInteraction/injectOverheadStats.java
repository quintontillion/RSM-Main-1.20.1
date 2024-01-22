package rsm.rsm.mixin.client.villagerWorldInteraction;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MatrixUtil;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rsm.rsm.villagerModifications.offerRender;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(HeadFeatureRenderer.class)
abstract public class injectOverheadStats<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {


    public injectOverheadStats(FeatureRendererContext<T, M> context) {
        super(context);
    }
    @Unique
    public void drawRectangle(MinecraftClient instance, Matrix4f matrices, int x, int y, int width, int height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS,VertexFormats.POSITION_TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        //RenderSystem.setShaderTexture(0,Identifier.tryParse("assets/minecraft/textures/item/emerald.png"));
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
    }
    @Unique
    public void renderItem(MatrixStack matrices, ItemStack Item, ModelTransformationMode mode, VertexConsumerProvider vertexProvider, World world) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(Identifier.tryParse("assets/minecraft/textures/item/emerald"));
    }
    @Unique
    public void renderLine(List<Text> text, List<ItemStack> item, Matrix4f matrix, int width, int height, int colour, int backgroundColour, int x, int y, MinecraftClient instance, int k, VertexConsumerProvider consumer) {
        if (text.size() == item.size()) {
            for (int i = 0; i < text.size(); i++) {
                int propDistY = y+i*k;
                int propDistX = x+width/2;

                instance.textRenderer.draw(text.get(i), (float)propDistX,(float)propDistY,colour, false, matrix,consumer, TextRenderer.TextLayerType.SEE_THROUGH,backgroundColour,1);
            }
        }
    }

    @Inject(method= "render*", at=@At("HEAD"))
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        // if theres no trades then there is no point to inject
        // also will thro an error if so
        if (livingEntity instanceof VillagerEntity merch && ((VillagerEntity)livingEntity).getOffers().size()!=0) {
            // get the merchant(Villager or wandering trader) from the entity
            // all
            List<DisplayEntity.TextDisplayEntity.TextLine> lines = new ArrayList<DisplayEntity.TextDisplayEntity.TextLine>();
            lines.add(new DisplayEntity.TextDisplayEntity.TextLine(Text.literal("balls").asOrderedText(),(int)MinecraftClient.getInstance().textRenderer.getTextHandler().getWidth(Text.literal("balls"))));
            DisplayEntity.TextDisplayEntity.TextLines lines1 = new DisplayEntity.TextDisplayEntity.TextLines(lines,(int)MinecraftClient.getInstance().textRenderer.getTextHandler().getWidth(Text.literal("balls")));
            matrixStack.push();
            Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
            //MinecraftClient.getInstance().textRenderer.draw(Text.literal("uduehdhe"),0,0,0,false,matrix4f,vertexConsumerProvider, TextRenderer.TextLayerType.POLYGON_OFFSET,0,i);
            //matrixStack.multiply(MinecraftClient.getInstance().getEntityRenderDispatcher().getRotation());
            assert MinecraftClient.getInstance().player != null;
            //System.out.println(MinecraftClient.getInstance().player.getPitch());

            //System.out.println(MinecraftClient.getInstance().player.getRoll()%360);
            // Get the camera thingy
            Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
            // copied math from the source HORIZONTAL billboardConstraint
            // normally y value would be -0.017453292F * livingEntity.getYaw(), but I just want it to hover over not be facing the camera since that is easier to look at
            matrixStack.multiply((new Quaternionf()).rotationYXZ(0, -0.017453292F * camera.getPitch(), 0.0F));
            //System.out.println((float)((System.currentTimeMillis())%10)/10-0.5F);\
            // getting positional of the 3x3 item
            float f1 = 1.0f/matrixStack.peek().getPositionMatrix().m33();
            // get the affinetransformation of the matrix
            // maybe temp get the triple again stolen from affine transformation source code
            Triple<Quaternionf, Vector3f, Quaternionf> triple = MatrixUtil.svdDecompose((new Matrix3f(matrix4f)).scale(f1));
            Quaternionf leftRotation = new Quaternionf(triple.getLeft());
            Quaternionf rightRotation = new Quaternionf(triple.getRight());
            // rotate the matrix to get the text to be moving around player 😀😀😀 finally got it fucking working
            matrixStack.peek().getPositionMatrix().rotate(leftRotation).rotate(rightRotation);
            //matrixStack.peek().getNormalMatrix().rotate(leftRotation).rotate(rightRotation);
            // stolen from the display text entity source code, again it works
            matrix4f.rotate(3.14159F, 0,1.0f, 0);
            matrix4f.scale(-0.025F, -0.025F, -0.025F);
            // temp text
            //MinecraftClient.getInstance().textRenderer.draw(Text.literal("uduehdhe"),0,0,0,false,matrix4f,vertexConsumerProvider, TextRenderer.TextLayerType.POLYGON_OFFSET,0,i);
            //MinecraftClient.getInstance().textRenderer.draw(Text.literal("cum"),0,30,0,false,matrix4f,vertexConsumerProvider,TextRenderer.TextLayerType.SEE_THROUGH,0,i);
            //matrix4f.rotate(3.1415927F, 0.0F, 1.0F, 0.0F);
            // math for the distances
            int l1 = lines1.width();
            float h1  = (float)l1 / 2.0F - (float)lines.get(0).width() / 2.0F;
            float g1 = 0.0f;
            int m1 = lines1.lines().size()*10;

            matrix4f.translate(1.0F - (float)l1 / 2.0F, (float)(-m1), 0.0F);
            // center alignment
            // remember to add shit
            matrix4f.scale(10,10,10);
            if (merch.rsm_template_1_20_2$getRealTradeOffers().get() != null) {
                TradeOfferList list = merch.rsm_template_1_20_2$getRealTradeOffers().get();
                /*Optional<TradeOfferList> list = merch.rsm_template_1_20_2$getRealTradeOffers();*/
                //merch.sendOffers(MinecraftClient.getInstance().player, Text.literal("vum"),merch.getVillagerData().getLevel());
                for (TradeOffer item : list) {
                    //System.out.println(item.getOriginalFirstBuyItem());
                    matrix4f.translate(0,-2,0);
                    new offerRender(item, matrixStack, vertexConsumerProvider,20,100 );
                }
            }

            //renderItem(matrixStack,merch.getOffers().get(0).getAdjustedFirstBuyItem(),ModelTransformationMode.FIXED,vertexConsumerProvider,MinecraftClient.getInstance().world);
            //matrix4f.rotate(3.1415927F,0.0f,0.0f,0.0f);

            matrixStack.pop();
        }

    }
}
