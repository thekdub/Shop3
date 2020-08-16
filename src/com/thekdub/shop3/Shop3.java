package com.thekdub.shop3;

import com.thekdub.shop3.commands.*;
import com.thekdub.shop3.objects.Transaction;
import net.milkbowl.vault.economy.Economy;
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
    getCommand("Buy").setExecutor(new CmdBuy());
    getCommand("CancelSell").setExecutor(new CmdCancelSell());
    getCommand("QuickSell").setExecutor(new CmdQuickSell());
    getCommand("Sell").setExecutor(new CmdSell());
    getCommand("Selling").setExecutor(new CmdSelling());
    getCommand("Shop3").setExecutor(new CmdShop3());
    getCommand("Stock").setExecutor(new CmdStock());
  }
  
  
  public static void main(String[] args) {
    
    String buyer = "thekdub";
    String seller = "server";
    int id = 263;
    int durability = 0;
    int amount = 100;
    double price = 1000.03;
    long timestamp = System.currentTimeMillis();
    System.out.println(String.format("%s purchased %dx %d:%d from %s for $%.2f on %7$tF %7$tI:%7$tM %7$Tp %7$tZ",
        buyer, amount, id, durability, seller, price, timestamp));
  }
  
}
