package com.thekdub.shop3.messages;

import com.thekdub.shop3.Util;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class PagedMessage {
  Message title;
  LinkedList<Message> messages = new LinkedList<>();

  public PagedMessage(String title, Message... messages) {
    this.title = new Message(title, true);
    this.messages.addAll(Arrays.asList(messages));
  }

  public void addMessages(Message... messages) {
    this.messages.addAll(Arrays.asList(messages));
  }

  public void send(Player player, int page) {
    int lastPage = (int) Math.ceil(messages.size() / 10.0);
    player.sendMessage(title + Util.translateColor(String.format(" Page %d of %d:", page + 1, lastPage + 1)));
    for (int i = page * 10; i < (page + 1) * 10 && i < messages.size(); i++) {
      player.sendMessage(String.format(" <%d> %s", i + 1, messages.get(i)));
    }
  }
}
