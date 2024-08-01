package com.gmail.kurumitk78.systemswap.commands;

import com.gmail.kurumitk78.systemswap.SystemSwap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Level;

public class createSystemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            if(SystemSwap.getSystemFromPlayerUUID(((Player) commandSender).getUniqueId()) == null){
                UUID returnedUUID = SystemSwap.createSystem(((Player) commandSender).getUniqueId());
                commandSender.sendMessage("System created using unique ID" + returnedUUID.toString());
                return false;
            }
            else{
                commandSender.sendMessage("You already have a system created.");
                return false;
            }

        }
        else{ //Else for checking if player
            Bukkit.getLogger().log(Level.WARNING, "Only a player may use this command");
            return false;
        }

    }
}
