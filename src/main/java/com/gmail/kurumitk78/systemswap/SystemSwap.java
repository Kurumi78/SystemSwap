package com.gmail.kurumitk78.systemswap;

import com.gmail.kurumitk78.systemswap.commands.createSystemCommand;
import com.gmail.kurumitk78.systemswap.listeners.chatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class SystemSwap extends JavaPlugin {

    private static HashMap<UUID, System> systemMapSystemUUID = new HashMap<UUID, System>();
    private static HashMap<UUID, System> systemMapPlayerUUID = new HashMap<UUID, System>();

    @Override
    public void onEnable() {
        this.getCommand("createsystem").setExecutor(new createSystemCommand());
        Bukkit.getPluginManager().registerEvents(new chatListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static System getSystemFromPlayerUUID(UUID playerUUID){
        return systemMapPlayerUUID.get(playerUUID);
    }

    public static System getSystemFromSystemUUID(UUID systemUUID){
        return systemMapSystemUUID.get(systemUUID);
    }
    public static UUID createSystem(UUID playerUUID){
        UUID systemUUID = new UUID(128,1);
        systemMapPlayerUUID.put(playerUUID, new System(systemUUID, playerUUID));
        systemMapSystemUUID.put(systemUUID, systemMapPlayerUUID.get(playerUUID));
        return systemUUID;
    }
}
