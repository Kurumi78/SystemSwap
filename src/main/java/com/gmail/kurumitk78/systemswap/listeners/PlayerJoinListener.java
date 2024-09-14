package com.gmail.kurumitk78.systemswap.listeners;

import com.gmail.kurumitk78.systemswap.SystemSwap;
import com.gmail.kurumitk78.systemswap.database.SQLiteHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void joinListener(final PlayerJoinEvent event) throws SQLException {
        ResultSet systemData = SQLiteHandler.dbCallReturn("SELECT * FROM systems WHERE playerUUID =" +event.getPlayer().getUniqueId());
        if(!systemData.first()){
            SystemSwap.initPlayerSystem(systemData, event.getPlayer().getUniqueId());
        }


    }
}
