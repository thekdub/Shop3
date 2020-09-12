package com.thekdub.shop3.commands;

import com.thekdub.itemutils.ItemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class Cmd {

  public static boolean execute(CommandSender sender, Command cmd, String[] args) {
    return false;
  }

  public static String getArg(String[] args, int index) {
    if (args.length > index && index >= 0) {
      return args[index] == null ? "" : args[index];
    }
    return "";
  }

  public static String idFromArgs(String[] args, int start) {
    String id = "";
    int end = args.length-1;
    do {
      StringBuilder testString = new StringBuilder();
      for (int i = start; i <= end--; i++) {
        testString.append(args[i]);
      }
      id = ItemUtils.getID(testString.toString());
      if (end == start) {
        break;
      }
    } while (id.equals(""));
    if (id.equals("")) {
      for (int i = start; i < args.length; i++) {
        if (args[i].matches("[0-9]+[:][0-9]+")) {
          id = args[i];
          break;
        }
        else if (args[i].matches("[0-9]+")) {
          id = args[i] + ":0";
          break;
        }
      }
    }
    return id;
  }

  public static int[] idDataAmtFromArgs(String[] args, int start) {
    int[] data = {0, 0, 1};
    String id = "";
    int end = args.length - 1;
    do {
      StringBuilder testString = new StringBuilder();
      for (int i = start; i <= end--; i++) {
        testString.append(args[i]);
      }
      id = ItemUtils.getID(testString.toString());
      if (end == start) {
        break;
      }
    } while (id.equals(""));
    if (id.equals("")) {
      for (int i = start; i < args.length; i++) {
        if (args[i].matches("[0-9]+[:][0-9]+")) {
          id = args[i];
          break;
        }
        else if (args[i].matches("[0-9]+")) {
          id = args[i] + ":0";
          break;
        }
      }
    }
    data[0] = Integer.parseInt(id.split(":")[0]);
    data[1] = Integer.parseInt(id.split(":")[1]);
    data[2] = end < args.length - 1 ? Integer.parseInt(args[args.length - 1]) : 1;
    return data;
  }
}
