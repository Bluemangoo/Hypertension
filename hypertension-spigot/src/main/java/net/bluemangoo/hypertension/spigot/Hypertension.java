package net.bluemangoo.hypertension.spigot;

import net.bluemangoo.hypertension.spigot.listener.FallDamage;
import net.bluemangoo.hypertension.spigot.listener.Miss;
import org.bukkit.plugin.java.JavaPlugin;


public final class Hypertension extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Miss(this), this);
        getServer().getPluginManager().registerEvents(new FallDamage(this), this);
    }
}
