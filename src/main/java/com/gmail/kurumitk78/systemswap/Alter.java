package com.gmail.kurumitk78.systemswap;

import java.util.HashMap;
import java.util.UUID;

public class Alter {
    private static HashMap<UUID, Alter> AllAlters = new HashMap<UUID, Alter>();



    private String name;
    private String description;
    private String proxytag;
    private UUID uniqueID;
    private UUID associatedsystemUUID;
    private String nickname;



    public Alter(String newName, UUID newUniqueID, UUID systemUUID){
        name = newName;
        uniqueID = newUniqueID;
        associatedsystemUUID = systemUUID;
        AllAlters.put(newUniqueID, this);
    }

    public void setNickname(String newNick){
        nickname = newNick;
    }

    public void setName(String newName){
        name = newName;
    }
    public void setDescription(String newDescription){
        description = newDescription;
    }
    public void setProxytag (String newTag){
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
    public static void addAltertoAllAlters(UUID toAddUUID, Alter toAddAlter){
       AllAlters.put(toAddUUID, toAddAlter);
    }

}
