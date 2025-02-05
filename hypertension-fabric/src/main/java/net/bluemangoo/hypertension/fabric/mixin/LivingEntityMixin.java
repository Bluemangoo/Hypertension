package net.bluemangoo.hypertension.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.bluemangoo.hypertension.fabric.Hypertension;
import net.bluemangoo.hypertension.fabric.event.EntityDamageEvent;
import net.bluemangoo.hypertension.fabric.event.EntityDeathEvent;
import net.bluemangoo.hypertension.fabric.mixinutils.DropEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements DropEntity {
    @Shadow
    public abstract void skipDropExperience();

    @Unique
    private boolean doDrop = true;

    @Unique
    public void hypertension$skipDrop() {
        doDrop = false;
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void hurt(DamageSource damageSource,
                     float f,
                     CallbackInfoReturnable<Boolean> cir,
                     @Local(argsOnly = true) LocalRef<DamageSource> damageSourceRef,
                     @Local(argsOnly = true) LocalFloatRef fRef) {
        var event = new EntityDamageEvent((LivingEntity) (Object) this, damageSource, f);
        for (var listeners : Hypertension.ENTITY_DAMAGE_LISTENERS) {
            listeners.onEntityDamage(event);
        }
        fRef.set(f);
        damageSourceRef.set(event.getSource());
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "die", at = @At("HEAD"))
    public void die(DamageSource damageSource, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        if (entity.level().isClientSide) {
            return;
        }
        var event = new EntityDeathEvent(entity, damageSource);
        for (var listeners : Hypertension.ENTITY_DEATH_LISTENERS) {
            listeners.onEntityDeath(event);
        }
        if (!event.shouldDrop()) {
            System.out.println("Skipping drop");
            this.hypertension$skipDrop();
        }
        if (!event.shouldDropExp()) {
            System.out.println("Skipping drop exp");
            this.skipDropExperience();
        }
    }

    public boolean hypertension$doDrop() {
        return doDrop;
    }
}
