package com.thekdub.shop3.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class Cmd {

  public static boolean execute(CommandSender sender, Command cmd, String[] args) {
    return false;
  }

}
