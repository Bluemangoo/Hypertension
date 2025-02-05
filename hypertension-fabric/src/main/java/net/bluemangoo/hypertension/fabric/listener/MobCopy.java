package net.bluemangoo.hypertension.fabric.listener;

import com.google.common.collect.ImmutableList;
import net.bluemangoo.hypertension.fabric.event.EntityDeathEvent;
import net.bluemangoo.hypertension.fabric.mixinutils.DropEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class MobCopy implements EntityDeathListener {
    protected final Random random = new Random();
    protected final ImmutableList<EntityType<?>> respawns =
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

    protected void spawn(Entity entity, int count) {
        Level level = entity.level();
        if (level instanceof ServerLevel serverLevel) {
            Vec3 position = entity.position();
            for (int i = 0; i < count; i++) {
                var type = entity.getType();
                Entity newEntity = type.create(serverLevel);
                if (newEntity != null) {
                    newEntity.moveTo(position.x, position.y, position.z, entity.getYRot(), entity.getXRot());
                    var dropEntity = (DropEntity) newEntity;
                    dropEntity.hypertension$skipDrop();
                    dropEntity.hypertension$skipDropExp();
                    serverLevel.addFreshEntity(newEntity);
                }
                spawnParticle(entity);
            }
        }
    }

    protected void spawn(Entity entity) {
        spawn(entity, 1);
    }

    protected void spawnParticle(Entity entity) {
        var boundingBox = entity.getBoundingBox();
        var center = boundingBox.getCenter();
        Level level = entity.level();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                ParticleTypes.HEART,
                center.x, center.y, center.z,
                (int) Math.ceil(Math.sqrt(boundingBox.getSize())) * 20,
                boundingBox.getXsize() / 1.5,
                boundingBox.getYsize() / 1.5,
                boundingBox.getZsize() / 1.5,
                0.0
            );
        }
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        if (respawns.contains(event.getEntity().getType())) {
            if (random.nextInt(20) < 3) {
                spawn(event.getEntity());
            } else if (random.nextInt(20) < 3) {
                spawn(event.getEntity(), 2);
            }
        }
    }
}
