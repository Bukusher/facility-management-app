package sample;

import java.sql.*;

public class DB_Connector {

    private String url = "jdbc:mysql://den1.mysql6.gear.host:3306/pc2fma2?user=pc2fma2&password=r00tr00t!";

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    void connect() {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.err.print("Connection could not be established");
        }

    }

    public void update(String table, String column, String value) {
        try {
            statement = connection.createStatement();
            int affected = statement.executeUpdate("'UPDATE " + table + " SET " + column + " = " + value + "'");
            System.out.println(affected + " rows where affected");
        } catch (SQLException ex) {
            System.err.print("Update failed");
        }
    }

    public void updateWhere(String table, String column, String value, String condition) {
        try {
            statement = connection.createStatement();
            int affected = statement.executeUpdate("UPDATE " + table + " SET " + column + " = " + value + " WHERE " + condition + "'");
            System.out.println(affected + " rows where affected");
        } catch (SQLException ex) {
            System.err.print("Update failed");
        }
    }

    public void insert(String table, String columns, String values) {
        try {
            statement = connection.createStatement();
            boolean working = statement.execute("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ")");
            if (working) {
                System.out.println("Inserted successfully");
            } else System.err.print("Error in insert statement");

        } catch (SQLException ex) {
            System.err.print("Update failed");
        } catch (NullPointerException ex) {
            System.err.print("bruh");
        }
    }

    public void executeSQL(String query) {
        try {
            statement = connection.createStatement();
            boolean working = statement.execute(query);
            if (working) {
                System.out.println("Inserted successfully");
            } else System.err.print("Error in insert statement");

        } catch (SQLException ex) {
            System.err.print("Execution of query failed");
        } catch (NullPointerException ex) {
            System.err.print("bruh moment");
        }
    }


}
