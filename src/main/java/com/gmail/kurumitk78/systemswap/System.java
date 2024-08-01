package com.gmail.kurumitk78.systemswap;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class System {

    private HashMap<UUID, Alter> Alters = new HashMap<UUID, Alter>();
    private UUID systemUUID;
    private Alter fronter;
    private UUID accountUUID;


    public System(UUID setsystemUUID, UUID setaccountUUID){
        systemUUID = setsystemUUID;
        accountUUID = setaccountUUID;

    }

    public void createAlter(String name){
        UUID alterUUID = UUID.randomUUID();
        while(!Alter.getAlterFromUUID(alterUUID).equals(null)){ //I dont know if Java will super rarely create duplicated. But lets avoid that.
            alterUUID = UUID.randomUUID();
        }

        Alters.put(alterUUID, new Alter(name, alterUUID, systemUUID));
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
    public void setFronter(Alter newFronter){
        fronter = newFronter;
    }

    public boolean containsProxyTag(String text){
        AtomicBoolean containstag = new AtomicBoolean();
        containstag.set(false);
        Alters.forEach((key, value) -> {
            boolean containstaglambda = false;
            int proxytaglength = value.getProxytag().length();
            String shortenedtext = text.substring(0, proxytaglength-1);
            if(shortenedtext.equals(value.getProxytag())){ containstag.set(true);}
        });
        return containstag.get();
    }





}
