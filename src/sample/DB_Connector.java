package sample;


//Make sure to use back ticks ````````` here are some

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

    public void update(String table, String column, String value, String condition, String equals) {
        try {
            connect();
            statement = connection.createStatement();
            int affected = statement.executeUpdate("UPDATE `" + table + "` SET `" + column + "` = '" + value +
                    "' WHERE `" + condition + "` = '" + equals + "'");
            System.out.println(affected + " rows where affected");
        } catch (SQLException ex) {
            System.err.print("Update failed");
        }
    }

    /*public void insert(String table, String columns, String values) {
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
    } Previus method */
    public void insert(String table, String columns, String values) {
        try {
            connect();
            String sql = " insert into " + table + " (" + columns + ") values (" + values + ")";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
            System.out.println("Inserted successfully");

        } catch (SQLException ex) {
            System.err.print("Update failed");
        } catch (NullPointerException ex) {
            System.err.print("null pointer");
        }
    }

    public void executeSQL(String query) {
        try {
            connect();
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException ex) {
            System.err.print("Execution of query failed");
        } catch (NullPointerException ex) {
            System.err.print("null pointer");
        }
    }

    public ResultSet select(String query) {
        try {
            connect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            System.err.print("Execution of query failed");
        } catch (NullPointerException ex) {
            System.err.print("null pointer");
        }
        return resultSet;
    }

    public ResultSet simpleSelect(String column, String table, String condition, String equals) {
        try {
            connect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT `" + column + "` FROM `" + table + "` WHERE `" +
                    condition + "` = '" + equals + "'");
        } catch (SQLException ex) {
            System.err.print("Execution of query failed");
        } catch (NullPointerException ex) {
            System.err.print("null pointer");
        }
        return resultSet;
    }
}
