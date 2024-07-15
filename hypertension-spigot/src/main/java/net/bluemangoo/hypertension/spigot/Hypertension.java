package net.bluemangoo.hypertension.spigot;

import net.bluemangoo.hypertension.spigot.listener.FallDamage;
import net.bluemangoo.hypertension.spigot.listener.Miss;
import net.bluemangoo.hypertension.spigot.listener.PvpPunishment;
import net.bluemangoo.hypertension.spigot.listener.MobCopy;
import org.bukkit.plugin.java.JavaPlugin;


public final class Hypertension extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Miss(this), this);
        getServer().getPluginManager().registerEvents(new FallDamage(this), this);
        getServer().getPluginManager().registerEvents(new PvpPunishment(this), this);
        getServer().getPluginManager().registerEvents(new MobCopy(this), this);
    }
}
