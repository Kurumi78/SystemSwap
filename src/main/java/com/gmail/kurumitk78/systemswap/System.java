package com.gmail.kurumitk78.systemswap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class System {

    private HashMap<UUID, Alter> Alters = new HashMap<UUID, Alter>();
    private UUID systemUUID;
    private Alter fronter;
    private UUID accountUUID;


    public System(UUID setsystemUUID, UUID setaccountUUID){
        systemUUID = setsystemUUID;
        accountUUID = setaccountUUID;

    }

    public UUID createAlter(String name){
        UUID alterUUID = UUID.randomUUID();
        while(Objects.nonNull(Alter.getAlterFromUUID(alterUUID))){ //I dont know if Java will super rarely create duplicated. But lets avoid that.
            alterUUID = UUID.randomUUID();
        }

        Alters.put(alterUUID, new Alter(name, alterUUID, systemUUID));
        return alterUUID;
    }

    public Alter getAlter(UUID uuid){
        return Alters.getOrDefault(uuid, null);
    }
    public HashMap getAlterList(){
        return Alters;
    }
    public UUID getSystemUUID(){
        return systemUUID;
    }
    public UUID getAccountUUID(){
        return accountUUID;
    }

    public Alter getFronter() {
        return fronter;
    }

    public void setFronter(Alter newFronter){
        if(Objects.nonNull(fronter)) {
            if (fronter.getUniqueID() == newFronter.getUniqueID()) {
                return;
            }
        }
        fronter = newFronter;
        Bukkit.getPlayer(accountUUID).sendMessage("Fronter changed to " + newFronter.getName());
        if(newFronter.getNickname() != ""){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nick player " + Bukkit.getPlayer(accountUUID).getName() + " " + newFronter.getNickname());
        }
    }

    public boolean containsProxyTag(String text){
        AtomicBoolean containstag = new AtomicBoolean();
        containstag.set(false);
        Alters.forEach((key, value) -> {
            boolean containstaglambda = false;
            int proxytaglength = value.getProxytag().length();
            String shortenedtext = text.substring(0, proxytaglength);
           // Bukkit.getLogger().log(Level.WARNING, "proxylength is " + proxytaglength + " and shortenedtext is" + shortenedtext + " and proxy tag is " + value.getProxytag());
            if(shortenedtext.equals(value.getProxytag())&& value.getProxytag().length() != 0){ containstag.set(true);}
        });
        return containstag.get();
    }





}
