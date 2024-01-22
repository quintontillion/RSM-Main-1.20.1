package rsm.rsm.villagerModifications;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ButtonImplement extends ButtonWidget {

    protected ButtonImplement(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    @Override
    public void renderButton(DrawContext matrices, int mouseX, int mouseY, float delta) {
        // Customize button rendering if needed
        super.renderButton(matrices, mouseX, mouseY, delta);
    }
}
