package net.bluemangoo.hypertension.spigot.utils;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LuckyLevel {
    static public int getLuckyLevel(Player player) {
        int luckyLevel = 0;
        PotionEffect luckEffect = player.getPotionEffect(PotionEffectType.LUCK);
        if (luckEffect != null) {
            luckyLevel += luckEffect.getAmplifier() + 1;
        }
        if (luckyLevel > 3) {
            luckyLevel = 3;
        }
        return luckyLevel;
    }
}
