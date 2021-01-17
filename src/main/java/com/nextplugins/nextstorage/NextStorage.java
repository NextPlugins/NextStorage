package com.nextplugins.nextstorage;

import com.google.inject.Injector;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.nextplugins.nextstorage.command.StorageCommand;
import me.bristermitten.pdm.PluginDependencyManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NextStorage extends JavaPlugin {

    private Injector injector;

    @Override
    public void onLoad() {

        this.saveDefaultConfig();

    }

    @Override
    public void onEnable() {

        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {

            PluginManager pluginManager = Bukkit.getPluginManager();
            try {

                InventoryManager.enable(this);
                this.getLogger().info("Enabled InventoryFramework");

                BukkitFrame bukkitFrame = new BukkitFrame(this);
                bukkitFrame.registerCommands(
                        this.injector.getInstance(StorageCommand.class)
                );

                this.getLogger().info("Registered commands and events successfully");


            }catch (Throwable t) {

                t.printStackTrace();
                this.getLogger().severe("A error occurred on plugin startup, turning off");

                pluginManager.disablePlugin(this);

            }

        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
