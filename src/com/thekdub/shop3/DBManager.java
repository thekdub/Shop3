package com.thekdub.shop3;

import com.thekdub.shop3.objects.DataQuery;
import com.thekdub.shop3.objects.Transaction;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

public class DBManager {

  Connection dataCon;
  Connection priceCon;

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

  public void createTables() {
    if (dataCon == null || priceCon == null) {
      connect();
    }
    String statement = "CREATE TABLE IF NOT EXISTS transactions (" +
          "buyer TEXT," +
          "seller TEXT," +
          "id INTEGER DEFAULT 0," +
          "durability INTEGER DEFAULT 0," +
          "amount INTEGER DEFAULT 0," +
          "price REAL DEFAULT 0.0," +
          "timestamp INTEGER DEFAULT 0," +
          "PRIMARY KEY(buyer, seller, timestamp));";
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

  public LinkedHashSet<Transaction> getTransactions(DataQuery dataQuery) {
    if (dataCon == null) {
      connect();
    }
    LinkedHashSet<Transaction> transactions = new LinkedHashSet<>();
    String statement = "SELECT * FROM transactions WHERE " + dataQuery + ";";
    try {
      ResultSet rs = dataCon.prepareStatement(statement).executeQuery();
      while (rs.next()) {
        transactions.add(new Transaction(rs.getString("buyer"),
              rs.getString("seller"),
              rs.getInt("id"),
              rs.getInt("durability"),
              rs.getInt("amount"),
              rs.getDouble("price"),
              rs.getLong("timestamp")));
      }
    }
    catch (SQLException e) {
      System.out.println("An error occurred while executing statement > " + statement + "\n" + e.getMessage());
    }
    return transactions;
  }

  public void addEMC(int id, int durability, double value) {
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

  public void addPrice(int id, int durability, double value) {
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
    return -1;
  }

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
    return -1;
  }

}
