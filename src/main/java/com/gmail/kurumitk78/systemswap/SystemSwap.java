package com.gmail.kurumitk78.systemswap;

import com.gmail.kurumitk78.systemswap.commands.AlterCommand;
import com.gmail.kurumitk78.systemswap.commands.SystemCommand;
import com.gmail.kurumitk78.systemswap.listeners.chatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class SystemSwap extends JavaPlugin {

    private static HashMap<UUID, System> systemMapSystemUUID = new HashMap<UUID, System>();
    private static HashMap<UUID, System> systemMapPlayerUUID = new HashMap<UUID, System>();
    public static Plugin SystemSwapInstance;

    @Override
    public void onEnable() {
        this.getCommand("system").setExecutor(new SystemCommand());
        this.getCommand("alters").setExecutor(new AlterCommand());
        Bukkit.getPluginManager().registerEvents(new chatListener(), this);

        SystemSwapInstance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static System getSystemFromPlayerUUID(UUID playerUUID){
        return systemMapPlayerUUID.getOrDefault(playerUUID, null);
    }

    public static System getSystemFromSystemUUID(UUID systemUUID){
        return systemMapSystemUUID.getOrDefault(systemUUID, null);
    }

    public static boolean isSystem(UUID playerUUID){
        return Objects.nonNull(systemMapPlayerUUID.getOrDefault(playerUUID, null));

    }
    public static UUID createSystem(UUID playerUUID){
        UUID systemUUID = UUID.randomUUID();
        while(Objects.nonNull(systemMapSystemUUID.getOrDefault(systemUUID, null))){ //I dont know if Java will super rarely create duplicated UUID's. But lets avoid that.
            systemUUID= UUID.randomUUID();
        }
        systemMapPlayerUUID.put(playerUUID, new System(systemUUID, playerUUID));
        systemMapSystemUUID.put(systemUUID, systemMapPlayerUUID.get(playerUUID));
        return systemUUID;
    }
    public static void deleteSystemPlayerUUID(UUID playerUUID){
        systemMapPlayerUUID.remove(playerUUID);
    }
    public static void deleteSystemSystemUUID(UUID systemUUID){
        systemMapSystemUUID.remove(systemUUID);
    }
}
