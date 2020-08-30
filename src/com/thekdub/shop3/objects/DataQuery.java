package com.thekdub.shop3.objects;

import java.util.HashSet;

public class DataQuery {
  private final HashSet<String> buyers = new HashSet<>();
  private final HashSet<String> sellers = new HashSet<>();
  private int minId = -1;
  private int maxId = -1;
  private int minDurability = -1;
  private int maxDurability = -1;
  private int minAmount = -1;
  private int maxAmount = -1;
  private double minPrice = -1;
  private double maxPrice = -1;
  private long minTimestamp = -1;
  private long maxTimestamp = -1;

  public DataQuery addBuyers(String... buyers) {
    for (String buyer:buyers) {
      this.buyers.add(buyer.toLowerCase());
    }
    return this;
  }
  public DataQuery addSellers(String... sellers) {
    for (String seller:sellers) {
      this.sellers.add(seller.toLowerCase());
    }
    return this;
  }
  public DataQuery setIdRange(int min, int max) {
    minId = min; maxId = max;
    return this;
  }
  public DataQuery setId(int id) {
    return setIdRange(id, id);
  }
  public DataQuery setDurabilityRange(int min, int max) {
    minDurability = min; maxDurability = max;
    return this;
  }
  public DataQuery setDurability(int durability) {
    return setDurabilityRange(durability, durability);
  }
  public DataQuery setAmountRange(int min, int max) {
    minAmount = min; maxAmount = max;
    return this;
  }
  public DataQuery setAmount(int amount) {
    return setAmountRange(amount, amount);
  }
  public DataQuery setPriceRange(double min, double max) {
    minPrice = min; maxPrice = max;
    return this;
  }
  public DataQuery setPrice(double price) {
    return setPriceRange(price, price);
  }
  public DataQuery setTimestampRange(long min, long max) {
    minTimestamp = min; maxTimestamp = max;
    return this;
  }
  public DataQuery setTimestamp(long timestamp) {
    return setTimestampRange(timestamp, timestamp);
  }

  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder segment = new StringBuilder();
    if (buyers.size() > 0) {
      stringBuilder.append("(");
      for (String buyer : buyers) {
        if (segment.length() > 0) {
          segment.append(" OR ");
        }
        segment.append(String.format("buyer=\"%s\"", buyer));
      }
      stringBuilder.append(segment);
      stringBuilder.append(")");
    }
    if (sellers.size() > 0) {
      if (stringBuilder.length() > 0) {
        stringBuilder.append(" AND ");
      }
      segment = new StringBuilder();
      stringBuilder.append("(");
      for (String seller : sellers) {
        if (segment.length() > 0) {
          segment.append(" OR ");
        }
        segment.append(String.format("seller=\"%s\"", seller));
      }
      stringBuilder.append(segment);
      stringBuilder.append(")");
    }
    if (minId >= 0 && maxId >= 0) {
      if (stringBuilder.length() > 0) {
        stringBuilder.append(" AND ");
      }
      stringBuilder.append(String.format("(id BETWEEN %d AND %d)", minId, maxId));
    }
    if (minDurability >= 0 && maxDurability >= 0) {
      if (stringBuilder.length() > 0) {
        stringBuilder.append(" AND ");
      }
      stringBuilder.append(String.format("(durability BETWEEN %d AND %d)", minDurability, maxDurability));
    }
    if (minAmount >= 0 && maxAmount >= 0) {
      if (stringBuilder.length() > 0) {
        stringBuilder.append(" AND ");
      }
      stringBuilder.append(String.format("(amount BETWEEN %d AND %d)", minAmount, maxAmount));
    }
    if (minPrice >= 0 && maxPrice >= 0) {
      if (stringBuilder.length() > 0) {
        stringBuilder.append(" AND ");
      }
      stringBuilder.append(String.format("(price BETWEEN %f AND %f)", minPrice, maxPrice));
    }
    if (minTimestamp >= 0 && maxTimestamp >= 0) {
      if (stringBuilder.length() > 0) {
        stringBuilder.append(" AND ");
      }
      stringBuilder.append(String.format("(timestamp BETWEEN %d AND %d)", minTimestamp, maxTimestamp));
    }
    return stringBuilder.toString();
  }
}
