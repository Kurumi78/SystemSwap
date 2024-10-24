package com.gmail.kurumitk78.systemswap;

import com.gmail.kurumitk78.systemswap.database.SQLiteHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
    public System(UUID setsystemUUID, UUID setaccountUUID, ResultSet AlterData) throws SQLException {
        systemUUID = setsystemUUID;
        accountUUID = setaccountUUID;
        initPlayerAlters(AlterData);
    }

    public UUID createAlter(String name){
        UUID alterUUID = UUID.randomUUID();
        while(Objects.nonNull(Alter.getAlterFromUUID(alterUUID))){ //I dont know if Java will super rarely create duplicated. But lets avoid that.
            alterUUID = UUID.randomUUID();
        }


        Alters.put(alterUUID, new Alter(name, alterUUID, systemUUID));
        SQLiteHandler.dbCall("INSERT INTO alters(alterUUID, name, systemUUID) VALUES('" + alterUUID.toString() + "', '" + name + "', '" + systemUUID + "');");
        return alterUUID;
    }
    public void deleteAlter(UUID alterUUID){
         Alters.remove(alterUUID);
         Alter.removeAltertoAllAlters(alterUUID);

    }
    public void initPlayerAlters(ResultSet playerData) throws SQLException {
        HashMap<UUID, String> proxyData = new HashMap<UUID, String>();
        HashMap<UUID, String> nicknameData = new HashMap<UUID, String>();

        while(playerData.next()){
            UUID alterUUID = UUID.fromString(playerData.getString("alterUUID"));
                    proxyData.put(alterUUID, playerData.getString("proxytag"));
                    nicknameData.put(alterUUID, playerData.getString("nickname"));
            Alters.put(alterUUID, new Alter(playerData.getString("name"),alterUUID,systemUUID));
        }
        playerData.close();
        proxyData.forEach((key, value) -> {
            if(value != null){
                Alters.get(key).setProxytag(value);
            }
        });
        nicknameData.forEach((key, value) -> {
            if(value != null){
                Alters.get(key).setNickname(value);
            }
        });
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
        SQLiteHandler.dbCall("UPDATE systems SET 'fronterUUID' = '"+ newFronter.getUniqueID().toString() + "' WHERE systemUUID = ' " +systemUUID + "';");
        Bukkit.getPlayer(accountUUID).sendMessage("Fronter changed to " + newFronter.getName());
        if(newFronter.getNickname() != ""){
            String nickCommand = SystemSwap.SystemSwapInstance.getConfig().getString("NickCommand");
            nickCommand = nickCommand.replace("[player]", Bukkit.getPlayer(accountUUID).getName());
            nickCommand = nickCommand.replace("[nickname]", newFronter.getNickname());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), nickCommand);
        }
    }

    public boolean containsProxyTag(String text){
        AtomicBoolean containstag = new AtomicBoolean();
        containstag.set(false);
        Alters.forEach((key, value) -> {
            boolean containstaglambda = false;
            int proxytaglength = value.getProxytag().length();

            if(text.length() > proxytaglength){
            String shortenedtext = text.substring(0, proxytaglength);
           // Bukkit.getLogger().log(Level.WARNING, "proxylength is " + proxytaglength + " and shortenedtext is" + shortenedtext + " and proxy tag is " + value.getProxytag());
            if(shortenedtext.equals(value.getProxytag())&& value.getProxytag().length() != 0){ containstag.set(true);}
            }
        });
        return containstag.get();
    }





}
