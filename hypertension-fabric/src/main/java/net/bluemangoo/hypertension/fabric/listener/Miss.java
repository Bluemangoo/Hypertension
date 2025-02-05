package net.bluemangoo.hypertension.fabric.listener;

import com.google.common.collect.ImmutableList;
import net.bluemangoo.hypertension.fabric.event.EntityDamageEvent;
import net.bluemangoo.hypertension.fabric.utils.DamageCause;
import net.bluemangoo.hypertension.fabric.utils.LuckyLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;

public class Miss implements EntityDamageListener {
    protected final ImmutableList<DamageCause> CAUSE_NOT_TO_DIE = ImmutableList.of(
        DamageCause.FIRE
    );
    protected final ImmutableList<DamageCause> PLAYER_CAUSE_TO_DECREASE_DAMAGE = ImmutableList.of(
        DamageCause.ENTITY_ATTACK,
        DamageCause.PROJECTILE);
    protected final ImmutableList<DamageCause> CAUSE_TO_DECREASE_DAMAGE = ImmutableList.of(
        DamageCause.DRAGON_BREATH,
        DamageCause.LIGHTNING,
        DamageCause.MAGIC,
        DamageCause.ENTITY_EXPLOSION,
        DamageCause.BLOCK_EXPLOSION,
        DamageCause.FALL
    );
    protected final Random random = new Random();

    protected void spawnMissParticle(Mob mob) {
        var boundingBox = mob.getBoundingBox();
        var center = boundingBox.getCenter();
        Level level = mob.level();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                ParticleTypes.COMPOSTER,
                center.x, center.y, center.z,
                (int) Math.ceil(Math.sqrt(boundingBox.getSize())) * 50,
                boundingBox.getXsize() / 1.5,
                boundingBox.getYsize() / 1.5,
                boundingBox.getZsize() / 1.5,
                0.0
            );
        }
    }

    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }

        if (event.getEntity() instanceof Player && !(event.getSource().getEntity() instanceof Player)) {
            return;
        }

        if (event.getSource().getEntity() == null) {
            return;
        }
        var cause = DamageCause.getDamageCause(event.getSource());

        if (mob.getHealth() - event.getDamage() <= 0) {
            if (this.CAUSE_NOT_TO_DIE.contains(cause)) {
                event.setCanceled(true);
                this.spawnMissParticle(mob);
                return;
            }
        }

        if ((this.PLAYER_CAUSE_TO_DECREASE_DAMAGE.contains(cause) && (event.getSource().getEntity() instanceof Player))
            || this.CAUSE_TO_DECREASE_DAMAGE.contains(cause)) {
            double fullHealth = mob.getMaxHealth();
            double currentHealth = mob.getHealth();
            currentHealth = Math.ceil(currentHealth);
            if (currentHealth > fullHealth) {
                fullHealth = currentHealth;
            }
            if (fullHealth > currentHealth * 100) {
                fullHealth = currentHealth * 100;
            }
            int luckyLevel = 0;
            if (event.getSource().getEntity() instanceof Player player) {
                luckyLevel = LuckyLevel.getLuckyLevel(player);
            }
            if ((this.random.nextDouble() * fullHealth > currentHealth * 3) && !(this.random.nextInt(10 - luckyLevel * 3) == 0)) {
                if ((event.getSource().getEntity() instanceof Player player) && this.random.nextInt(4) == 0) {
                    player.hurt(event.getSource(), (float) (player.getHealth() - this.random.nextDouble() * (2 - (double) luckyLevel / 2) * event.getDamage()));
                }
                this.spawnMissParticle(mob);
                if (this.random.nextInt(4) > 0) {
                    event.setCanceled(true);
                } else {
                    event.setDamage(0);
                }
            }
        }
    }
}
