package net.bluemangoo.hypertension.fabric.listener;

import net.bluemangoo.hypertension.fabric.event.EntityDeathEvent;

public interface EntityDeathListener {
    void onEntityDeath(EntityDeathEvent event);
}
