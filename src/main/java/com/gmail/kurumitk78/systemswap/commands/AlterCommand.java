package com.gmail.kurumitk78.systemswap.commands;

import com.gmail.kurumitk78.systemswap.System;
import com.gmail.kurumitk78.systemswap.SystemSwap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Level;

public class AlterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            if(args[0] == "create"){
                createAlterCommand(args, (Player)commandSender);
            }
        }
        else{ //Else for checking if player
            Bukkit.getLogger().log(Level.WARNING, "Only a player may use this command");

        }
        return false;
    }

    public void createAlterCommand(String[] args, Player sender){
        if(!SystemSwap.getSystemFromPlayerUUID(sender.getUniqueId()).equals(null)){
            System sendersSystem = SystemSwap.getSystemFromPlayerUUID(sender.getUniqueId());
            if(args[1].equals(null)) {
                sender.sendMessage("Please put in a name for the Alter");
            }
            UUID newAlterUUID = sendersSystem.createAlter(args[1]);
            sender.sendMessage("New alter created with name " + args[1] + " and with Unique UUID " + newAlterUUID.toString());


        }
        else{
            sender.sendMessage("Only systems may use this command");

        }

    }
}
