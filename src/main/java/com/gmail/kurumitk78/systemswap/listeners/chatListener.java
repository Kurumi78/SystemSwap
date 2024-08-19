package com.gmail.kurumitk78.systemswap.listeners;

import com.gmail.kurumitk78.systemswap.Alter;
import com.gmail.kurumitk78.systemswap.System;
import com.gmail.kurumitk78.systemswap.SystemSwap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.sleep;

public class chatListener implements Listener {

    @EventHandler
    public void checkForFrontChanges(final AsyncPlayerChatEvent event) throws InterruptedException {

        if(event.isAsynchronous()){
            Player chatter = event.getPlayer();
        if(SystemSwap.getSystemFromPlayerUUID(chatter.getUniqueId()) != null){
            System playersSystem = SystemSwap.getSystemFromPlayerUUID(event.getPlayer().getUniqueId());
            if(playersSystem.containsProxyTag(event.getMessage())){
                ArrayList<Alter> alters = new ArrayList<>();
                alters.addAll(playersSystem.getAlterList().values());

                for(int iterations = 0; iterations < alters.size(); iterations++) {
                    int proxytaglength = alters.get(iterations).getProxytag().length();
                    String shortenedtext = event.getMessage().substring(0, proxytaglength);
                   // event.getPlayer().sendMessage("proxylength is " + proxytaglength + " and shortenedtext is" + shortenedtext + " and proxy tag is " + alters.get(iterations).getProxytag());
                    if (shortenedtext.equals(alters.get(iterations).getProxytag())) {
                        final Alter newFronter = alters.get(iterations);
                        event.setMessage(event.getMessage().substring(proxytaglength));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                playersSystem.setFronter(newFronter);

                            }
                        }.runTask(SystemSwap.SystemSwapInstance);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                event.getPlayer().chat(event.getMessage());

                            }
                        }.runTaskLater(SystemSwap.SystemSwapInstance, 10);
                        event.setCancelled(true);
                    }

                }


            }
        }
        }



    }
}
