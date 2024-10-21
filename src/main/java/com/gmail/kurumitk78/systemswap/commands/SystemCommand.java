package com.gmail.kurumitk78.systemswap.commands;

import com.gmail.kurumitk78.systemswap.Alter;
import com.gmail.kurumitk78.systemswap.System;
import com.gmail.kurumitk78.systemswap.SystemSwap;
import com.gmail.kurumitk78.systemswap.database.SQLiteHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class SystemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            if (args.length >= 1) {
                switch (args[0].toLowerCase()) {
                    case "create":
                        createSystemCommand((Player) commandSender);
                        break;
                    case "delete":
                        deleteSystemCommand((Player) commandSender);
                        break;
                    case "front":
                        if(args.length >= 2) {
                            setFronterCommand((Player) commandSender, args[1]);
                            break;
                        }
                        else {
                            commandSender.sendMessage("Command usage: /system (create/delete/front) <New Fronter>");
                            break;
                        }

                    default:
                        commandSender.sendMessage("Command usage: /system (create/delete/front) <New Fronter>");
                        break;

                }

            }
            else{commandSender.sendMessage("Command usage: /system (create/delete/front) <New Fronter>");}
        }
        else{ //Else for checking if player
            Bukkit.getLogger().log(Level.WARNING, "Only a player may use this command");
        }
    return false;
    }


    private void createSystemCommand(Player player){
        if(SystemSwap.getSystemFromPlayerUUID(player.getUniqueId()) == null){
            UUID returnedUUID = SystemSwap.createSystem(player.getUniqueId());
            player.sendMessage("System created using unique ID " + returnedUUID.toString());

        }
        else{
            player.sendMessage("You already have a system created.");

        }

    }

    private void deleteSystemCommand(Player player){
            if(SystemSwap.getSystemFromPlayerUUID(player.getUniqueId()) != null){
             UUID systemUUID = SystemSwap.getSystemFromPlayerUUID(player.getUniqueId()).getSystemUUID();
                SystemSwap.deleteSystemSystemUUID(systemUUID);
                SystemSwap.deleteSystemPlayerUUID(player.getUniqueId());
                SQLiteHandler.dbCall("DELETE FROM systems WHERE systemUUID = '" + systemUUID + "'");
                player.sendMessage("System deleted.");

            }

    }
    private void setFronterCommand(Player player, String alterName){
        System playerSystem = SystemSwap.getSystemFromPlayerUUID(player.getUniqueId());
        ArrayList<Alter> alters = new ArrayList<>();
        alters.addAll(playerSystem.getAlterList().values());
        for(int iterations = 0; iterations < alters.size(); iterations++) {
               if(alters.get(iterations).getName().toLowerCase().equals(alterName.toLowerCase())){
                   playerSystem.setFronter(alters.get(iterations));
                   break;
               }
               else if(iterations == alters.size()){
                   player.sendMessage("Alter with name " + alterName + " not found.");
            }
        }
    }

}
