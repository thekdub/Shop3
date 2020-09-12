package com.thekdub.shop3;

import com.thekdub.shop3.objects.DataQuery;
import com.thekdub.shop3.objects.Transaction;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class DBManager {

  Connection dataCon;
  Connection priceCon;

  /**
   * Creates a connection with the Data and Price databases.
   */
  public void connect() {
    if (dataCon == null) {
      try {
        String url = "jdbc:sqlite:" + Shop3.getInstance().getDataFolder() + File.separator + "shop3_data.db";
        dataCon = DriverManager.getConnection(url);
        System.out.println("Connected to data database successfully!");
      } catch (SQLException e) {
        System.out.println("An error occurred while connecting to the data database!\n" + e.getMessage());
      }
    }
    if (priceCon == null) {
      try {
        String url = "jdbc:sqlite:" + Shop3.getInstance().getDataFolder() + File.separator + "shop3_prices.db";
        priceCon = DriverManager.getConnection(url);
        System.out.println("Connected to prices database successfully!");
      }
      catch (SQLException e) {
        System.out.println("An error occurred while connecting to the prices database!\n" + e.getMessage());
      }
    }
  }

  /**
   * Disconnects from the Data and Price databases.
   */
  public void disconnect() {
    if (dataCon != null) {
      try {
        dataCon.close();
        dataCon = null;
        System.out.println("Closed the data database successfully!");
      }
      catch (SQLException e) {
        System.out.println("An error occurred while closing the data database!\n" + e.getMessage());
      }
    }
    if (priceCon != null) {
      try {
        priceCon.close();
        priceCon = null;
        System.out.println("Closed the price database successfully!");
      }
      catch (SQLException e) {
        System.out.println("An error occurred while closing the prices database!\n" + e.getMessage());
      }
    }
  }

  /**
   * Creates default tables in the Data and Price databases.
   */
  public void createTables() {
    if (dataCon == null || priceCon == null) {
      connect();
    }
    String statement = "CREATE TABLE IF NOT EXISTS transactions (" +
          "buyer TEXT," +
          "seller TEXT," +
          "id INTEGER DEFAULT 0," +
          "durability INTEGER DEFAULT 0," +
          "amount REAL DEFAULT 0," +
          "price REAL DEFAULT 0.0," +
          "timestamp INTEGER DEFAULT 0," +
          "PRIMARY KEY(buyer, seller, timestamp));";
    try {
      dataCon.prepareStatement(statement).execute();
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    statement = "CREATE TABLE IF NOT EXISTS stock (" +
          "user TEXT," +
          "id INTEGER DEFAULT 0," +
          "durability INTEGER DEFAULT 0," +
          "amount REAL DEFAULT 0," +
          "PRIMARY KEY(user, id, durability));";
    try {
      dataCon.prepareStatement(statement).execute();
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    statement = "CREATE TABLE IF NOT EXISTS emc (" +
          "id INTEGER DEFAULT 0," +
          "durability INTEGER DEFAULT 0," +
          "value REAL DEFAULT 0.0," +
          "PRIMARY KEY(id, durability));";
    try {
      priceCon.prepareStatement(statement).execute();
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    statement = "CREATE TABLE IF NOT EXISTS prices (" +
          "id INTEGER DEFAULT 0," +
          "durability INTEGER DEFAULT 0," +
          "value REAL DEFAULT 0.0," +
          "PRIMARY KEY(id, durability));";
    try {
      priceCon.prepareStatement(statement).execute();
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
  }

  /**
   * Adds transaction data to the Data database.
   * @param transaction Data object for Shop^3 transactions.
   */
  public void addTransaction(Transaction transaction) {
    if (dataCon == null) {
      connect();
    }
    String statement = "INSERT INTO TABLE transactions VALUES (" + transaction.toDatabaseString() + ");";
    try {
      dataCon.prepareStatement(statement).execute();
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
  }

  /**
   * Retrieves transaction data based on the provided DataQuery.
   * @param dataQuery Data query object for Shop^3.
   * @return A LinkedHashSet of transaction data matching the query.
   */
  public LinkedHashSet<Transaction> getTransactions(DataQuery dataQuery) {
    if (dataCon == null) {
      connect();
    }
    LinkedHashSet<Transaction> transactions = new LinkedHashSet<>();
    String statement = "SELECT * FROM transactions " + dataQuery + ";";
    try {
      ResultSet rs = dataCon.prepareStatement(statement).executeQuery();
      while (rs.next()) {
        transactions.add(new Transaction(rs.getString("buyer"),
              rs.getString("seller"),
              rs.getInt("id"),
              rs.getInt("durability"),
              rs.getDouble("amount"),
              rs.getDouble("price"),
              rs.getLong("timestamp")));
      }
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    return transactions;
  }

  /**
   * Sets a user's stock count for the specified item.
   * @param user A user's UUID without dashes.
   * @param id An item's ID
   * @param durability An item's durability.
   * @param amount The amount of stock the user has.
   */
  public void setStock(String user, int id, int durability, double amount) {
    if (dataCon == null) {
      connect();
    }
    String statement = "REPLACE INTO stock (user, id, durability, amount) VALUES (" +
          "\"" + user.toLowerCase() + "\"," +
          id + "," +
          durability + "," +
          amount + ");";
    try {
      priceCon.prepareStatement(statement).execute();
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
  }

  /**
   * Retrieves the user's stock count for the specified item.
   * @param user A user's UUID without dashes.
   * @param id An item's ID.
   * @param durability An item's durability.
   * @return The amount of stock the user has.
   */
  public double getStock(String user, int id, int durability) {
    if (dataCon == null) {
      connect();
    }
    String statement = "SELECT amount FROM stock WHERE " +
          "user=\"" + user.toLowerCase() + "\" AND " +
          "id=" + id + " AND " +
          "durability=" + durability + ";";
    try {
      ResultSet rs = priceCon.prepareStatement(statement).executeQuery();
      if (rs.next()) {
        return rs.getDouble("amount");
      }
    } catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    return 0;
  }

  /**
   * Retrieves the stock count for the specified item.
   *
   * @param id         An item's ID.
   * @param durability An item's durability.
   * @return The amount of stock available.
   */
  public double getStock(int id, int durability) {
    if (dataCon == null) {
      connect();
    }
    String statement = "SELECT amount FROM stock WHERE " +
          "id=" + id + " AND " +
          "durability=" + durability + ";";
    try {
      ResultSet rs = priceCon.prepareStatement(statement).executeQuery();
      if (rs.next()) {
        return rs.getDouble("amount");
      }
    } catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    return 0;
  }


  /**
   * Retrieves a hashmap of users selling the given item and the amount being sold.
   *
   * @param id         An item's ID.
   * @param durability An item's durability.
   * @return A hashmap of users, amount of item being sold.
   */
  public HashMap<String, Double> getUsersSelling(int id, int durability) {
    if (dataCon == null) {
      connect();
    }
    HashMap<String, Double> data = new HashMap<>();
    String statement = "SELECT user, amount FROM stock WHERE " +
          "id=" + id + " AND " +
          "durability=" + durability + ";";
    try {
      ResultSet rs = priceCon.prepareStatement(statement).executeQuery();
      while (rs.next()) {
        data.put(rs.getString("user"), rs.getDouble("amount"));
      }
    } catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    return data;
  }

  /**
   * Sets the EMC value of the specified item.
   *
   * @param id         An item's ID.
   * @param durability An item's durability.
   * @param value      An item's EMC value.
   */
  public void setEMC(int id, int durability, double value) {
    if (priceCon == null) {
      connect();
    }
    String statement = "REPLACE INTO emc (id, durability, value) VALUES (" +
          id + "," +
          durability + "," +
          value + ");";
    try {
      priceCon.prepareStatement(statement).execute();
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
  }

  /**
   * Sets the price value of the specified item.
   * @param id An item's ID.
   * @param durability An item's durability.
   * @param value An item's price.
   */
  public void setPrice(int id, int durability, double value) {
    if (priceCon == null) {
      connect();
    }
    String statement = "REPLACE INTO price (id, durability, value) VALUES (" +
          id + "," +
          durability + "," +
          value + ");";
    try {
      priceCon.prepareStatement(statement).execute();
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
  }

  /**
   * Retrieves the EMC value of the specified item.
   * @param id An item's ID.
   * @param durability An item's durability.
   * @return The EMC value of the item. 0 if not present.
   */
  public double getEMC(int id, int durability) {
    if (priceCon == null) {
      connect();
    }
    String statement = "SELECT * FROM emc WHERE id=" + id + " AND durability=" + durability + ";";
    try {
      ResultSet rs = priceCon.prepareStatement(statement).executeQuery();
      if (rs.next()) {
        return rs.getDouble("value");
      }
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    return 0;
  }

  /**
   * Retrieves the price value of the specified item.
   * @param id An item's ID.
   * @param durability An item's durability.
   * @return The price of an item. 0 if not present.
   */
  public double getPrice(int id, int durability) {
    if (priceCon == null) {
      connect();
    }
    String statement = "SELECT * FROM price WHERE id=" + id + " AND durability=" + durability + ";";
    try {
      ResultSet rs = priceCon.prepareStatement(statement).executeQuery();
      if (rs.next()) {
        return rs.getDouble("value");
      }
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    return 0;
  }

}
