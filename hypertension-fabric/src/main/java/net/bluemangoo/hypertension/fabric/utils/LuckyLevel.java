package net.bluemangoo.hypertension.fabric.utils;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class LuckyLevel {
    static public int getLuckyLevel(Player player) {
        int luckyLevel = 0;
        var luckEffect= player.getEffect(MobEffects.LUCK);
        if (luckEffect != null) {
            luckyLevel += luckEffect.getAmplifier() + 1;
        }
        if (luckyLevel > 3) {
            luckyLevel = 3;
        }
        return luckyLevel;
    }
}
