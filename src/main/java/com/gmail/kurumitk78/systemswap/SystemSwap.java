package com.gmail.kurumitk78.systemswap;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class SystemSwap extends JavaPlugin {

    private HashMap<UUID, System> systemMap = new HashMap<UUID, System>();

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public System getSystem(UUID playerUUID){
        return systemMap.get(playerUUID);
    }
    public void createSystem(UUID playerUUID){
        String systemUUID = ""; //TO-DO add system to create UUIDs
        systemMap.put(playerUUID, new System(systemUUID, playerUUID));
    }
}
