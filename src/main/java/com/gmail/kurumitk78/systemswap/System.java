package com.gmail.kurumitk78.systemswap;

import java.util.HashMap;
import java.util.UUID;

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

    public Alter getAlter(String uuid){
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



}
