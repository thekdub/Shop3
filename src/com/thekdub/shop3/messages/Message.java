package com.thekdub.shop3.messages;

import com.thekdub.shop3.Shop3;
import com.thekdub.shop3.Util;
import org.bukkit.entity.Player;

public class Message {
  String message = "";
  boolean prefix = false;

  public Message(String message) {
    if (message != null) {
      this.message = Util.translateColor(message);
    }
  }

  public Message(String message, boolean prefix) {
    if (message != null) {
      this.message = Util.translateColor(message);
    }
    this.prefix = prefix;
  }

  @Override
  public String toString() {
    return Util.translateColor(Shop3.getInstance().getConfig().getString("prefix")) + " " + message;
  }
}
