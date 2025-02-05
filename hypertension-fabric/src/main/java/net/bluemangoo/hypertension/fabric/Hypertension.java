package net.bluemangoo.hypertension.fabric;

import com.google.common.collect.ImmutableList;
import net.bluemangoo.hypertension.fabric.listener.*;
import net.fabricmc.api.ModInitializer;

public class Hypertension implements ModInitializer {
    public static final ImmutableList<EntityDamageListener> ENTITY_DAMAGE_LISTENERS = ImmutableList.of(
        new FallDamage(),
        new Miss(),
        new PvpPunishment()
    );
    public static final ImmutableList<EntityDeathListener> ENTITY_DEATH_LISTENERS = ImmutableList.of(
        new MobCopy()
    );

    @Override
    public void onInitialize() {
    }
}
