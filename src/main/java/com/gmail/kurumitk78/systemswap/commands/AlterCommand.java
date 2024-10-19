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

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class AlterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            if(args.length >= 1) {
                switch (args[0].toLowerCase()) {
                    case "create":
                        createAlterCommand(args, (Player) commandSender);
                        break;
                    case "set":
                        setAlterDataCommand(args, (Player) commandSender);
                        break;
                    case "delete":
                        deleteAlterCommand(args, (Player) commandSender);
                        break;
                    default:
                        commandSender.sendMessage("Command usage: /alter (create/set*/delete) (name) <setting*> <value>");
                        break;
                }
            }
            else{commandSender.sendMessage("Command usage: /alter (create/set*/delete) (name) <setting*> <value>");}
        }

        else{ //Else for checking if player
            Bukkit.getLogger().log(Level.WARNING, "Only a player may use this command");

        }
        return false;
    }

    public void createAlterCommand(String[] args, Player sender){
        if(SystemSwap.isSystem(sender.getUniqueId())){
            System sendersSystem = SystemSwap.getSystemFromPlayerUUID(sender.getUniqueId());
            if(args.length == 1) {
                sender.sendMessage("Please put in a name for the Alter");
                return;
            }
            UUID newAlterUUID = sendersSystem.createAlter(args[1]);
            sender.sendMessage("New alter created with name " + args[1] + " and with Unique UUID " + newAlterUUID.toString());
            sendersSystem.setFronter(sendersSystem.getAlter(newAlterUUID));


        }
        else{
            sender.sendMessage("Only systems may use this command");
            return;
        }

    }
    public void deleteAlterCommand(String[] args, Player sender){
        if(SystemSwap.isSystem(sender.getUniqueId())){
            if(args.length == 1) {
                sender.sendMessage("usage /alter delete (name)");

            }
            else{
                Alter deletingAlter = Alter.getAlterfromName(args[1], sender.getUniqueId());
                if(deletingAlter != null){
                    SystemSwap.getSystemFromPlayerUUID(sender.getUniqueId()).deleteAlter(deletingAlter.getUniqueID());
                    SQLiteHandler.dbCall("DELETE FROM alters WHERE alterUUID = '" + deletingAlter.getUniqueID() + "'");
                    sender.sendMessage("Alter " + args[1] + " deleted successfully.");
                }
                else{sender.sendMessage("Alter with name " + args[1] + " not found.");}
            }

        }
    }

    public void setAlterDataCommand(String[] args, Player sender){
        if(SystemSwap.isSystem(sender.getUniqueId())) {
            if (args.length < 2) {
                sender.sendMessage("Command usage: /alter set (name) (name/nickname/proxytag/description) <value>");
            } else {
                Alter modifiedAlter = Alter.getAlterfromName(args[1], sender.getUniqueId());
                if (Objects.isNull(modifiedAlter)) {
                    sender.sendMessage("No Alters found in your system named " + args[1]);
                    return;
                }
                if (args.length >= 4) {
                    switch (args[2]) {
                        case "name":
                            modifiedAlter.setName(args[3]);
                            sender.sendMessage("Name successfully updated");
                            break;
                        case "nickname":
                            modifiedAlter.setNickname(args[3]);
                            sender.sendMessage("Nickname successfully updated");
                            break;
                        case "proxytag":
                            modifiedAlter.setProxytag(args[3]);
                            sender.sendMessage("Proxytag successfully updated");
                            break;
                        case "description":
                            modifiedAlter.setDescription(args[3]);
                            sender.sendMessage("Description successfully updated");
                            break;
                    }
                } else {
                    sender.sendMessage("Command usage: /alter set (name) (name/nickname/proxytag/description) <value>");
                }
            }
        }
        else{sender.sendMessage("Only systems may use this command"); return; }
    }
}
