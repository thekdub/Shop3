package com.thekdub.shop3.objects;

import java.util.Objects;

public class Transaction {
  public final String buyer;
  public final String seller;
  public final int id;
  public final int durability;
  public final int amount;
  public final double price;
  public final long timestamp;
  
  public Transaction(String buyer, String seller, int id, int durability, int amount, double price, long timestamp) {
    this.buyer = buyer.toLowerCase();
    this.seller = seller.toLowerCase();
    this.id = id;
    this.durability = durability;
    this.amount = amount;
    this.price = price;
    this.timestamp = timestamp;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction that = (Transaction) o;
    return id == that.id &&
        durability == that.durability &&
        amount == that.amount &&
        Double.compare(that.price, price) == 0 &&
        timestamp == that.timestamp &&
        seller.equalsIgnoreCase(that.seller) &&
        buyer.equalsIgnoreCase(that.buyer);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(seller, buyer, id, durability, amount, price, timestamp);
  }
  
  @Override
  public String toString() {
    return String.format("%s purchased %dx %d:%d from %s for $%.2f on %7$tF %7$tI:%7$tM %7$Tp %7$tZ",
        buyer, amount, id, durability, seller, price, timestamp);
  }

  public String toDatabaseString() {
    return String.format("\"%s\",\"%s\",%d,%d,%d,%f,%d", buyer, seller, id, durability, amount, price, timestamp);
  }
}
