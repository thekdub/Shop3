package com.thekdub.shop3.objects;

import java.util.HashSet;

public class DataQuery {
  private final HashSet<String> buyers = new HashSet<>();
  private final HashSet<String> sellers = new HashSet<>();
  private int minId = -1;
  private int maxId = -1;
  private int minDurability = -1;
  private int maxDurability = -1;
  private double minAmount = -1;
  private double maxAmount = -1;
  private double minPrice = -1;
  private double maxPrice = -1;
  private long minTimestamp = -1;
  private long maxTimestamp = -1;
  private Field sortField = null;
  private boolean sortDescend = false;

  /**
   * Field for specifying how to sort the data query.
   */
  public enum Field {
    BUYER("buyer"),
    SELLER("seller"),
    ID("id"),
    DURABILITY("durability"),
    AMOUNT("amount"),
    PRICE("price"),
    TIMESTAMP("timestamp");
    public final String label;
    private Field(String label) {
      this.label = label;
    }
  }

  /**
   * @param buyers A list of buyer UUIDs to query.
   * @return The DataQuery object.
   */
  public DataQuery addBuyers(String... buyers) {
    for (String buyer:buyers) {
      this.buyers.add(buyer.toLowerCase());
    }
    return this;
  }

  /**
   * @param sellers A list of seller UUIDs to query.
   * @return The DataQuery object.
   */
  public DataQuery addSellers(String... sellers) {
    for (String seller:sellers) {
      this.sellers.add(seller.toLowerCase());
    }
    return this;
  }

  /**
   * @param min Minimum ID value to query.
   * @param max Maximum ID value to query.
   * @return The DataQuery object.
   */
  public DataQuery setIdRange(int min, int max) {
    minId = min; maxId = max;
    return this;
  }

  /**
   * @param id ID value to query.
   * @return The DataQuery object.
   */
  public DataQuery setId(int id) {
    return setIdRange(id, id);
  }

  /**
   * @param min Minimum durability to query.
   * @param max Maximum durability to query.
   * @return The DataQuery object.
   */
  public DataQuery setDurabilityRange(int min, int max) {
    minDurability = min; maxDurability = max;
    return this;
  }

  /**
   * @param durability Durability to query.
   * @return The DataQuery object.
   */
  public DataQuery setDurability(int durability) {
    return setDurabilityRange(durability, durability);
  }

  /**
   * @param min Minimum amount to query.
   * @param max Maximum amount to query.
   * @return The DataQuery object.
   */
  public DataQuery setAmountRange(double min, double max) {
    minAmount = min; maxAmount = max;
    return this;
  }

  /**
   * @param amount Amount to query.
   * @return The DataQuery object.
   */
  public DataQuery setAmount(double amount) {
    return setAmountRange(amount, amount);
  }

  /**
   * @param min Minimum price to query.
   * @param max Maximum price to query.
   * @return The DataQuery object.
   */
  public DataQuery setPriceRange(double min, double max) {
    minPrice = min; maxPrice = max;
    return this;
  }

  /**
   * @param price Price to query.
   * @return The DataQuery object.
   */
  public DataQuery setPrice(double price) {
    return setPriceRange(price, price);
  }

  /**
   * @param min Minimum timestamp to query.
   * @param max Maximum timestamp to query.
   * @return The DataQuery object.
   */
  public DataQuery setTimestampRange(long min, long max) {
    minTimestamp = min; maxTimestamp = max;
    return this;
  }

  /**
   * @param timestamp Timestamp to query.
   * @return The DataQuery object.
   */
  public DataQuery setTimestamp(long timestamp) {
    return setTimestampRange(timestamp, timestamp);
  }

  /**
   * @param field The field to sort by.
   * @param descending True = Sort descending. False = Sort ascending.
   * @return The DataQuery object.
   */
  public DataQuery sortBy(Field field, boolean descending) {
    sortField = field;
    sortDescend = descending;
    return this;
  }

  /**
   * @return A string for concatenation to the 'WHERE' portion of a SQLite query.
   */
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder segment = new StringBuilder();
    if (buyers.size() > 0 || sellers.size() > 0 ||
          minId > 0 || maxId > 0 ||
          minDurability > 0 || maxDurability > 0 ||
          minAmount > 0 || maxAmount > 0 ||
          minPrice > 0 || maxPrice > 0 ||
          minTimestamp > 0 || maxTimestamp > 0) {
      stringBuilder.append("WHERE ");
    }
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
      stringBuilder.append(String.format("(amount BETWEEN %f AND %f)", minAmount, maxAmount));
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
    if (sortField != null) {
      if (stringBuilder.length() > 0) {
        stringBuilder.append(" ");
      }
      stringBuilder.append("ORDER BY ");
      stringBuilder.append(sortField);
      stringBuilder.append(" ");
      stringBuilder.append(sortDescend ? "DESC" : "ASC");
    }
    return stringBuilder.toString();
  }
}
