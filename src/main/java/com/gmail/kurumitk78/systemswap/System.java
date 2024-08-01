package com.gmail.kurumitk78.systemswap;

import java.util.HashMap;
import java.util.UUID;

public class System {

    private HashMap<String, Alter> Alters = new HashMap<String, Alter>();
    private String systemUUID;
    private Alter fronter;
    private UUID accountUUID;


    public System(String setsystemUUID, UUID setaccountUUID){
        systemUUID = setsystemUUID;
        accountUUID = setaccountUUID;

    }



    public Alter getAlter(String uuid){
        return Alters.getOrDefault(uuid, null);
    }
    public HashMap getAlterList(){
        return Alters;
    }
    public String getSystemUUID(){
        return systemUUID;
    }
    public UUID getAccountUUID(){
        return accountUUID;
    }
    public void setFronter(Alter newFronter){
        fronter = newFronter;
    }


}
