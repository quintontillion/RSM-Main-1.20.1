package rsm.rsm.mixin.client.clientGetterAndSetter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import rsm.rsm.getIds;

import java.util.ArrayList;
import java.util.Optional;

@Mixin(MinecraftClient.class)
abstract public class getAndSet extends ReentrantThreadExecutor<Runnable> implements WindowEventHandler, getIds {
    ArrayList<String> EntityChangeIds = new ArrayList<>();
    @Override
    public Optional<ArrayList<String>> rsm_template_1_20_2$getIds() {
        if (!EntityChangeIds.isEmpty()) {
            return Optional.of(EntityChangeIds);
        }
        return Optional.empty();
    }
    @Override
    public void rsm_template_1_20_2$setIds(ArrayList<String> ids) {
        this.EntityChangeIds = ids;
    }
    public getAndSet(String string) {
        super(string);
    }
}
