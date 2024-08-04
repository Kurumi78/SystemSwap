package com.gmail.kurumitk78.systemswap.commands;

import com.gmail.kurumitk78.systemswap.SystemSwap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            }

        }
        else{ //Else for checking if player
            Bukkit.getLogger().log(Level.WARNING, "Only a player may use this command");
        }
    return false;
    }


    private void createAlterCommand(Player player){
        if(SystemSwap.getSystemFromPlayerUUID(((Player) player).getUniqueId()) == null){
            UUID returnedUUID = SystemSwap.createSystem(((Player) player).getUniqueId());
            player.sendMessage("System created using unique ID " + returnedUUID.toString());

        }
        else{
            player.sendMessage("You already have a system created.");

        }

    }

}
