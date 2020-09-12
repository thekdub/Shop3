package com.thekdub.shop3.commands;

import com.thekdub.itemutils.ItemUtils;
import com.thekdub.shop3.DBManager;
import com.thekdub.shop3.Shop3;
import com.thekdub.shop3.Util;
import com.thekdub.shop3.messages.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CmdBuy extends Cmd {
  public static boolean execute(CommandSender sender, Command cmd, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(new Message("&cOnly players may use this command!", true).toString());
      return true;
    }
    Player player = (Player) sender;
    int[] argData = idDataAmtFromArgs(args, 0);
    // Verify item
    double price = Shop3.getDbManager().getPrice(argData[0], argData[1]) * argData[2];
    String name = Util.getItemName(argData[0], argData[1]);
    if (price <= 0) {
      sender.sendMessage(new Message(String.format("%s (%d:%d) does not have a price!", name, argData[0], argData[1]),
            true).toString());
      return true;
    }
    // Verify balance
    double balance = Shop3.getEconomy().getBalance(sender.getName());
    if (balance < price) { // Not enough money
      sender.sendMessage(new Message(String.format("&cYou cannot afford that! Your balance: $%f.2. %dx %s (%d:%d) " +
            "costs $%f.2", balance, argData[2], name, argData[0], argData[1], price), true).toString());
      return true;
    }
    // Verify inventory
    int freeSpaces = Util.getFreeSpaces(player.getInventory(), argData[0], argData[1]);
    if (freeSpaces < argData[2]) { // Not enough room
      sender.sendMessage(new Message(String.format("&cYou do not have enough room! You have %d slots free " +
            "for %s (%d:%d)", freeSpaces, name, argData[0], argData[1]), true).toString());
      return true;
    }
    // Verify stock
    // Item is valid, has balance, has room.
    double stockCount = Shop3.getDbManager().getStock(argData[0], argData[1]);
    boolean adminSale = Shop3.getInstance().getConfig().getBoolean("allow_admin_sale", false);
    if (stockCount < argData[2] && !adminSale) {
      sender.sendMessage(new Message(String.format("&cThere are only %fx %s (%d:%d) in stock!", stockCount, name,
            argData[0], argData[1]), true).toString());
      return true;
    }
    HashMap<String, Double> sellers = Shop3.getDbManager().getUsersSelling(argData[0], argData[1]);

    // TODO: Complete the purchase. Need to find the algorithm I came up with for calculating how much of the item each
    //  person should sell.

    return true;
  }
}
