package com.thekdub.shop3;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Util {
  
  public static String getItemName(ItemStack itemStack) {
    return getItemName(itemStack.getTypeId(), itemStack.getDurability());
  }
  
  public static String getItemName(int id) {
    return getItemName(id, (short) 0);
  }
  
  public static String getItemName(int id, short durability) {
    return id + ":" + durability;
  }
  
  public static String translateColor(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
  
}
