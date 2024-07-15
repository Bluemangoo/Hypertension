package net.bluemangoo.hypertension.spigot.listener;

import com.google.common.collect.ImmutableList;
import net.bluemangoo.hypertension.spigot.Hypertension;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class MobCopy implements Listener {
    protected final Hypertension plugin;
    protected final Random random = new Random();
    protected final ImmutableList<EntityType> respawns =
        ImmutableList.of(
            EntityType.SILVERFISH,
            EntityType.CREEPER,
            EntityType.ENDERMITE,
            EntityType.PHANTOM,
            EntityType.SLIME,
            EntityType.MAGMA_CUBE,
            EntityType.GHAST
        );

    public MobCopy(Hypertension plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if (this.respawns.contains(event.getEntity().getType())) {
            if (this.random.nextInt(10) < 3) {
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), event.getEntity().getType());
            } else if (this.random.nextInt(10) < 3) {
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), event.getEntity().getType());
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), event.getEntity().getType());
            }
        }
    }
}
