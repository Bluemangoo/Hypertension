package net.bluemangoo.hypertension.spigot.listener;

import com.google.common.collect.ImmutableList;
import net.bluemangoo.hypertension.spigot.Hypertension;
import net.bluemangoo.hypertension.spigot.utils.LuckyLevel;
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

public class Miss implements Listener {
    protected final Hypertension plugin;
    protected final ImmutableList<EntityDamageEvent.DamageCause> CAUSE_NOT_TO_DIE = ImmutableList.of(
        EntityDamageEvent.DamageCause.FIRE
    );
    protected final ImmutableList<EntityDamageEvent.DamageCause> PLAYER_CAUSE_TO_DECREASE_DAMAGE = ImmutableList.of(
        EntityDamageEvent.DamageCause.ENTITY_ATTACK,
        EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK,
        EntityDamageEvent.DamageCause.PROJECTILE);
    protected final ImmutableList<EntityDamageEvent.DamageCause> CAUSE_TO_DECREASE_DAMAGE = ImmutableList.of(
        EntityDamageEvent.DamageCause.DRAGON_BREATH,
        EntityDamageEvent.DamageCause.POISON,
        EntityDamageEvent.DamageCause.LIGHTNING,
        EntityDamageEvent.DamageCause.MAGIC,
        EntityDamageEvent.DamageCause.ENTITY_EXPLOSION,
        EntityDamageEvent.DamageCause.BLOCK_EXPLOSION,
        EntityDamageEvent.DamageCause.FALL
    );
    protected final Random random = new Random();

    public Miss(Hypertension plugin) {
        this.plugin = plugin;
    }

    protected void spawnMissParticle(@NotNull Mob mob) {
        BoundingBox boundingBox = mob.getBoundingBox();
        mob.getWorld().spawnParticle(
            Particle.COMPOSTER, mob.getLocation().setDirection(boundingBox.getCenter()),
            (int) Math.ceil(Math.sqrt(boundingBox.getVolume())) * 50,
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
            currentHealth = Math.ceil(currentHealth);
            fullHealth *= 2;
            if (currentHealth > fullHealth) {
                fullHealth = currentHealth;
            }
            if (fullHealth > currentHealth * 100) {
                fullHealth = currentHealth * 100;
            }
            int luckyLevel = 0;
            if ((event.getDamageSource().getCausingEntity() instanceof Player)) {
                luckyLevel = LuckyLevel.getLuckyLevel((Player) event.getDamageSource().getCausingEntity());
            }
            if ((this.random.nextDouble() * fullHealth > currentHealth) && !(this.random.nextInt(0, 10 - luckyLevel * 3) == 0)) {
                if ((event.getDamageSource().getCausingEntity() instanceof Player player) && this.random.nextInt(0, 4) == 0) {
                    player.damage(player.getHealth() - this.random.nextDouble() * 2 * event.getDamage(), event.getDamageSource());
                }
                if (this.random.nextInt(0, 4) > 0) {
                    event.setCancelled(true);
                } else {
                    event.setDamage(0);
                }
                this.spawnMissParticle(mob);
            }
        }
    }

}
