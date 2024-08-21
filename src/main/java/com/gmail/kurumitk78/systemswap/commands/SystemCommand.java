package com.gmail.kurumitk78.systemswap.commands;

import com.gmail.kurumitk78.systemswap.Alter;
import com.gmail.kurumitk78.systemswap.System;
import com.gmail.kurumitk78.systemswap.SystemSwap;
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
            switch(args[0].toLowerCase()) {
                case "create":
                    createAlterCommand((Player) commandSender);
                    break;
                case "delete":
                    deleteAlterCommand((Player) commandSender);
                    break;
            }

        }
        else{ //Else for checking if player
            Bukkit.getLogger().log(Level.WARNING, "Only a player may use this command");
        }
    return false;
    }


    private void createAlterCommand(Player player){
        if(SystemSwap.getSystemFromPlayerUUID(player.getUniqueId()) == null){
            UUID returnedUUID = SystemSwap.createSystem(player.getUniqueId());
            player.sendMessage("System created using unique ID " + returnedUUID.toString());

        }
        else{
            player.sendMessage("You already have a system created.");

        }

    }

    private void deleteAlterCommand(Player player){
            if(SystemSwap.getSystemFromPlayerUUID(player.getUniqueId()) != null){
             UUID systemUUID = SystemSwap.getSystemFromPlayerUUID(player.getUniqueId()).getSystemUUID();
                SystemSwap.deleteSystemSystemUUID(systemUUID);
                SystemSwap.deleteSystemPlayerUUID(player.getUniqueId());

            }

    }
    private void setFronterCommand(Player player, String alterName){
        System playerSystem = SystemSwap.getSystemFromPlayerUUID(player.getUniqueId());
        ArrayList<Alter> alters = new ArrayList<>();
        alters.addAll(playerSystem.getAlterList().values());
        for(int iterations = 0; iterations < alters.size(); iterations++) {
               if(alters.get(iterations).getName().toLowerCase() == alterName){
                   playerSystem.setFronter(alters.get(iterations));
                   break;
               }
               else if(iterations == alters.size()){
                   player.sendMessage("Alter with name " + alterName + " not found.");
            }
        }
    }

}
