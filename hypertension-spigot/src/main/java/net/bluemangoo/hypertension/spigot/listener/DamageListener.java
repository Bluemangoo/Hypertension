package net.bluemangoo.hypertension.spigot.listener;

import com.google.common.collect.ImmutableList;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

public class DamageListener implements Listener {
    protected final ImmutableList<EntityDamageEvent.DamageCause> CAUSE_NOT_TO_DIE;
    protected final ImmutableList<EntityDamageEvent.DamageCause> PLAYER_CAUSE_TO_DECREASE_DAMAGE;
    protected final ImmutableList<EntityDamageEvent.DamageCause> CAUSE_TO_DECREASE_DAMAGE;

    public DamageListener() {
        CAUSE_NOT_TO_DIE = ImmutableList.of(
            EntityDamageEvent.DamageCause.FIRE
        );
        PLAYER_CAUSE_TO_DECREASE_DAMAGE = ImmutableList.of(
            EntityDamageEvent.DamageCause.ENTITY_ATTACK,
            EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK,
            EntityDamageEvent.DamageCause.PROJECTILE
        );
        CAUSE_TO_DECREASE_DAMAGE = ImmutableList.of(
            EntityDamageEvent.DamageCause.DRAGON_BREATH
        );
    }

    @EventHandler(ignoreCancelled = true)
    void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Mob)) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            return;
        }

        Logger logger = PluginLogger.getLogger("Hypertension");

        if (event.getDamageSource().getCausingEntity() == null) {
            return;
        }

        Mob mob = (Mob) event.getEntity();
        if (mob.getHealth() - event.getDamage() <= 0) {
            if (this.CAUSE_NOT_TO_DIE.contains(event.getCause())) {
                event.setCancelled(true);
                mob.getWorld().spawnParticle(Particle.COMPOSTER, mob.getLocation(), 10, .1D, .1D, .1D);
                return;
            }
        }
        if (this.PLAYER_CAUSE_TO_DECREASE_DAMAGE.contains(event.getCause()) && (event.getDamageSource().getCausingEntity() instanceof Player)
            || this.CAUSE_TO_DECREASE_DAMAGE.contains(event.getCause()))
            if (this.PLAYER_CAUSE_TO_DECREASE_DAMAGE.contains(event.getCause())) {
                double fullHealth = Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
                double currentHealth = mob.getHealth();
                if (currentHealth > fullHealth) {
                    fullHealth = currentHealth;
                }
                if (new Random().nextDouble() * fullHealth > currentHealth) {
                    event.setCancelled(true);
                    mob.getWorld().spawnParticle(Particle.COMPOSTER, mob.getLocation().add(new Vector(0, 1.3, 0)), 30, .2D, .7D, .2D);
                }
            }
    }

}
