package rsm.rsm.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class villagerOverheadEntity extends DisplayEntity {
    public MerchantEntity binded = null;
    private ArrayList<Text> text = new ArrayList<Text>();
    public villagerOverheadEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }


    protected void refreshData(boolean shouldLerp, float lerpProgress) {

    }
    public void setText(ArrayList<Text> e) {
        this.text = e;
    }

}
