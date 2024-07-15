package net.bluemangoo.hypertension.spigot.listener;

import net.bluemangoo.hypertension.spigot.Hypertension;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class SilverfishCopy implements Listener {
    protected final Hypertension plugin;
    protected final Random random = new Random();

    public SilverfishCopy(Hypertension plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if (event.getEntity() instanceof Silverfish) {
            if (this.random.nextInt(10) < 3) {
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), event.getEntity().getType());
            } else if (this.random.nextInt(10) < 3) {
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), event.getEntity().getType());
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), event.getEntity().getType());
            }
        }
    }
}
