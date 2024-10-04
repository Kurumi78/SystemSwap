package com.gmail.kurumitk78.systemswap;

import com.gmail.kurumitk78.systemswap.commands.AlterCommand;
import com.gmail.kurumitk78.systemswap.commands.SystemCommand;
import com.gmail.kurumitk78.systemswap.database.SQLiteHandler;
import com.gmail.kurumitk78.systemswap.listeners.PlayerJoinListener;
import com.gmail.kurumitk78.systemswap.listeners.chatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class SystemSwap extends JavaPlugin {

    private static HashMap<UUID, System> systemMapSystemUUID = new HashMap<UUID, System>();
    private static HashMap<UUID, System> systemMapPlayerUUID = new HashMap<UUID, System>();
    public static Plugin SystemSwapInstance;

    @Override
    public void onEnable() {
        SystemSwapInstance = this;
       if(this.getConfig().get("pluginSetup") != "true"){
           this.saveDefaultConfig();
           SQLiteHandler.firstRun();
           this.getConfig().set("pluginSetup", "true");
           this.saveConfig();
       }

        this.getCommand("system").setExecutor(new SystemCommand());
        this.getCommand("alters").setExecutor(new AlterCommand());
        Bukkit.getPluginManager().registerEvents(new chatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);


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
        SQLiteHandler.dbCall("INSERT INTO systems(systemUUID, playerUUID) VALUES('" + systemUUID.toString() + "', '" + playerUUID.toString() + "');");
        return systemUUID;
    }
    public static void deleteSystemPlayerUUID(UUID playerUUID){
        systemMapPlayerUUID.remove(playerUUID);
    }
    public static void deleteSystemSystemUUID(UUID systemUUID){
        systemMapSystemUUID.remove(systemUUID);
    }

    public static void initPlayerSystem(ResultSet playerData, UUID playerUUID) throws SQLException {
        UUID systemUUID;
        systemUUID = UUID.fromString(playerData.getString("systemUUID"));
        playerData.close();
        ResultSet alterData = SQLiteHandler.dbCallReturn("SELECT * FROM alters WHERE systemUUID = '" +systemUUID + "';");



        systemMapPlayerUUID.put(playerUUID, new System(systemUUID, playerUUID, alterData));
        systemMapSystemUUID.put(systemUUID, systemMapPlayerUUID.get(playerUUID));


    }


}
