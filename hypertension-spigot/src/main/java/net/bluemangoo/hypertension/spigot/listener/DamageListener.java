package net.bluemangoo.hypertension.spigot.listener;

import com.google.common.collect.ImmutableList;
import net.bluemangoo.hypertension.spigot.Hypertension;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class DamageListener implements Listener {
    protected final Hypertension plugin;
    protected final ImmutableList<EntityDamageEvent.DamageCause> CAUSE_NOT_TO_DIE = ImmutableList.of(
        EntityDamageEvent.DamageCause.FIRE
    );
    protected final ImmutableList<EntityDamageEvent.DamageCause> PLAYER_CAUSE_TO_DECREASE_DAMAGE = ImmutableList.of(
        EntityDamageEvent.DamageCause.ENTITY_ATTACK,
        EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK,
        EntityDamageEvent.DamageCause.PROJECTILE);
    protected final ImmutableList<EntityDamageEvent.DamageCause> CAUSE_TO_DECREASE_DAMAGE = ImmutableList.of(
        EntityDamageEvent.DamageCause.DRAGON_BREATH
    );
    protected final Random random = new Random();

    public DamageListener(Hypertension plugin) {
        this.plugin = plugin;
    }

    protected void spawnMissParticle(@NotNull Mob mob) {
        BoundingBox boundingBox = mob.getBoundingBox();
        mob.getWorld().spawnParticle(
            Particle.COMPOSTER, mob.getLocation().setDirection(boundingBox.getCenter()),
            (int) Math.ceil(boundingBox.getVolume()) * 30,
            boundingBox.getWidthX() / 1.5,
            boundingBox.getHeight() / 1.5,
            boundingBox.getWidthZ() / 1.5);
    }

    @EventHandler(ignoreCancelled = true)
    void onEntityDamage(@NotNull EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            return;
        }

        if (event.getDamageSource().getCausingEntity() == null) {
            return;
        }

        if (mob.getHealth() - event.getDamage() <= 0) {
            if (this.CAUSE_NOT_TO_DIE.contains(event.getCause())) {
                event.setCancelled(true);
                this.spawnMissParticle(mob);
                return;
            }
        }
        if (this.PLAYER_CAUSE_TO_DECREASE_DAMAGE.contains(event.getCause()) && (event.getDamageSource().getCausingEntity() instanceof Player)
            || this.CAUSE_TO_DECREASE_DAMAGE.contains(event.getCause())) {
            double fullHealth = Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
            double currentHealth = mob.getHealth();
            if (currentHealth > fullHealth) {
                fullHealth = currentHealth;
            }
            if (this.random.nextDouble() * fullHealth > currentHealth) {
                if (this.random.nextInt(0, 3) > 0) {
                    event.setCancelled(true);
                } else {
                    event.setDamage(0);
                }
                this.spawnMissParticle(mob);
                return;
            }
            if (this.random.nextInt(0, 3) == 0) {
                if (this.random.nextInt(0, 3) > 0) {
                    event.setCancelled(true);
                } else {
                    event.setDamage(event.getDamage() * this.random.nextDouble());
                }
                this.spawnMissParticle(mob);
            }
        }
    }

}
