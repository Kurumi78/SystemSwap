package com.gmail.kurumitk78.systemswap;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class SystemSwap extends JavaPlugin {

    private static HashMap<UUID, System> systemMapSystemUUID = new HashMap<UUID, System>();
    private static HashMap<UUID, System> systemMapPlayerUUID = new HashMap<UUID, System>();

    @Override
    public void onEnable() {
        // Plugin startup logic

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
    public void createSystem(UUID playerUUID){
        UUID systemUUID = new UUID(128,128);
        systemMapPlayerUUID.put(playerUUID, new System(systemUUID, playerUUID));
        systemMapSystemUUID.put(systemUUID, systemMapPlayerUUID.get(playerUUID));
    }
}
