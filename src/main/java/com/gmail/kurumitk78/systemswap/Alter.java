package com.gmail.kurumitk78.systemswap;

import java.util.UUID;

public class Alter {
    private String name;
    private String description;
    private String proxytag;
    private UUID uniqueID;



    public Alter(String newName, UUID newuniqueID){
        name = newName;
        uniqueID = newuniqueID;
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
    public UUID getUniqueID() {return uniqueID;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getProxytag()    {return proxytag;}
}
