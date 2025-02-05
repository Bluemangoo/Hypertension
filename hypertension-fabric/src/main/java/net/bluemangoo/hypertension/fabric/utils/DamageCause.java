package net.bluemangoo.hypertension.fabric.utils;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;

public enum DamageCause {
    KILL,
    WORLD_BORDER,
    CONTACT,
    ENTITY_ATTACK,
    PROJECTILE,
    SUFFOCATION,
    FALL,
    FIRE,
    FIRE_TICK,
    LAVA,
    DROWNING,
    BLOCK_EXPLOSION,
    ENTITY_EXPLOSION,
    VOID,
    LIGHTNING,
    SUICIDE,
    STARVATION,
    MAGIC,
    WITHER,
    FALLING_BLOCK,
    THORNS,
    DRAGON_BREATH,
    CUSTOM,
    FLY_INTO_WALL,
    HOT_FLOOR,
    CAMPFIRE,
    CRAMMING,
    DRYOUT,
    FREEZE,
    SONIC_BOOM;

    public static DamageCause getDamageCause(DamageSource source) {
        final Entity damager = source.getDirectEntity();

        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            return DamageCause.BLOCK_EXPLOSION;
        }

        if (damager != null) {
            if (damager instanceof net.minecraft.world.entity.projectile.Projectile) {
                if (damager instanceof ThrownPotion) {
                    return DamageCause.MAGIC;
                }
                if (damager instanceof Projectile) {
                    return DamageCause.PROJECTILE;
                }
            }
            if (source.is(DamageTypes.THORNS)) {
                return DamageCause.THORNS;
            }
            if (source.is(DamageTypes.SONIC_BOOM)) {
                return DamageCause.SONIC_BOOM;
            }
            if (source.is(DamageTypes.FALLING_STALACTITE) || source.is(DamageTypes.FALLING_BLOCK) || source.is(DamageTypes.FALLING_ANVIL)) {
                return DamageCause.FALLING_BLOCK;
            }
            if (source.is(DamageTypes.LIGHTNING_BOLT)) {
                return DamageCause.LIGHTNING;
            }
            if (source.is(DamageTypes.FALL)) {
                return DamageCause.FALL;
            }
            if (source.is(DamageTypes.DRAGON_BREATH)) {
                return DamageCause.DRAGON_BREATH;
            }
            if (source.is(DamageTypes.MAGIC)) {
                return DamageCause.MAGIC;
            }
            return DamageCause.ENTITY_ATTACK;
        }

        if (source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return DamageCause.VOID;
        }
        if (source.is(DamageTypes.LAVA)) {
            return DamageCause.LAVA;
        }
        if (source.is(DamageTypes.CACTUS) || source.is(DamageTypes.SWEET_BERRY_BUSH) || source.is(DamageTypes.STALAGMITE) || source.is(DamageTypes.FALLING_STALACTITE) || source.is(DamageTypes.FALLING_ANVIL)) {
            return DamageCause.CONTACT;
        }
        if (source.is(DamageTypes.HOT_FLOOR)) {
            return DamageCause.HOT_FLOOR;
        }
        if (source.is(DamageTypes.IN_FIRE)) {
            return DamageCause.FIRE;
        }
        if (source.is(DamageTypes.STARVE)) {
            return DamageCause.STARVATION;
        }
        if (source.is(DamageTypes.WITHER)) {
            return DamageCause.WITHER;
        }
        if (source.is(DamageTypes.IN_WALL)) {
            return DamageCause.SUFFOCATION;
        }
        if (source.is(DamageTypes.DROWN)) {
            return DamageCause.DROWNING;
        }
        if (source.is(DamageTypes.ON_FIRE)) {
            return DamageCause.FIRE_TICK;
        }
        if (source.is(DamageTypes.FALL)) {
            return DamageCause.FALL;
        }
        if (source.is(DamageTypes.FLY_INTO_WALL)) {
            return DamageCause.FLY_INTO_WALL;
        }
        if (source.is(DamageTypes.CRAMMING)) {
            return DamageCause.CRAMMING;
        }
        if (source.is(DamageTypes.DRY_OUT)) {
            return DamageCause.DRYOUT;
        }
        if (source.is(DamageTypes.FREEZE)) {
            return DamageCause.FREEZE;
        }
        if (source.is(DamageTypes.GENERIC_KILL)) {
            return DamageCause.KILL;
        }
        if (source.is(DamageTypes.OUTSIDE_BORDER)) {
            return DamageCause.WORLD_BORDER;
        }
        return DamageCause.CUSTOM;
    }
}
