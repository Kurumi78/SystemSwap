package com.gmail.kurumitk78.systemswap;

import com.gmail.kurumitk78.systemswap.database.SQLiteHandler;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class Alter {
    private static HashMap<UUID, Alter> AllAlters = new HashMap<UUID, Alter>();



    private String name = "";
    private String description = "";
    private String proxytag = "";
    private UUID uniqueID;
    private UUID associatedsystemUUID;
    private String nickname = "";



    public Alter(String newName, UUID newUniqueID, UUID systemUUID){
        name = newName;
        uniqueID = newUniqueID;
        associatedsystemUUID = systemUUID;
        AllAlters.put(newUniqueID, this);
    }

    public void setNickname(String newNick){
        SQLiteHandler.dbCall("UPDATE alters SET 'nickname' = '"+ newNick + "' WHERE alterUUID = ' " +uniqueID.toString() + "';");
        nickname = newNick;
    }

    public void setName(String newName){
        SQLiteHandler.dbCall("UPDATE alters SET 'name' = '"+ newName + "' WHERE alterUUID = ' " +uniqueID.toString() + "';");
        name = newName;
    }
    public void setDescription(String newDescription){
        SQLiteHandler.dbCall("UPDATE alters SET 'description' = '"+ newDescription + "' WHERE alterUUID = ' " +uniqueID.toString() + "';");
        description = newDescription;
    }
    public void setProxytag (String newTag){
        SQLiteHandler.dbCall("UPDATE alters SET 'proxytag' = '"+ newTag + "' WHERE alterUUID = ' " +uniqueID.toString() + "';");
        proxytag = newTag;
    }

    public System getassociatedSystem()  {return SystemSwap.getSystemFromSystemUUID(associatedsystemUUID);}
    public UUID getUniqueID()            {return uniqueID;}
    public String getName()              {return name;}
    public String getDescription()       {return description;}
    public String getProxytag()          {return proxytag;}
    public String getNickname()          {return nickname;}

    public static Alter getAlterFromUUID(UUID togetUUID){
        return AllAlters.getOrDefault(togetUUID, null);
    }
    public static Alter getAlterfromName(String nameinput, UUID playerUUID){
       System unknownaltersystem =  SystemSwap.getSystemFromPlayerUUID(playerUUID);
       HashMap<UUID, Alter> altermap = unknownaltersystem.getAlterList();
        AtomicReference<Alter> atomicAlter = new AtomicReference<>();
        altermap.forEach((key, value) -> {
            if(value.getName().equalsIgnoreCase(nameinput)){ atomicAlter.set(value);}
        });
        return atomicAlter.get();
    }
    public static void addAltertoAllAlters(UUID toAddUUID, Alter toAddAlter){
       AllAlters.put(toAddUUID, toAddAlter);
    }

}
