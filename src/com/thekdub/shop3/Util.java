package com.thekdub.shop3;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Util {

  /**
   * @param itemStack An ItemStack object.
   * @return The name of the ItemStack.
   */
  public static String getItemName(ItemStack itemStack) {
    return getItemName(itemStack.getTypeId(), itemStack.getDurability());
  }

  /**
   * @param id An item ID.
   * @return The name of the item ID.
   */
  public static String getItemName(int id) {
    return getItemName(id, (short) 0);
  }

  /**
   * @param id An item ID.
   * @param durability An item durability value.
   * @return The name of the item ID and durability value.
   */
  public static String getItemName(int id, short durability) {
    return id + ":" + durability;
  }

  /**
   * @param s The string you want to translate color codes in.
   * @return A color code translated string.
   */
  public static String translateColor(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
  
}
