package net.bluemangoo.hypertension.fabric.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class EntityDamageEvent {
    private final Entity entity;
    private DamageSource source;
    private float damage;
    private boolean canceled = false;

    public EntityDamageEvent(Entity entity, DamageSource source, float amount) {
        this.entity = entity;
        this.source = source;
        this.damage = amount;
    }

    public Entity getEntity() {
        return entity;
    }

    public DamageSource getSource() {
        return source;
    }

    public void setSource(DamageSource source) {
        this.source = source;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
