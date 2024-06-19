package net.bluemangoo.hypertension.spigot.listener;

import net.bluemangoo.hypertension.spigot.Hypertension;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class PvpPunishment implements Listener {
    protected final Hypertension plugin;
    protected final Random random = new Random();

    public PvpPunishment(Hypertension plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    void onEntityDamage(@NotNull EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!(event.getDamageSource().getCausingEntity() instanceof Player player)) {
            return;
        }
        if (event.getEntity() == event.getDamageSource().getCausingEntity()) {
            return;
        }
        player.damage(event.getDamage() * 3 * random.nextDouble(), event.getDamageSource());
    }
}
