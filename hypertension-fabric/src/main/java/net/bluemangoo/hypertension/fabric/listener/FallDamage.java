package net.bluemangoo.hypertension.fabric.listener;

import net.bluemangoo.hypertension.fabric.event.EntityDamageEvent;
import net.bluemangoo.hypertension.fabric.utils.LuckyLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class FallDamage implements EntityDamageListener {
    protected final Random random = new Random();

    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (event.getSource().is(DamageTypeTags.IS_FALL)) {
            return;
        }
        int luckyLevel = LuckyLevel.getLuckyLevel(player);
        float damage = event.getDamage();
        damage = (float) (damage * this.random.nextDouble() * (11 - luckyLevel * 3) / 2);
        event.setDamage(damage);
    }
}
