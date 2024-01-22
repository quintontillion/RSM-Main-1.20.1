package rsm.rsm.mixin.client.villagerScreen;

import net.minecraft.client.gui.widget.ButtonWidget;
import java.util.List;

public interface ButtonWidgetAccessor {
    List<ButtonWidget> getButtonList();
    void setX(int x);
}
