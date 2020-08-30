package com.thekdub.shop3;

import com.thekdub.shop3.commands.*;
import com.thekdub.shop3.objects.Transaction;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class Shop3 extends JavaPlugin {
  
  private static Shop3 instance;
  private static Economy economy;
  
  public void onEnable() {
    instance = this;
    if (!new File(getDataFolder() + File.separator + "config.yml").exists()) {
      saveDefaultConfig();
    }
    reloadConfig();
    if (!hookEconomy()) {
      getLogger().log(Level.WARNING, "Unable to hook Vault Economy. Make sure you have Vault installed!");
      getLogger().log(Level.INFO, "Disabling Shop^3!");
      getPluginLoader().disablePlugin(this);
      return;
    }
    getLogger().log(Level.INFO, "Hooked Vault Economy successfully!");
    registerCommands();
  }
  
  public void onDisable() {
  
  }
  
  public static Shop3 getInstance() {
    return instance;
  }
  
  private boolean hookEconomy() {
    Plugin vault = getServer().getPluginManager().getPlugin("Vault");
    if (vault != null) {
      RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
      if (rsp != null) {
        economy = rsp.getProvider();
      }
    }
    return economy != null;
  }

  private void registerCommands() {
    getCommand("Buy").setExecutor(this);
    getCommand("CancelSell").setExecutor(this);
    getCommand("EMC").setExecutor(this);
    getCommand("Price").setExecutor(this);
    getCommand("QuickSell").setExecutor(this);
    getCommand("Sell").setExecutor(this);
    getCommand("Selling").setExecutor(this);
    getCommand("Shop3").setExecutor(this);
    getCommand("Stock").setExecutor(this);
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    StringBuilder argsBuilder = new StringBuilder();
    for (String arg : args) {
      if (argsBuilder.length() > 0) {
        argsBuilder.append(" ");
      }
      argsBuilder.append(arg);
    }
    String argString = argsBuilder.toString();
    getLogger().log(Level.INFO, sender.getName() + ": /" + cmd.getLabel() + " " + argString);
    switch (cmd.getLabel().toLowerCase()) {
      case "buy":
        return CmdBuy.execute(sender, cmd, args);
      case "cancelsell":
        return CmdCancelSell.execute(sender, cmd, args);
      case "emc":
        return false; // TODO: Add CmdEMC
      case "price":
        return false; // TODO: Add CmdPrice
      case "quicksell":
        return CmdQuickSell.execute(sender, cmd, args);
      case "sell":
        return CmdSell.execute(sender, cmd, args);
      case "selling":
        return CmdSelling.execute(sender, cmd, args);
      case "shop3":
        return CmdShop3.execute(sender, cmd, args);
      case "stock":
        return CmdStock.execute(sender, cmd, args);
      default:
        return false;
    }
  }
}
