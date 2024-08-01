package com.gmail.kurumitk78.systemswap;

import java.util.HashMap;
import java.util.UUID;

public class System {

    private static HashMap<UUID, Alter> Alters = new HashMap<UUID, Alter>();
    private UUID systemUUID;
    private Alter fronter;
    private UUID accountUUID;


    public System(UUID setsystemUUID, UUID setaccountUUID){
        systemUUID = setsystemUUID;
        accountUUID = setaccountUUID;

    }

    public void createAlter(String name){
        UUID alterUUID = new UUID(128,128);  //TO-DO add system to create UUIDs
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
