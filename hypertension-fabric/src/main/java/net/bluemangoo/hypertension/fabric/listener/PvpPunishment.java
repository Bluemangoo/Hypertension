package net.bluemangoo.hypertension.fabric.listener;

import net.bluemangoo.hypertension.fabric.event.EntityDamageEvent;
import net.bluemangoo.hypertension.fabric.utils.LuckyLevel;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class PvpPunishment implements EntityDamageListener {
    protected final Random random = new Random();

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }
        if (event.getEntity() == event.getSource().getEntity()) {
            return;
        }
        player.hurt(event.getSource(), event.getDamage() * (3 - LuckyLevel.getLuckyLevel(player)) * random.nextFloat());

    }
}
