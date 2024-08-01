package com.gmail.kurumitk78.systemswap.listeners;

import com.gmail.kurumitk78.systemswap.Alter;
import com.gmail.kurumitk78.systemswap.System;
import com.gmail.kurumitk78.systemswap.SystemSwap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class chatListener implements Listener {

    @EventHandler
    public void checkForFrontChanges(final AsyncPlayerChatEvent event){
        Player chatter = event.getPlayer();
        if(!SystemSwap.getSystemFromPlayerUUID(chatter.getUniqueId()).equals(null)){
            System playersSystem = SystemSwap.getSystemFromPlayerUUID(event.getPlayer().getUniqueId());
            if(playersSystem.containsProxyTag(event.getMessage())){
                HashMap<UUID, Alter> alters = playersSystem.getAlterList();
                AtomicReference<UUID> atomicAlterUUID = new AtomicReference<UUID>();
                alters.forEach((key, value) -> {
                    int proxytaglength = value.getProxytag().length();
                    String shortenedtext = event.getMessage().substring(0, proxytaglength-1);
                    if(shortenedtext.equals(value.getProxytag())){
                        atomicAlterUUID.set(value.getUniqueID());}
                });
                if(!playersSystem.getFronter().equals(playersSystem.getAlter(atomicAlterUUID.get()))) {
                    playersSystem.setFronter(playersSystem.getAlter(atomicAlterUUID.get()));
                }
            }
        }



    }
}
