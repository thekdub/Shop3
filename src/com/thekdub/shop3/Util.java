package com.thekdub.shop3;

import com.thekdub.itemutils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
    return getItemName(id, 0);
  }

  /**
   * @param id         An item ID.
   * @param durability An item durability value.
   * @return The name of the item ID and durability value.
   */
  public static String getItemName(int id, int durability) {
    try {
      return ItemUtils.getName(id, durability);
    } catch (Exception e) {
    }
    return id + ":" + durability;
  }

  /**
   * @param s The string you want to translate color codes in.
   * @return A color code translated string.
   */
  public static String translateColor(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }

  public static int getFreeSlots(PlayerInventory inventory) {
    int slots = 0;
    for (int slot = 0; slot < inventory.getContents().length; slot++) {
      if (inventory.getContents()[slot] == null) {
        slots++;
      }
    }
    return slots;
  }

  public static int getFreeSpaces(PlayerInventory inventory, int id, int durability) {
    int spaces = 0;
    for (int slot = 0; slot < inventory.getContents().length; slot++) {
      if (inventory.getContents()[slot] == null) {
        spaces += new ItemStack(id, 1, (short) durability).getMaxStackSize();
      }
      else {
        spaces += inventory.getContents()[slot].getMaxStackSize() - inventory.getContents()[slot].getAmount();
      }
    }
    return spaces;
  }

}
