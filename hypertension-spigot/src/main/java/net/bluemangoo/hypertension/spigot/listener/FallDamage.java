package net.bluemangoo.hypertension.spigot.listener;

import net.bluemangoo.hypertension.spigot.Hypertension;
import net.bluemangoo.hypertension.spigot.utils.LuckyLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FallDamage implements Listener {
    protected final Hypertension plugin;
    protected final Random random = new Random();

    public FallDamage(Hypertension plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    void onEntityDamage(@NotNull EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        int luckyLevel = LuckyLevel.getLuckyLevel(player);
        double damage = event.getDamage();
        damage = damage * this.random.nextDouble() * (10 - luckyLevel * 3);
        event.setDamage(damage);
    }
}
