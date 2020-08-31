package com.thekdub.shop3.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdPrice extends Cmd {
  public static boolean execute(CommandSender sender, Command cmd, String[] args) {

    // Two variants... Administrator and User
    /*

    Administrator:
      Condition: Has shop3.command.price.admin
      Usages:
        - /Price [Get | Set | Clear] [[Item Name | ID] [Price] | Price]
        - /Price [Item Name | ID]

     */

    // /Price Energy Collector Mk3 100
    // /Price Set Energy Collector Mk3 240232

    boolean isPlayer = sender instanceof Player;

    if (sender.hasPermission("shop3.command.price.admin")) {
      if (getArg(args, 0).equalsIgnoreCase("get")) {
        String id = idFromArgs(args, 1);
        if (id.equals("") && isPlayer) {

        }
        else {
          sender.sendMessage("");
        }
      }
      else if (getArg(args, 0).equalsIgnoreCase("set")) {

      }
      else if (getArg(args, 0).equalsIgnoreCase("clear")) {

      }
      else {

      }

    }

    return false;
  }
}
