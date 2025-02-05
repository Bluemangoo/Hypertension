package net.bluemangoo.hypertension.fabric.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class EntityDeathEvent {
    private Entity entity;
    private DamageSource source;
    private boolean doDrop = true;
    private boolean doDropExp = true;

    public boolean shouldDrop() {
        return doDrop;
    }

    public void skipDrop() {
        this.doDrop = false;
    }

    public boolean shouldDropExp() {
        return doDropExp;
    }

    public void skipDropExp() {
        this.doDropExp = false;
    }


    public EntityDeathEvent(Entity entity, DamageSource source) {
        this.entity = entity;
        this.source = source;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public DamageSource getSource() {
        return source;
    }

    public void setSource(DamageSource source) {
        this.source = source;
    }
}
