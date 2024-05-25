package net.bluemangoo.hypertension.spigot;

import net.bluemangoo.hypertension.spigot.listener.DamageListener;
import org.bukkit.plugin.java.JavaPlugin;


public final class Hypertension extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
