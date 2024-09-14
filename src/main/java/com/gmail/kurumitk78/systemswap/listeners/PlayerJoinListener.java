package com.gmail.kurumitk78.systemswap.listeners;

import com.gmail.kurumitk78.systemswap.SystemSwap;
import com.gmail.kurumitk78.systemswap.database.SQLiteHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void joinListener(final PlayerJoinEvent event){


            new BukkitRunnable() {
                @Override
                public void run() {
                    ResultSet systemData = SQLiteHandler.dbCallReturn("SELECT * FROM systems WHERE playerUUID = '" +event.getPlayer().getUniqueId() + "';");

                    try {
                        SystemSwap.initPlayerSystem(systemData, event.getPlayer().getUniqueId());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }.runTaskAsynchronously(SystemSwap.SystemSwapInstance);



    }
}
