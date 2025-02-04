package net.bluemangoo.hypertension.spigot.listener;

import com.google.common.collect.ImmutableList;
import net.bluemangoo.hypertension.spigot.Hypertension;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

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
            EntityType.GHAST,
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER
        );
    protected final static String COPIED_META_KEY = "copied";

    public MobCopy(Hypertension plugin) {
        this.plugin = plugin;
    }

    void spawn(@NotNull EntityDeathEvent event, int count) {
        for (int i = 0; i < count; i++) {
            Entity entity = event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), event.getEntity().getType());
            entity.setMetadata(COPIED_META_KEY, new FixedMetadataValue(this.plugin, true));
        }
    }

    void spawn(@NotNull EntityDeathEvent event) {
        spawn(event, 1);
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if (event.getEntity().hasMetadata(COPIED_META_KEY)) {
            event.setDroppedExp(0);
            event.getDrops().clear();
        }
        if (this.respawns.contains(event.getEntity().getType())) {
            if (this.random.nextInt(10) < 3) {
                spawn(event);
            } else if (this.random.nextInt(10) < 3) {
                spawn(event, 2);
            }
        }
    }
}
